package com.weex.app;

import android.app.Application;

import com.weex.app.component.IWXCalendar;
import com.weex.app.component.IWXMap;
import com.weex.app.component.IWXWebView;
import com.weex.app.component.IWXHost;
import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.weex.app.extend.ImageAdapter;
import com.weex.app.extend.WXEventModule;
import com.weex.app.modle.ParamsModule;
import com.weex.app.modle.UtilsModule;
import com.weex.app.modle.WXLocationModule;
import com.weex.app.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.weex.app.util.Utils;
import com.weex.app.weexAdapter.URIAdapter;
import com.weex.app.weexAdapter.WXNavigator;

public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initWeex();;
    AppConfig.init(this);
  }
  private void initWeex(){
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
      BindingX.register();
      WXSDKEngine.registerComponent("hostPage",IWXHost.class);
      WXSDKEngine.registerComponent("webView",IWXWebView.class);
      WXSDKEngine.registerComponent("map",IWXMap.class);
      WXSDKEngine.registerComponent("calendar",IWXCalendar.class);
      WXSDKEngine.registerModule("event", WXEventModule.class);
      WXSDKEngine.registerModule("params", ParamsModule.class);
      WXSDKEngine.registerModule("location", WXLocationModule.class);
      WXSDKEngine.registerModule("util", UtilsModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
  }
}
