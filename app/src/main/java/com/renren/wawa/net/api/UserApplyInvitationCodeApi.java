package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * /user/apply-invitation-code使用邀请码
 */

public interface UserApplyInvitationCodeApi {
    @FormUrlEncoded
    @POST("invite")
    Observable<BaseObject> getUserApplyInvitationCode(@Field("inviteCode") String code);
}
