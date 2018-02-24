package com.renren.wawa.net.api;

import com.renren.wawa.model.BaseObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 使用一个邀请码
 */

public interface ApplyInvitationCodeApi {
    @FormUrlEncoded
    @POST("me.apply_invitation_code")
    Observable<BaseObject> applyIvitationCode(@Field("code") String code);
}
