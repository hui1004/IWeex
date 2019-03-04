package com.weex.app.util;

import android.net.Uri;
import android.text.TextUtils;

public class UrlParse {
    public static String getDebugUrl(Uri uri){
        String url = uri.toString();
        String scheme = uri.getScheme();
        if (uri.isHierarchical()) {
            if (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https")) {
                String weexTpl = uri.getQueryParameter(Constants.WEEX_TPL_KEY);
                if (!TextUtils.isEmpty(weexTpl)) {
                    url = weexTpl;
                }
            }
        }
        return url;
    }
    public static String getLocalUrl(String url){
        return  url;
    }
    public static String getOriginUrl(String url){
        return  url;
    }
    public static String getUrl(String url){
        return  url;
    }
}
