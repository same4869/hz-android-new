package com.renren.wawa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Created by xunwang on 2017/10/8.
 */

public class CommClassicsFooter extends LinearLayout implements RefreshFooter {
    private static final String REFRESH_BOTTOM_PULLUP = "上拉加载更多";
    private static final String REFRESH_BOTTOM_RELEASE = "释放立即加载";
    private static final String REFRESH_BOTTOM_LOADING = "加载中";
    private TextView mBottomText;
    private ImageView mProgressView;
    private ProgressDrawable mProgressDrawable;
    private SpinnerStyle mSpinnerStyle;
    private Runnable restoreRunable;

    public CommClassicsFooter(Context context) {
        super(context);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, (AttributeSet)null, 0);
    }

    public CommClassicsFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, attrs, 0);
    }

    public CommClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        DensityUtil density = new DensityUtil();
        this.setGravity(17);
        this.setMinimumHeight(density.dip2px(60.0F));
        this.mProgressDrawable = new ProgressDrawable();
        this.mProgressDrawable.setColor(Color.parseColor("#FD95BB"));
        this.mProgressView = new ImageView(context);
        this.mProgressView.setImageDrawable(this.mProgressDrawable);
        LinearLayout.LayoutParams lpPathView = new LinearLayout.LayoutParams(density.dip2px(16.0F), density.dip2px(16.0F));
        lpPathView.rightMargin = density.dip2px(10.0F);
        this.addView(this.mProgressView, lpPathView);
        this.mBottomText = new AppCompatTextView(context, attrs, defStyleAttr);
        this.mBottomText.setTextColor(-10066330);
        this.mBottomText.setTextSize(16.0F);
        this.mBottomText.setText(REFRESH_BOTTOM_LOADING);
        this.addView(this.mBottomText, -2, -2);
        if(!this.isInEditMode()) {
            this.mProgressView.setVisibility(GONE);
        }

//        TypedArray ta = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter);
//        this.mSpinnerStyle = SpinnerStyle.values()[ta.getInt(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
//        if(ta.hasValue(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor)) {
//            int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsFooter_srlAccentColor, 0);
//            this.setAccentColor(accentColor);
//        }
//
//        ta.recycle();
    }

    public void onInitialized(RefreshKernel layout, int height, int extendHeight) {
    }

    public void onPullingUp(float percent, int offset, int bottomHeight, int extendHeight) {
    }

    public void onPullReleasing(float percent, int offset, int headHeight, int extendHeight) {
    }

    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        this.mProgressView.setVisibility(VISIBLE);
        this.mProgressDrawable.start();
    }

    public void onFinish(RefreshLayout layout) {
        this.mProgressDrawable.stop();
        this.mProgressView.setVisibility(GONE);
    }

    public void setPrimaryColors(int... colors) {
        if(this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            if(colors.length > 1) {
                this.setBackgroundColor(colors[0]);
                this.mBottomText.setTextColor(colors[1]);
                this.mProgressDrawable.setColor(colors[1]);
            } else if(colors.length > 0) {
                this.setBackgroundColor(colors[0]);
                if(colors[0] == -1) {
                    this.mBottomText.setTextColor(-10066330);
                    this.mProgressDrawable.setColor(-10066330);
                } else {
                    this.mBottomText.setTextColor(-1);
                    this.mProgressDrawable.setColor(-1);
                }
            }
        }

    }

    @NonNull
    public View getView() {
        return this;
    }

    public SpinnerStyle getSpinnerStyle() {
        return this.mSpinnerStyle;
    }

    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch(newState.ordinal()) {
            case 1:
                this.restoreRefreshLayoutBackground();
            case 2:
                this.mBottomText.setText(REFRESH_BOTTOM_LOADING);
                break;
            case 3:
                this.mBottomText.setText(REFRESH_BOTTOM_LOADING);
                break;
            case 4:
                this.mBottomText.setText(REFRESH_BOTTOM_LOADING);
                this.replaceRefreshLayoutBackground(refreshLayout);
        }

    }

    private void restoreRefreshLayoutBackground() {
        if(this.restoreRunable != null) {
            this.restoreRunable.run();
            this.restoreRunable = null;
        }

    }

    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
        if(this.restoreRunable == null && this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            this.restoreRunable = new Runnable() {
                Drawable drawable = refreshLayout.getLayout().getBackground();

                public void run() {
                    refreshLayout.getLayout().setBackgroundDrawable(this.drawable);
                }
            };
            refreshLayout.getLayout().setBackgroundDrawable(this.getBackground());
        }

    }

    public CommClassicsFooter setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    public CommClassicsFooter setAccentColor(int accentColor) {
        this.mBottomText.setTextColor(accentColor);
        this.mProgressDrawable.setColor(accentColor);
        return this;
    }
}
