package com.weex.iweex.util.download;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.weex.iweex.R;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by liulu on 16/10/19
 */
public class DownloadService extends Service {
    private List<String> urls = new ArrayList<String>();
    private final String FILE_DIR = Environment.getExternalStorageDirectory().toString() + "";
    private final int DOWNLOAD_ERROR = 0;
    private final int DOWNLOAD_SUCCESS = 1;
    private final int DOWNLOAD_PROGRESS = 2;
    private final int DOWNLOAD_PASUE=3;
    public static final String DOWNLOAD_URL = "url";
    public static final String DOWNLOAD_PATH = "path";
    public static final String DOWNLOAD_SIZE = "size";
    public static final String IS_PASUE="isPasue";
    public static final String NOTIFY_LARGE_ICON = "large_icon";
    public static final String NOTIFY_SMALL_ICON = "small_icon";
    private String url;
    private String path;
    private long size;
    public static boolean isPasue=false;
    Handler handler;
    UploadUtil downloadUtil;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initHandler();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            url = intent.getStringExtra(DOWNLOAD_URL);
            path = intent.getStringExtra(DOWNLOAD_PATH);
            size = intent.getLongExtra(DOWNLOAD_SIZE, 0);
            downloadUtil=new UploadUtil(DownloadService.this,url,path,size);
                startDownload(url, path, size);
        }

        return super.onStartCommand(intent, flags, startId);
    }
    private void initHandler() {
        handler = new Handler(getMainLooper()) {
            @SuppressLint("StringFormatInvalid")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == DOWNLOAD_PROGRESS || msg.what == DOWNLOAD_SUCCESS) {
                    Bundle b = msg.getData();
                    String file = b.getString("file");
                    long max = b.getLong("max");
                    long current = b.getLong("current");
                    String url = b.getString("url");

                    int percent = (int) (current * 100 / max);
                    downloadUtil.notifyChange(DownloadService.this, getString(R.string.app_name), percent);
                    if (msg.what == DOWNLOAD_SUCCESS && urls.contains(url)) {
                        File filetemp = new File(file);
                        if (filetemp.exists()) {
                            Log.i("lllttt", "DownloadService : " + filetemp.getAbsolutePath());
                            update(filetemp, DownloadService.this.getApplicationContext(),size);
                            Toast.makeText(DownloadService.this, R.string.completed_download, Toast.LENGTH_SHORT).show();
                        }
                        urls.remove(url);
                    }
                } else if (msg.what == DOWNLOAD_ERROR) {
                    Bundle b = msg.getData();
                    String name = b.getString("name");
                    String url = b.getString("url");
                    String file = b.getString("file");
                    String message = b.getString("error_message");
                    Toast.makeText(DownloadService.this, message, Toast.LENGTH_LONG).show();
                    if (urls.contains(url)) {
                        urls.remove(url);
                    }
                    File fileTemp = new File(file);
//                    if (fileTemp != null && fileTemp.exists()) {
//                        fileTemp.delete();
//                    }
                    Toast.makeText(DownloadService.this, String.format(getString(R.string.download_fail), name), Toast.LENGTH_LONG).show();
                }else{
                    if (urls.contains(url)) {
                        urls.remove(url);
                    }
                    Bundle b = msg.getData();
                    long current = b.getLong("current");
                    long max = b.getLong("max");
                    int percent = (int) (current * 100 / max);
                    downloadUtil.notifyPasue(DownloadService.this,"已暂停下载",percent);
                    Toast.makeText(DownloadService.this,"已暂停下载", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    File dir;
    long downloadLength=0; //记录已经下载的文件长度

    /**
     * @param url
     * @param filePath
     * @param size
     */
    private void startDownload(final String url, String filePath, final long size) {
          dir= new File(filePath);
        try {
            //如果文件已经存在，并且文件大小大于等于要下载的文件大小，就将文件删除
            if (dir.exists()&&dir.length()>=size) {
                dir.delete();
            }
            else if(dir.exists()){//如果文件只是已存在，那么获取文件大小
              downloadLength=dir.length();
            }
            dir.createNewFile();//如果文件不存在，创建新文件，文件存在，不作处理
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, R.string.start_download, Toast.LENGTH_SHORT).show();
        if (!TextUtils.isEmpty(url) && !urls.contains(url)) {
            urls.add(url);
        } else {
            return;
        }
        Log.v("下载地址",url);
//        final File fileTemp = new File(dir.getAbsolutePath());
        OkHttpClient okHttpClient = new OkHttpClient();
//        .connectTimeout(120, TimeUnit.SECONDS)
//                .readTimeout(120, TimeUnit.SECONDS)
//                .build();
        /**
         * HTTP请求是有一个Header的，里面有个Range属性是定义下载区域的，它接收的值是一个区间范围，
         * 比如：Range:bytes=0-10000。这样我们就可以按照一定的规则，将一个大文件拆分为若干很小的部分，
         * 然后分批次的下载，每个小块下载完成之后，再合并到文件中；这样即使下载中断了，重新下载时，
         * 也可以通过文件的字节长度来判断下载的起始点，然后重启断点续传的过程，直到最后完成下载过程。10.2
         */
        Request request;
//        if(downloadLength>0){
            String downPoint=String.valueOf(downloadLength);
            request = new Request.Builder().url(url).header("Range",downPoint).build();
//        }else{
//             request = new Request.Builder().url(url).build();
//
//        Log.e("message-----","正在下载......");
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DownloadService.this, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("message-----","正在下载2"+"-code:"+response.code());
                if (response != null && response.code() == 200) {
                    InputStream is = null;
                    Log.e("message-----","正在下载3");
                    FileOutputStream out = null;
                    long total = 0;
                    try {
                        ResponseBody body = response.body();
                        is = body.byteStream();
                        RandomAccessFile savedFile=null;
                        savedFile=new RandomAccessFile(dir,"rw");
                        savedFile.seek(downloadLength);//跳过已经下载的字节
                        total = size;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        long lastTime = System.currentTimeMillis();
                        while ((len = is.read(buf)) != -1&&!isPasue) {//当文件还没有读完，并且不是暂停状态,也就是暂停状态下停止
                            savedFile.write(buf, 0, len);
//                            out.write(buf, 0, len);
                            downloadLength += len;
                            long currentTime = System.currentTimeMillis();
                            if (currentTime - lastTime > 1000) {
                                Message msg = handler.obtainMessage();
                                msg.what = DOWNLOAD_PROGRESS;
                                Bundle b = msg.getData();
                                b.putString("file", dir.getAbsolutePath());
                                b.putLong("max", total);
                                b.putLong("current", downloadLength);
                                b.putString("name", "xinao.apk");
                                handler.sendMessage(msg);
                                lastTime = currentTime;
                            }
                        }
                        Message msg = handler.obtainMessage();
                        if(isPasue){//更新下载通知栏的视图效果
                            msg.what=DOWNLOAD_PASUE;
                            is.close();
                        }else{
                            msg.what = DOWNLOAD_SUCCESS;
                        }
                        Bundle b = msg.getData();
                        b.putString("file", dir.getAbsolutePath());
                        b.putLong("max", total);
                        b.putLong("current", downloadLength);
                        b.putString("url", url);
                        handler.sendMessageDelayed(msg, 1000);
                        savedFile.close();
                    } catch (Exception e) {
                        if (downloadLength == 0 || downloadLength != total) {
                            Message msg = handler.obtainMessage();
                            Bundle b = msg.getData();
                            b.putString("url", url);
                            b.putString("name", "xinao.apk");
                            b.putString("file", dir.getAbsolutePath());
                            b.putString("error_message", "message: " + e.getMessage());
                            Log.e("Error-------",e.getMessage());
                            msg.what = DOWNLOAD_ERROR;
                            handler.sendMessage(msg);
                        }
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }

                }
            }
        });
    }


    /**
     * 安装应用
     */
    public static void update(File apkFile, Context context,long size) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (apkFile != null && apkFile.exists()&&apkFile.length()==size) {
            chmod(apkFile.getAbsolutePath());//授权
            if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
                Uri apkUri = FileProvider.getUriForFile(context, "com.xinao.yunyigc.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(install);
            } else{
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
//                intent.setDataAndType(Uri.fromFile(apkFile),
//                        "application/vnd.android.package-archive");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
        } else {
            Toast.makeText(context, "安装失败,安装文件未找到", Toast.LENGTH_SHORT).show();
        }
    }

    public static int chmod(String pathc){
        String chmodCmd = "chmod 777" + pathc;
        int status=0;
        DataOutputStream dos = null;
        try {
           Process p = Runtime.getRuntime().exec(chmodCmd);
           status=p.waitFor();
           return status;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }
}
