package com.weex.app.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.app.adapter.PageViewAdapter;
import com.weex.app.view.WXHostView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MWXHost extends WXComponent<ViewPager>{
    ViewPager hostView=null;
    ArrayList<View> weexPages=null;
    WXSDKInstance mInstance=null;
    public MWXHost(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        mInstance=instance;
    }
    @Override
    protected ViewPager initComponentHostView(@NonNull Context context) {
        hostView=new ViewPager(context);
        return hostView;
    }
    @WXComponentProp(name = "pages")
    public void setPages(final ArrayList<String> pages) {
         for (int i=0;i<pages.size();i++){
             String url=pages.get(i);
             WXSDKInstance instance=new WXSDKInstance(mInstance.getContext());
             Map<String, Object> options = new HashMap<>();
             options.put(WXSDKInstance.BUNDLE_URL, url);
             final int finalI = i;
             instance.registerRenderListener(new IWXRenderListener() {
                 @Override
                 public void onViewCreated(WXSDKInstance instance, View view) {
                     weexPages.add(view);
                     PageViewAdapter adapter=new PageViewAdapter(weexPages);
                     hostView.setAdapter(adapter);
                     adapter.notifyDataSetChanged();
                     HashMap map=new HashMap();
                     map.put("index",finalI);
                     if (finalI ==pages.size()-1){
                         fireEvent("pageFinish",map);
                     }
                 }
                 @Override
                 public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
                 }
                 @Override
                 public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
                 }
                 @Override
                 public void onException(WXSDKInstance instance, String errCode, String msg) {

                 }
             });
             instance.renderByUrl("Iweex",url,options,null, WXRenderStrategy.APPEND_ASYNC);
         }
    }


}
