package com.weex.iweex.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.iweex.util.UrlParse;

import java.util.HashMap;

/**
 * Created by liuxinye on 2018/4/17.
 */

public class IWXWebView extends WXComponent<WebView> {
    private WebView webView;
    private WXSDKInstance instance;
    private String url="file:///android_asset/html/index.html";
    private boolean  mLoadFinish=false;
    private String mCharInfo="";

    public IWXWebView(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        this.instance=instance;
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void init(){
        webView.setInitialScale(100);
        webView.addJavascriptInterface(this,"native");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setAllowFileAccess(false); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.loadUrl(url);
        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                    mLoadFinish=true;
                    fireEvent("finish");
            }
        });
    }
    @Override
    protected WebView initComponentHostView(@NonNull Context context) {
        if(getAttrs().get("path")!=null){
            String path=getAttrs().get("path").toString();
            this.url= UrlParse.getHtmlUrl(path);
        }
        this.webView = new WebView(context);
        init();
        return this.webView;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
    }
    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
    }
    /*要加载的html的文件路径*/
    @WXComponentProp(name = "path")
    public void setPath(String path) {
        this.url= UrlParse.getHtmlUrl(path);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onFinishLayout() {
        super.onFinishLayout();
    }
//   public void onEventMainThread(Event event){
//
//    } @Subscribe(threadMode = ThreadMode.MAIN)
////
    //为html提供的通用的调用原生方法的方法，可以触发webView在weex中的对应方法名的事件
    @JavascriptInterface
    public void fireWeexEvent(final String weexMethod, String param){
        String name="";
        if(param!=null){
            name=param;
        }
        final HashMap map=new HashMap();
        map.put("name",name);
        this.webView.post(new Runnable() {
            @Override
            public void run() {
                fireEvent(weexMethod,map);
            }
        });
    }
    /*为weex界面提供的调用h5中js方法的接口*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JSMethod
    public void executeJsFunction(String functionName, String param, final JSCallback callback){
        if (!mLoadFinish){
            return;
        }
        if (!TextUtils.isEmpty(functionName)) {
            webView.evaluateJavascript("javascript:"+functionName+"("+param+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    if(callback!=null){
                        HashMap map=new HashMap();
                        map.put("res",value);
                        callback.invoke(map);
                    }
                }
            });
        }
    }
}
