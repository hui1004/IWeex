package com.weex.iweex.util.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.weex.iweex.util.download.DownloadService.isPasue;

/**
 * Created by liuxinye on 2018/1/13.
 */

public class ApkDownReceiver extends BroadcastReceiver{
    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
            isPasue=!isPasue;
            if(isPasue){//改变完状态如果是暂停状态，那么不在通知中做其他操作,下载服务类中可以接收到全局变量pasue的变化
                       //然后在服务中停止下载，并更新下载的通知栏状态
                Log.e("Error-------","是否暂停"+isPasue);
            }else {//改变后是下载状态，那么就启动服务继续下载
                String url=intent.getStringExtra(DownloadService.DOWNLOAD_URL);
                String filePath=intent.getStringExtra(DownloadService.DOWNLOAD_PATH);
                long fileSize=intent.getLongExtra(DownloadService.DOWNLOAD_SIZE,0);
                Intent startService=new Intent(context,DownloadService.class);
                startService.putExtra(DownloadService.DOWNLOAD_URL,url);
                startService.putExtra(DownloadService.DOWNLOAD_PATH,filePath);
                startService.putExtra(DownloadService.DOWNLOAD_SIZE,fileSize);
                context.startService(startService);
            }
    }
}
