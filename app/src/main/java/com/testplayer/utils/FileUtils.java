package com.testplayer.utils;

import android.content.Context;

import com.testplayer.Constants;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static final String getOutFilePath(final Context context){
        final File baseCacheDirectory = new File(context.getExternalCacheDir().getAbsolutePath());
        if (!baseCacheDirectory.exists())
            baseCacheDirectory.mkdirs();
        try {
            final File tempFile = File.createTempFile(Constants.OUT_FILE_NAME, Constants.OUT_FILE_EXTENSION, context.getCacheDir());
            tempFile.deleteOnExit();
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
