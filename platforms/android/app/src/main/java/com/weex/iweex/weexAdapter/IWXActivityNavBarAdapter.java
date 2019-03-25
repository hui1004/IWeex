package com.weex.iweex.weexAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.widget.Toolbar;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.weex.iweex.WXApplication;
import com.weex.iweex.WXPageActivity;
import com.weex.iweex.extend.IWXActivityManager;

import java.util.Objects;

public class IWXActivityNavBarAdapter implements IActivityNavBarSetter {
//    private Context context;
    public  IWXActivityNavBarAdapter(){
    }
    @Override
    public boolean push(String param) {
        return false;
    }

    @Override
    public boolean pop(String param) {
        return false;
    }

    @Override
    public boolean setNavBarRightItem(String param) {
        return true;
    }

    @Override
    public boolean clearNavBarRightItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarLeftItem(String param) {
        return false;
    }

    @Override
    public boolean clearNavBarLeftItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarMoreItem(String param) {
        return false;
    }

    @Override
    public boolean clearNavBarMoreItem(String param) {
        return false;
    }

    @Override
    public boolean setNavBarTitle(String param) {
        android.support.v7.widget.Toolbar toolbar=getActivity().getToolbar();
        if (toolbar!=null){
            toolbar.setTitle(param);
        }
        return true;
    }
    public WXPageActivity getActivity(){
         return  (WXPageActivity) IWXActivityManager.getInstance().getCurrentActivity();
    }
}
