//package com.renren.wawa.adapter;
//
//import android.content.Context;
//import android.net.Uri;
//import android.view.View;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.wawaji.vip.R;
//import com.renren.wawa.model.UserGameDetailBean;
//import com.renren.wawa.utils.StringUtil;
//
//import java.util.List;
//
///**
// * 游戏详情 action
// */
//
//public class GameDetailActionAdapter extends BaseQuickAdapter<UserGameDetailBean.DataBean.ActionBean, BaseViewHolder> {
//
//    private Context context;
//    private ActionsListener actionsListener;
//
//
//    public GameDetailActionAdapter(List<UserGameDetailBean.DataBean.ActionBean> data, Context context) {
//        super(R.layout.adapter_game_detail_action, data);
//        this.context = context;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder baseViewHolder, final UserGameDetailBean.DataBean.ActionBean actionsBean) {
//        baseViewHolder.setText(R.id.description, actionsBean.getDescription());
//        baseViewHolder.setText(R.id.action_btn, actionsBean.getText());
//        baseViewHolder.getView(R.id.action_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = actionsBean.getUrl();
//                if(StringUtil.isBlank(url)){
//                    return;
//                }
//                Uri uri = Uri.parse(url);
//                String  action = uri.getQueryParameter("action");
//                if(actionsListener!=null){
//                    actionsListener.actionsListener(action);
//                }
//
//            }
//        });
//
//    }
//
//
//    public void setActionsListener(ActionsListener actionsListener) {
//        this.actionsListener = actionsListener;
//    }
//
//    public interface ActionsListener {
//        void actionsListener(String action);
//    }
//
//}
