package com.weex.app;

import android.app.Application;

<<<<<<< HEAD
import com.weex.app.component.MWXHost;
=======
import com.alibaba.android.bindingx.plugin.weex.BindingX;
>>>>>>> 2f3a698593065cbfbe902398609b60b1704bbd1f
import com.weex.app.extend.ImageAdapter;
import com.weex.app.extend.WXEventModule;
import com.weex.app.modle.ParamsModule;
import com.weex.app.util.AppConfig;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.weex.app.view.WXHostView;
import com.weex.app.weexAdapter.URIAdapter;
import com.weex.app.weexAdapter.WXNavigator;

public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    WXSDKEngine.addCustomOptions("appName", "WXSample");
    WXSDKEngine.addCustomOptions("appGroup", "WXApp");
    try {
      WXSDKEngine.registerComponent("hostPage",MWXHost.class);
    } catch (WXException e) {
      e.printStackTrace();
    }
    WXSDKEngine.setNavigator(new WXNavigator());
    try {
      BindingX.register();
    } catch (WXException e) {
      e.printStackTrace();
    }
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
