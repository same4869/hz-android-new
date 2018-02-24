package com.renren.wawa.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.renren.wawa.R;
import com.renren.wawa.activity.CommWebActivity;
import com.renren.wawa.activity.GameRoomActivity;
import com.renren.wawa.adapter.HomeCardNewAdapter;
import com.renren.wawa.app.WawaApplication;
import com.renren.wawa.base.CommonInvokerActivity;
import com.renren.wawa.config.Constants;
import com.renren.wawa.manager.GameManager;
import com.renren.wawa.manager.QiNiuUploadManager;
import com.renren.wawa.model.AppKeysBean;
import com.renren.wawa.model.AppUploadBean;
import com.renren.wawa.model.PaySettingBean;
import com.renren.wawa.model.RoomFreeBean;
import com.renren.wawa.model.RoomListBean;
import com.renren.wawa.model.RoomStatusUpdate;
import com.renren.wawa.model.ZoneChangeBean;
import com.renren.wawa.net.HttpMethods;
import com.renren.wawa.utils.AppUtil;
import com.renren.wawa.utils.BBLog;
import com.renren.wawa.utils.PicassoImageLoader;
import com.renren.wawa.utils.StringUtil;
import com.renren.wawa.utils.ToastUtil;
import com.renren.wawa.view.WeChatRechargeDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.WeakHandler;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.renren.wawa.activity.WawaMainActivity.CHANGE_ZONE;

public class RoomFragment extends BaseFragment implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.room_recycler_view)
    RecyclerView homeRecyclerView;
    @BindView(R.id.room_list_refresh)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.quick_start_layout)
    RelativeLayout quickLayout;

    private final static int UPDATE_ROOM_STATUS = 0;
    private Banner banner;
    private HomeCardNewAdapter homeCardNewAdapter;
    private List<RoomListBean.DataBean.RoomsBean> roomList = new ArrayList<>();
    private ArrayList<Integer> roomIdsList = new ArrayList<>();
    private static boolean loginFlag = false;
    private boolean refreshFlag = false;
    private static int limit = 10;
    private int currentPage = 1;
    private boolean isLoadEnd;

    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case UPDATE_ROOM_STATUS:
                    BBLog.e("UPDATE_ROOM_STATUS");
                    RoomStatusUpdate roomStatusUpdate = (RoomStatusUpdate) message.obj;
                    for (RoomListBean.DataBean.RoomsBean roomsBean : roomList) {
                        if (roomsBean.getId() == roomStatusUpdate.getRoomId()) {
                            roomsBean.setStatus(roomStatusUpdate.getStatus());
                            homeCardNewAdapter.setNewData(roomList);
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @Override
    public int getLayoutId() {
        return R.layout.fragment_room;
    }

    @Override
    public void lazyFetchData() {
        BBLog.d("kkkkkkkk", "lazyFetchData getRoomListData");
        getRoomListData();

        getAppKeys();
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        loginFlag = true;

        homeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeCardNewAdapter = new HomeCardNewAdapter(getActivity(), roomList);
        homeRecyclerView.setAdapter(homeCardNewAdapter);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_header, homeRecyclerView, false);
        homeCardNewAdapter.addHeaderView(headView);
        banner = headView.findViewById(R.id.banner);
        mRefreshLayout.setOnLoadmoreListener(this);
        mRefreshLayout.setOnRefreshListener(this);

        homeCardNewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (AppUtil.isFastDoubleClick(1500)) {
                    return;
                }
                RoomListBean.DataBean.RoomsBean room = homeCardNewAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), GameRoomActivity.class);
                intent.putExtra("room_id", String.valueOf(room.getId()));
                intent.putExtra("room_price", room.getCoin());
                intent.putExtra("room_state", room.getStatus());
//                intent.putIntegerArrayListExtra("room_id_list", roomIdsList);
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.quick_start_layout)
    public void quickStartClick() {
        getRoomFree();
    }

    @Override
    public void onRoomStateUpdated(RoomStatusUpdate roomStatusUpdate) {
        Message msg = new Message();
        msg.what = UPDATE_ROOM_STATUS;
        msg.obj = roomStatusUpdate;
        mHandler.sendMessage(msg);
    }

    /**
     * 空闲房间
     */
    private void getRoomFree() {
        HttpMethods.getInstance().getRoomFree(new Subscriber<RoomFreeBean>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(RoomFreeBean roomFreeBean) {
                if (roomFreeBean != null && roomFreeBean.isSucceed()) {
                    Intent intent = new Intent(getActivity(), GameRoomActivity.class);
                    BBLog.d("kkkkkkkk", "roomFreeBean.getData().getRoom_id() --> " + roomFreeBean.getData().getRoom_id());
                    intent.putExtra("room_id", String.valueOf(roomFreeBean.getData().getRoom_id()));
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取房间列表
     */
    private void getRoomListData() {
        HttpMethods.getInstance().getRoomList(limit, currentPage, new Subscriber<RoomListBean>() {
            @Override
            public void onCompleted() {
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onError(Throwable e) {
                mRefreshLayout.finishRefresh();
            }

            @Override
            public void onNext(RoomListBean roomListBean) {
                if (roomListBean != null && roomListBean.isSucceed()) {
                    if (refreshFlag) {
                        homeCardNewAdapter.setNewData(roomListBean.getData().getRoomList());
                        roomList = homeCardNewAdapter.getData();
//                        roomIdsList = setupRoomIdsList(roomList);
                        homeRecyclerView.scrollToPosition(0);
                    } else {
                        homeCardNewAdapter.addData(roomListBean.getData().getRoomList());
                        roomList = homeCardNewAdapter.getData();
//                        roomIdsList = setupRoomIdsList(roomList);
                    }
                    if (roomListBean.getData().getRoomList().size() == 0) {////数据全部加载完毕
                        BBLog.e(Constants.TAG, "数据全部加载完毕");
                        isLoadEnd = true;
                    } else {
                        BBLog.e(Constants.TAG, "数据还有 " + roomListBean.getData().getRoomList().size());
                        currentPage++;
                    }
                    mRefreshLayout.finishLoadmore();

//                    if (roomListBean.getData().getPage() == null) {////数据全部加载完毕
//                        BBLog.e(Constants.TAG, "数据全部加载完毕");
//                        isLoadEnd = true;
//                    } else {
//                        BBLog.e(Constants.TAG, "数据还有 " + roomListBean.getData().getRoomList().size());
//                        currentPage = roomListBean.getData().getPage().getNext_id();
//                    }
                }
            }
        });
    }

//    private ArrayList<Integer> setupRoomIdsList(List<RoomListBean.DataBean.RoomsBean> roomList) {
//        ArrayList<Integer> roomIdsList = new ArrayList<>();
//        if (roomList == null || roomList.size() < 1) {
//            return roomIdsList;
//        }
//        for (int i = 0; i < roomList.size(); i++) {
//            roomIdsList.add(roomList.get(i).getId());
//        }
//        return roomIdsList;
//    }

    /**
     * 初始化 Banner
     *
     * @param bannersBeanList
     */
    private void initBanner(final List<AppKeysBean.DataBean.BannersBean> bannersBeanList) {
        banner.startAutoPlay();
        banner.setDelayTime(3000);
        List<String> imageList = new ArrayList<>();
        for (AppKeysBean.DataBean.BannersBean bannersBean : bannersBeanList) {
            imageList.add(bannersBean.getImg());
        }
        BBLog.e("imageList " + imageList.toString());
        banner.setImages(imageList).setImageLoader(new PicassoImageLoader()).start();

        if (bannersBeanList.size() == 1) {
            banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtil.isFastDoubleClick(1500)) {
                        return;
                    }
                    String url = bannersBeanList.get(0).getUrl();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    if (url.contains(CommonInvokerActivity.SCHEME_RECHARGE)) {//  判断是否是充值
                        loadChargeList();
                        return;
                    }
                    Intent intent = new Intent(getActivity(), CommWebActivity.class);
                    intent.putExtra(CommWebActivity.COMM_WEB_URL, bannersBeanList.get(0).getUrl());
                    BBLog.e("url " + bannersBeanList.get(0).getUrl());
                    startActivity(intent);
                }
            });
        } else {
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (AppUtil.isFastDoubleClick(1500)) {
                        return;
                    }
                    String url = bannersBeanList.get(position).getUrl();
                    if (TextUtils.isEmpty(url)) {
                        return;
                    }
                    if (url.contains(CommonInvokerActivity.SCHEME_RECHARGE)) {//  判断是否是充值
                        loadChargeList();
                        return;
                    }
                    Intent intent = new Intent(getActivity(), CommWebActivity.class);
                    intent.putExtra(CommWebActivity.COMM_WEB_URL, bannersBeanList.get(position).getUrl());
                    startActivity(intent);
                }
            });
        }


    }

    /**
     * 外部跳转  判断是否登录
     *
     * @return
     */
    public static boolean isLoginFlag() {
        return loginFlag;
    }

    public static void setLoginFlag(boolean loginFlag) {
        RoomFragment.loginFlag = loginFlag;
    }

    /**
     * 上传视频
     */
    private void getVideoConfig(final int sessionId) {
        //类型 1 日志 2 游戏视频
        HttpMethods.getInstance().getAppUpload(2, sessionId, new Subscriber<AppUploadBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AppUploadBean appUploadBean) {
                QiNiuUploadManager qiNiuUploadManager = new QiNiuUploadManager();
                qiNiuUploadManager.upLoadVideo(sessionId, null, appUploadBean.getData().getToken());
            }
        });
    }


    /**
     * 获取 app 配置
     */
    private void getAppKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("\"login_type\"");
        keys.add("\"banners\"");

        HttpMethods.getInstance().getAppKeys(keys, new Subscriber<AppKeysBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AppKeysBean appKeysBean) {
                initBanner(appKeysBean.getData().getBanners());

            }
        });
    }


    /**
     * 充值 列表
     */
    private void loadChargeList() {

        HttpMethods.getInstance().getPaySetting(new Subscriber<PaySettingBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PaySettingBean paySettingBean) {
                if (!WawaApplication.getInstance().getmWxApi().isWXAppInstalled()) {
                    ToastUtil.showToast("您还未安装微信客户端");
                    return;
                }
                WeChatRechargeDialog weChatRechargeDialog = new WeChatRechargeDialog(getActivity(), paySettingBean.getData());
                weChatRechargeDialog.show();
            }
        });
    }

    /**
     * 换区
     */
    private void getZoneChange() {
        showLoadingDialog();
        HttpMethods.getInstance().getZoneChange(new Subscriber<ZoneChangeBean>() {
            @Override
            public void onCompleted() {
                cancelLoadingDialog();
            }

            @Override
            public void onError(Throwable e) {
                BBLog.e(Constants.TAG, e.toString());

                cancelLoadingDialog();
            }

            @Override
            public void onNext(ZoneChangeBean zoneChangeBean) {
                if (zoneChangeBean != null && zoneChangeBean.isSucceed() && StringUtil.isNotBlank(zoneChangeBean.getData().getTcp())) {
//                    HttpMethods.setBaseUrl(zoneChangeBean.getData().getTcp());
                    GameManager.getInstance().connect(zoneChangeBean.getData().getTcp());
                    BBLog.d("kkkkkkkk", "getZoneChange getRoomListData");
                    getRoomListData();
                }
            }
        });
    }

    @Subscribe
    public void onEventMainThread(Message message) {
        BBLog.e("onEventMainThread ", message.toString());
        switch (message.what) {
            case CHANGE_ZONE:
                getZoneChange();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshLayout) {
        if (!isLoadEnd) {
            refreshFlag = false;
            BBLog.d("kkkkkkkk", "onLoadmore getRoomListData");
            getRoomListData();
        } else {
            mRefreshLayout.finishLoadmore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshFlag = true;
        isLoadEnd = false;
        currentPage = 1;
        BBLog.d("kkkkkkkk", "onRefresh getRoomListData");
        getRoomListData();
        getAppKeys();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        roomList.clear();
        roomIdsList.clear();
        refreshFlag = false;
        currentPage = 1;
        isLoadEnd = false;
        BBLog.d("kkkkkkkk", "onDestroyView");
    }
}
