package com.renren.wawa.net.api;

import com.renren.wawa.model.ListInviteBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xunwang on 2017/10/11.
 */

public interface InvitationListApi {
    @POST("user/invitation-list")
    Observable<ListInviteBean> getInvitationList(@Body RequestBody body);
}
