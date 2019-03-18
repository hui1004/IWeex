package com.weex.app.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.app.adapter.PageViewAdapter;
import com.weex.app.view.WXHostView;

import java.util.ArrayList;
import java.util.HashMap;

public class IWXSuperHost extends WXVContainer<ViewPager> {
    private ViewPager hostView=null;
    private ArrayList<View> weexPages=null;
    private int position=0;
    private PageViewAdapter adapter=null;
    public IWXSuperHost(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        mInstance=instance;
        weexPages=new ArrayList<View>();
    }

    @Override
    protected ViewPager initComponentHostView(@NonNull Context context) {
        hostView=new WXHostView(context);
        adapter=new PageViewAdapter(weexPages);
        hostView.setAdapter(adapter);
        if(getAttrs().get("index")!=null){
            position= Integer.parseInt(getAttrs().get("index").toString());
        }
        return hostView;
    }
    @WXComponentProp(name = "index")
    public void setIndex(int index){
        this.position=index;
        hostView.setCurrentItem(this.position,true);
    }
    /*创建子元素布局*/
    @Override
    public void createChildViewAt(int index) {
        //这个方法会调用addSubView
        super.createChildViewAt(index);
    }
    //添加子元素时回调
    @Override
    public void addSubView(View child, int index) {
        weexPages.add(child);
        adapter.notifyDataSetChanged();
        if(index==getChildCount()-1){
             hostView.setCurrentItem(position);
        }
    }
    //移除子元素时回调
    @Override
    public void remove(WXComponent child, boolean destroy) {
        weexPages.remove(child.getHostView());
        adapter.notifyDataSetChanged();
    }
    /*子元素布局管理*/
    @Override
    public ViewGroup.LayoutParams getChildLayoutParams(WXComponent child, View childView, int width, int height, int left, int right, int top, int bottom) {
        ViewGroup.LayoutParams lp = null;
        if (childView != null) {
            lp = childView.getLayoutParams();
        }

        if(lp == null) {
            lp = new ViewGroup.LayoutParams(width,height);
        }else{
            lp.width = width;
            lp.height = height;
            if(lp instanceof ViewGroup.MarginLayoutParams){
                this.setMarginsSupportRTL((ViewGroup.MarginLayoutParams) lp, left, top, right, bottom);
            }
        }
        return lp;
    }
     /*添加事件*/
    @Override
    public void addEvent(String type) {
         if (type.equals("onPageSelected")||type.equals("onPageScrolled")){
               hostView.addOnPageChangeListener(new OnPageChangeListener());
         }
        super.addEvent(type);
    }
    /*事件监听器*/
    public class OnPageChangeListener implements ViewPager.OnPageChangeListener{

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
            fireEvent("onPageScrollStateChanged");
        }
    }
}
