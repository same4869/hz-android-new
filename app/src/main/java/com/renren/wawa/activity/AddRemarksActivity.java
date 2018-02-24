package com.renren.wawa.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.renren.wawa.R;
import com.renren.wawa.base.BaseTitleBarActivity;
import com.renren.wawa.utils.StringUtil;

import butterknife.BindView;

import static com.renren.wawa.activity.RequestSentOutGoodsActivty.AddRemarksData;

/**
 * 添加备注
 */

public class AddRemarksActivity extends BaseTitleBarActivity {
    @BindView(R.id.remarks_edit)
    EditText remarksEdit;
    private String remarksData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_remarks;
    }

    @Override
    public void initData() {
        remarksData = getIntent().getStringExtra(AddRemarksData);
        if(StringUtil.isNotBlank(remarksData)){
            remarksEdit.setText(remarksData);
        }
    }

    @Override
    public void menuListener(View v) {
        super.menuListener(v);
        Intent intent = new Intent();
        intent.putExtra(AddRemarksData,remarksEdit.getText().toString());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}