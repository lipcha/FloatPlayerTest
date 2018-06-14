package com.testplayer.models;

import java.util.ArrayList;
import java.util.List;

public class PlayList {

    private List<AudioPlayListItem> audioPlayList = new ArrayList<>();

    public void addPlayListItem(final AudioPlayListItem playListItem){
        audioPlayList.add(playListItem);
    }

    public List<AudioPlayListItem> getAudioPlayList() {
        return audioPlayList;
    }
}
