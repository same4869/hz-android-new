package com.renren.wawa.net.api;

import com.renren.wawa.model.UserAppliedInvitationCodeBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xunwang on 2017/11/29.
 */

public interface UserAppliedInvitationCodeApi {
    @POST("invited/detail")
    Observable<UserAppliedInvitationCodeBean> userAppliedInvitationCode();
}
