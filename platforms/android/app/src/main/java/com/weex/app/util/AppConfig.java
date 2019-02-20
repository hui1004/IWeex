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
    if (isLaunchLocally()) {
      return sPreferences.getString("local_url", "file://assets/index.js");
    }
    return sPreferences.getString("launch_url", "http://192.168.1.103:8081/dist/index.js");
   }
    public static String getDebugId() {
        return sPreferences.getString("debugId", "http://192.168.1.103:8082");
    }
  public static Boolean isLaunchLocally() {
    return sPreferences.getBoolean("launch_locally", false);
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
