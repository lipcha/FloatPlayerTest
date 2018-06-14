package com.testplayer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleProgress extends View{

    private static final int DEFAULT_FINISHED_COLOR = Color.rgb(66, 145, 241);
    private static final int DEFAULT_UNFINISHED_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_MAX = 100;

    private int progress = 0;
    private int max = DEFAULT_MAX;
    private int finishedColor = DEFAULT_FINISHED_COLOR;
    private int unfinishedColor = DEFAULT_UNFINISHED_COLOR;

    private Paint paint = new Paint();
    private RectF rectF = new RectF();


    public CircleProgress(Context context) {
        super(context);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void invalidate() {
        paint.setAntiAlias(true);
        super.invalidate();
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public void setProgress(int progress){
        this.progress = progress;
        if (this.progress > max) {
            this.progress %= max;
        }
        invalidate();
    }

    public int getFinishedColor() {
        return finishedColor;
    }

    public void setFinishedColor(int finishedColor) {
        this.finishedColor = finishedColor;
        this.invalidate();
    }

    public int getUnfinishedColor() {
        return unfinishedColor;
    }

    public void setUnfinishedColor(int unfinishedColor) {
        this.unfinishedColor = unfinishedColor;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        rectF.set(0, 0, MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float yHeight = progress / (float) max * getHeight();
        float radius = getWidth() / 2f;
        float angle = (float) (Math.acos((radius - yHeight) / radius) * 180 / Math.PI);
        float startAngle = 90 + angle;
        float sweepAngle = 360 - angle * 2;
        paint.setColor(getUnfinishedColor());
        canvas.drawArc(rectF, startAngle, sweepAngle, false, paint);

        canvas.save();
        canvas.rotate(180, getWidth() / 2, getHeight() / 2);
        paint.setColor(getFinishedColor());
        canvas.drawArc(rectF, 270 - angle, angle * 2, false, paint);
        canvas.restore();
    }
}
