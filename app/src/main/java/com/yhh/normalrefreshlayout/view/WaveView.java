package com.yhh.normalrefreshlayout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yx on 2017/2/8.
 */

public class WaveView extends View {
    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint;

    private float startX = 250;
    private float startY = 250;
    private int percent;

    @Override
    protected void onDraw(Canvas canvas) {

        double degrees = 45.0;
        double radians = Math.toRadians(degrees);

        for (int i = 0;i<10;i++){
            Path path = new Path();
            path.moveTo(startX,startY);
            path.lineTo((float)(startX+100*Math.cos(radians)),startY+50);
            path.lineTo(startX,startY+100);

            path.close();

            canvas.drawPath(path,mPaint);

            startY += 100;

        }
        //以下五行代码属于另外的Demo，效果是渲染成动画
//        if (percent < 100){
//            canvas.drawLine(100,100,percent*10,percent*10,mPaint);
//            percent += 1;
//        }
//        invalidate();
        Log.e("Test","绘制啦");


    }

    private int mWidth;
    private float mRectWidth;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            mWidth = widthSize;
            mRectWidth = (float)(widthSize*0.8);
        }else if (widthMode == MeasureSpec.AT_MOST){
            mWidth = 300;
            mRectWidth = (float) (mWidth * 0.8);
        }
        setMeasuredDimension(widthSize,heightSize);

    }
}
