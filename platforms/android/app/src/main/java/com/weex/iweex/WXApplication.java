package com.weex.iweex;

import android.app.Application;

import com.weex.iweex.component.IWXInfoWindow;
import com.weex.iweex.component.IWXMap;
import com.weex.iweex.component.IWXMapMarker;
import com.weex.iweex.component.IWXSuperHost;
import com.weex.iweex.component.IWXWebView;
import com.weex.iweex.component.IWXHost;
import com.alibaba.android.bindingx.plugin.weex.BindingX;
import com.weex.iweex.extend.ImageAdapter;
import com.weex.iweex.extend.WXEventModule;
import com.weex.iweex.modle.ParamsModule;
import com.weex.iweex.modle.UtilsModule;
import com.weex.iweex.modle.WXLocationModule;
import com.weex.iweex.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.weex.iweex.weexAdapter.URIAdapter;
import com.weex.iweex.weexAdapter.WXNavigator;

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
      WXSDKEngine.registerComponent("superHostPage",IWXSuperHost.class);
      WXSDKEngine.registerComponent("webView",IWXWebView.class);
      WXSDKEngine.registerComponent("mapMarker",IWXMapMarker.class);
      WXSDKEngine.registerComponent("mapInfoWindows",IWXInfoWindow.class);
      WXSDKEngine.registerComponent("map",IWXMap.class);
      WXSDKEngine.registerModule("event", WXEventModule.class);
      WXSDKEngine.registerModule("params", ParamsModule.class);
      WXSDKEngine.registerModule("location", WXLocationModule.class);
      WXSDKEngine.registerModule("util", UtilsModule.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
  }
}