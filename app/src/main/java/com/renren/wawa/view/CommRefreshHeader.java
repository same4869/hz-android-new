package com.renren.wawa.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.internal.pathview.PathsView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CommRefreshHeader extends RelativeLayout implements RefreshHeader {
    public static String REFRESH_HEADER_PULLDOWN = "下拉即可刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    private Date mLastTime;
    private TextView mHeaderText;
    //    private TextView mLastUpdateText;
    private PathsView mArrowView;
    private ImageView mProgressView;
    private ProgressDrawable mProgressDrawable;
    private DateFormat mFormat;
    private SpinnerStyle mSpinnerStyle;
    private Runnable restoreRunable;

    public CommRefreshHeader(Context context) {
        super(context);
        this.mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, (AttributeSet) null, 0);
    }

    public CommRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, attrs, 0);
    }

    public CommRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFormat = new SimpleDateFormat("上次更新 M-d HH:mm", Locale.CHINA);
        this.mSpinnerStyle = SpinnerStyle.Translate;
        this.initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        DensityUtil density = new DensityUtil();
        this.setMinimumHeight(density.dip2px(80.0F));
        this.mProgressDrawable = new ProgressDrawable();
        this.mProgressDrawable.setColor(Color.parseColor("#FD95BB"));
        this.mProgressView = new ImageView(context);
        this.mProgressView.setImageDrawable(this.mProgressDrawable);
        LayoutParams lpProgress = new LayoutParams(density.dip2px(20.0F), density.dip2px(20.0F));
        lpProgress.leftMargin = density.dip2px(80.0F);
        lpProgress.addRule(15);
        lpProgress.addRule(9);
        this.addView(this.mProgressView, lpProgress);
        this.mArrowView = new PathsView(context);
        this.mArrowView.parserColors(Color.parseColor("#FD95BB"));
        this.mArrowView.parserPaths(new String[]{"M20,12l-1.41,-1.41L13,16.17V4h-2v12.17l-5.58,-5.59L4,12l8,8 8,-8z"});
        this.addView(this.mArrowView, lpProgress);
        LinearLayout layout = new LinearLayout(context, attrs, defStyleAttr);
        layout.setGravity(1);
        layout.setOrientation(LinearLayout.VERTICAL);
        this.mHeaderText = new TextView(context);
        this.mHeaderText.setText(REFRESH_HEADER_PULLDOWN);
        this.mHeaderText.setTextColor(Color.parseColor("#999999"));
        this.mHeaderText.setTextSize(16.0F);
//        this.mLastUpdateText = new TextView(context);
//        this.mLastUpdateText.setText(this.mFormat.format(new Date()));
//        this.mLastUpdateText.setTextColor(-8618884);
//        this.mLastUpdateText.setTextSize(12.0F);
        android.widget.LinearLayout.LayoutParams lpHeaderText = new android.widget.LinearLayout.LayoutParams(-2, -2);
        lpHeaderText.leftMargin = density.dip2px(20.0F);
        lpHeaderText.rightMargin = density.dip2px(20.0F);
        layout.addView(this.mHeaderText, lpHeaderText);
//        android.widget.LinearLayout.LayoutParams lpUpdateText = new android.widget.LinearLayout.LayoutParams(-2, -2);
//        layout.addView(this.mLastUpdateText, lpUpdateText);
        LayoutParams lpHeaderLayout = new LayoutParams(-2, -2);
        lpHeaderLayout.addRule(13);
        this.addView(layout, lpHeaderLayout);
        if (this.isInEditMode()) {
            this.mArrowView.setVisibility(GONE);
            this.mHeaderText.setText(REFRESH_HEADER_REFRESHING);
        } else {
            this.mProgressView.setVisibility(GONE);
        }

//        TypedArray ta = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader);
//        this.mSpinnerStyle = SpinnerStyle.values()[ta.getInt(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
//        int primaryColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlPrimaryColor, 0);
//        int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlAccentColor, 0);
//        if (primaryColor != 0) {
//            if (accentColor != 0) {
//                this.setPrimaryColors(new int[]{primaryColor, accentColor});
//            } else {
//                this.setPrimaryColors(new int[]{primaryColor});
//            }
//        } else if (accentColor != 0) {
//            this.setPrimaryColors(new int[]{0, accentColor});
//        }
//
//        ta.recycle();
    }

    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
    }

    public void onPullingDown(float percent, int offset, int headHeight, int extendHeight) {
    }

    public void onReleasing(float percent, int offset, int headHeight, int extendHeight) {
    }

    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        this.mProgressDrawable.start();
    }

    public void onFinish(RefreshLayout layout) {
        this.mProgressDrawable.stop();
    }

    public void setPrimaryColors(int... colors) {
        if (colors.length > 1) {
            this.setBackgroundColor(colors[0]);
            this.mArrowView.parserColors(new int[]{colors[1]});
            this.mHeaderText.setTextColor(colors[1]);
            this.mProgressDrawable.setColor(colors[1]);
//            this.mLastUpdateText.setTextColor(colors[1] & 16777215 | -1728053248);
        } else if (colors.length > 0) {
            this.setBackgroundColor(colors[0]);
            if (colors[0] == -1) {
                this.mArrowView.parserColors(new int[]{-10066330});
                this.mHeaderText.setTextColor(-10066330);
                this.mProgressDrawable.setColor(-10066330);
//                this.mLastUpdateText.setTextColor(-1721342362);
            } else {
                this.mArrowView.parserColors(new int[]{-1});
                this.mHeaderText.setTextColor(-1);
                this.mProgressDrawable.setColor(-1);
//                this.mLastUpdateText.setTextColor(-1426063361);
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
        switch (newState.ordinal()) {
            case 1:
                this.restoreRefreshLayoutBackground();
            case 2:
                this.mHeaderText.setText(REFRESH_HEADER_PULLDOWN);
                this.mArrowView.setVisibility(VISIBLE);
                this.mProgressView.setVisibility(GONE);
                this.mArrowView.animate().rotation(0.0F);
                break;
            case 3:
                this.mHeaderText.setText(REFRESH_HEADER_REFRESHING);
                this.mProgressView.setVisibility(VISIBLE);
                this.mArrowView.setVisibility(GONE);
                break;
            case 4:
                this.mHeaderText.setText(REFRESH_HEADER_RELEASE);
                this.mArrowView.animate().rotation(180.0F);
                this.replaceRefreshLayoutBackground(refreshLayout);
        }

    }

    private void restoreRefreshLayoutBackground() {
        if (this.restoreRunable != null) {
            this.restoreRunable.run();
            this.restoreRunable = null;
        }

    }

    private void replaceRefreshLayoutBackground(final RefreshLayout refreshLayout) {
        if (this.restoreRunable == null && this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            this.restoreRunable = new Runnable() {
                Drawable drawable = refreshLayout.getLayout().getBackground();

                public void run() {
                    refreshLayout.getLayout().setBackgroundDrawable(this.drawable);
                }
            };
            refreshLayout.getLayout().setBackgroundDrawable(this.getBackground());
        }

    }

//    public CommRefreshHeader setLastUpdateTime(Date time) {
//        this.mLastTime = time;
//        this.mLastUpdateText.setText(this.mFormat.format(time));
//        return this;
//    }
//
//    public CommRefreshHeader setTimeFormat(DateFormat format) {
//        this.mFormat = format;
//        this.mLastUpdateText.setText(this.mFormat.format(this.mLastTime));
//        return this;
//    }

    public CommRefreshHeader setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }
}
