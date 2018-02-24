package com.renren.wawa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wawaji.vip.R;
import com.renren.wawa.model.RoomInfoBean;
import com.renren.wawa.utils.CircleTransform;
import com.renren.wawa.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameRoomAdapter extends BaseAdapter {
    private static final int SESSION_ITEM = 0;
    private static final int IMAGE_ITEM = 1;
    private static final int HEADER_ITEM = 2;

    private Context mContext;

    private List<String> mImages;
    private List<RoomInfoBean.DataBean.GamesBean> mSessions;
    private RoomInfoBean.DataBean.ProductBean mProduct;

    public GameRoomAdapter(Context context) {
        mContext = context;

        mSessions = new ArrayList<>();
        mImages = new ArrayList<>();
    }

    public void setContent(List<RoomInfoBean.DataBean.GamesBean> sessions, RoomInfoBean.DataBean.ProductBean product) {
        mSessions = sessions;
        mProduct = product;
        mImages = product.getImages();
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == mSessions.size() + 1) {
            return HEADER_ITEM;
        } else if (position <= mSessions.size()) {
            return SESSION_ITEM;
        } else {
            return IMAGE_ITEM;
        }
    }

    @Override
    public int getCount() {
        return mSessions.size() + mImages.size() + 2;
    }

    @Override
    public Object getItem(int i) {
        if (i <= mSessions.size()) {
            return mSessions.get(i - 1);
        } else {
            return mImages.get(i - 2 - mSessions.size());
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == HEADER_ITEM) {
            HeaderViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.section_header, null);
                viewHolder = new HeaderViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (HeaderViewHolder)view.getTag();
            }
            viewHolder.bindView(mContext, getSectionHeader(i));
        } else if (itemViewType == SESSION_ITEM) {
            RoomInfoBean.DataBean.GamesBean session = (RoomInfoBean.DataBean.GamesBean)getItem(i);
            SessionViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.session_item, null);
                viewHolder = new SessionViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (SessionViewHolder)view.getTag();
            }
            viewHolder.bindView(mContext, session);
        } else {
            String imageURL = (String)getItem(i);
            ImageViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.description_item, null);
                viewHolder = new ImageViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ImageViewHolder)view.getTag();
            }
            viewHolder.bindView(mContext, imageURL);
        }
        return view;
    }

    private String getSectionHeader(int position) {
        if (position == 0) {
            return "最近抓中的记录";
        } else {
            return mProduct == null ? "" : mProduct.getName();
        }
    }

    public static class SessionViewHolder {
        @BindView(R.id.session_avatar) ImageView mAvatarView;
        @BindView(R.id.session_nickname) TextView mNickname;
        @BindView(R.id.session_time) TextView mTimeLabel;

        public SessionViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindView(Context context, RoomInfoBean.DataBean.GamesBean session) {
            mNickname.setText(session.getPlayer().getNickname());
            mTimeLabel.setText(session.getCreated_at());
            if(StringUtil.isNotBlank(session.getPlayer().getAvatar())){
                Picasso.with(context).load(session.getPlayer().getAvatar()).transform(new CircleTransform()).into(mAvatarView);
            }else{
                Picasso.with(context).load(R.mipmap.ic_launcher).transform(new CircleTransform()).into(mAvatarView);
            }

        }
    }

    public static class ImageViewHolder {
        @BindView(R.id.description_image) ImageView mImageView;

        public ImageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindView(Context context, String url) {
            Picasso.with(context).load(url).fit().into(mImageView);
        }
    }

    public static class HeaderViewHolder {
        @BindView(R.id.section_title) TextView mTitleView;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindView(Context context, String title) {
            mTitleView.setText(title);
        }
    }
}
