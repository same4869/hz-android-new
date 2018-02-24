package com.renren.wawa.manager;

import com.renren.wawa.config.Constants;
import com.renren.wawa.json.JSONToBeanHandler;
import com.renren.wawa.model.EmailLoginBean;
import com.renren.wawa.sp.PrefsMgr;
import com.renren.wawa.utils.BBLog;

import static com.renren.wawa.sp.PrefsMgr.TABLE_USER;

/**
 * 管理用户登录返回数据
 */
public class UserManager {
	private static final String USER_ID_KEY = "user_id_key";
	private static final String USER_PROFILE_KEY = "user_profile_key";
	private static EmailLoginBean emailLoginBean;

	public synchronized static void saveUserProfile(EmailLoginBean loginBean) {
		if(loginBean == null ){
			return;
		}
		emailLoginBean = (EmailLoginBean) loginBean.clone();
		if (emailLoginBean != null) {
			String userProfileStr = emailLoginBean.toString();
			String key = USER_PROFILE_KEY + UserManager.emailLoginBean.getData().getUserInfo().getId();

			PrefsMgr.putString(TABLE_USER,key,userProfileStr);

			saveCurUserId(UserManager.emailLoginBean.getData().getUserInfo().getId());
		}
	}

	private static void saveCurUserId(long uid) {
		PrefsMgr.putLong(TABLE_USER,USER_ID_KEY,uid);
	}

	public synchronized static EmailLoginBean getEmailLoginBean() {
		if (emailLoginBean != null) {
			return emailLoginBean;
		}
		emailLoginBean = loadUserProfile(getCurUserId());
		if (emailLoginBean != null) {
			return (EmailLoginBean) emailLoginBean.clone();
		}
		return null;
	}


	public synchronized static long getCurUserId() {
		if (emailLoginBean != null) {
			return emailLoginBean.getData().getUserInfo().getId();
		}
		return PrefsMgr.getLong(TABLE_USER,USER_ID_KEY,0);
	}

	public synchronized static String getCurUserNickName() {
		if (emailLoginBean != null) {
			return emailLoginBean.getData().getUserInfo().getNickname();
		} else {
			EmailLoginBean emailLoginBean = getEmailLoginBean();
			if (emailLoginBean != null) {
				return emailLoginBean.getData().getUserInfo().getNickname();
			}
		}
		return null;
	}

	public synchronized static void deleteCurUserInfo() {
		long  uid = getCurUserId();
		PrefsMgr.putLong(TABLE_USER,USER_PROFILE_KEY + uid,0);
		PrefsMgr.putString(TABLE_USER,USER_ID_KEY,null);
		emailLoginBean = null;
	}

	private static EmailLoginBean loadUserProfile(long uid) {
		String data = PrefsMgr.getString(TABLE_USER,USER_PROFILE_KEY + uid,null);
		if (data == null) {
			return null;
		}
		try {
			return JSONToBeanHandler.fromJsonString(data, EmailLoginBean.class);
		} catch (Exception e) {
			BBLog.e(Constants.TAG, e.toString());
		}
		return null;
	}




}
