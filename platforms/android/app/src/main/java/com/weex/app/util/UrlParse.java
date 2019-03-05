package com.weex.app.util;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.weex.app.WXPageActivity;

import static com.weex.app.weexAdapter.WXNavigator.schema;

public class UrlParse {
    /*调试包url解析*/
    public static String getDebugUrl(Context context,String url){
        /*这个是weex调试默认的url获取方式*/
        Uri uri=Uri.parse(url);
        String scheme = uri.getScheme();
        if (uri.isHierarchical()) {
            if (TextUtils.equals(scheme, "http") || TextUtils.equals(scheme, "https")) {
                String weexTpl = uri.getQueryParameter(Constants.WEEX_TPL_KEY);
                if (!TextUtils.isEmpty(weexTpl)) {
                    url = weexTpl;
                }
            }
        }
        return getReleaseUrl(context,url);
    }
    /*非调试包url解析*/
    public static String getReleaseUrl(Context context,String url){
        WXPageActivity wa= (WXPageActivity) context;
        String preUrl=wa.getUrl();  //file://assets/app/work/work.js
        if(url.startsWith("root")){ //
            //root:work/work.js to file://assets/app/work/work.js
            if(preUrl.startsWith("http")){
                url.replace("root:",schema);
            }else{
                url.replace("root:","file://assets/app/");
            }
            return url;
        }else if(url.startsWith("../")){//jump form fold use ../
            //../index.js jump from work fold to src  file://assets/app/index.js
            String [] p1=preUrl.split("/");
            String p1Str="";
            for (int i=0;i<p1.length-2;i++){
                p1Str+=p1[i]+"/";
            }
            String p2Str=p1Str+url.split("\\.\\.\\/")[1];
            return p2Str;
        }else if(url.startsWith("./")){//in same fold use ./
            // ./index.js with work fold to  file://assets/work/index.js
            String [] p1=preUrl.split("/");
            String p1Str="";
            for (int i=0;i<p1.length-1;i++){
                p1Str+=p1[i]+"/";
            }
            String p2Str=p1Str+url.split("\\.\\/")[1];
            return p2Str;
        }else{ //user complete path like file://assets/work/index.js or http://xxx.xxx.xx/app/index.js
            return url;
        }
    }
}
