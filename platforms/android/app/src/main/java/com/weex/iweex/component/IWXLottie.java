package com.weex.iweex.component;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class IWXLottie extends WXComponent<LottieAnimationView> {
    private LottieAnimationView animationView;
    private LottieAnimationView.CacheStrategy cacheStrategy;
    private String animationName;
    private Boolean loop=false;
    public IWXLottie(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }
    public IWXLottie(WXSDKInstance instance, WXVContainer parent,int type, BasicComponentData basicComponentData) {
        super(instance, parent,type, basicComponentData);
    }
    @Override
    protected LottieAnimationView initComponentHostView(@NonNull Context context) {
        animationView=new LottieAnimationView(context);
        for(String key:getAttrs().keySet()){
            setProperty(key,getAttrs().get(key));
        }
        return animationView;
    }
    @WXComponentProp(name = "loop")
    public void setLoop(boolean loop) {
        this.loop=loop;
    }
    @WXComponentProp(name = "animalName")
    public void setAnimationName(String setAnimationName) {
            this.animationName=setAnimationName;
    }
    public void isCache(boolean isCache){
         if (isCache){
             cacheStrategy= LottieAnimationView.CacheStrategy.Weak;
         }else {
             cacheStrategy= LottieAnimationView.CacheStrategy.None;
         }
    }
    @Override
    protected void onHostViewInitialized(LottieAnimationView host) {
        super.onHostViewInitialized(host);
        startAnimal();
    }

    @Override
    public void addEvent(String type) {
        super.addEvent(type);
    }

    @Override
    protected void onFinishLayout() {
        super.onFinishLayout();
    }
    @JSMethod(uiThread = true)
    public void pauseAnimation(){
        animationView.pauseAnimation();
    }
    @JSMethod(uiThread = true)
    public void starAnimation(){
        animationView.playAnimation();
    }
    @JSMethod(uiThread = true)
    public void stopAnimation(){
        animationView.setProgress(0);
    }
    private void startAnimal(){
         String imagePath="app/lottie/"+animationName+"/images";
         String jsonPath="app/lottie/"+animationName+"/data.json";
         animationView.setImageAssetsFolder(imagePath);
         animationView.setAnimation(jsonPath);
         animationView.loop(loop);
         animationView.playAnimation();
    }
    @Override
    protected boolean setProperty(String key, Object param) {
        if (key.equals("animationName")){
            setAnimationName(param.toString());
            return true;
        }
        if (key.equals("loop")){
            setLoop(Boolean.parseBoolean(param.toString()));
            return true;
        }
        return super.setProperty(key, param);
    }
}
