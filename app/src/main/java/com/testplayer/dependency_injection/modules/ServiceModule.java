package com.testplayer.dependency_injection.modules;

import android.content.Context;
import android.view.WindowManager;

import com.testplayer.player.Player;
import com.testplayer.player.PlayerImpl;
import com.testplayer.hls_downloader.HlsDownloaderImpl;
import com.testplayer.hls_downloader.HlsDownloader;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    public WindowManager provideWindowManager(final Context context){
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    public Player providePlayer(){
        return new PlayerImpl();
    }

    @Provides
    public HlsDownloader provideHlsDownloader(final Context context){
        return new HlsDownloaderImpl(context);
    }
}
