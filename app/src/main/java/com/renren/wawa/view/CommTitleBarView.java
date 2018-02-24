package com.renren.wawa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.renren.wawa.R;
import com.renren.wawa.utils.AppUtil;

/**
 * TitleBarView 顶部栏
 */

public class CommTitleBarView extends FrameLayout implements View.OnClickListener {
    public static final String COMM_TITLE_BAR_TAG = "comm_titlebar";

    private Context mContext;
    private TextView menuView;
    private TextView menuView2;
    private TextView backView;
    private Drawable menuIconDrawable;
    private Drawable menuIconDrawable2;
    private Drawable backIconDrawable;
    private int menuDrawablePadding = 0;
    private int menuRightDrawablePadding = 0;
    private CharSequence titleText;
    private CharSequence menuText;
    private CharSequence menuText2;
    private CharSequence backText;
    private FrameLayout mCustomViewContainer;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private CommTitleBarListener commTitleBarListener;
    private RelativeLayout rootView;
    private Resources mResources;
    private boolean isHideBack;

    private ImageView titlebarImg;

    public void setCommTitleBarListener(CommTitleBarListener commTitleBarListener) {
        this.commTitleBarListener = commTitleBarListener;
    }

    public interface CommTitleBarListener {
        public void backListener(View v);

        public void menuListener(View v);

        public void menu2Listener(View v);
    }

    public CommTitleBarView(Context context) {
        super(context);
        initView(context, null);
    }

    public CommTitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CommTitleBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mResources = mContext.getResources();
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.CommTitleBarView);

        int backIconRes = a.getResourceId(R.styleable.CommTitleBarView_backIconRes, -1);
        int menuIconRes = a.getResourceId(R.styleable.CommTitleBarView_menuIconRes, -1);
        int menuIconRes2 = a.getResourceId(R.styleable.CommTitleBarView_menuIconRes2, -1);
        titleText = a.getText(R.styleable.CommTitleBarView_titleText);
        menuText = a.getText(R.styleable.CommTitleBarView_menuText);
        menuText2 = a.getText(R.styleable.CommTitleBarView_menuText2);
        int titleBarViewHeight = (int) a.getDimension(R.styleable.CommTitleBarView_titleBarViewHeight, 0);
        a.recycle();

        setTag(COMM_TITLE_BAR_TAG);

        LayoutInflater.from(mContext).inflate(R.layout.comm_view_titlebar, this);
        rootView = (RelativeLayout) findViewById(R.id.comm_titlebar_root);
        rootView.setBackgroundColor(mContext.getResources().getColor(R.color.themeColor));
        if (titleBarViewHeight != 0) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rootView.getLayoutParams();
            layoutParams.height = titleBarViewHeight;
            rootView.setLayoutParams(layoutParams);
        }
        mCustomViewContainer = (FrameLayout) findViewById(R.id.comm_custom_layout);
        backView = (TextView) findViewById(R.id.comm_titlebar_back);
        menuDrawablePadding = AppUtil.dpToPx(mContext, 10);
        menuRightDrawablePadding = AppUtil.dpToPx(mContext, 8);
        menuView = (TextView) findViewById(R.id.comm_collect_titlebar_menu_tv);
        menuView2 = (TextView) findViewById(R.id.comm_titlebar_menu2);
        backView.setOnClickListener(this);
        menuView.setOnClickListener(this);
        menuView2.setOnClickListener(this);
        menuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    menuView.setPressed(false);
                    menuView.performClick();
                } else {
                    menuView.setPressed(true);
                }
                return true;
            }
        });
        menuView2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    menuView2.setPressed(false);
                    menuView2.performClick();
                } else {
                    menuView2.setPressed(true);
                }
                return true;
            }
        });
        if (backIconRes != -1) {
            setBackIcon(backIconRes);
        } else {
            if (!isHideBack) {
                backIconDrawable = mResources.getDrawable(R.drawable.comm_back);
                setBackIcon(backIconDrawable);
            } else {
                hideBackButton();
            }
        }
        setMenuIcon(menuIconRes);
        setMenuIcon2(menuIconRes2);
        if (menuText != null) {
            setMenuText(menuText);
        }
        if (menuText2 != null) {
            setMenuText2(menuText2);
        }

        titlebarImg = findViewById(R.id.comm_titlebar_img);
        mTitleView = (TextView) findViewById(R.id.comm_titlebar_title);
        if (mTitleView != null && titleText != null) {
            mTitleView.setText(titleText);
            titlebarImg.setVisibility(View.INVISIBLE);
        }
        mSubTitleView = (TextView) findViewById(R.id.comm_titlebar_sub_title);
    }

    public void setIsShowBack(boolean isHideBack) {
        this.isHideBack = isHideBack;
    }

    @SuppressWarnings("deprecation")
    public void setBackIcon(int resId) {
        if (resId != -1) {
            Drawable drawable = getResources().getDrawable(resId);
            setBackIcon(drawable);
        }
    }

    public void setBackIcon(Drawable drawable) {
        if (drawable != null) {
            backView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            if (backText != null && !"".equals(backText)) {
                backView.setCompoundDrawablePadding(menuDrawablePadding);
            } else {
                backView.setCompoundDrawablePadding(AppUtil.dpToPx(mContext, 20));
            }
            backView.setVisibility(View.VISIBLE);
        } else {
            backView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            backView.setVisibility(TextUtils.isEmpty(backView.getText().toString()) ? View.GONE : View.VISIBLE);
        }
        backIconDrawable = drawable;
    }

    public void setBackText(CharSequence text) {
        backText = text;
        backView.setText(text);
        if (backText != null && !"".equals(backText) && backIconDrawable != null) {
            backView.setCompoundDrawablePadding(menuDrawablePadding);
        } else {
            backView.setCompoundDrawablePadding(0);
        }
        backView.setPadding(AppUtil.dpToPx(mContext, 20), 0, 0, 0);
        backView.setVisibility(View.VISIBLE);
    }

    public void hideBackButton() {
        backView.setVisibility(View.GONE);
    }

    @SuppressWarnings("deprecation")
    public void setMenuIcon(int resId) {
        if (resId != -1) {
            Drawable drawable = getResources().getDrawable(resId);
            setMenuIcon(drawable);
        }
    }

    @SuppressWarnings("deprecation")
    public void setMenuIcon2(int resId) {
        if (resId != -1) {
            Drawable drawable = getResources().getDrawable(resId);
            setMenuIcon2(drawable);
        }
    }

    public void setMenuIcon(Drawable drawable) {
        if (drawable != null) {
            menuView.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            if (menuText != null && !"".equals(menuText)) {
                menuView.setCompoundDrawablePadding(menuRightDrawablePadding);
            } else {
                menuView.setCompoundDrawablePadding(0);
                menuView.setPadding(0, 0, AppUtil.dpToPx(mContext, 5), 0);
            }
            menuView.setTextSize(14);
            menuView.setVisibility(View.VISIBLE);
        } else {
            menuView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            menuView.setTextSize(16);
            menuView.setVisibility(TextUtils.isEmpty(menuView.getText().toString()) ? View.GONE : View.VISIBLE);
        }
        menuIconDrawable = drawable;
    }

    public void setMenuIcon2(Drawable drawable) {
        if (drawable != null) {
            menuView2.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            if (menuText2 != null && !"".equals(menuText2)) {
                menuView2.setCompoundDrawablePadding(menuDrawablePadding);
            } else {
                menuView2.setCompoundDrawablePadding(0);
            }
            menuView2.setTextSize(14);
            menuView2.setVisibility(View.VISIBLE);
        } else {
            menuView2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            menuView2.setTextSize(16);
            menuView2.setVisibility(TextUtils.isEmpty(menuView2.getText().toString()) ? View.GONE : View.VISIBLE);
        }
        menuIconDrawable2 = drawable;
    }

    public void setMenuText(CharSequence text) {
        menuText = text;
        menuView.setText(text);
        if (menuText != null && !"".equals(menuText) && menuIconDrawable != null) {
            menuView.setCompoundDrawablePadding(menuRightDrawablePadding);
        } else {
            menuView.setCompoundDrawablePadding(0);
        }
        menuView.setVisibility(View.VISIBLE);
    }

    public void setMenuText2(CharSequence text) {
        menuText2 = text;
        menuView2.setText(text);
        if (menuText2 != null && !"".equals(menuText2) && menuIconDrawable2 != null) {
            menuView2.setCompoundDrawablePadding(menuDrawablePadding);
        } else {
            menuView2.setCompoundDrawablePadding(0);
        }
        menuView2.setVisibility(View.VISIBLE);
    }

    public void setMenuVisible(int visible) {
        menuView.setVisibility(visible);
    }

    public void setMenu2Visible(int visible) {
        menuView2.setVisibility(visible);
    }

    public void setTitleBarText(String text) {
        if (mTitleView != null && text != null) {
            mTitleView.setText(text);
            titlebarImg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.comm_titlebar_back) {
            if (commTitleBarListener != null) {
                commTitleBarListener.backListener(v);
            }

        } else if (i == R.id.comm_collect_titlebar_menu_tv) {
            if (commTitleBarListener != null) {
                commTitleBarListener.menuListener(v);
            }

        } else if (i == R.id.comm_titlebar_menu2) {
            if (commTitleBarListener != null) {
                commTitleBarListener.menu2Listener(v);
            }

        }

    }

    public void setCustomView(View view, FrameLayout.LayoutParams layoutParams) {
        if (mCustomViewContainer != null) {
            if (mCustomViewContainer.getChildCount() > 0) {
                mCustomViewContainer.removeAllViews();
            }
            mCustomViewContainer.addView(view, layoutParams);
        }
    }

    public View getRootView() {
        return rootView;
    }

    public void setTitleTextSize(int size) {
        mTitleView.setTextSize(size);
    }

    public void setSubTitle(String subTitle) {
        if (TextUtils.isEmpty(subTitle)) {
            mSubTitleView.setVisibility(View.GONE);
            mTitleView.setTextSize(20);
        } else {
            mTitleView.setTextSize(16);
            mSubTitleView.setVisibility(View.VISIBLE);
            mSubTitleView.setText(subTitle);
        }
    }

    public void setBackgroundColor(int color) {
        rootView.setBackgroundColor(color);
    }

    public TextView getMenuView() {
        return (TextView) findViewById(R.id.comm_collect_titlebar_menu_tv);
    }

    public void setTitlebarImgVisible(int visible) {
        titlebarImg.setVisibility(visible);
        invalidate();
    }
}
