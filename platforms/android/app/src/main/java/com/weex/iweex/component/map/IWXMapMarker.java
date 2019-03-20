package com.weex.iweex.component.map;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.devtools.common.LogUtil;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.iweex.util.Constant;
import com.weex.iweex.util.URIType;
import com.weex.iweex.view.WeexMapView;
import com.weex.iweex.weexAdapter.URIAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class IWXMapMarker extends WXComponent{
    private WXSDKInstance mInstance;
    private WeexMapView mapView;
    private MarkerOptions options;
    private Animation markerAnimarion;
    private boolean isOrigin=false;
    private Marker marker;
    private int attrCount=0;
    public IWXMapMarker(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        mInstance=instance;
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        mapView = (WeexMapView) getParent().getRealView();
        addMarkerListener();
        options=new MarkerOptions();
        for (String key:getAttrs().keySet()){
             setProperty(key,getAttrs().get(key));
        }

        return super.initComponentHostView(context);
    }
    private void addMarkerListener(){
        mapView.aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                fireEvent("onMarkerClick");
                marker.showInfoWindow();
                return true;
            }
        });
        mapView.aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                fireEvent("onMarkerDragStart");
            }
            @Override
            public void onMarkerDrag(Marker marker) {
                fireEvent("onMarkerDrag");
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                fireEvent("onMarkerDragEnd");
            }
        });
        mapView.aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                fireEvent("onInfoWindowClick");
            }
        });
    }
    @WXComponentProp(name = "point")
    public void setPoint(Double[] point){
         if (point!=null&&point.length==2){
             LatLng latLng=new LatLng(point[0],point[1]);
             options.position(latLng);
         }else{
             Toast.makeText(mInstance.getContext(),"坐标数据错误！",Toast.LENGTH_LONG).show();
         }
    }
    @WXComponentProp(name = Constant.MapProp.TITLE)
    public void setTitle(String title){
        options.title(title);
    }
    @WXComponentProp(name =  Constant.MapProp.CONTENT)
    public void setContent(String content){
        options.snippet(content);
    }
    @WXComponentProp(name = Constant.MapProp.IMGSRC)
    public void setImage(String imageStr){
        JSONObject image=JSON.parseObject(imageStr);
        String url=image.getString("url");
        int w=image.getInteger("width");
        int h=image.getInteger("height");
        if(url.startsWith("http")){
            isOrigin=true;
            setOriginImage(url,w,h);
        }else{
            isOrigin=false;
            setLocalImage(url,w,h);
        }
    }
    @WXComponentProp(name = Constant.MapProp.DRAGGABLE)
    public void setDraggable(boolean draggable){
       options.draggable(draggable);
    }
    @WXComponentProp(name = Constant.MapProp.FLAT)
    public void setFlat(boolean isFlat){
        options.setFlat(isFlat);
    }
    @WXComponentProp(name = Constant.MapProp.ANIMATION)
    public void isAnimation(boolean isAnimation){
        if(isAnimation){
            Animation animation = new RotateAnimation(0,180);
            markerAnimarion=animation;
        }
    }
    /*初始化时设置属性值*/
    @Override
    protected boolean setProperty(String key, Object param) {
        switch (key){
            case Constant.MapProp.POINT:
                JSONArray point=JSON.parseArray(param.toString());
                Double[] points={Double.parseDouble(point.get(0).toString())
                        , Double.parseDouble(point.get(1).toString())};
                setPoint(points);
                attrCount++;
                return true;
            case Constant.MapProp.TITLE:
                options.title(param.toString());
                attrCount++;
                return true;
            case Constant.MapProp.CONTENT:
                options.snippet(param.toString());
                attrCount++;
                return true;
            case Constant.MapProp.IMGSRC:
                setImage(param.toString());
                attrCount++;
                return true;
            case Constant.MapProp.DRAGGABLE:
                setDraggable((Boolean) param);
                attrCount++;
                return true;
            case Constant.MapProp.FLAT:
                setFlat((Boolean) param);
                attrCount++;
                return true;
            case Constant.MapProp.ANIMATION:
                isAnimation((Boolean) param);
                attrCount++;
                return true;
            case "showType":
                 attrCount++;
                return true;
            default:
                attrCount++;
                return super.setProperty(key,param);
        }
    }
    @Override
    public void updateAttrs(Map attrs) {
        super.updateAttrs(attrs);
        for (Object key:attrs.keySet()){
            setProperty(key.toString(),attrs.get(key));
        }
        addMarker();
    }
    @Override
    protected void onFinishLayout() {
        super.onFinishLayout();
    }
    @Override
    public void onRenderFinish(int state) {
        super.onRenderFinish(state);
    }
    /*初始化完成回调，会在setProperty设置完所有属性之后执行*/
    @Override
    protected void onHostViewInitialized(View host) {
        super.onHostViewInitialized(host);
        if (isOrigin){
            return;
        }
        addMarker();
    }
    private void addMarker(){
        if (marker!=null){
            marker.destroy();
        }
        marker = mapView.aMap.addMarker(options);
        LatLng latLng=options.getPosition();
        options.position(latLng);
        if(markerAnimarion!=null){
            marker.setAnimation(markerAnimarion);
        }
        marker.showInfoWindow();
        marker.setObject(getAttrByKey("showType")==null?"-1":getAttrByKey("showType"));
    }
    private void resetMarker(){

    }
    public String getShowType(){
        String s=getAttrs().get("showType").toString();
        return s;
    }
    public MarkerOptions getOptions() {
        return options;
    }
    private void setLocalImage(String src, int w, int h){
        Bitmap image = null;
        if(src.startsWith("app:")){
            src=src.replace("app:","app/");
        }else{
            src= new URIAdapter().rewrite(mInstance, URIType.IMG,Uri.parse(src)).toString();
            if(src.startsWith("http")){
                setOriginImage(src,w,h);
                return;
            }
        }
        AssetManager am = mInstance.getContext().getResources().getAssets();
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        //matrix.setScale(1.5f,1.5f);
        try {
            InputStream is = am.open(src);
            image = BitmapFactory.decodeStream(is);
            is.close();
            Bitmap dstbmp = Bitmap.createScaledBitmap(image,w,h,true);
            options.icon(BitmapDescriptorFactory.fromBitmap(dstbmp));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setOriginImage(String url, final int width, final int height){
        Picasso.with(mInstance.getContext())
                .load(Uri.parse(url))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (bitmap==null)
                            return;
                        Bitmap dstbmp =  Bitmap.createScaledBitmap(bitmap,width,height, true);
                        options.icon(BitmapDescriptorFactory.fromBitmap(dstbmp));
                        if (attrCount==getAttrs().entrySet().size()){
                            addMarker();
                        }else{
                            isOrigin=false;
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        addMarker();
                    }
                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }
}
