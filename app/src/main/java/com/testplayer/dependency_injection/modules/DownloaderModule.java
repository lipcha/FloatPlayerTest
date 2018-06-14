package com.testplayer.dependency_injection.modules;

import android.content.Context;

import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.OkHttpDownloader;
import com.testplayer.hls_downloader.DefaultHlsParser;
import com.testplayer.hls_downloader.HlsParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class DownloaderModule {

    private final Context mContext;

    public DownloaderModule(Context context) {
        this.mContext = context;
    }

    @Provides
    public DownloadManager provideDownloaderManager(final OkHttpClient okHttpClient){
        return new DownloadManager.Builder().context(mContext)
                .downloader(OkHttpDownloader.create(okHttpClient))
                .threadPoolSize(2)
                .build();
    }

    @Provides
    public HlsParser provideHlsParser(){
        return  new DefaultHlsParser();
    }

    @Provides
    public OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }
}
