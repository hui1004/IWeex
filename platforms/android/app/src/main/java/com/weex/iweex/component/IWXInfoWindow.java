package com.weex.iweex.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXDiv;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.ui.view.WXFrameLayout;
import com.weex.iweex.view.WeexMapView;

public class IWXInfoWindow extends WXDiv{
    /*相当于一个div*/
    private String showType;
    public IWXInfoWindow(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected WXFrameLayout initComponentHostView(@NonNull Context context) {
        showType=getAttrs().get("showType").toString();
        WXFrameLayout frameLayout = new WXFrameLayout(context);
        frameLayout.setTag(showType);
        return frameLayout;
    }

    @Override
    protected boolean setProperty(String key, Object param) {
        if(key.equals("showType")){
            showType=param.toString();
            getRealView().setTag(showType);
            return  true;
        }else{
            return super.setProperty(key, param);
        }
    }
}
