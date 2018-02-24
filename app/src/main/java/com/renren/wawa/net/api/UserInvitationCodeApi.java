package com.renren.wawa.net.api;

import com.renren.wawa.model.UserInvitationCodeBean;

import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/invitation-code   邀请码 + 微信分享
 */

public interface UserInvitationCodeApi {
    @POST("invite/send")
    Observable<UserInvitationCodeBean> getUserInvitationCode();
}
