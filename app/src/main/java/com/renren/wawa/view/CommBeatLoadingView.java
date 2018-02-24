package com.renren.wawa.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.renren.wawa.R;
import com.renren.wawa.utils.BBLog;

public class CommBeatLoadingView extends FrameLayout implements View.OnClickListener {
    private Context mContext;
    private OnReloadListener onReloadListener;
    private ImageView animImageView;


    public CommBeatLoadingView(Context context) {
        super(context);
        init(context);
    }

    public CommBeatLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommBeatLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context.getApplicationContext();
        LayoutInflater.from(mContext).inflate(R.layout.comm_view_beat_loading, this);

        animImageView = (ImageView) findViewById(R.id.comm_view_beat_loading_img);

        animImageView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);

        setClickable(false);
        setOnClickListener(this);

    }

    private void resetLoading() {
        animImageView.clearAnimation();
        animImageView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);
        setContentText("加载中...");
    }

    public void startLoading() {
        setVisibility(View.VISIBLE);
        findViewById(R.id.comm_view_beat_loading_img).setVisibility(View.VISIBLE);
        resetLoading();
        AnimationDrawable anim = (AnimationDrawable) animImageView.getBackground();
        if (anim != null) {
            anim.start();
        }
        setClickable(false);
    }

    public void endLoading(boolean isGone) {
        animImageView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);
        AnimationDrawable anim = (AnimationDrawable) animImageView.getBackground();
        if (anim != null) {
            anim.stop();
        }
        findViewById(R.id.comm_view_beat_loading_img).setVisibility(View.GONE);
        if (isGone) {
            setVisibility(View.GONE);
            setClickable(false);
        } else {
            setVisibility(View.VISIBLE);
            setClickable(true);
        }
    }

    public void setZeroStaticBackground(int resId, String text) {
        setVisibility(View.VISIBLE);
        animImageView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);
        AnimationDrawable anim = (AnimationDrawable) animImageView.getBackground();
        if (anim != null) {
            anim.stop();
        }
        try {
            findViewById(R.id.comm_view_beat_loading_img).setBackgroundResource(resId);
        } catch (OutOfMemoryError e) {
            BBLog.w("wenba", e);
        } catch (Exception e) {
            BBLog.w("wenba", e);
        }
        findViewById(R.id.comm_view_beat_loading_img).setVisibility(View.VISIBLE);
        TextView tv = (TextView) findViewById(R.id.comm_view_beat_hint_text);
        if (!TextUtils.isEmpty(text)) {
            tv.setText(text);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(View.GONE);
        }
        findViewById(R.id.comm_view_beat_text).setVisibility(View.GONE);
    }

    public void setStaticBackground(int resId, String text) {
        setVisibility(View.VISIBLE);
        animImageView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);
        AnimationDrawable anim = (AnimationDrawable) animImageView.getBackground();
        if (anim != null) {
            anim.stop();
        }
        try {
            findViewById(R.id.comm_view_beat_loading_img).setBackgroundResource(resId);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.comm_view_beat_loading_img).setVisibility(View.VISIBLE);
        TextView textView = (TextView) findViewById(R.id.comm_view_beat_text);
        if (text != null) {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        TextView tv = (TextView) findViewById(R.id.comm_view_beat_hint_text);
        tv.setVisibility(View.GONE);
    }

    public void setStaticBackground(int resId) {
        setStaticBackground(resId, null);
    }

    public boolean isVisibility() {
        return getVisibility() == View.VISIBLE ? true : false;
    }

    public void setContentText(String text) {
        ((TextView) this.findViewById(R.id.comm_view_beat_text)).setText(text);
        findViewById(R.id.comm_view_beat_text).setVisibility(View.VISIBLE);
        findViewById(R.id.comm_view_beat_hint_text).setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
    }

    public void hideAllLoading() {
        findViewById(R.id.comm_view_beat_loading_img).setVisibility(View.GONE);
        findViewById(R.id.comm_view_beat_text).setVisibility(View.GONE);
        findViewById(R.id.comm_view_beat_hint_text).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (onReloadListener != null) {
            onReloadListener.onReload();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onReloadListener = null;
    }

    public interface OnReloadListener {
        void onReload();
    }

    public OnReloadListener getOnReloadListener() {
        return onReloadListener;
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public void setZeroStaticBackgroundClickAble(int locationFail, String text) {
        setClickable(true);
        setZeroStaticBackground(locationFail, text);
    }

}