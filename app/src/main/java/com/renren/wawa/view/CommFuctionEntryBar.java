package com.renren.wawa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wawaji.vip.R;

public class CommFuctionEntryBar extends RelativeLayout implements View.OnClickListener {

    private ImageView ivIcon;
    private TextView tvTitle, tvTitle2;
    private TextView tvRedMessage;
    private View tvRedPoint;
    // 文字右上角小红点
    private ImageView redpointTitleImg;

    private TextView tvHintMessage;
    private ImageView arrowImg;
    private boolean bSwitchMode;
    private OnSwitchChangeListener onSwitchChangeListener;

    public CommFuctionEntryBar(Context context) {
        this(context, null);
    }

    public CommFuctionEntryBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CommFuctionEntryBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private void initView(Context context, AttributeSet attrs) {
        Context mContext = context.getApplicationContext();
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CommFuctionEntryBar);

        int iconRes = a.getResourceId(R.styleable.CommFuctionEntryBar_fuctionIconRes, -1);
        CharSequence titleText = a.getText(R.styleable.CommFuctionEntryBar_fuctionTitle);
        CharSequence titleText2 = a.getText(R.styleable.CommFuctionEntryBar_fuctionTitle2);
        boolean bShowTopLine = a.getBoolean(R.styleable.CommFuctionEntryBar_fuctionShowTopLine, true);
        boolean bShowIconRes = a.getBoolean(R.styleable.CommFuctionEntryBar_fuctionShowIconRes, true);
        boolean bShowArrow = a.getBoolean(R.styleable.CommFuctionEntryBar_fuctionShowArrow, true);
        boolean titleCenter = a.getBoolean(R.styleable.CommFuctionEntryBar_fuctionTitleCenter, false);

        bSwitchMode = a.getBoolean(R.styleable.CommFuctionEntryBar_fuctionSwitch, false);
        // 左内边距
        int paddingLeft = (int) a.getDimension(R.styleable.CommFuctionEntryBar_fuctionPaddingLeft, getResources().getDimension(R.dimen.dp15));
        a.recycle();

        View v = LayoutInflater.from(mContext).inflate(R.layout.comm_view_fuction_entry_bar, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(v, lp);
        ivIcon = (ImageView) findViewById(R.id.comm_fuction_icon);
        LayoutParams params = (LayoutParams) ivIcon.getLayoutParams();
        params.leftMargin = paddingLeft;
        tvTitle = (TextView) findViewById(R.id.comm_fuction_title);
        tvTitle2 = (TextView) findViewById(R.id.comm_fuction_title2);
        tvRedPoint = findViewById(R.id.comm_fuction_redpoint);
        tvRedMessage = (TextView) findViewById(R.id.comm_fuction_redMessage);
        redpointTitleImg = (ImageView) findViewById(R.id.comm_fuction_redpoint_title_img);
        tvHintMessage = (TextView) findViewById(R.id.comm_fuction_hint_messsage);
        arrowImg = (ImageView) findViewById(R.id.comm_fuction_arrow);
        if (bSwitchMode) {
            arrowImg.setImageDrawable(getResources().getDrawable(R.drawable.comm_fuction_switch));
            arrowImg.setOnClickListener(this);
        } else {
            arrowImg.setImageDrawable(getResources().getDrawable(R.mipmap.comm_icon_arrow));
        }

        if (titleCenter) {
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
            params1.addRule(RelativeLayout.CENTER_IN_PARENT);
            tvTitle.setLayoutParams(params1);
        }

        ivIcon.setVisibility(bShowIconRes ? View.VISIBLE : View.GONE);
        arrowImg.setVisibility(bShowArrow ? View.VISIBLE : View.GONE);

        if (iconRes != -1) {
            ivIcon.setImageResource(iconRes);
        }

        if (titleText != null) {
            tvTitle.setText(titleText);
        }

        if (titleText2 != null) {
            tvTitle2.setText(titleText2);
        }
        if (bShowTopLine) {
            setSelected(true);
        }
    }

    public void setIcon(Drawable drawable) {
        ivIcon.setImageDrawable(drawable);
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public CharSequence getTitle() {
        return tvTitle.getText();
    }

    @Override
    public void dispatchWindowVisibilityChanged(int visibility) {
        super.dispatchWindowVisibilityChanged(visibility);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.comm_fuction_arrow) {
            arrowImg.setSelected(!v.isSelected());
            if (onSwitchChangeListener != null) {
                onSwitchChangeListener.onSwitchChange(this, v.isSelected());
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnClickListener(null);
        onSwitchChangeListener = null;
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(View view, boolean on);
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    public void setSwitch(boolean on) {
        arrowImg.setSelected(on);
        if (onSwitchChangeListener != null) {
            onSwitchChangeListener.onSwitchChange(this, on);
        }
    }

}