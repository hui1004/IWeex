package com.weex.iweex.view;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.weex.iweex.R;
import com.weex.iweex.modle.WXLocationModule;

/**
 * Created by liuxinye on 2017/12/14.
 */

public class WeexMapView extends LinearLayout{
    MapView mapView=null;
    public AMap aMap=null;
    double mLatitude=0.0;
    double mLongitude = 0.0;
    MyLocationStyle myLocationStyle=new MyLocationStyle();
    ToggleButton button=null;
    WXLocationModule module;
    Bundle savedInstanceState=null;
    Context context;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public WeexMapView(Context context) {
        super(context);
        context=context;
        module=new WXLocationModule();
        LinearLayout.inflate(context, R.layout.location_layout,this);
        mapView=(MapView)findViewById(R.id.map);
        if(aMap==null){
            aMap=mapView.getMap();
        }
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                mLatitude=location.getLatitude();
                mLongitude=location.getLongitude();
            }
        });
        mapView.onCreate(Bundle.EMPTY);
        /*showLocationPoint();*/
    }
    /*定位模式设置*/
    public void setMapModel(int model){
        switch (model){
            case 1://连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
                break;
            case 2://连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
                break;
            case 3://连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
                break;
            case 4://连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
                myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
                break;
        }
        aMap.setMyLocationStyle(myLocationStyle);
    }
    public void setMapType(int type){
        switch (type){
            case 1:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 设置普通地图模式
                break;
            case 2:
                aMap.setMapType(AMap.MAP_TYPE_NAVI);// 设置导航地图模式
                break;
            case 3:
                aMap.setMapType(AMap.MAP_TYPE_NIGHT);// 设置夜景地图模式
                break;
            case 4:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式
                break;
        }
    }

    //设置默认定位按钮是否显示，非必需设置。
    public void showLocationButton(boolean isShow){
        aMap.getUiSettings().setMyLocationButtonEnabled(isShow);
    }
    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    public void showLocationPoint(boolean isShow){
        aMap.setMyLocationEnabled(isShow);
    }
     public void onDestroy(){
        mapView.onDestroy();
     }
     public void onResume(){
         mapView.onResume();
     }
     public void onPause(){
         mapView.onPause();
     }
     public void onCreate(Bundle outState){
         mapView.onCreate(outState);
     }
     public void onSaveInstanceState(Bundle outState){
//         Activity activity;
//         while (!(context instanceof Activity) && context instanceof ContextWrapper) {
//             context = ((ContextWrapper) context).getBaseContext();
//         }
//         if (context instanceof Activity) {
//              activity=(Activity) context;
//         }
         mapView.onSaveInstanceState(outState);
     }
}
