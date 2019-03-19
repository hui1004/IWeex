package com.weex.iweex.weexAdapter;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.taobao.weex.WXSDKInstance;
import com.weex.iweex.util.URIType;
import com.weex.iweex.util.UrlParse;

public class URIAdapter implements com.taobao.weex.adapter.URIAdapter {
    @NonNull
    @Override
    public Uri rewrite(WXSDKInstance instance, String type, Uri uri) {
        if(URIType.IMG.equals(type)){
            String url=UrlParse.getReleaseUrl(instance.getContext(),uri.toString());
            if (url.startsWith("file://")){
                url=url.replace("assets","/android_asset");
            }
            return Uri.parse(url);
        }else if(URIType.DBUGURI.equals(type)){
            return Uri.parse(UrlParse.getDebugUrl(instance.getContext(),uri.toString()));
        }else if(URIType.REALURI.equals(type)){
            return Uri.parse(UrlParse.getReleaseUrl(instance.getContext(),uri.toString()));
        }else{
            return uri;
        }
    }

    @NonNull
    @Override
    public Uri rewrite(String bundleURL, String type, Uri uri) {
        return null;
    }
}
