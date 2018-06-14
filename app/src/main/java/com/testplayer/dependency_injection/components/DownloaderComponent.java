package com.testplayer.dependency_injection.components;

import com.testplayer.dependency_injection.modules.DownloaderModule;
import com.testplayer.dependency_injection.scope.DownloaderScope;
import com.testplayer.hls_downloader.HlsDownloaderImpl;
import dagger.Component;

@DownloaderScope
@Component(modules = DownloaderModule.class)
public interface DownloaderComponent {

    void inject(HlsDownloaderImpl hlsDownloader);
}
