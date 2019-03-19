package com.weex.iweex.component;

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
import com.weex.iweex.view.WeexMapView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class IWXMapMarker extends WXComponent{
    private WXSDKInstance mInstance;
    private WeexMapView mapView;
    private ImageView imageView;
    private MarkerOptions options;
    private Animation markerAnimarion;
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
//             addMarker();
         }else{
             Toast.makeText(mInstance.getContext(),"坐标数据错误！",Toast.LENGTH_LONG).show();
         }
    }
    @WXComponentProp(name = Constant.MapProp.TITLE)
    public void setTitle(String title){
        options.title(title);
//        addMarker();
    }
    @WXComponentProp(name =  Constant.MapProp.CONTENT)
    public void setContent(String content){
        options.snippet(content);
//        addMarker();
    }

    @WXComponentProp(name = Constant.MapProp.IMGSRC)
    public void setImage(String imageStr){
        JSONObject image=JSON.parseObject(imageStr);
        String url=image.getString("url");
        int w=image.getInteger("width");
        int h=image.getInteger("height");
        if(url.startsWith("http")){
            setOriginImage(url,w,h);
        }else{
            setLocalImage(url,w,h);
        }
//        addMarker();
    }
    @WXComponentProp(name = Constant.MapProp.DRAGGABLE)
    public void setDraggable(boolean draggable){
       options.draggable(draggable);
//        addMarker();
    }
    @WXComponentProp(name = Constant.MapProp.FLAT)
    public void setFlat(boolean isFlat){
        options.setFlat(isFlat);
//        addMarker();
    }
    @WXComponentProp(name = Constant.MapProp.ANIMATION)
    public void isAnimation(boolean isAnimation){
        if(isAnimation){
            Animation animation = new RotateAnimation(0,180);
            markerAnimarion=animation;
        }
//        addMarker();
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
                return true;
            case Constant.MapProp.TITLE:
                options.title(param.toString());
                return true;
            case Constant.MapProp.CONTENT:
                options.snippet(param.toString());
                return true;
            case Constant.MapProp.IMGSRC:
                setImage(param.toString());
                return true;
            case Constant.MapProp.DRAGGABLE:
                setDraggable((Boolean) param);
                return true;
            case Constant.MapProp.FLAT:
                setFlat((Boolean) param);
                return true;
            case Constant.MapProp.ANIMATION:
                isAnimation((Boolean) param);
                return true;
            default:
                return super.setProperty(key,param);
        }
    }

    @Override
    protected void onFinishLayout() {
        super.onFinishLayout();

    }

    @Override
    public void onRenderFinish(int state) {
        super.onRenderFinish(state);
    }
    /*初始化完成回调*/
    @Override
    protected void onHostViewInitialized(View host) {
        super.onHostViewInitialized(host);
//        Marker marker = mapView.aMap.addMarker(options);
//        marker.showInfoWindow();
//        marker.setAnimation(markerAnimarion);
    }
    private void addMarker(){
        Marker marker = mapView.aMap.addMarker(options);
        marker.showInfoWindow();
        marker.setObject(getAttrByKey("showType")==null?"-1":getAttrByKey("showType"));
        marker.setAnimation(markerAnimarion);
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
        if (src.startsWith("root:")){
            src=src.replace("root:","app/");
        }
        AssetManager am = mInstance.getContext().getResources().getAssets();
        // 定义矩阵对象
        Matrix matrix = new Matrix();
        //matrix.setScale(1.5f,1.5f);
        try {
            InputStream is = am.open(src);
            image = BitmapFactory.decodeStream(is);
            is.close();
            Bitmap dstbmp = Bitmap.createBitmap(image,0,0, w, h,
                    matrix, true);
            options.icon(BitmapDescriptorFactory.fromBitmap(dstbmp));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setOriginImage(String url, final int width, final int height){
        imageView=new ImageView(mInstance.getContext());
        imageView.setDrawingCacheEnabled(true);
        Picasso.with(mInstance.getContext())
                .load(Uri.parse(url))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (bitmap==null)
                            return;
//                        Matrix matrix = new Matrix();
                        Bitmap dstbmp =  Bitmap.createScaledBitmap(bitmap,width,height, true);
                        options.icon(BitmapDescriptorFactory.fromBitmap(dstbmp));
                        if (imageLoadedListener!=null){
                            imageLoadedListener.loaded();
                        }
                        imageView.setDrawingCacheEnabled(false);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }
    private OnImageLoadedListener imageLoadedListener;
    public  void  setOnImageLoadedListener(OnImageLoadedListener imageLoadedListener){
        this.imageLoadedListener=imageLoadedListener;
    }
    public interface OnImageLoadedListener{
        public void loaded();
    }
}
