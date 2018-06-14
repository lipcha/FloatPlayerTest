package com.testplayer.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatValueHolder;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FloatViewHelper implements View.OnTouchListener {

    private final WindowManager mWindowManager;
    private final View mFloatView;
    private final Context mContext;
    private final GestureDetectorCompat gestureDetectorCompat;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    FlingAnimation flingX;
    FlingAnimation flingY;

    private final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);

    public FloatViewHelper(WindowManager windowManager, View floatView, final Context context) {
        this.mWindowManager = windowManager;
        this.mFloatView = floatView;
        this.mContext = context;
        params.gravity = Gravity.CENTER;
        mFloatView.setOnTouchListener(this);
        gestureDetectorCompat = new GestureDetectorCompat(context, flingListener);

        flingX = new FlingAnimation(mFloatView, DynamicAnimation.TRANSLATION_X);
        flingY = new FlingAnimation(mFloatView, DynamicAnimation.TRANSLATION_Y);
    }

    public WindowManager.LayoutParams getParams() {
        return params;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                initialX = params.x;
                initialY = params.y;

                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return false;
            case MotionEvent.ACTION_MOVE:
                if ((int) (event.getRawX() - initialTouchX) == 0 && (int) (event.getRawY() - initialTouchY) == 0)
                    return true;
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);

                mWindowManager.updateViewLayout(mFloatView, params);
                return false;
        }
        return false;
    }

    private final GestureDetector.SimpleOnGestureListener flingListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//            flingX.setStartVelocity(velocityX)
//                    .setFriction(1.1f)
//                    .start();
//
//            flingY.setStartVelocity(velocityY)
//                    .setFriction(1.1f)
//                    .start();

            return true;
        }
    };
}
