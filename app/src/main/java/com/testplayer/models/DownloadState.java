package com.testplayer.models;

public class DownloadState {

    private boolean inProgress;
    private Throwable error;
    private int progress;
    private String outFilePath;

    public static DownloadState inProgress(long progress, long total){
        final int percentProgress = (int) (progress * 100 / total);
        return new DownloadState(true, percentProgress, null);
    }

    public static DownloadState completed(final String outFilePath){
        return new DownloadState(false, 0, outFilePath);
    }

    public static DownloadState error(final Throwable throwable){
        return new DownloadState(throwable);
    }

    private DownloadState(boolean inProgress, int progress, String outFilePath) {
        this.inProgress = inProgress;
        this.progress = progress;
        this.outFilePath = outFilePath;
    }

    private DownloadState(Throwable error) {
        this.error = error;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public int getProgress() {
        return progress;
    }

    public String getOutFilePath() {
        return outFilePath;
    }
}
