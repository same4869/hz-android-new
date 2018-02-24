package com.renren.wawa.net;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.renren.wawa.R;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.AppConfig;
import com.renren.wawa.model.AppKeysBean;
import com.renren.wawa.model.AppUploadBean;
import com.renren.wawa.model.BaseObject;
import com.renren.wawa.model.CheckOutRefund;
import com.renren.wawa.model.CheckoutShipping;
import com.renren.wawa.model.ConfigResult;
import com.renren.wawa.model.EmailLoginBean;
import com.renren.wawa.model.GameDetail;
import com.renren.wawa.model.GameResult;
import com.renren.wawa.model.GameStartBean;
import com.renren.wawa.model.GameStatusBean;
import com.renren.wawa.model.InvitationCode;
import com.renren.wawa.model.ListInviteBean;
import com.renren.wawa.model.LiveUserResult;
import com.renren.wawa.model.OrderRefundBean;
import com.renren.wawa.model.OrderShippingBean;
import com.renren.wawa.model.OrderShippingFeeBean;
import com.renren.wawa.model.OrderUnboundItemsBean;
import com.renren.wawa.model.PaySettingBean;
import com.renren.wawa.model.PayWeiXinBean;
import com.renren.wawa.model.ProductorOrder;
import com.renren.wawa.model.ReportIssue;
import com.renren.wawa.model.Room;
import com.renren.wawa.model.RoomFreeBean;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.model.RoomListBean;
import com.renren.wawa.model.RoomListResult;
import com.renren.wawa.model.RoomResult;
import com.renren.wawa.model.ScratchRecord;
import com.renren.wawa.model.Servers;
import com.renren.wawa.model.SessionResult;
import com.renren.wawa.model.ShippingFee;
import com.renren.wawa.model.SigninResult;
import com.renren.wawa.model.UserAddressBean;
import com.renren.wawa.model.UserAddressCreate;
import com.renren.wawa.model.UserAddressList;
import com.renren.wawa.model.UserAppliedInvitationCodeBean;
import com.renren.wawa.model.UserGameBean;
import com.renren.wawa.model.UserGameDetailBean;
import com.renren.wawa.model.UserGameIssueBean;
import com.renren.wawa.model.UserInfo;
import com.renren.wawa.model.UserInfoMeProfileBean;
import com.renren.wawa.model.UserInvitationCodeBean;
import com.renren.wawa.model.UserNotificationBean;
import com.renren.wawa.model.UserWallet;
import com.renren.wawa.model.UserWalletBean;
import com.renren.wawa.model.UserWalletFlowBean;
import com.renren.wawa.model.UserWalletTransactions;
import com.renren.wawa.model.VideoConfig;
import com.renren.wawa.model.WalletRechargeSettings;
import com.renren.wawa.model.WalletRechargeWeChat;
import com.renren.wawa.model.WeChatLoginResult;
import com.renren.wawa.model.ZoneChangeBean;
import com.renren.wawa.net.api.AccountApi;
import com.renren.wawa.net.api.AppConfigApi;
import com.renren.wawa.net.api.AppKeysApi;
import com.renren.wawa.net.api.AppUploadApi;
import com.renren.wawa.net.api.ApplyInvitationCodeApi;
import com.renren.wawa.net.api.CheckOutRefundApi;
import com.renren.wawa.net.api.CheckoutShippingApi;
import com.renren.wawa.net.api.EmailLoginApi;
import com.renren.wawa.net.api.FeedbackApi;
import com.renren.wawa.net.api.GameApi;
import com.renren.wawa.net.api.GameDetailApi;
import com.renren.wawa.net.api.GameSessionGetVideoApi;
import com.renren.wawa.net.api.GameStartApi;
import com.renren.wawa.net.api.GameStatusApi;
import com.renren.wawa.net.api.InvitationCodeApi;
import com.renren.wawa.net.api.InvitationListApi;
import com.renren.wawa.net.api.LiveUserApi;
import com.renren.wawa.net.api.OldUserWalletApi;
import com.renren.wawa.net.api.OrderRefundApi;
import com.renren.wawa.net.api.OrderShippingApi;
import com.renren.wawa.net.api.OrderShippingFeeApi;
import com.renren.wawa.net.api.OrderUnboundItemsApi;
import com.renren.wawa.net.api.PaySettingApi;
import com.renren.wawa.net.api.PayWeiXinApi;
import com.renren.wawa.net.api.ProductOrderApi;
import com.renren.wawa.net.api.ReportIssueApi;
import com.renren.wawa.net.api.RoomFreeApi;
import com.renren.wawa.net.api.RoomInfoApi;
import com.renren.wawa.net.api.RoomListApi;
import com.renren.wawa.net.api.ScratchRecordApi;
import com.renren.wawa.net.api.SearchRoomApi;
import com.renren.wawa.net.api.ServersApi;
import com.renren.wawa.net.api.ShippingFeeApi;
import com.renren.wawa.net.api.UserAddressApi;
import com.renren.wawa.net.api.UserAddressCreateApi;
import com.renren.wawa.net.api.UserAddressDeleteApi;
import com.renren.wawa.net.api.UserAddressListApi;
import com.renren.wawa.net.api.UserAddressModifyApi;
import com.renren.wawa.net.api.UserAppliedInvitationCodeApi;
import com.renren.wawa.net.api.UserApplyInvitationCodeApi;
import com.renren.wawa.net.api.UserGameApi;
import com.renren.wawa.net.api.UserGameDetailApi;
import com.renren.wawa.net.api.UserGameIssueApi;
import com.renren.wawa.net.api.UserGetByIdApi;
import com.renren.wawa.net.api.UserInfoApi;
import com.renren.wawa.net.api.UserInfoMeProfileApi;
import com.renren.wawa.net.api.UserInvitationCodeApi;
import com.renren.wawa.net.api.UserNotificationApi;
import com.renren.wawa.net.api.UserQueryGameSessionApi;
import com.renren.wawa.net.api.UserWalletApi;
import com.renren.wawa.net.api.UserWalletFlowApi;
import com.renren.wawa.net.api.UserWalletTransactionsApi;
import com.renren.wawa.net.api.VideoUploadConfigApi;
import com.renren.wawa.net.api.WalletRechargeSettingsApi;
import com.renren.wawa.net.api.WalletRechargeWeChatApi;
import com.renren.wawa.net.api.WeChatLoginApi;
import com.renren.wawa.net.api.WeiXinLoginApi;
import com.renren.wawa.net.api.ZoneChangeApi;
import com.renren.wawa.sp.CommSetting;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.NetWorkUtil;
import com.renren.wawa.utils.ToastUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HttpMethods {
    private Retrofit mClient;
    private GameSessionGetVideoApi gameSessionGetVideoApi;//加载云端视频
    private RoomFreeApi roomFreeApi;// 空闲房间
    private UserNotificationApi userNotificationApi; //消息通知


    /**
     * 消息通知
     *
     * @param next_id
     * @param limit
     * @param subscriber
     */
    public void getUserNotifition(int next_id, int limit, Subscriber<UserNotificationBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        if (next_id != 0) {
            params.put("next_id", next_id);
        }
        params.put("limit", limit);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userNotificationApi = mClient.create(UserNotificationApi.class);
        Observable observable = userNotificationApi.userNotification(body).filter(new HttpResultFunc<UserNotificationBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 空闲房间
     *
     * @param subscriber
     */
    public void getRoomFree(Subscriber<RoomFreeBean> subscriber) {
        roomFreeApi = mClient.create(RoomFreeApi.class);
        subscribe(roomFreeApi.getRoomFree().filter(new HttpResultFunc<RoomFreeBean>()), subscriber);
    }


    /**
     * 加载云端视频接口
     */
    public void getGameSessionGetVideoApi(Subscriber<BaseObject> subscriber, int sessionId) {
        gameSessionGetVideoApi = mClient.create(GameSessionGetVideoApi.class);
        subscribe(gameSessionGetVideoApi.gameSessionGetVideo(sessionId).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //        新的api

    private EmailLoginApi emailLoginApi;//邮箱登录
    private AppKeysApi appKeysApi;//获取 app 配置
    private RoomListApi roomListApi;//获取房间列表
    private RoomInfoApi roomInfoApi;//获取房间信息
    private GameStatusApi gameStatusApi; //获取当前游戏状态
    private GameStartApi gameStartApi; //开始游戏
    private UserWalletApi userWalletApi; //获取当前用户钱包信息
    private UserInfoApi userInfoApi; //获取用户信息
    private UserGameApi userGameApi; //获取用户游戏信息
    private WeiXinLoginApi weiXinLoginApi; // 微信登录
    private UserWalletFlowApi userWalletFlowApi; //获取当前用户钱包流水
    private UserInvitationCodeApi userInvitationCodeApi;//邀请码 + 微信分享
    private UserApplyInvitationCodeApi userApplyInvitationCodeApi; //使用邀请码
    private UserGameDetailApi userGameDetailApi; //游戏详情
    private OrderRefundApi orderRefundApi;//将抓到的娃娃兑换成喵喵币
    private PaySettingApi paySettingApi;//充值设置
    private PayWeiXinApi payWeiXinApi; //微信充值
    private UserAddressApi userAddressApi; // 用户发货地址
    private UserAddressCreateApi userAddressCreateApi; //新建发货地址
    private OrderUnboundItemsApi orderUnboundItemsApi; //获取未申请发货的商品列表
    private OrderShippingFeeApi orderShippingFeeApi; //获取邮费
    private OrderShippingApi orderShippingApi; //创建发货订单
    private AppUploadApi appUploadApi;//上传视屏
    private ZoneChangeApi zoneChangeApi; // 换区
    private UserGameIssueApi userGameIssueApi; //用户上报游戏异常
    private UserAddressModifyApi userAddressModifyApi;//修改地址
    private UserAddressDeleteApi userAddressDeleteApi;//删除地址
    private InvitationListApi invitationListApi; //邀请码列表
    private FeedbackApi feedbackApi; //用户反馈
    private UserAppliedInvitationCodeApi userAppliedInvitationCodeApi;


    /**
     * 创建一个发货地址
     *
     * @param subscriber
     * @param recipentAddress
     * @param recipentName
     * @param recipentMobile
     */
    public void getUserAddressModify(String recipentAddress, String recipentName, String recipentMobile, int addressId, Subscriber<BaseObject> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", addressId);
        params.put("address", recipentAddress);
        params.put("name", recipentName);
        params.put("phoneNo", recipentMobile);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userAddressModifyApi = mClient.create(UserAddressModifyApi.class);
        subscribe(userAddressModifyApi.getUserAddressModify(body).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @param subscriber
     */
    public void getUserAddressDelete(int addressId, Subscriber<BaseObject> subscriber) {
        userAddressDeleteApi = mClient.create(UserAddressDeleteApi.class);
        subscribe(userAddressDeleteApi.getUserAddressDelete(addressId).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }

    /**
     * 用户上报游戏异常
     *
     * @param subscriber
     */
    public void getUserGameIssue(int id, String reason, String remark, Subscriber<UserGameIssueBean> subscriber) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", id);
        hashMap.put("reason", reason);
        hashMap.put("remark", remark);
        userGameIssueApi = mClient.create(UserGameIssueApi.class);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        subscribe(userGameIssueApi.getUserGameIssue(body).filter(new HttpResultFunc<UserGameIssueBean>()), subscriber);
    }

    /**
     * 换区
     *
     * @param subscriber
     */
    public void getZoneChange(Subscriber<ZoneChangeBean> subscriber) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        zoneChangeApi = mClient.create(ZoneChangeApi.class);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        subscribe(zoneChangeApi.getZoneChange(body).filter(new HttpResultFunc<ZoneChangeBean>()), subscriber);
    }

    /**
     * 上传视频
     *
     * @param type       类型 1 日志 2 游戏视频
     * @param gameId
     * @param subscriber
     */
    public void getAppUpload(int type, int gameId, Subscriber<AppUploadBean> subscriber) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("id", gameId);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        appUploadApi = mClient.create(AppUploadApi.class);
        subscribe(appUploadApi.getAppUpload(body).filter(new HttpResultFunc<AppUploadBean>()), subscriber);
    }

    /**
     * 创建一个发货订单
     *
     * @param subscriber
     * @param productOrderItemIds
     * @param addressId
     * @param remark
     */
    public void getOrderShipping(Subscriber<BaseObject> subscriber, String productOrderItemIds, int addressId, String remark) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ids", productOrderItemIds);
        hashMap.put("addressId", addressId);
        if (!TextUtils.isEmpty(remark)) {
            hashMap.put("remark", remark);
        }
        BBLog.d("kkkkkkkk", "getOrderShipping ids --> " + productOrderItemIds);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        orderShippingApi = mClient.create(OrderShippingApi.class);
        subscribe(orderShippingApi.getOrderShipping(body).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }

    /**
     * 计算商品订单项的邮费
     *
     * @param subscriber
     * @param productOrderItemIds
     * @param addressId
     */
    public void getOrderShippingFee(Subscriber<OrderShippingFeeBean> subscriber, String productOrderItemIds, int addressId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ids", productOrderItemIds);
        hashMap.put("address_id", addressId);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        orderShippingFeeApi = mClient.create(OrderShippingFeeApi.class);
        subscribe(orderShippingFeeApi.orderShippingFee(body).filter(new HttpResultFunc<OrderShippingFeeBean>()), subscriber);
    }

    /**
     * 获取用户发货地址
     */
    public void getOrderUnboundItems(int limit, int next_id, Subscriber<OrderUnboundItemsBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        if (next_id != 0) {
            params.put("next_id", next_id);
        }
        params.put("limit", limit);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        orderUnboundItemsApi = mClient.create(OrderUnboundItemsApi.class);
        Observable observable = orderUnboundItemsApi.getOrderUnboundItems(body).filter(new HttpResultFunc<OrderUnboundItemsBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 创建一个发货地址
     *
     * @param subscriber
     * @param recipentAddress
     * @param recipentName
     * @param recipentMobile
     */
    public void getUserAddressCreate(Subscriber<UserAddressCreate> subscriber, String recipentAddress, String recipentName, String recipentMobile) {
        HashMap<String, String> params = new HashMap<>();
        params.put("address", recipentAddress);
        params.put("name", recipentName);
        params.put("phoneNo", recipentMobile);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userAddressCreateApi = mClient.create(UserAddressCreateApi.class);
        subscribe(userAddressCreateApi.userAddressCreate(body).filter(new HttpResultFunc<UserAddressCreate>()), subscriber);
    }

    /**
     * 获取用户发货地址
     */
    public void getUserAddress(int limit, int next_id, Subscriber<UserAddressBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userAddressApi = mClient.create(UserAddressApi.class);
        Observable observable = userAddressApi.getUserAddress(body).filter(new HttpResultFunc<UserAddressBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 用户发起微信充值
     */
    public void getPayWeiXin(Subscriber<PayWeiXinBean> subscriber, int money) {
        payWeiXinApi = mClient.create(PayWeiXinApi.class);
        HashMap<String, Integer> params = new HashMap<>();
        params.put("amount", money);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        subscribe(payWeiXinApi.getPayWeiXin(body).filter(new HttpResultFunc<PayWeiXinBean>()), subscriber);
    }

    /**
     * 获取充值设定
     */
    public void getPaySetting(Subscriber<PaySettingBean> subscriber) {
        paySettingApi = mClient.create(PaySettingApi.class);
        subscribe(paySettingApi.getPaySetting().filter(new HttpResultFunc<PaySettingBean>()), subscriber);
    }

    /**
     * 创建一个兑换喵喵币订单
     *
     * @param subscriber
     * @param productOrderItemIds
     */
    public void getOrderRefund(int productOrderItemIds, Subscriber<OrderRefundBean> subscriber) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("itemId", productOrderItemIds);
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        orderRefundApi = mClient.create(OrderRefundApi.class);
        subscribe(orderRefundApi.getOrderRefund(body).filter(new HttpResultFunc<OrderRefundBean>()), subscriber);
    }

    /**
     * 游戏详情
     */
    public void getUserGameDetail(String id, Subscriber<UserGameDetailBean> subscriber) {
        userGameDetailApi = mClient.create(UserGameDetailApi.class);
        subscribe(userGameDetailApi.getUserGameDetail(id).filter(new HttpResultFunc<UserGameDetailBean>()), subscriber);
    }

    /**
     * 游戏详情
     */
    public void startFeedBack(String mobile, String text, Subscriber<BaseObject> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("text", text);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        feedbackApi = mClient.create(FeedbackApi.class);
        subscribe(feedbackApi.startFeedback(body).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }

    /**
     * 使用邀请码
     */
    public void getUserApplyInvitationCode(String code, Subscriber<BaseObject> subscriber) {
        userApplyInvitationCodeApi = mClient.create(UserApplyInvitationCodeApi.class);
        subscribe(userApplyInvitationCodeApi.getUserApplyInvitationCode(code).filter(new HttpResultFunc<BaseObject>()), subscriber);
    }

    /**
     * 用户已使用的邀请码
     *
     * @param subscriber
     */
    public void getUserAppliedInvitationCode(Subscriber<UserAppliedInvitationCodeBean> subscriber) {
        userAppliedInvitationCodeApi = mClient.create(UserAppliedInvitationCodeApi.class);
        Observable observable = userAppliedInvitationCodeApi.userAppliedInvitationCode().filter(new HttpResultFunc<UserAppliedInvitationCodeBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 邀请码 + 微信分享
     */
    public void getUserInvitationCode(Subscriber<UserInvitationCodeBean> subscriber) {
        userInvitationCodeApi = mClient.create(UserInvitationCodeApi.class);
        subscribe(userInvitationCodeApi.getUserInvitationCode().filter(new HttpResultFunc<UserInvitationCodeBean>()), subscriber);
    }

    /**
     * 获取当前用户钱包流水
     */
    public void getUserWalletFlow(int limit, int next_id, Subscriber<UserWalletFlowBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNo", next_id);
        params.put("pageSize", limit);
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userWalletFlowApi = mClient.create(UserWalletFlowApi.class);
        Observable observable = userWalletFlowApi.getUserWalletFlow(body).filter(new HttpResultFunc<UserWalletFlowBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 微信登录
     *
     * @param code       微信OAuth认证获得的代码
     * @param subscriber
     */
    public void getWeXinLogin(String code, Subscriber<EmailLoginBean> subscriber) {
        weiXinLoginApi = mClient.create(WeiXinLoginApi.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("sign", code);
        params.put("type", "1");
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        Observable observable = weiXinLoginApi.getWeiXinLogin(body).filter(new HttpResultFunc<EmailLoginBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 获取用户游戏信息
     */
    public void getUserGame(long userId, int limit, int next_id, int state, Subscriber<UserGameBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("pageNo", next_id);
        params.put("pageSize", limit);
        if (state != -1) {
            params.put("status", state);
        }

        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userGameApi = mClient.create(UserGameApi.class);
        Observable observable = userGameApi.getUserGame(body).filter(new HttpResultFunc<UserGameBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(long userId, Subscriber<UserInfo> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        if (userId != 0) {
            params.put("user_id", userId);
        }
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        userInfoApi = mClient.create(UserInfoApi.class);
        Observable observable = userInfoApi.getUserInfo(body).filter(new HttpResultFunc<UserInfo>());
        subscribe(observable, subscriber);
    }

    /**
     * 获取当前游戏状态
     */
    public void getUserWallet(Subscriber<UserWalletBean> subscriber) {
        userWalletApi = mClient.create(UserWalletApi.class);
        subscribe(userWalletApi.getUserWallet().filter(new HttpResultFunc<UserWalletBean>()), subscriber);
    }

    /**
     * 邀请码列表
     *
     * @param subscriber
     */
    public void getListInvite(Subscriber<ListInviteBean> subscriber) {
        HashMap<String, Object> params = new HashMap<>();
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        invitationListApi = mClient.create(InvitationListApi.class);
        subscribe(invitationListApi.getInvitationList(body).filter(new HttpResultFunc<ListInviteBean>()), subscriber);
    }

    /**
     * 获取当前游戏状态
     */
    public void getGameStart(String roomId, Subscriber<GameStartBean> subscriber) {
        gameStartApi = mClient.create(GameStartApi.class);
        subscribe(gameStartApi.getGameStart(roomId).filter(new HttpResultFunc<GameStartBean>()), subscriber);
    }

    /**
     * 获取当前游戏状态
     */
    public void getGameStatus(String roomId, Subscriber<GameStatusBean> subscriber) {
        gameStatusApi = mClient.create(GameStatusApi.class);
        subscribe(gameStatusApi.getGameStatus(roomId).filter(new HttpResultFunc<GameStatusBean>()), subscriber);
    }

    /**
     * 获取房间信息
     *
     * @param roomId
     * @param subscriber
     */
    public void getRoomInfo(String roomId, Subscriber<RoomInfoBean> subscriber) {
        roomInfoApi = mClient.create(RoomInfoApi.class);
        subscribe(roomInfoApi.getRoomInfo(roomId).filter(new HttpResultFunc<RoomInfoBean>()), subscriber);
    }

    /**
     * 邮箱登录
     *
     * @param email
     * @param passwd
     * @param subscriber
     */
    public void getEmailLogin(String email, String passwd, Subscriber<EmailLoginBean> subscriber) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", passwd);
        params.put("type", "2");
        RequestBody body = RequestBody.create(null, new JSONObject(params).toString());
        emailLoginApi = mClient.create(EmailLoginApi.class);
        Observable observable = emailLoginApi.getEmailLogin(body).filter(new HttpResultFunc<EmailLoginBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 获取 app 配置
     *
     * @param keys
     * @param subscriber
     */
    public void getAppKeys(List<String> keys, Subscriber<AppKeysBean> subscriber) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("items", keys.toString());
        RequestBody body = RequestBody.create(null, new Gson().toJson(hashMap));
        appKeysApi = mClient.create(AppKeysApi.class);
        Observable observable = appKeysApi.getAppKeys(body).filter(new HttpResultFunc<AppKeysBean>());
        subscribe(observable, subscriber);
    }

    /**
     * 获取房间列表
     */
    public void getRoomList(int limit, int next_id, Subscriber<RoomListBean> subscriber) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageSize", limit);
        hashMap.put("pageNo", next_id);
        RequestBody body = RequestBody.create(null, new JSONObject(hashMap).toString());
        roomListApi = mClient.create(RoomListApi.class);
        subscribe(roomListApi.getRoomList(body).filter(new HttpResultFunc<RoomListBean>()), subscriber);

    }


    public static HttpMethods getInstance() {
        return SingletonHolder.getHttpMethods();
    }

    private HttpMethods(String url) {
        OkHttpClient client;
        if (Constants.isDebug) {
            client = new OkHttpClient
                    .Builder()
                    .addInterceptor(new GlobalInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))//日志拦截器
                    .build();
        } else {
            client = new OkHttpClient
                    .Builder()
                    .addInterceptor(new GlobalInterceptor())
                    .build();
        }

        BBLog.e("url " + url);
        mClient = new Retrofit
                .Builder()
                .baseUrl(url)
                .callFactory(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static class SingletonHolder {
        private static HttpMethods INSTANCE;
        private static String baseUrl = Constants.BASE_URL;

        /**
         * 切换服务器  重新设置URL
         *
         * @param url
         */
        public static void setHttpMethodsUrl(String url) {
            baseUrl = "http://" + url + "/api/v1/";
        }

        public static HttpMethods getHttpMethods() {
            INSTANCE = new HttpMethods(baseUrl);
            return INSTANCE;
        }

    }

    private <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        if (!NetWorkUtil.checkNetWork(WawaApplication.getContext())) {
            ToastUtil.showToast(WawaApplication.getContext().getResources().getString(R.string.error_network));
        }
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private class HttpResultFunc<T extends BaseObject> implements Func1<T, Boolean> {
        @Override
        public Boolean call(T result) {
            Boolean succeed = result.isSucceed();
            if (!succeed) {
                if (result.getCode() == Constants.RESPONSE_STATUS_CODE_NOT_AUTH) {//添加token异常 退出登录
                    BBLog.e("token过期");
                    Intent intent = new Intent(Constants.BROADCAST_LOGOUT);
                    LocalBroadcastManager.getInstance(WawaApplication.getContext()).sendBroadcast(intent);
                } else {
                    ToastUtil.showToastWithHandler(result.getMsg());
                }
            }
            return succeed;
        }
    }

    static final class GlobalInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();

            String token = CommSetting.getToken();
            if (token != null) {
                builder.addHeader("SESSIONID", token);
                BBLog.d("kkkkkkkk", "SESSIONID --> " + token);
            }
            builder.addHeader("WAWA-VERSION", "-1");
            builder.addHeader("WAWA-BUILD", "200");//后面要改  后端没时间临时加的
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("WAWA-PLATEFORM", "android");
            builder.addHeader("WAWA-DEVICEID", AppUtil.getDeviceId());
            builder.addHeader("channel", AppUtil.getChannelByMeta());
            BBLog.d("kkkkkkkk", "channel --> " + AppUtil.getChannelByMeta());
            Request request = builder.build();

            Response response = chain.proceed(request);

            return response;
        }
    }
}
