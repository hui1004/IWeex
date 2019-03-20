package com.weex.iweex.component.map;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.iweex.util.Constant;
import com.weex.iweex.view.WeexMapView;

import java.util.Map;

public class IWXMapCircle  extends WXComponent{
    private WeexMapView mapView;
    private Circle circle;
    private float lat,lng;
    private int radius,strokeWidth;
    private int fillColor;
    private int strokeColor;
    public IWXMapCircle(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }
    @Override
    protected View initComponentHostView(@NonNull Context context) {
        mapView = (WeexMapView) getParent().getRealView();
        for (String key:getAttrs().keySet()){
            setProperty(key,getAttrs().get(key));
        }
        return super.initComponentHostView(context);
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_LAT)
    public void setLat(float lat) {
        this.lat = lat;
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_LNG)
    public void setLng(float lng) {
        this.lng = lng;
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_RADIUS)
    public void setRadius(int radius) {
        this.radius = radius;
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_STROKE_WIDTH)
    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_FILL_COLOR)
    public void setFillColor(String fillColor) {
        //rgba(1,1,1,0.1);
        this.fillColor =  this.strokeColor = parseRgbaColor(fillColor);;
    }
    @WXComponentProp(name = Constant.MapProp.CIRCLE_STROKE_COLOR)
    public void setStrokeColor(String strokeColor) {
        this.strokeColor = parseRgbaColor(strokeColor);
    }
    /*初始化时设置属性值*/
    @Override
    protected boolean setProperty(String key, Object param) {
        switch (key){
            case Constant.MapProp.CIRCLE_LAT:
                setLat(Float.parseFloat(param.toString()));
                return true;
            case Constant.MapProp.CIRCLE_LNG:
                setLng(Float.parseFloat(param.toString()));
                return true;
            case Constant.MapProp.CIRCLE_RADIUS:
                setRadius(Integer.parseInt(param.toString()));
                return true;
            case Constant.MapProp.CIRCLE_STROKE_WIDTH:
                setStrokeWidth(Integer.parseInt(param.toString()));
                return true;
            case Constant.MapProp.CIRCLE_STROKE_COLOR:
                setStrokeColor(param.toString());
                return true;
            case Constant.MapProp.CIRCLE_FILL_COLOR:
                setFillColor(param.toString());
                return true;
            default:
                return super.setProperty(key,param);
        }
    }
    /*更新属性值时回调*/
    @Override
    public void updateAttrs(Map attrs) {
        super.updateAttrs(attrs);
        for (Object key:attrs.keySet()){
            setProperty(key.toString(),attrs.get(key));
        }
        addCircle();
    }
    /*初始化完成回调，会在setProperty设置完所有属性之后执行*/
    @Override
    protected void onHostViewInitialized(View host) {
        super.onHostViewInitialized(host);
        addCircle();
    }
    private void addCircle(){
        if(circle!=null){
            circle.remove();
        }
         LatLng latLng=new LatLng(lat,lng);
         circle=mapView.aMap.addCircle(new CircleOptions()
                 .center(latLng)
                 .radius(radius)
                 .fillColor(fillColor)
                 .strokeColor(strokeColor)
                 .strokeWidth(strokeWidth));
    }
    private int parseRgbaColor(String rgbaColor){
        String s1=rgbaColor.split("\\(")[1];
        String s2=s1.split("\\)")[0];
        String[] s3=s2.split(",");
        int alpha=(int)(Double.parseDouble(s3[3])*100);
        int red=Integer.parseInt(s3[2]);
        int green=Integer.parseInt(s3[1]);
        int blue=Integer.parseInt(s3[0]);
        return  Color.argb(alpha,red,green,blue);
    }
}
