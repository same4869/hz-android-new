package com.renren.wawa.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.renren.wawa.R;

/**
 * 圆形图片
 */

@SuppressLint("AppCompatCustomView")
public class CommRoundAngleImageView extends ImageView {
    private Context mContext;
    private float mRadius = 0F;
    private float spaceSize = 0F;

    public CommRoundAngleImageView(Context context) {
        super(context);
    }

    public CommRoundAngleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context.getApplicationContext();
        TypedArray typeArray = mContext.obtainStyledAttributes(attrs, R.styleable.CommRoundAngleImageView);
        mRadius = typeArray.getDimension(R.styleable.CommRoundAngleImageView_drawableRadius, -1);
        spaceSize = typeArray.getDimension(R.styleable.CommRoundAngleImageView_spaceSize, 0);
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        typeArray.recycle();
    }

    public void setSpaceSize(float spaceSize) {
        this.spaceSize = spaceSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRadius == -1) {
            final int width = View.MeasureSpec.getSize(widthMeasureSpec);
            mRadius = (width - spaceSize) / 2.0F;
        }
    }

    public void setRadiusAndSpaceSize(float mRadius, float spaceSize) {
        this.mRadius = mRadius;
        this.spaceSize = spaceSize;
    }

    @Override
    public void draw(Canvas canvas) {

        // 实例化一个和ImageView一样大小的bitmap
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        // 实例化一个canvas，这个canvas对应的内存为上面的bitmap
        Canvas canvas2 = new Canvas(bitmap);
        if (bitmap.isRecycled()) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            canvas2 = new Canvas(bitmap);
        }

        // 将imageView自己绘制到canvas2上，这个导致bitmap里面存放了imageView
        super.draw(canvas2);

        // 利用canvas画一个圆角矩形，这个会修改bitmap的数据
        drawRoundAngle(canvas2);

        // 将裁剪好的bitmap绘制到系统当前canvas上，这样裁剪好的imageview就能显示到屏幕上
        Paint paint = new Paint();
        paint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        bitmap.recycle();
    }

    private void drawRoundAngle(Canvas canvas) {
        Paint maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Path maskPath = new Path();
        maskPath.addRoundRect(new RectF(spaceSize, spaceSize, getWidth() - spaceSize, getHeight() - spaceSize), mRadius, mRadius, Path.Direction.CW);

        // 这是设置了填充模式，非常关键
        maskPath.setFillType(Path.FillType.INVERSE_WINDING);
        canvas.drawPath(maskPath, maskPaint);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
    }
}
