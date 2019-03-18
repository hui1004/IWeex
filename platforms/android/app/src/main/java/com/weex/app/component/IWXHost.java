package com.weex.app.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.app.adapter.PageViewAdapter;
import com.weex.app.util.UrlParse;
import com.weex.app.view.WXHostView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class IWXHost extends WXVContainer<ViewPager>{
    private ViewPager hostView=null;
    private ArrayList<View> weexPages=null;
    private WXSDKInstance mInstance=null;
    private int position=0;
    private PageViewAdapter adapter=null;
    int viewCount=0;
    public IWXHost(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        mInstance=instance;
    }
    @Override
    protected ViewPager initComponentHostView(@NonNull Context context) {
        hostView=new ViewPager(context);
        if(getAttrs().get("index")!=null){
            position= Integer.parseInt(getAttrs().get("index").toString());
        }
        addOnPageChangeListener();
        return hostView;
    }

    //    @JSMethod
    @WXComponentProp(name = "index")
    public void setIndex(int index){
        this.position=index;
        hostView.setCurrentItem(this.position,true);
    }
    @WXComponentProp(name = "pages")
    public void setPages(final ArrayList<String> pages) {
         weexPages=new ArrayList<>();
         adapter=new PageViewAdapter(weexPages);
         for (int i=0;i<pages.size();i++){
             String url=pages.get(i);
             url= UrlParse.getDebugUrl(mInstance.getContext(),url);
             WXSDKInstance instance=new WXSDKInstance(mInstance.getContext());
             Map<String, Object> options = new HashMap<>();
             options.put(WXSDKInstance.BUNDLE_URL, url);
             registerRenderListener(instance,i,pages.size());
             instance.renderByUrl("Iweex",url,options,null, WXRenderStrategy.APPEND_ASYNC);
         }
    }
    private void addOnPageChangeListener(){
        hostView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                HashMap map=new HashMap();
                map.put("position",position);
                map.put("positionOffset",positionOffset);
                map.put("positionOffsetPixels",positionOffsetPixels);
                fireEvent("onPageScrolled",map);
            }
            @Override
            public void onPageSelected(int position) {
                HashMap map=new HashMap();
                map.put("position",position);
                fireEvent("onPageSelected",map);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                fireEvent("onPageScrolled");
            }
        });
    }
    private void registerRenderListener(WXSDKInstance instance, final int index, final int length){
       instance.registerRenderListener(new IWXRenderListener() {
           @Override
           public void onViewCreated(WXSDKInstance instance, View view) {
               view.setTag(index);
               weexPages.add(view);
               if (weexPages.size()==length){
                   Collections.sort(weexPages, new Comparator<View>() {
                       @Override
                       public int compare(View o1, View o2) {
                           if(Integer.parseInt(o1.getTag().toString())>Integer.parseInt(o2.getTag().toString())){
                               return 1;
                           }else{
                               return -1;
                           }
                       }
                   });
                   int a=weexPages.size();
                   hostView.setAdapter(adapter);
                   hostView.setCurrentItem(position,true);
                   HashMap map=new HashMap();
                   map.put("index",index);
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
   }
}
