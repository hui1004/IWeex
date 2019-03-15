package com.weex.app.util.download;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.appset.weex.R;

import java.io.File;


public class UploadUtil {
    File file;
    Notification nf;
    Notification.Builder buider;
    NotificationManager nfm;

    /**
     * @param context
     * @param url
     * @param filePath
     * @param fileSize
     */
    public UploadUtil(Context context, String url, String filePath, long fileSize){
      buider = new Notification.Builder(context);
      nfm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      Intent Intent = new Intent(context, ApkDownReceiver.class);
      Intent.putExtra(DownloadService.DOWNLOAD_URL, url);
      Intent.putExtra(DownloadService.DOWNLOAD_PATH, filePath);
      Intent.putExtra(DownloadService.DOWNLOAD_SIZE, fileSize);
      PendingIntent intent=PendingIntent.getBroadcast(context,0,Intent,PendingIntent.FLAG_UPDATE_CURRENT);
      buider.setContentIntent(intent);
    }
    /**
     * 下面是一个自定义的回调函数，用到回调上传文件是否完成
     *
     * @author shimingzheng
     */
    public interface OnUploadProcessListener {
        /**
         * 上传响应
         *
         * @param responseCode
         * @param message
         */
        void onUploadFail(int responseCode, String message);

        /**
         * 上传响应
         *
         * @param responseCode
         * @param message
         */
        void onUploadDone(int responseCode, String message);
    }

    private static OnUploadProcessListener onUploadProcessListener;

    public void setOnMoreItemClickListener(OnUploadProcessListener onUploadProcessListener) {
        this.onUploadProcessListener = onUploadProcessListener;
    }

    /**
     * 更新
     *
     * @param context
     * @param title
     * @param percent
     */
    @SuppressLint({"NewApi", "StringFormatMatches"})
    public  void notifyChange(Context context, String title, int percent) {
        buider.setOnlyAlertOnce(true);
        buider.setAutoCancel(false);
        buider.setProgress(100, percent, false);
        buider.setContentTitle(context.getResources().getString(
                R.string.download_ing_, title));
        buider.setContentText(context.getString(R.string.download_precent, percent) + "%");
        buider.setSmallIcon(R.drawable.ic_launcher_sales);
        buider.setWhen(System.currentTimeMillis());
        nf = buider.build();
        nfm.notify(1121, nf);
        if (percent == 100) {
            nfm.cancel(1121);
        }
    }
    @SuppressLint({"NewApi", "StringFormatMatches"})
    public  void notifyPasue(Context context,String title,int percent){
        buider.setOnlyAlertOnce(true);
        buider.setAutoCancel(false);
        buider.setProgress(100, percent, false);
        buider.setContentTitle( title);
        buider.setContentText(context.getString(R.string.download_precent, percent) + "%");
        buider.setSmallIcon(R.drawable.app_logo120);
        buider.setWhen(System.currentTimeMillis());
        nf = buider.build();
        nfm.notify(1121, nf);
        if (percent == 100) {
            nfm.cancel(1121);
        }
    }
    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
