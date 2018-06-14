package com.testplayer.hls_downloader;

import com.testplayer.models.AudioPlayListItem;
import com.testplayer.models.AudioTrack;

import java.util.List;

public class HlsHelper {

    public static final AudioPlayListItem getHighestAudioTrackUrl(List<AudioPlayListItem> playListItems){
        AudioPlayListItem bestQualityItem = null;
        for (AudioPlayListItem item : playListItems){
            if (bestQualityItem == null || bestQualityItem.getIntQuality() < item.getIntQuality()) {
                bestQualityItem = item;
            }
        }
        return bestQualityItem;
    }
}
