package com.testplayer.player;


import com.testplayer.models.PlayerState;

import io.reactivex.Observable;

public interface Player {

    void setFilePath(final String filePath);
    void play();
    boolean isPlaying();
    void pause();
    void release();

    Observable<PlayerState> getPlayerCallback();
}
