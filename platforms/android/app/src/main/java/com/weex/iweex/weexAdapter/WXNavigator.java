package com.weex.iweex.weexAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.appfram.navigator.INavigator;
import com.weex.iweex.WXPageActivity;
import com.weex.iweex.modle.ParamsModule;
import com.weex.iweex.util.AppConfig;
import com.weex.iweex.util.Constants;
import com.weex.iweex.util.UrlParse;

import static com.taobao.weex.common.Constants.Value.URL;

public class WXNavigator implements INavigator{
    public static String schema; //http://xxx.xxx.xxx/app/
    @Override
    public boolean push(Activity activity, String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        //获取url
        String url = jsonObject.getString(URL);
        JSONObject params=jsonObject.getJSONObject("param");
        //将参数放到ParamsModule，可以在界面中调用get方法获得这个参数
        ParamsModule.putParam(params);
        Intent intent=new Intent(activity,WXPageActivity.class);
        //调试模式和非调试模式的url解析方式稍有不同，这里做一个三目判断
        url= AppConfig.isDebug()?UrlParse.getDebugUrl(activity,url):UrlParse.getReleaseUrl(activity,url);
        //传递要渲染的url路径
        intent.putExtra(Constants.URL,url);
        intent.putExtra(Constants.TITLE,jsonObject.getString(Constants.TITLE));
        //启动界面
        activity.startActivity(intent);
        //返回true，返回false会走weex默认路由方式
        return true;
    }

    @Override
    public boolean pop(Activity activity, String param) {
        //直接销毁界面
        activity.finish();
        return false;
    }
}
