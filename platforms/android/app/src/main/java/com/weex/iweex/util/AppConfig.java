package com.weex.iweex.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by budao on 2016/10/12.
 */
public class AppConfig {
  private static final String TAG = "AppConfig";
  private static AppPreferences sPreferences = new AppPreferences();
  public static void init(Context context) {
    loadAppSetting(context);
  }

  public static String getLaunchUrl(Context context) {
      if (isDebug()){
          return getDebugUrl(context);
      }
      return sPreferences.getString("launch_url", "file://assets/app/index.js");
   }
    public static String getDebugUrl(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("debug",Context.MODE_PRIVATE);
        String debugUrl=sharedPreferences.getString("debugUrl","-1");
        if (debugUrl.equals("-1")){
            return sPreferences.getString("launch_url", "file://assets/app/index.js");
        }else {
            setDebugId(debugUrl);
            return debugUrl;
        }

    }
    public static void setDebugUrl(Context context,String debugUrl) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("debug",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("debugUrl",debugUrl);
        editor.apply();
    }
    public static String getDebugId() {
        return sPreferences.getString("debugId", "http://192.168.1.102:8082");
    }
    public static void setDebugId(String debugId) {
        sPreferences.set("debugId",debugId);
    }
  public static Boolean isDebug() {
    return sPreferences.getBoolean("debug", false);
  }
  private static void loadAppSetting(Context context) {
    AppConfigXmlParser parser = new AppConfigXmlParser();
    parser.parse(context);
    sPreferences = parser.getPreferences();
  }
}
