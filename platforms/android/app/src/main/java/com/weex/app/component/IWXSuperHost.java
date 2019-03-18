package com.weex.app.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

public class IWXSuperHost extends WXVContainer<ViewPager> {
    public IWXSuperHost(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected ViewPager initComponentHostView(@NonNull Context context) {
        return super.initComponentHostView(context);
    }
    @Override
    public void createChildViewAt(int index) {
        //这个方法会调用addSubView
        super.createChildViewAt(index);
    }
    //添加子元素时回调
    @Override
    public void addSubView(View child, int index) {
        super.addSubView(child, index);
    }
    //移除子元素时回调
    @Override
    public void remove(WXComponent child, boolean destroy) {
        super.remove(child, destroy);
    }

    @Override
    public void addEvent(String type) {
        super.addEvent(type);
    }
}
