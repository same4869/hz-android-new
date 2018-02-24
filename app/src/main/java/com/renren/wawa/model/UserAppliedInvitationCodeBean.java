package com.renren.wawa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by xunwang on 2017/11/29.
 */

public class UserAppliedInvitationCodeBean extends BaseObject {

    /**
     * data : {"code":"","description":"填写好友给你的邀请码"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * code :
         * description : 填写好友给你的邀请码
         */

        private String inviteCode;

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }
    }
}
