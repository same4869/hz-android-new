package com.renren.wawa.net.api;


import com.renren.wawa.model.InvitationCode;

import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取邀请码
 */

public interface InvitationCodeApi {
    @GET("me.invitation_code")
    Observable<InvitationCode> getinvitationCode();
}
