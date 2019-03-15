package com.weex.app.util.download;


import android.content.Context;
import android.content.Intent;

/**
 * Created by liulu on 16/10/18
 */
public class ApkDownloadUtils {

    /**
     * @param context
     * @param url
     * @param filePath
     * @param fileSize
     */
    public static void startDownload(Context context, String url, String filePath, long fileSize) {
        Intent i = new Intent(context, DownloadService.class);
        i.putExtra(DownloadService.DOWNLOAD_URL, url);
        i.putExtra(DownloadService.DOWNLOAD_PATH, filePath);
        i.putExtra(DownloadService.DOWNLOAD_SIZE, fileSize);
        context.startService(i);
    }
}
