package com.weex.iweex.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXVContainer;

public class IWXSwipeView extends WXVContainer<SwipeMenuLayout> {
    private SwipeMenuLayout swipeMenuLayout;
    public IWXSwipeView(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        swipeMenuLayout=new SwipeMenuLayout(instance.getContext());
    }
    @Override
    protected SwipeMenuLayout initComponentHostView(@NonNull Context context) {
        swipeMenuLayout.setIos(true);
        swipeMenuLayout.setLeftSwipe(true);
        swipeMenuLayout.setSwipeEnable(true);
        return swipeMenuLayout;
    }

    @Override
    public void addSubView(View child, int index) {
        if (getRealView()==null){
            return;
        }
         getRealView().addView(child);
    }
}
