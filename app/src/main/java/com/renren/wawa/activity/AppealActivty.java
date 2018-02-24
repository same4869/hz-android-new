//package com.renren.wawa.activity;
//
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.renren.wawa.R;
//import com.renren.wawa.adapter.AppealReasonAdapter;
//import com.renren.wawa.base.BaseTitleBarActivity;
//import com.renren.wawa.manager.QiNiuUploadManager;
//import com.renren.wawa.model.AppUploadBean;
//import com.renren.wawa.model.BaseObject;
//import com.renren.wawa.model.UserGameDetailBean;
//import com.renren.wawa.model.UserGameIssueBean;
//import com.renren.wawa.net.HttpMethods;
//import com.renren.wawa.utils.AppUtil;
//import com.renren.wawa.utils.BBLog;
//import com.renren.wawa.utils.FileUtil;
//import com.renren.wawa.utils.StringUtil;
//import com.renren.wawa.utils.ToastUtil;
//import com.renren.wawa.view.CommDialog;
//import com.renren.wawa.view.CommEditDialog;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//import java.io.IOException;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import rx.Subscriber;
//
//import static com.renren.wawa.activity.ScratchVideoActivity.VIDEO_URL;
//
///**
// * 申述
// */
//
//public class AppealActivty extends BaseTitleBarActivity {
//    @BindView(R.id.session_avatar)
//    ImageView sessionAvatar;
//    @BindView(R.id.session_video)
//    ImageButton sessionVideo;
//    @BindView(R.id.appeal_status_txt)
//    TextView appealStatusTxt;
//    @BindView(R.id.upload_video_btn)
//    Button uploadVideoBtn;
//    @BindView(R.id.reason_recycler_view)
//    RecyclerView reasonRecyclerView;
//    @BindView(R.id.scroll_view)
//    ScrollView scrollView;
//
//    private UserGameDetailBean gameDetail;
//    private AppealReasonAdapter appealReasonAdapter;
//    private boolean videoIsOk = false;
//    private Boolean[] booleans;
//    private int currentPosition = -1;
//    private String misreportContent;
//    private int sessionId;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_appeal;
//    }
//
//    @Override
//    public void initData() {
//        init();
//    }
//
//
//    private void init() {
//        scrollView.smoothScrollTo(0, 20);
//        gameDetail = (UserGameDetailBean) getIntent().getSerializableExtra("gameDetailData");
//        if (gameDetail == null || gameDetail.getData().getGame() == null || gameDetail.getData().getWawa() == null) {
//            return;
//        }
//        sessionId = gameDetail.getData().getGame().getId();
//        Picasso.with(this).load(gameDetail.getData().getWawa().getImgUrl().get(0)).into(sessionAvatar);
//        booleans = new Boolean[gameDetail.getData().getReasons().size()];
//        for (int i = 0; i < booleans.length; i++) {
//            booleans[i] = false;
//        }
//        appealReasonAdapter = new AppealReasonAdapter(gameDetail.getData().getReasons());
//        reasonRecyclerView.setAdapter(appealReasonAdapter);
//        reasonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        appealReasonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
//                currentPosition = position;
//                for (int i = 0; i < booleans.length; i++) {
//                    if (i == position) {
//                        booleans[i] = true;
//                    } else {
//                        booleans[i] = false;
//                    }
//                    appealReasonAdapter.updateData(booleans);
//                    appealReasonAdapter.notifyDataSetChanged();
//                }
//
//                String url = appealReasonAdapter.getData().get(position).getUrl();
//                String action = Uri.parse(url).getQueryParameter("action");
//                if (action.equals("misreport")) {
//                    showEditDialog(1);
//                }
//
//            }
//        });
//
//        videoIsInvalid();
//    }
//
//
//    /**
//     * 验证视频是否有效
//     */
//    private void videoIsInvalid() {
//        String videoUrl = gameDetail.getData().getSession().getVideo_url();
//        if (StringUtil.isBlank(videoUrl)) {
//            ToastUtil.showToast(getString(R.string.game_video_url_error));
//            return;
//        }
//        //创建okHttpClient对象
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        final Request request = new Request.Builder()
//                .url(gameDetail.getData().getSession().getVideo_url() + "?avinfo")
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                BBLog.e(e.toString());
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                BBLog.e(response.toString() + " response.message() " + response.message());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if ("OK".equals(response.message())) {
//                            appealStatusTxt.setText(getString(R.string.video_already));
//                            uploadVideoBtn.setVisibility(View.GONE);
//                            sessionVideo.setImageResource(R.mipmap.icon_video);
//                            sessionVideo.setEnabled(true);
//                            videoIsOk = true;
//                        } else {
//                            appealStatusTxt.setText(getString(R.string.appeal_upload_video_tips));
//                            uploadVideoBtn.setVisibility(View.VISIBLE);
//                            sessionVideo.setImageResource(R.mipmap.setting_video_gray_btn);
//                            sessionVideo.setEnabled(false);
//                            videoIsOk = false;
//                        }
//                    }
//                });
//
//            }
//        });
//    }
//
//
//    @OnClick(R.id.session_video)
//    public void playVideo() {
//        if (gameDetail == null && !videoIsOk) {
//            return;
//        }
//        Intent intent = new Intent(this, ScratchVideoActivity.class);
//        intent.putExtra(VIDEO_URL, gameDetail.getData().getSession().getVideo_url());
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.upload_video_btn)
//    public void uploadVideo() {
//        if (gameDetail == null) {
//            return;
//        }
//        String videoUrl = FileUtil.getSaveVideoDirectory() + gameDetail.getData().getSession().getId() + ".mp4";
//        File file = new File(videoUrl);
//        if (file.exists() && FileUtil.getFileSize(file) > 0) {
//            ToastUtil.showToast("已在本地找到视频，正在上传，请稍等");
//            getVideoConfig(gameDetail.getData().getSession().getId());
//        } else {
//            showVideoDialog();
//        }
//    }
//
//    @OnClick(R.id.appeal_submit_btn)
//    public void appealSubmit() {
//        if (currentPosition == -1) {
//            ToastUtil.showToast("请选择上诉原因");
//            return;
//        }
//
//
//        if (gameDetail == null) {
//            return;
//        }
//        String url = gameDetail.getData().getReasons().get(currentPosition).getUrl();
//        String action = Uri.parse(url).getQueryParameter("action");
//        if (action.equals("misreport") && StringUtil.isBlank(misreportContent)) {
//            ToastUtil.showToast("申诉内容不能为空");
//            return;
//        }
//        updateReportIssue(action, gameDetail.getData().getReasons().get(currentPosition).getDescription(), gameDetail.getData().getSession().getId());
//    }
//
//
//    /**
//     * 显示Video弹窗
//     */
//    private void showVideoDialog() {
//        commDialog = new CommDialog(this, "", getString(R.string.no_video_tips), false);
//        commDialog.setCanceledOnTouchOutside(false);
//        commDialog.show();
//        commDialog.setLeftButtonText(getString(R.string.comm_tips_cancel));
//        commDialog.setRightButtonText(getString(R.string.comm_tips_confirm));
//        commDialog.setLeftButtonPositive(false);
//        commDialog.setRightButtonPositive(true);
//        commDialog.setLeftListener(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                commDialog.dismiss();
//            }
//        });
//        commDialog.setRightListener(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                getGameSessionVideo(sessionId);
//                commDialog.dismiss();
//            }
//        });
//    }
//
//
//    /**
//     * 上传视频
//     */
//    private void getVideoConfig(final int sessionId) {
//        //类型 1 日志 2 游戏视频
//        HttpMethods.getInstance().getAppUpload(2, sessionId, new Subscriber<AppUploadBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(AppUploadBean appUploadBean) {
//                if (appUploadBean != null && appUploadBean.isSucceed()) {
//
//                    QiNiuUploadManager qiNiuUploadManager = new QiNiuUploadManager();
//                    qiNiuUploadManager.upLoadVideo(sessionId, appUploadBean.getData().getFile_name(), appUploadBean.getData().getToken());
//                }
//            }
//        });
//    }
//
//    /**
//     * 上传  申诉
//     *
//     * @param reason
//     * @param comment
//     * @param sessionId
//     */
//    private void updateReportIssue(String reason, String comment, int sessionId) {
//        HttpMethods.getInstance().getUserGameIssue(sessionId, reason, comment, new Subscriber<UserGameIssueBean>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(UserGameIssueBean userGameIssueBean) {
//                if (userGameIssueBean.isSucceed()) {
//                    ToastUtil.showToast(userGameIssueBean.getMsg());
//                    setResult(Activity.RESULT_OK);
//                    finish();
//                }
//
//            }
//        });
//    }
//
//    /**
//     * 其他  弹出框
//     *
//     * @param num
//     */
//    private void showEditDialog(int num) {
//        CommDialog commEditDialog = new CommDialog(this, "填写申诉理由", R.layout
//                .edit_dialog,
//                false);
//        commEditDialog.show();
//        final View customView = commEditDialog.getCustomView();
//        final EditText editText = customView.findViewById(R.id.name_et);
//        final ImageView imageView = customView.findViewById(R.id.close_img);
//        editText.setText(misreportContent);
//        commEditDialog.setRightButtonPositive(true);
//        commEditDialog.setCancelable(true);
//        commEditDialog.setRightListener(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (AppUtil.isFastDoubleClick(1000)) {
//                    return;
//                }
//                Editable editable = editText.getText();
//                if (editable.length() == 0) {
//                    ToastUtil.showToast("申诉内容不能为空");
//                    return;
//                } else if (editable.length() < 2) {
//                    ToastUtil.showToast("理由内容不要太短");
//                    return;
//                } else {
//                    misreportContent = editable.toString();
//                    String description = appealReasonAdapter.getData().get(currentPosition).getDescription();
//                    appealReasonAdapter.getData().get(currentPosition).setDescription(description + " " + misreportContent);
//                    appealReasonAdapter.notifyItemChanged(currentPosition);
//                }
//                dialog.dismiss();
//            }
//        });
//        commEditDialog.setLeftListener(new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editText.setText("");
//                imageView.setVisibility(View.INVISIBLE);
//            }
//        });
//        editText.addTextChangedListener(
//                new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                        Editable editable = editText.getText();
//                        int len = editable.length();
//                        if (len > 0) {
//                            if (imageView.getVisibility() != View.VISIBLE) {
//                                imageView.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            imageView.setVisibility(View.INVISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//                    }
//                }
//        );
//    }
//
//    /**
//     * @param sessionId
//     */
//    private void getGameSessionVideo(final int sessionId) {
//        HttpMethods.getInstance().getGameSessionGetVideoApi(new Subscriber<BaseObject>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(BaseObject httpResult) {
//                if (httpResult.isSucceed()) {
//                    getGameDetail(sessionId);
//                }
//            }
//        }, sessionId);
//    }
//
//    /**
//     * 根据 id 获取一个游戏记录详情
//     *
//     * @param id
//     */
//    private void getGameDetail(int id) {
//        HttpMethods.getInstance().getUserGameDetail(String.valueOf(id), new Subscriber<UserGameDetailBean>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                BBLog.e(e.toString());
//            }
//
//            @Override
//            public void onNext(UserGameDetailBean userGameDetailBean) {
//                gameDetail = userGameDetailBean;
//                videoIsInvalid();
//            }
//        });
//    }
//
//}
