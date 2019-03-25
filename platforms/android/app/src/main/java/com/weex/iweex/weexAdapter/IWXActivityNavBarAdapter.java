package com.weex.iweex.weexAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.view.menu.MenuBuilder;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.weex.iweex.WXApplication;
import com.weex.iweex.WXPageActivity;
import com.weex.iweex.extend.IWXActivityManager;
import com.weex.iweex.modle.ParamsModule;
import com.weex.iweex.util.AppConfig;
import com.weex.iweex.util.Constants;
import com.weex.iweex.util.UrlParse;

import java.util.Objects;

import static com.taobao.weex.common.Constants.Value.URL;

public class IWXActivityNavBarAdapter implements IActivityNavBarSetter{
//    private Context context;
    public  IWXActivityNavBarAdapter(){
    }
    @Override
    public boolean push(String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        //获取url
        String url = jsonObject.getString(URL);
        JSONObject params=jsonObject.getJSONObject("param");
        //将参数放到ParamsModule，可以在界面中调用get方法获得这个参数
        ParamsModule.putParam(params);
        Intent intent=new Intent(getActivity(),WXPageActivity.class);
        //调试模式和非调试模式的url解析方式稍有不同，这里做一个三目判断
        url= AppConfig.isDebug()? UrlParse.getDebugUrl(getActivity(),url):UrlParse.getReleaseUrl(getActivity(),url);
        //传递要渲染的url路径
        intent.putExtra(Constants.URL,url);
        intent.putExtra(Constants.TITLE,jsonObject.getString(Constants.TITLE));
        //启动界面
        getActivity().startActivity(intent);
        //返回true，返回false会走weex默认路由方式
        return true;
    }

    @Override
    public boolean pop(String param) {
        getActivity().finish();
        return false;
    }

    @Override
    public boolean setNavBarRightItem(String param) {
        TextView title=getActivity().getToolbarTitle();
        if (title==null){
            return false;
        }
        title.setText(param);
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
        TextView title=getActivity().getToolbarTitle();
        if (title==null){
            return false;
        }
        title.setText(param);
        return true;
    }
    public WXPageActivity getActivity(){
         return  (WXPageActivity) IWXActivityManager.getInstance().getCurrentActivity();
    }
}
