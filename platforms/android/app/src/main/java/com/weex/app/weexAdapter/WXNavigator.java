package com.weex.app.weexAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.appfram.navigator.INavigator;
import com.weex.app.WXPageActivity;
import com.weex.app.util.AppConfig;
import com.weex.app.util.UrlParse;

import static com.taobao.weex.common.Constants.Value.URL;

public class WXNavigator implements INavigator{
    public static String schema; //http://xxx.xxx.xxx/app/
    @Override
    public boolean push(Activity activity, String param) {
        JSONObject jsonObject = JSON.parseObject(param);
        String url = jsonObject.getString(URL);
        Intent intent=new Intent(activity,WXPageActivity.class);
        url= AppConfig.isDebug()?UrlParse.getDebugUrl(activity,url):UrlParse.getReleaseUrl(activity,url);
        Uri data = Uri.parse(url);
        if (data != null) {
            intent.setData(data);
        }
        activity.startActivity(intent);
        return true;
    }

    @Override
    public boolean pop(Activity activity, String param) {
        activity.finish();
        return false;
    }
}
