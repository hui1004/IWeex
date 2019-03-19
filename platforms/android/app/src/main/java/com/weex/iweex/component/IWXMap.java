package com.weex.iweex.component;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.alibaba.fastjson.JSONException;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXDiv;
import com.taobao.weex.ui.component.WXVContainer;
import com.weex.iweex.R;
import com.weex.iweex.view.WeexMapView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by even on 2017/10/18.
 */

public class IWXMap extends WXVContainer<WeexMapView> {
    WeexMapView mapView;
    private static final int REQUEST_CODE_MAPVIEW = 1001;
    WXSDKInstance wxInstance;
    private Activity mActivity;
    private static String[] permissions = new String[]{
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"
    };

    public IWXMap(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
        this.wxInstance=instance;
    }

    @Override
    protected WeexMapView initComponentHostView(@NonNull Context context) {
        this.mapView = new WeexMapView(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
        requestPermissions();
        return this.mapView;
    }
    public boolean checkPermissions(Activity context, String[] permissions) {
        boolean granted = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        Toast.makeText(context, "please give me the permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return granted;
    }
    private boolean requestPermissions() {
        boolean granted = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            granted = false;
            if (mActivity != null) {
                if (!checkPermissions(mActivity, permissions)) {
                    ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_CODE_MAPVIEW);
                } else {
                    granted = true;
                }
            }
        }
        return granted;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_MAPVIEW:
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onCreate() {
        super.onCreate();
    }
    @Override
    public void onActivityDestroy() {
        mapView.onDestroy();
        super.onActivityDestroy();
    }
    /*定位模式设置*/
    @WXComponentProp(name = "model")
    public void setMapModel(int model) {
       mapView.setMapModel(model);
    }
    //设置默认定位按钮是否显示，非必需设置。
    @WXComponentProp(name = "showLocationButton")
    public void showLocationButton(boolean isShow){
        mapView.showLocationButton(isShow);
    }
    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    @WXComponentProp(name = "showsUserLocation")
    public void showLocationPoint(boolean isShow)
    {
        mapView.showLocationPoint(isShow);
    }
    /*移动地图中心点到指定坐标*/
    @JSMethod
    public void updateLocationView(float latitude, float longitude){
        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude,longitude),
                10,30,0));
       mapView.aMap.moveCamera(mCameraUpdate);
    }
    /**
     *设置地图模式：夜间模式，卫星图模式等
     * @param type
     */
    @WXComponentProp(name = "mapType")
    public void setMapType(int type){
        mapView.setMapType(type);
    }
    /**
     * 在地图上显示提示信息
     * @param lnt 经度
     * @param lat 纬度
     * @param title 标题
     * @param content 内容
     * @param draggable 是否可拖动
     * @param setFlat 是否平贴地图
     * @param isAnimation 是否使用动画
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JSMethod(uiThread = true)
      public void addMarker(float lnt, float lat, String title, String content,
                          String src,boolean draggable, boolean setFlat, boolean isAnimation) {
        LatLng latLng = new LatLng(lnt,lat);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title(title).snippet(content);
        markerOption.draggable(draggable);//设置Marker可拖动
        Bitmap image = null;
        AssetManager am = wxInstance.getContext().getResources().getAssets();
        try
        {
            InputStream is = am.open(src);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // 定义矩阵对象
        Matrix matrix = new Matrix();
//        matrix.setScale(1.5f,1.5f);
        try {
            Bitmap dstbmp = Bitmap.createBitmap(image,0,0, image.getWidth(), image.getHeight(),
                    matrix, true);
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(dstbmp));
        }catch (Exception e){
            e.printStackTrace();
        }
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(setFlat);//设置marker平贴地图效果
        markerOption.rotateAngle(180);
        Marker marker=mapView.aMap.addMarker(markerOption);
        marker.showInfoWindow();
        if(isAnimation){
            Animation animation = new RotateAnimation(marker.getRotateAngle(),marker.getRotateAngle()+180);
            animation.setDuration(500);
            animation.setInterpolator(new LinearInterpolator());
            marker.setAnimation(animation);
            marker.startAnimation();
        }
        /*点击事件*/
        mapView.aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
//                marker.getAlpha();
                HashMap map=new HashMap();
                map.put("data",marker.getOptions());
                map.put("position",marker.getPosition());
                fireEvent("markerClick",map);
                return true;
            }
        });
        /*拖动事件*/
        mapView.aMap.setOnMarkerDragListener(new AMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                HashMap<String,Object> map=new HashMap<String, Object>();
                map.put("data",marker.getOptions());
                map.put("position",marker.getPosition());
                fireEvent("onMarkerDragStart",map);
            }
            @Override
            public void onMarkerDrag(Marker marker) {
                HashMap<String,Object> map=new HashMap<String, Object>();
                map.put("data",marker.getOptions());
                map.put("position",marker.getPosition());
                fireEvent("onMarkerDrag",map);
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
                HashMap<String,Object> map=new HashMap<String, Object>();
                map.put("data",marker.getOptions());
                map.put("position",marker.getPosition());
                fireEvent("onMarkerDragEnd",map);
            }
        });
    }
    /**
     * @param lat 经度
     * @param lng 纬度
     * @param radius 半径
     * @param strokeWidth 边框宽度
     * 添加圆形区域
     */
    @JSMethod(uiThread = true)
    public void addCircle(float lat,float lng,int radius,int strokeWidth){
        LatLng latLng=new LatLng(lat,lng);
        mapView.aMap.addCircle(new CircleOptions().
                center(latLng).radius(radius).fillColor(Color.argb(100, 38,140,240)).
                strokeColor(Color.argb(50, 38,140,240)).strokeWidth(strokeWidth));
    }
    @JSMethod(uiThread = true)
    /*地图缩放到指定比例大小*/
    public void setZoomTo(int zoom){
        mapView.aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
    /**
     * 在地图上绘制线
     * @param list 坐标数组
     * @throws JSONException
     */
    @JSMethod(uiThread = true)
    public void addline(ArrayList list) throws JSONException {
        ArrayList<LatLng> latLngList=new ArrayList<LatLng>();

        for(int i=0;i<list.size();i++){
            com.alibaba.fastjson.JSONObject jsonObj=(com.alibaba.fastjson.JSONObject) list.get(i);
            /*H5传过来的是BigDecimal，BigDecimal转换成float类型*/
            BigDecimal bigLat= (BigDecimal) jsonObj.get("lat");
            BigDecimal bigLng= (BigDecimal) jsonObj.get("lnt");
            float lat=bigLat.floatValue();
            float lng=bigLng.floatValue();
//            Log.d(lat+"-"+lng,"经纬度");
            LatLng latLng=new LatLng(lat,lng);
            latLngList.add(latLng);
        }
        mapView.aMap.addPolyline(new PolylineOptions().addAll(latLngList).width(10).color(Color.BLUE));
    }
    /**
     * 绘制热力图图层
     * @param list
     */
    @JSMethod(uiThread = true)
    public void addTileOverlay(ArrayList list){
        ArrayList<LatLng> latLngList=new ArrayList<LatLng>();
        for(int i=0;i<list.size();i++){
            com.alibaba.fastjson.JSONObject jsonObj=(com.alibaba.fastjson.JSONObject) list.get(i);
            /*H5传过来的是BigDecimal，BigDecimal转换成float类型*/
            BigDecimal bigLat= (BigDecimal) jsonObj.get("latitude");
            BigDecimal bigLng= (BigDecimal) jsonObj.get("longitude");
            float lat=bigLat.floatValue();
            float lng=bigLng.floatValue();
//            Log.d(lat+"-"+lng,"经纬度");
            LatLng latLng=new LatLng(lat,lng);
            latLngList.add(latLng);
        }
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(latLngList); // 设置热力图绘制的数据
//                .gradient(A); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
// Gradient 的设置可见参考手册
// 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
// 向地图上添加 TileOverlayOptions 类对象
        mapView.aMap.addTileOverlay(tileOverlayOptions);
    }
    @JSMethod(uiThread = true)
    public void setInfoWindowAdapter(){
        mapView.aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            /**
             * 监听自定义infowindow窗口的infowindow事件回调
             */
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }
            View infoWindow = null;
            /**
             * 监听自定义infowindow窗口的infocontents事件回调
             */
            @Override
            public View getInfoContents(Marker marker) {
                if(infoWindow == null) {
                    infoWindow = LayoutInflater.from(wxInstance.getContext()).inflate(
                            R.layout.tabitem_view, null);
                }
//              render(marker, infoWindow);
                return infoWindow;
                //加载custom_info_window.xml布局文件作为InfoWindow的样式
            }
        });
        mapView.aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String content=marker.getSnippet().toString();
                Toast.makeText(wxInstance.getContext(),content,Toast.LENGTH_LONG).show();
            }
        });
    }
    @JSMethod(uiThread = true)
    public void mapPause() {
         mapView.onPause();
    }
    @JSMethod(uiThread = true)
    public void mapResume() {
        mapView.onResume();
    }
    @Override
    protected void onFinishLayout() {
        mapView.aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                HashMap map=new HashMap();
                LatLng latLng=cameraPosition.target;
                map.put("lat",latLng.latitude);
                map.put("lng",latLng.longitude);
                fireEvent("onCameraChange",map);
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                HashMap map=new HashMap();
                LatLng latLng=cameraPosition.target;
                map.put("lat",latLng.latitude);
                map.put("lng",latLng.longitude);
                fireEvent("onCameraChangeFinish",map);
            }
        });
        fireEvent("mapLoaded");
    }
    HashMap infoList=new HashMap();
        @Override
    public void createChildViewAt(int index) {
        super.createChildViewAt(index);
        Pair<WXComponent, Integer> ret = rearrangeIndexAndGetChild(index);
        if (ret.first != null) {
            final WXComponent child = ret.first;
            //执行这个方法会才会调用子元素一系列回调方法
            child.createView();
            if(child instanceof IWXMapMarker){
              final MarkerOptions options=((IWXMapMarker) child).getOptions();
              ((IWXMapMarker) child).setOnImageLoadedListener(new IWXMapMarker.OnImageLoadedListener() {
                  @Override
                  public void loaded() {
                      Marker marker= mapView.aMap.addMarker(options);
                      marker.setObject(((IWXMapMarker) child).getShowType());
                  }
              });
                Marker marker= mapView.aMap.addMarker(options);
//              marker.setObject(((IWXMapMarker) child).getShowType());
//              marker.showInfoWindow();
//              marker.setAnimation(markerAnimarion);
            }else if (child instanceof WXDiv){
                infoList.put("1",child.getRealView());
                if(index==getChildCount()-1){
                    addInfoAdapter();
                }
            }
        }
    }

    @Override
    protected void onHostViewInitialized(WeexMapView host) {
        super.onHostViewInitialized(host);
    }

    @Override
    public void onRenderFinish(int state) {
        super.onRenderFinish(state);
    }

    private void addInfoAdapter(){
        mapView.aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                String showType=marker.getObject().toString();
                if (infoList.containsKey(showType)){
                      return (View) infoList.get("1");
                }else{
                    return (View) infoList.get("1");
                }
            }
            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }
}
