package com.weex.app;

import android.app.Application;

import com.weex.app.extend.ImageAdapter;
import com.weex.app.extend.WXEventModule;
import com.weex.app.modle.ParamsModule;
import com.weex.app.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.weex.app.weexAdapter.URIAdapter;
import com.weex.app.weexAdapter.WXNavigator;

public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    WXSDKEngine.addCustomOptions("appName", "WXSample");
    WXSDKEngine.addCustomOptions("appGroup", "WXApp");
    WXSDKEngine.setNavigator(new WXNavigator());
    WXSDKEngine.initialize(this,
        new InitConfig.Builder()
                .setImgAdapter(new ImageAdapter())
                .setURIAdapter(new URIAdapter())
                .build()
    );
    try {
      WXSDKEngine.registerModule("event", WXEventModule.class);
        WXSDKEngine.registerModule("params", ParamsModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
    AppConfig.init(this);
  }
}
