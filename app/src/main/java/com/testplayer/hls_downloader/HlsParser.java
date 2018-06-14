package com.testplayer.hls_downloader;

import com.testplayer.models.AudioTrack;
import com.testplayer.models.PlayList;

import java.io.IOException;
import java.util.List;

public interface HlsParser {

    PlayList parseHlsIndex(final String m3u8String) throws IOException;
    List<AudioTrack> parseAudioIndex(final String m3u8String) throws IOException;
}
