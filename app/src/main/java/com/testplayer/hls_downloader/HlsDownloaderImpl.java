package com.testplayer.hls_downloader;

import android.content.Context;

import com.coolerfall.download.DownloadManager;
import com.coolerfall.download.DownloadRequest;
import com.coolerfall.download.Priority;
import com.testplayer.App;
import com.testplayer.models.DownloadState;
import com.testplayer.models.PlayList;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HlsDownloaderImpl implements HlsDownloader {


    @Inject
    DownloadManager mDownloadManager;

    @Inject
    OkHttpClient mOkHttpClient;

    @Inject
    HlsParser hlsParser;

    private String mMainUrl;

    public HlsDownloaderImpl(final Context context) {
        App.getDownloaderComponent(context).inject(this);
    }

    @Override
    public Observable<DownloadState> downloadFile(String indexUrl, final String outFilePath) {
        mMainUrl = indexUrl.substring(0, indexUrl.lastIndexOf("/") + 1);
        return getIndexRequest(indexUrl)
                .map(response ->  hlsParser.parseHlsIndex(response))
                .map(PlayList::getAudioPlayList)
                .map(track -> HlsHelper.getHighestAudioTrackUrl(track).getUrl())
                .flatMap(audioIndexUrl -> getIndexRequest(mMainUrl + audioIndexUrl))
                .map(response -> hlsParser.parseAudioIndex(response))
                .map(audioTracks -> audioTracks.get(0).getFileUrl())
                .flatMap(fileUrl -> loadAndSaveAudioStream(mMainUrl + fileUrl, outFilePath))
                .onErrorReturn(DownloadState::error);
    }

    private Observable<String> getIndexRequest(final String url){
        return Observable.create(emitter -> {
            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            final Response response = mOkHttpClient.newCall(request).execute();
            if (response.code() != 200 || response.body() == null){
                emitter.onError(new Throwable(response.message()));
                return;
            }
            emitter.onNext(response.body().string());
            emitter.onComplete();
        });
    }

    private Observable<DownloadState> loadAndSaveAudioStream(final String fileUrl, final String outFilePath){
        return Observable.create(emitter -> {
            final DownloadRequest downloadRequest = new DownloadRequest.Builder()
                    .url(fileUrl)
                    .retryTime(2)
                    .retryInterval(2, TimeUnit.SECONDS)
                    .progressInterval(500, TimeUnit.MILLISECONDS)
                    .priority(Priority.HIGH)
                    .allowedNetworkTypes(DownloadRequest.NETWORK_WIFI)
                    .destinationFilePath(outFilePath)
                    .downloadCallback(new RxDownloadCallback(emitter, mDownloadManager))
                    .build();

            mDownloadManager.add(downloadRequest);

        });
    }
}
