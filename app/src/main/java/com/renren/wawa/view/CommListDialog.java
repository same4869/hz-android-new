package com.renren.wawa.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wawaji.vip.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 底部弹出框
 */

public class CommListDialog extends Dialog {
    private List<String> dataList;
    private ListAdapter adapter;
    private ListView listView;
    private TextView cancelTextView;
    private TextView titleTextView;
    private Context mContext;

    private OnListDialogItemClickListener listener;

    public CommListDialog(Context context,String title) {
        super(context, R.style.CommDialogStyle);
        mContext = context.getApplicationContext();

        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.comm_view_list_dialog_root, null);
        setContentView(rootView);
        listView = (ListView) rootView.findViewById(R.id.comm_lv_list_dialog);
        cancelTextView = (TextView) rootView.findViewById(R.id.comm_lv_list_dialog_tv);
        titleTextView = rootView.findViewById(R.id.comm_title_tv);
        titleTextView.setText(title);
        cancelTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setDataList(String[] newData) {
        if (newData == null) {
            return;
        }
        setDataList(Arrays.asList(newData));
    }

    public void setDataList(List<String> newData) {
        if (newData == null) {
            return;
        }
        if (dataList == null) {
            dataList = new ArrayList<String>();
        } else {
            dataList.clear();
        }
        dataList.addAll(newData);

        if (adapter == null) {
            adapter = new ListAdapter();
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // this.listener = null;
    }

    public interface OnListDialogItemClickListener {
        void onItemClick(int position);
    }

    public void setOnListDialogItemClickListener(OnListDialogItemClickListener listener) {
        this.listener = listener;
    }

    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (dataList != null) {
                return dataList.size();
            }
            return 0;
        }

        @Override
        public String getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        private class ViewHolder {
            TextView tvName;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.comm_view_list_dialog_item, null);
                vh.tvName = (TextView) convertView.findViewById(R.id.comm_tv_name);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.tvName.setText(getItem(position));
            vh.tvName.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                    dismiss();
                }
            });
            return convertView;
        }
    }

}