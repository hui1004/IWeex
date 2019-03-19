package com.weex.iweex.modle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.baidu.speech.asr.SpeechConstant;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.weex.iweex.WXPageActivity;
import com.weex.iweex.util.MIUIUtils;
import com.weex.iweex.weexAdapter.WXNavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import baidu.speech.baidu_voice.MessageStatusRecogListener;
import baidu.speech.baidu_voice.MyRecognizer;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.weex.iweex.receiver.MyReceiver.badgeCount;


public class UtilsModule extends WXModule{
    protected MyRecognizer myRecognizer;
    /*
     * 。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected boolean enableOffline = false;
    public static int tag=0;
    private int module;
    protected boolean running = false;
    public UtilsModule(){
        tag++;
    }
    /*激光登录*/
    @JSMethod
    public void JPushLogin(String userId){
//        Log.e("Jpush------>",userId);
        JPushInterface.setAlias(mWXSDKInstance.getContext(),400,userId);
    }
    /*清空应用角标数字*/
    @JSMethod
    public void  setNotification(int number) {
        if (!MIUIUtils.isMIUI()) {
            badgeCount = number;
            ShortcutBadger.applyCount(mWXSDKInstance.getContext(), badgeCount); //for 1.1.4+
        }
    }
    /*选择联系人*/
    @JSMethod
    public void turnToContact(int code)
    {
        Intent intentPhone=new Intent(Intent.ACTION_PICK);
        intentPhone.setData(ContactsContract.Contacts.CONTENT_URI);
        WXPageActivity weexActivity=(WXPageActivity)mWXSDKInstance.getContext();
        weexActivity.startActivityForResult(intentPhone,code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((WXPageActivity)mWXSDKInstance.getContext(), new String[]{Manifest.permission.READ_CONTACTS},
                        0);//申请权限
            } else {//拥有当前权限
            }
        } else {

        }
    }
    @JSMethod
    public boolean checkPermissions(Activity context, String[] permissions) {
        boolean granted = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        Toast.makeText(context, "please give me the permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return granted;
    }
    /*打开语音识别*/
    @JSMethod
    public void openvoiceCog(String code, final JSCallback callbackStart, final JSCallback callbackRunning, final JSCallback callbackEnd, final JSCallback callbackResult, final JSCallback callbackError)
    {
//        Toast.makeText(mWXSDKInstance.getContext(),""+tag,Toast.LENGTH_LONG).show();
        if(code.equals("open")){
            Log.e("voiceOpen",code);
            if(myRecognizer!=null){
                myRecognizer.release();
                myRecognizer = null;
            }
            // 1. 确定识别参数
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
            // 该参数=0时开启长语音。 不为0时，如果用户觉得SDK判断句子结束过快或者过慢，可以调节此参数。
            // 即连续xxx ms静音后，SDK认为一句话结束。
            params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT,0);
            //关闭自动断句
            params.put(SpeechConstant.VAD,"touch");

            // 具体的params的值在 测试demo成功后，myRecognizer.start(params);中打印

            // 2. 初始化IRecogListener
            MessageStatusRecogListener listener = new MessageStatusRecogListener(null);
            listener.setOnVoiceResultLisener(new MessageStatusRecogListener.OnVoiceResultLisener() {
                @Override
                public void onVoiceResult(String result) {
                    HashMap map=new HashMap();
                    map.put("data",result);
                    Log.e("onVoiceResult",result);
                    callbackResult.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceError(String result) {
                    HashMap map=new HashMap();
                    map.put("data",result);

                    callbackError.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceEnd() {
                    HashMap map=new HashMap();
                    map.put("data","end");
                    callbackEnd.invokeAndKeepAlive(map);
                    myRecognizer.release();
                }

                @Override
                public void onVoiceStart() {
                    HashMap map=new HashMap();
                    map.put("data","start");
                    callbackStart.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceRunning(String reN) {
                    HashMap map=new HashMap();
                    map.put("data",reN);
                    callbackRunning.invokeAndKeepAlive(map);
                }
            });
            // 3 初始化 MyRecognizer
            myRecognizer = new MyRecognizer(mWXSDKInstance.getContext(), listener);
//        myRecognizer.loadOfflineEngine(params);
            // 4. 启动识别
            myRecognizer.start(params);
            // 日志显示在logcat里，UI界面上是没有的。
            // 5 识别结束了别忘了释放。
        }else if(code.equals("stop")){
            if(myRecognizer!=null){
                Log.e("voiceStop",code);
                myRecognizer.stop();
                return;
            }

        }else if(code.equals("cancel")){
            if(myRecognizer!=null){
                Log.e("cancel",code);
                myRecognizer.cancel();
            }
        }
    }
    public JSCallback permissionCallback;
    public final int VIOCE_PERMISSION_CODE=222222;
    /*申请权限*/
    @JSMethod
    public void requestPermissions(String code, final JSCallback callback)
    {
        boolean granted = true;
        permissionCallback=callback;
        final HashMap map=new HashMap();
        if(code.equals("定位")){
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            };
            ArrayList<String> toApplyList = new ArrayList<String>();
            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(), perm)) {
                    toApplyList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }
            String[] tmpList = new String[toApplyList.size()];
            if (!toApplyList.isEmpty()) {
                ActivityCompat.requestPermissions((WXPageActivity) mWXSDKInstance.getContext(), toApplyList.toArray(tmpList), VIOCE_PERMISSION_CODE);
            }
            map.put("result","100");
            callback.invokeAndKeepAlive(map);
        }else if(code.equals("语音")){
            permissionCallback=callback;
            String[] permissions = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ArrayList<String> toApplyList = new ArrayList<String>();
            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(), perm)) {
                    toApplyList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }
            String[] tmpList = new String[toApplyList.size()];
            if (!toApplyList.isEmpty()) {
                ActivityCompat.requestPermissions((Activity) mWXSDKInstance.getContext(), toApplyList.toArray(tmpList), VIOCE_PERMISSION_CODE);
             }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HashMap map=new HashMap();
        map.put("result","100");
        permissionCallback.invokeAndKeepAlive(map);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @JSMethod
    public void call(final String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((WXPageActivity)mWXSDKInstance.getContext(), new String[]{Manifest.permission.CALL_PHONE},
                            0);//申请权限
                } else {//拥有当前权限
                }
            } else {

            }
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        mWXSDKInstance.getContext().startActivity(intent);
    }
//
    public void deleteFile(File file){
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(file);
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }
    /*设置容器根目录*/
    @JSMethod
    public void setSchema(String appDir){
        String schema=appDir;
        WXNavigator.schema=schema;
    }
    @JSMethod
    public void getPhoneInfo(JSCallback callback) {
        HashMap res=new HashMap();
        res.put("phoneModel",Build.MODEL);
        res.put("osVersion",Build.VERSION.RELEASE);
        res.put("brand",Build.BRAND);
        res.put("osName","Android");
        callback.invoke(res);
    }
    @JSMethod
    public void pushWebApp(String url){
//        Intent intent=new Intent(mWXSDKInstance.getContext(), MyWebApp.class);
//        intent.putExtra("url",url);
//        mWXSDKInstance.getContext().startActivity(intent);
    }
    /**
     * 检查包是否存在
     * @param packname
     * @return
     */
    @JSMethod
    private boolean checkAppIsInstall(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = mWXSDKInstance.getContext().getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
    @JSMethod
    public void pushNativeApp(String packageName,String jsonStr){
        Intent intent = mWXSDKInstance.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.putExtra("data", jsonStr);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mWXSDKInstance.getContext().startActivity(intent);
        }
    }
    @JSMethod
    public void pushNativePage(String url){
//        "appSet://com.xinao.app/utils?type=110"
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mWXSDKInstance.getContext().startActivity(intent);
//        被打开应用中activity配置
//    <intent-filter>
//        <data
//            android:host="com.xinao.app"
//            android:path="/utils"
//            android:scheme="appSet" />
//        <action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.BROWSABLE" />
//    </intent-filter>
    }
    /*界面是否在后台运行，是的话就打开此界面*/
    public  boolean openPackage(Context context, String packageName) {
        Context pkgContext =getPackageContext(context, packageName);
        Intent intent = getAppOpenIntentByPackageName(context, packageName);
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent);
            return true;
        }
        return false;
    }
    public  Context getPackageContext(Context context, String packageName) {
        Context pkgContext = null;
        if (context.getPackageName().equals(packageName)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(packageName,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgContext;
    }
    public  Intent getAppOpenIntentByPackageName(Context context,String packageName){
        //Activity完整名
        String mainAct = null;
        //根据包名寻找
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED|Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant")
        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }

}
