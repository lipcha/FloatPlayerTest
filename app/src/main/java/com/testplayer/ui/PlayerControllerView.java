package com.testplayer.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.testplayer.R;
import com.testplayer.ui.CircleProgress;

public class PlayerControllerView extends FrameLayout{

    private ImageView btnPlay;
    private ImageView btnPause;
    private CircleProgress loadingProgress;

    public PlayerControllerView(@NonNull Context context) {
        super(context);
        init();
    }

    public PlayerControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_player_controller, this, true);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        loadingProgress = findViewById(R.id.loadingProgress);
    }

    public void showProgress(int progress){
        loadingProgress.setVisibility(VISIBLE);
        loadingProgress.setProgress(progress);
        btnPlay.setVisibility(GONE);
        btnPause.setVisibility(GONE);
    }

    public void showPlay(){
        btnPlay.setVisibility(VISIBLE);
        btnPause.setVisibility(GONE);
        loadingProgress.setVisibility(GONE);
    }

    public void showPause(){
        btnPause.setVisibility(VISIBLE);
        btnPlay.setVisibility(GONE);
        loadingProgress.setVisibility(GONE);
    }
}
