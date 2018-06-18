package com.testplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.testplayer.models.DownloadState;
import com.testplayer.hls_downloader.HlsDownloader;
import com.testplayer.models.PlayerState;
import com.testplayer.player.Player;
import com.testplayer.rx.RxExecutor;
import com.testplayer.ui.FloatViewHelper;
import com.testplayer.ui.PlayerControllerView;
import com.testplayer.utils.FileUtils;

import javax.inject.Inject;

public class PlayerService extends Service implements View.OnClickListener {

    private RxExecutor mRxExecutor;

    @Inject
    WindowManager mWindowManager;

    @Inject
    Player mPlayer;

    @Inject
    HlsDownloader mDownloader;

    private PlayerControllerView mPlayerControllerView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRxExecutor = App.getAppComponent().getRxExecutor();
        App.getServiceComponent().inject(this);
        addFloatingPlayerControllerView();

        mRxExecutor.execute(
                mPlayer.getPlayerCallback(),
                this::handlePlayerState
        );
    }

    private void addFloatingPlayerControllerView() {
        mPlayerControllerView = new PlayerControllerView(this);

        final FloatViewHelper mHelper = new FloatViewHelper(mWindowManager, mPlayerControllerView, this);
        mWindowManager.addView(mPlayerControllerView, mHelper.getParams());

        mPlayerControllerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mPlayer.isPlaying())
            mPlayer.pause();
        else mPlayer.play();
    }

    private void handlePlayerState(PlayerState state){
        switch (state){
            case PLAYING:
                mPlayerControllerView.showPause();
                break;
            case COMPLETED:
                mPlayerControllerView.showPlay();
                break;
            case PAUSED:
                mPlayerControllerView.showPlay();
                break;
            case ERROR:
                loadFile();
                mPlayerControllerView.setOnClickListener(null);
                mPlayerControllerView.showProgress(0);
                break;
        }
    }

    private void loadFile(){
        mRxExecutor.execute(
                mDownloader.downloadFile(Constants.BASE_URL, FileUtils.getOutFilePath(this)),
                this::handleDownloadState
        );
    }

    private void handleDownloadState(DownloadState downloadState){
        if (downloadState.isInProgress()){
            mPlayerControllerView.showProgress(downloadState.getProgress());
            return;
        }

        if (!TextUtils.isEmpty(downloadState.getOutFilePath())){
            mPlayer.setFilePath(downloadState.getOutFilePath());
            mPlayerControllerView.setOnClickListener(this);
            mPlayer.play();
            return;
        }
        if (downloadState.getError() != null){
            mPlayerControllerView.setOnClickListener(this);
            mPlayerControllerView.showPlay();
            Toast.makeText(this, "Load error :  " + downloadState.getError().getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxExecutor.onDestroy();
        mPlayer.release();
        if (mPlayerControllerView != null)
            mWindowManager.removeView(mPlayerControllerView);

    }
}
