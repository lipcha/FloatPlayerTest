package com.testplayer.hls_downloader;


import com.testplayer.models.DownloadState;

import io.reactivex.Observable;

public interface HlsDownloader {

    Observable<DownloadState> downloadFile(final String fileUrl, final String outFilePath);
}
