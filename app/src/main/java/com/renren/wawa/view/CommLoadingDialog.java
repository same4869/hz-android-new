package com.renren.wawa.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wawaji.vip.R;

public class CommLoadingDialog extends Dialog implements DialogInterface.OnCancelListener, DialogInterface.OnShowListener {

    private View loadingView;
    private TextView messageView;
    private int size = 0;

    public CommLoadingDialog(Context context) {
        super(context, true, null);
        size = context.getResources().getDimensionPixelSize(R.dimen.dp200);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.comm_view_progress_loading);

        setOnCancelListener(this);
        setOnShowListener(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = size;
        lp.height = size;
        lp.gravity = Gravity.CENTER;
        getWindow().setAttributes(lp);

        init();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnCancelListener(null);
        setOnShowListener(null);
    }

    private void init() {
        loadingView = findViewById(R.id.comm_progress_loading_img);
        messageView = (TextView) findViewById(R.id.comm_progress_loading_message);
    }

    public void setContentMessage(String message) {
        messageView.setText(message);
        messageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        loadingView.clearAnimation();
    }

    @Override
    public void onShow(DialogInterface dialog) {
        loadingView.clearAnimation();
        loadingView.setBackgroundResource(R.drawable.comm_loading_dialog_w_anim);
        AnimationDrawable anim = (AnimationDrawable) loadingView.getBackground();
        if (anim != null) {
            anim.start();
        }
    }

}
