package com.renren.wawa.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.renren.wawa.R;
import com.renren.wawa.config.Constants;
import com.renren.wawa.model.OrderUnboundItemsBean;
import com.renren.wawa.utils.BBLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductorOrderAdapter extends BaseQuickAdapter<OrderUnboundItemsBean.DataBean.ProductBean, BaseViewHolder> {


    private Context context;
    private List<Integer> productOrderItemIds = new ArrayList<>();
    private ProductOrderItemIdsListener productOrderItemIdsListener;
    private int defaultId = -1;
    private List<OrderUnboundItemsBean.DataBean.ProductBean> productOrderItemsBeanList = new ArrayList<>();

    public ProductorOrderAdapter(int id, List<OrderUnboundItemsBean.DataBean.ProductBean> data, Context context) {
        super(R.layout.adapter_productor_order, data);
        this.context = context;
        productOrderItemsBeanList = data;
        defaultId = id;
        for (OrderUnboundItemsBean.DataBean.ProductBean productOrderItemsBean : data) {
            if (defaultId == productOrderItemsBean.getId()) {
                productOrderItemsBean.setChecked(true);
                break;
            }
        }
        productOrderItemIds.add(defaultId);
    }


    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final OrderUnboundItemsBean.DataBean.ProductBean productOrderItemsBean) {
        baseViewHolder.setText(R.id.session_nickname, productOrderItemsBean.getName());
        baseViewHolder.setText(R.id.session_count, "1");
        Picasso.with(context).load(productOrderItemsBean.getImgUrl()).into((ImageView) baseViewHolder.getView(R.id.session_avatar));
        for (Integer i : productOrderItemIds) {
            if (getData().get(baseViewHolder.getLayoutPosition()).getId() == i) {
                ((CheckBox) baseViewHolder.getView(R.id.select_check_box)).setChecked(true);
                BBLog.e(Constants.TAG, "checked " + getData().size() + " id " + productOrderItemsBean.getId());
                break;
            } else {
                ((CheckBox) baseViewHolder.getView(R.id.select_check_box)).setChecked(false);
                BBLog.e(Constants.TAG, "un checked " + getData().size() + " id " + productOrderItemsBean.getId());
            }
        }


        ((CheckBox) baseViewHolder.getView(R.id.select_check_box)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (!productOrderItemIds.contains(getData().get(baseViewHolder.getLayoutPosition()).getId())) {
                        productOrderItemIds.add(getData().get(baseViewHolder.getLayoutPosition()).getId());
                    }
                } else if (productOrderItemIds.size() > 0) {
                    productOrderItemIds.remove((Object) getData().get(baseViewHolder.getLayoutPosition()).getId());
                    defaultId = -1;
                }
                BBLog.e(Constants.TAG, "check " + productOrderItemIds.toString() + " id " + productOrderItemsBean.getId());
                productOrderItemIdsListener.productOrderItemIdsListener(productOrderItemIds);

            }
        });

    }


    public void setProductOrderItemIdsListener(ProductOrderItemIdsListener productOrderItemIdsListener) {
        this.productOrderItemIdsListener = productOrderItemIdsListener;
    }

    public interface ProductOrderItemIdsListener {
        void productOrderItemIdsListener(List<Integer> productOrderItemIds);
    }


}
