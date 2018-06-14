package com.testplayer.hls_downloader;

import com.coolerfall.download.DownloadCallback;
import com.coolerfall.download.DownloadManager;
import com.testplayer.models.DownloadState;

import io.reactivex.ObservableEmitter;

public class RxDownloadCallback extends DownloadCallback {

    private final ObservableEmitter<DownloadState> mEmitter;
    private final DownloadManager mDownloadManager;

    public RxDownloadCallback(ObservableEmitter<DownloadState> emitter, DownloadManager downloadManager) {
        super();
        mEmitter = emitter;
        mDownloadManager = downloadManager;
    }

    @Override
    public void onStart(int downloadId, long totalBytes) {
        mEmitter.onNext(DownloadState.inProgress(0, totalBytes));
    }

    @Override
    public void onRetry(int downloadId) {
        super.onRetry(downloadId);
    }

    @Override
    public void onProgress(int downloadId, long bytesWritten, long totalBytes) {
        if (mEmitter.isDisposed()){
            mDownloadManager.cancelAll();
            return;
        }
        mEmitter.onNext(DownloadState.inProgress(bytesWritten, totalBytes));
    }

    @Override
    public void onSuccess(int downloadId, String filePath) {
        if (mEmitter.isDisposed())
            return;
        mEmitter.onNext(DownloadState.completed(filePath));
        mEmitter.onComplete();
    }

    @Override
    public void onFailure(int downloadId, int statusCode, String errMsg) {
        if (mEmitter.isDisposed())
            return;
        mEmitter.onNext(DownloadState.error(new Throwable(errMsg)));
        mEmitter.onComplete();
    }
}
