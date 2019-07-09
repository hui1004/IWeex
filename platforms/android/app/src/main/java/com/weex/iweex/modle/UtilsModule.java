package com.xindi.salesplatform.modules;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.baidu.speech.asr.SpeechConstant;
import com.farwolf.perssion.Perssion;
import com.farwolf.perssion.PerssionCallback;
import com.farwolf.weex.activity.WeexActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.xindi.salesplatform.baidu_voice.MessageStatusRecogListener;
import com.xindi.salesplatform.baidu_voice.MyRecognizer;
import com.xindi.salesplatform.baidu_voice.StatusRecogListener;
import com.xindi.salesplatform.utils.MIUIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;
import static com.xindi.salesplatform.receiver.MyReceiver.badgeCount;


public class UtilsModule extends WXModule {
    protected MyRecognizer myRecognizer;
    /*
     * 。根据此参数，判断是否需要调用SDK的ASR_KWS_LOAD_ENGINE事件
     */
    protected boolean enableOffline = false;
    public static int tag = 0;
    private int module;
    protected boolean running = false;

    public UtilsModule() {
        tag++;
    }
    /*设置界面旋转方向*/
    @JSMethod
    public void setOrientation(int type){
        WeexActivity activity= (WeexActivity) mWXSDKInstance.getContext();
        activity.setRequestedOrientation(type);
    }
    /*获取当前界面的方向*/
    @JSMethod(uiThread = false)
    public Map getRequestedOrientation(){
        WeexActivity activity= (WeexActivity) mWXSDKInstance.getContext();
        int ort=activity.getRequestedOrientation();
        HashMap map=new HashMap();
        if(ort== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
             map.put("isPortrait",1);
        }else{
             map.put("isPortrait",0);
        }
        return  map;
    }
    /*获取当前设备的屏幕高度和状态栏高度（已经转化为px）*/
    @JSMethod(uiThread = false)
    public Map getDevice(){
        int statusBarHeight = -1;
//获取status_bar_height资源的ID
        int resourceId = mWXSDKInstance.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight =mWXSDKInstance.getContext().getResources().getDimensionPixelSize(resourceId);
        }

        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        WeexActivity activity= (WeexActivity) mWXSDKInstance.getContext();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight=dm.heightPixels;
        int screenWidth=dm.widthPixels;

        HashMap map=new HashMap();
        float num1=(float)(statusBarHeight*750/screenWidth);
        float num2=(float)(screenHeight*750/screenWidth);
        map.put("statusBarHeight",num1);
        map.put("screenHeight",num2);
        return map;
    }
    /*选择联系人*/
    @JSMethod
    public void turnToContact(int code) {
        Intent intentPhone = new Intent(Intent.ACTION_PICK);
        intentPhone.setData(ContactsContract.Contacts.CONTENT_URI);
        WeexActivity weexActivity = (WeexActivity) mWXSDKInstance.getContext();
        weexActivity.startActivityForResult(intentPhone, code);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((WeexActivity) mWXSDKInstance.getContext(), new String[]{Manifest.permission.READ_CONTACTS},
                        0);//申请权限
            } else {//拥有当前权限
            }
        } else {

        }
    }

    /*清空应用角标数字*/
    @JSMethod
    public void setNotification(int number) {
        if (!MIUIUtils.isMIUI()) {
            if(number>=100){
                number=99;
            }
            badgeCount = number;
            ShortcutBadger.applyCount(mWXSDKInstance.getContext(), badgeCount); //for 1.1.4+
        }else{
//            ShortcutBadger.applyNotification(mWXSDKInstance.getContext().getApplicationContext(), notification, badgeCount);
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
    public void openvoiceCog(String code, final JSCallback callbackStart, final JSCallback callbackRunning, final JSCallback callbackEnd, final JSCallback callbackResult, final JSCallback callbackError,String type) {
//        Toast.makeText(mWXSDKInstance.getContext(),""+tag,Toast.LENGTH_LONG).show();
        if (code.equals("open")) {
            if (myRecognizer != null) {
                myRecognizer.release();
                myRecognizer = null;
            }
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
                ActivityCompat.requestPermissions((Activity) mWXSDKInstance.getContext(), toApplyList.toArray(tmpList), 123);
                return;
            }
            // 1. 确定识别参数
            Map<String, Object> params = new LinkedHashMap<String, Object>();
            if(type==null){
                params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
                // 该参数=0时开启长语音。 不为0时，如果用户觉得SDK判断句子结束过快或者过慢，可以调节此参数。
                // 即连续xxx ms静音后，SDK认为一句话结束。
                params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0);
                //关闭自动断句
                params.put(SpeechConstant.VAD, "touch");
            }else if (type.equals("auto")){
//                params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
                // 该参数=0时开启长语音。 不为0时，如果用户觉得SDK判断句子结束过快或者过慢，可以调节此参数。
                // 即连续xxx ms静音后，SDK认为一句话结束。
                params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 500);
            }else{
                params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
                // 该参数=0时开启长语音。 不为0时，如果用户觉得SDK判断句子结束过快或者过慢，可以调节此参数。
                // 即连续xxx ms静音后，SDK认为一句话结束。
                params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0);
                //关闭自动断句
                params.put(SpeechConstant.VAD, "touch");
            }

            // 具体的params的值在 测试demo成功后，myRecognizer.start(params);中打印

            // 2. 初始化IRecogListener
            MessageStatusRecogListener listener = new MessageStatusRecogListener(null);
            listener.setOnVoiceResultLisener(new MessageStatusRecogListener.OnVoiceResultLisener() {
                @Override
                public void onVoiceResult(String result) {
                    HashMap map = new HashMap();
                    map.put("data", result);
                    Log.e("onVoiceResult", result);
                    myRecognizer.release();
                    callbackResult.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceError(String result) {
                    HashMap map = new HashMap();
                    map.put("data", result);
                    myRecognizer.release();
                    callbackError.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceEnd() {
                    final HashMap map = new HashMap();
                    map.put("data", "end");
                    callbackEnd.invokeAndKeepAlive(map);
//                    myRecognizer.release();
                }

                @Override
                public void onVoiceStart() {
                    HashMap map = new HashMap();
                    map.put("data", "start");
                    callbackStart.invokeAndKeepAlive(map);
                }

                @Override
                public void onVoiceRunning(String reN) {
                    HashMap map = new HashMap();
                    map.put("data", reN);
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
        } else if (code.equals("stop")) {
            if (myRecognizer != null) {
                Log.e("voiceStop", code);
                myRecognizer.stop();
                return;
            }

        } else if (code.equals("cancel")) {
            if (myRecognizer != null) {
                Log.e("cancel", code);
                myRecognizer.cancel();
            }
        }
    }

    /*激光登录*/
    @JSMethod
    public void JPushLogin(String userId) {
//        Log.e("Jpush------>",userId);
        JPushInterface.setAlias(mWXSDKInstance.getContext(), 400, userId);
    }

    /*申请权限*/
    @JSMethod
    public void requestPermissions(String code, final JSCallback callback) {
        boolean granted = true;
        final HashMap map = new HashMap();
        if (code.equals("定位")) {
            Perssion.check((Activity) mWXSDKInstance.getContext(), Manifest.permission.ACCESS_FINE_LOCATION, new PerssionCallback() {
                @Override
                public void onGranted() {
                    Perssion.check((Activity) mWXSDKInstance.getContext(), Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, new PerssionCallback() {
                        @Override
                        public void onGranted() {
                            map.put("result", "100");
                            callback.invoke(map);
                        }
                    });
                }
            });
        } else if (code.equals("语音")) {
            map.put("result", "100");
            callback.invoke(map);
        }
    }

    @JSMethod
    public void call(final String phoneNumber) {
        Perssion.check((Activity) mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE, new PerssionCallback() {
            @Override
            public void onGranted() {
                Perssion.check((Activity) mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE, new PerssionCallback() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + phoneNumber);
                        intent.setData(data);
                        if (ActivityCompat.checkSelfPermission(mWXSDKInstance.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mWXSDKInstance.getContext().startActivity(intent);
                    }
                });
            }
        });

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
}
