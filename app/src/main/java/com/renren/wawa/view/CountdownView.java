package com.renren.wawa.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.renren.wawa.R;
import com.renren.wawa.utils.AppUtil;

public class CountdownView extends View {
    private Drawable mCrawler;
    private Paint mBorderPaint;
    private Paint mPaint;
    private int mBorderWidth;
    private float mPercentage = 1.0f;

    public void setPercentage(float percentage) {
        mPercentage = percentage;
        invalidate();
    }

    public CountdownView(Context context) {
        super(context);

        initialize();
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    public CountdownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize();
    }

    private void initialize() {
        mBorderWidth = AppUtil.dp2px(8);

        mCrawler = getResources().getDrawable(R.drawable.image_crawler_selector);
        // mCrawler = getResources().getDrawable(R.mipmap.game_take);


        mBorderPaint = new Paint();
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setColor(0xFFFFFFFF);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFF94E8FF);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
//        RectF rect = new RectF(mBorderWidth / 2.0f, mBorderWidth / 2.0f, width - mBorderWidth, height - mBorderWidth);
//
//        float sweep = 360 * mPercentage;
//        canvas.drawArc(rect, 0, 360, false, mBorderPaint);
//        canvas.drawArc(rect, 270 - sweep, sweep, false, mPaint);

        mCrawler.setBounds(0, 0, width, height);
        mCrawler.draw(canvas);
    }
}
