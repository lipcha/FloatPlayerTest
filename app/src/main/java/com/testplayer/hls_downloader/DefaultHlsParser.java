package com.testplayer.hls_downloader;

import com.testplayer.models.AudioPlayListItem;
import com.testplayer.models.AudioTrack;
import com.testplayer.models.PlayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class DefaultHlsParser implements HlsParser {

    @Override
    public PlayList parseHlsIndex(String m3u8String) throws IOException {

        final BufferedReader reader = new BufferedReader(new StringReader(m3u8String));
        String line;
        final PlayList playList = new PlayList();
        while ((line = reader.readLine()) != null){
            if (!line.isEmpty() && line.contains("TYPE=AUDIO")) {
                final String [] params = line.split(",");
                for (String parameter : params){
                    if (parameter.contains("URI=")){
                        final String[] url = parameter.split("=", 2);
                        if (url.length >= 2)
                            playList.addPlayListItem(new AudioPlayListItem(url[1].replace("\"", "")));
                    }
                }
            }


        }
        return playList;
    }

    @Override
    public List<AudioTrack> parseAudioIndex(String m3u8String) throws IOException {
        final BufferedReader reader = new BufferedReader(new StringReader(m3u8String));
        String line;
        final List<AudioTrack> audioTracks = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("#"))
                audioTracks.add(new AudioTrack(line));
        }

        return audioTracks;
    }
}
