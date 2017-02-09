package com.yhh.normalrefreshlayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by yx on 2017/2/8.
 */

public class CircleProgressBar extends View {

    /*圆弧线宽*/
    private float circleBorderWidth = 20;
    /*内边距*/
    private float circlePadding = 20;
    /*字体大小*/
    private float textSize = 50;
    /*绘制圆周的画笔*/
    private Paint backCirclePaint;
    /*绘制圆周白色分割线的画笔*/
    private Paint linePaint;
    /*绘制文字的画笔*/
    private Paint textPaint;
    /*百分比*/
    private int percent = 0;
    /*渐变圆周颜色数组*/
    private int[] gradientColorArray = new int[]{Color.GREEN, Color.parseColor("#fe751a"), Color.parseColor("#13be23"), Color.GREEN};
    private Paint gradientCirclePaint;

    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(mMeasureWidth, mMeasureHeight), Math.min(mMeasureWidth, mMeasureHeight));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(new RectF(circlePadding * 2, circlePadding * 2,
                getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2), -90, 360, false, backCirclePaint);

        LinearGradient linearGradient = new LinearGradient(circlePadding, circlePadding,
                getMeasuredWidth() - circlePadding,
                getMeasuredHeight() - circlePadding,
                gradientColorArray, null, Shader.TileMode.MIRROR);
        gradientCirclePaint.setShader(linearGradient);
        gradientCirclePaint.setShadowLayer(10, 10, 10, Color.RED);
        canvas.drawArc(
                new RectF(circlePadding * 2, circlePadding * 2,
                        getMeasuredWidth() - circlePadding * 2, getMeasuredHeight() - circlePadding * 2),
                -90, 360, false, gradientCirclePaint);
        //半径
        float radius = (getMeasuredWidth() - circlePadding * 3) / 2;
        //X轴中点坐标
        int centerX = getMeasuredWidth() / 2;

        //3.绘制100份线段，切分空心圆弧
        for (float i = 0; i < 360; i += 3.6) {
            double rad = i * Math.PI / 180;
            float startX = (float) (centerX + (radius - circleBorderWidth) * Math.sin(rad));
            float startY = (float) (centerX + (radius - circleBorderWidth) * Math.cos(rad));

            float stopX = (float) (centerX + radius * Math.sin(rad) + 1);
            float stopY = (float) (centerX + radius * Math.cos(rad) + 1);

            canvas.drawLine(startX, startY, stopX, stopY, linePaint);
        }
        //4.绘制文字
        float textWidth = textPaint.measureText(percent + "%");
        int textHeight = (int) (Math.ceil(textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent) + 2);
        canvas.drawText(percent + "%", centerX - textWidth / 2, centerX + textHeight / 4, textPaint);
    }

    private void init() {
        backCirclePaint = new Paint();
        backCirclePaint.setStyle(Paint.Style.STROKE);
        backCirclePaint.setAntiAlias(true);
        backCirclePaint.setColor(Color.LTGRAY);
        backCirclePaint.setStrokeWidth(circleBorderWidth);
//        backCirclePaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));

        gradientCirclePaint = new Paint();
        gradientCirclePaint.setStyle(Paint.Style.STROKE);
        gradientCirclePaint.setAntiAlias(true);
        gradientCirclePaint.setColor(Color.LTGRAY);
        gradientCirclePaint.setStrokeWidth(circleBorderWidth);

        linePaint = new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setStrokeWidth(5);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
    }

}
