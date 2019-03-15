package com.weex.app.util;

import android.content.Context;


/**
 * Created by budao on 2016/10/12.
 */
public class AppConfig {
  private static final String TAG = "AppConfig";
  private static AppPreferences sPreferences = new AppPreferences();

  public static void init(Context context) {
    loadAppSetting(context);
  }

  public static String getLaunchUrl() {
//    if (!isDebug()) {
      return sPreferences.getString("local_url", "file://assets/app/index.js");
//    }
//    return sPreferences.getString("debug_url", "http://192.168.1.103:8081/dist/index.js");
   }
    public static String getDebugId() {
        return sPreferences.getString("debugId", "http://192.168.1.103:8082");
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
