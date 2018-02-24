package com.renren.wawa.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.renren.wawa.R;
import com.renren.wawa.app.WawaApplication;
import com.squareup.picasso.Picasso;

public class ToastUtil {
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;

    private static Toast getToast(Context context) {
        if (toast != null) {
            return toast;
        }
        if (context == null) {
            return null;
        }
        toast = new Toast(context.getApplicationContext());
        return toast;
    }

    public static void showToast(String msg) {
        Toast.makeText(WawaApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastWithHandler(final String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Context context = WawaApplication.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.comm_tip_message_layout, null);
                TextView textView = (TextView) layout.findViewById(R.id.comm_tip_msg_tv);
                textView.setText(msg);
                // 实例化一个Toast对象
                Toast toast = new Toast(context);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    public static final void showToastInCenter(final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                Context context = WawaApplication.getContext();
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.comm_tip_message_layout, null);
                TextView textView = (TextView) layout.findViewById(R.id.comm_tip_msg_tv);
                textView.setText(text);
                // 实例化一个Toast对象
                Toast toast = new Toast(context);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    public static final void showToastImageText(String text, String url) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(text)) {
            return;
        }
        final View toastRoot = LayoutInflater.from(WawaApplication.getContext()).inflate(R.layout.view_image_text_toast, null);
        TextView message = toastRoot.findViewById(R.id.toast_text);
        ImageView imageView = toastRoot.findViewById(R.id.toast_img);
        if (message != null && imageView != null) {
            message.setText(text + "  抓到了娃娃");
            Picasso.with(WawaApplication.getContext()).load(url).into(imageView);
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast toastStart = getToast(WawaApplication.getContext());
                if (toastStart == null) {
                    return;
                }
                toastStart.setGravity(Gravity.TOP | Gravity.LEFT, (int) ScreenUtil.dip2px(10), (int) ScreenUtil.dip2px(60));
                toastStart.setDuration(Toast.LENGTH_LONG);
                toastStart.setView(toastRoot);
                toastStart.show();
            }
        }, 100);

    }
}
