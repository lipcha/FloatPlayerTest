package com.testplayer.player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import com.testplayer.models.PlayerState;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class PlayerImpl implements Player, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private final BehaviorSubject<PlayerState> mPlayerCallback;
    private MediaPlayer mMediaPlayer;
    private boolean isPause;

    public PlayerImpl() {
        mPlayerCallback = BehaviorSubject.create();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void play() {
        if (isPause){
            isPause = false;
            mMediaPlayer.start();
            mPlayerCallback.onNext(PlayerState.PLAYING);
            return;
        }

        try {
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }catch (Exception e){
            mPlayerCallback.onNext(PlayerState.ERROR);
        }
        mMediaPlayer.start();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    @Override
    public void setFilePath(String filePath) {
        try {
            mMediaPlayer.setDataSource(filePath);
        } catch (Exception e) {
            mPlayerCallback.onNext(PlayerState.ERROR);
        }
    }

    @Override
    public void pause() {
        isPause = true;
        mMediaPlayer.pause();
        mPlayerCallback.onNext(PlayerState.PAUSED);
    }

    @Override
    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    @Override
    public Observable<PlayerState> getPlayerCallback() {
        return mPlayerCallback;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mPlayerCallback.onNext(PlayerState.PLAYING);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mMediaPlayer.reset();
        mPlayerCallback.onNext(PlayerState.COMPLETED);
    }
}
