package com.weex.iweex.modle;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by even on 2017/10/24.
 */

public class WXLocationModule extends WXModule{
     OfflineMapManager amapManager;
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption clientOption = null;
    //初始化定位
    /*
     * 获取用户当前位置信息
     */
    @JSMethod
    public  void getLocation(String a,final JSCallback callback) {
       //声明AMapLocationClient类对象
        mLocationClient = new AMapLocationClient(WXEnvironment.getApplication().getApplicationContext());
      //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                    if (callback != null) {
                        HashMap map = new HashMap(2);
                        map.put("lat", aMapLocation.getLatitude());
                        map.put("lng", aMapLocation.getLongitude());
                        map.put("locationInfo", aMapLocation);
                        map.put("result",1);
                        callback.invokeAndKeepAlive(map);
                        stopLocation();
                    }
                } else {
                    String errText = "定位失败," + aMapLocation.getErrorInfo();
                    HashMap map = new HashMap(2);
                    map.put("result",-1);
                    map.put("errorInfo",errText);
                    callback.invokeAndKeepAlive(map);
                    WXLogUtils.e("WXlocationModule", errText);
                }
            }
        });
        clientOption=new AMapLocationClientOption();
//       //设置为高精度定位模式
//        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //连续定位
//        clientOption.setOnceLocation(false);
//        //连续定位模式2s一次
//        clientOption.setInterval(2000);
//        //超时设置20s
//        clientOption.setHttpTimeOut(20000);
        //设置定位场景，签到
        clientOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(null != mLocationClient){
            mLocationClient.setLocationOption(clientOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }
    @JSMethod
    public void stopLocation(){
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
    @JSMethod
    public void getLocationInfo(float latitude,float longitude,final JSCallback callback) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(mWXSDKInstance.getContext());//位置搜对象
        LatLonPoint point=new LatLonPoint(latitude,longitude);//坐标
        float range=600;//范围
        RegeocodeQuery query=new RegeocodeQuery(point,600,GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {//获得位置信息的回调方法
                Map<String, Object> map = new HashMap<>();
                map.put("locationInfo", regeocodeResult);
                callback.invokeAndKeepAlive(map);
            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Map<String, Object> map = new HashMap<>();
                map.put("locationInfo", geocodeResult);
//                callback.invokeAndKeepAlive(map);
            }
        });
    }
    /*
     *地址转换成坐标
     */
    @JSMethod
    public void getLocationByName(String name,String cityCode,final JSCallback callback){
        GeocodeSearch geocoderSearch = new GeocodeSearch(mWXSDKInstance.getContext());//位置搜对象
        // name表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode
        GeocodeQuery query = new GeocodeQuery(name,cityCode);
        geocoderSearch.getFromLocationNameAsyn(query);
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Map<String, Object> map = new HashMap<>();
                map.put("location", geocodeResult);
                callback.invokeAndKeepAlive(map);
            }
        });
    }
    /**
     * @param pace 地点
     * @param type 类型
     */
    @JSMethod
    public void getWeatherInfo(String pace,String type){
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
    }
    /**
     * 计算两点之间的距离
     * @param list
     */
    @JSMethod
    public void getDistance(ArrayList list,final JSCallback callback) throws JSONException {
        ArrayList<LatLng> latLngList=new ArrayList<LatLng>();
        for(int i=0;i<list.size();i++){
            JSONObject jsonObj=(JSONObject) list.get(i);
            /*H5传过来的是BigDecimal，BigDecimal转换成float类型*/
            BigDecimal bigLat= (BigDecimal) jsonObj.get("lat");
            BigDecimal bigLng= (BigDecimal) jsonObj.get("lng");
            float lat=bigLat.floatValue();
            float lng=bigLng.floatValue();
            Log.d(lat+"-"+lng,"经纬度");
            LatLng latLng=new LatLng(lat,lng);
            latLngList.add(latLng);
        }
        float distance = AMapUtils.calculateLineDistance(latLngList.get(0), latLngList.get(1));
        HashMap<String,String> map=new HashMap<String, String>();
        map.put("distance",String.valueOf(distance));
        map.put("result",distance==0?"failed":"success");
        callback.invoke(map);
    }

    /**
     * 下载离线地图
     * @param cityname
     */
    @JSMethod
    public void downloadMapOffline(String cityname) throws AMapException {
//构造OfflineMapManager对象
        amapManager = new OfflineMapManager(mWXSDKInstance.getContext(), new OfflineMapManager.OfflineMapDownloadListener() {
            @Override
            public void onDownload(int i, int i1, String s) {
            }
            @Override
            public void onCheckUpdate(boolean b, String s) {
            }

            @Override
            public void onRemove(boolean b, String s, String s1) {
            }
        });
//按照citycode下载
//        amapManager.downloadByCityCode(String citycode)；
//按照cityname下载
        amapManager.downloadByCityName(cityname);
//        amapManager.pause();
//        amapManager.stop();
        // 设置应用单独的地图存储目录
//        MapsInitializer.sdcardDir = "自定义的目录";
    }

    /**
     * @param cityname
     * @throws AMapException
     */
    @JSMethod
    public void updateOfflineCityByName(String cityname) throws AMapException {
        //通过updateOfflineCityByName方法判断离线地图数据是否存在更新
        amapManager.updateOfflineCityByName(cityname);
    }
    public void deleteOfflineCityByName(String cityName){
        //删除某一城市的离线地图包
        amapManager.remove(cityName);
    }
    @JSMethod
    public void searchPlaces(float latitude,float longitude,final JSCallback callback){

        PoiSearch.Query query=new PoiSearch.Query("","120000|120100|120200|140000|170000","");
        query.setPageSize(0);
        query.setPageNum(10);
        PoiSearch poiSearch=new PoiSearch(mWXSDKInstance.getContext(),query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude,
                longitude), 1000));//设置周边搜索的中心点以及半径
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                HashMap map=new HashMap(2);
                if(callback!=null){
                    ArrayList<PoiItem> poiItems=poiResult.getPois();
                    if(i==1000){
                        if(poiItems.size()==0){
                            List<SuggestionCity> suggestionCitys=poiResult.getSearchSuggestionCitys();
//                                map.put("suggestionCitys",suggestionCitys.get(0).getCityName());
                            map.put("result",300);
                            map.put("poiItems",poiItems);
                        }else{
                            map.put("poiItems",poiItems);
                            map.put("result",200);
                        }
                    }else {
                        map.put("result",444);//搜索失败
                    }
                    callback.invoke(map);
                }
                Log.e("searchMap",map.toString());
            }
            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }
    /*
     *搜索地点
     */
    @JSMethod
    public void searchPlace(String place,int pageSize,int pageIndex,String cityCode,final JSCallback callback){
        Log.e("searchMap--cityCode",cityCode);
        Log.e("searchMap--place",place);
        PoiSearch.Query query=new PoiSearch.Query(place,"",cityCode);
        query.setPageSize(pageSize);
        query.setPageNum(pageIndex);
        PoiSearch poiSearch=new PoiSearch(mWXSDKInstance.getContext(),query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                    HashMap map=new HashMap(2);
                    if(callback!=null){
                        ArrayList<PoiItem> poiItems=poiResult.getPois();
                        if(i==1000){
                            if(poiItems.size()==0){
                                List<SuggestionCity> suggestionCitys=poiResult.getSearchSuggestionCitys();
//                                map.put("suggestionCitys",suggestionCitys.get(0).getCityName());
                                map.put("result",300);
                                map.put("poiItems",poiItems);
                            }else{
                                map.put("poiItems",poiItems);
                                map.put("result",200);
                            }
                        }else {
                            map.put("result",444);//搜索失败
                        }
                        callback.invoke(map);
                    }
                    Log.e("searchMap",map.toString());
            }
            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }
    @JSMethod
    public CameraUpdate updateLocationView(float latitude,float longitude){
        //参数依次是：视角调整区域的中心点坐标、希望调整到的缩放级别、俯仰角0°~45°（垂直与地图时为0）、偏航角 0~360° (正北方为0)
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(latitude,longitude),
                18,30,0));
        return mCameraUpdate;
    }
    /**
     * 获取下载的离线地图列表
     */
    @JSMethod
    public void getDownloadOfflineMapCityList(JSCallback callback){

        HashMap map=new HashMap();
//        获取城市列表
        amapManager = new OfflineMapManager(mWXSDKInstance.getContext(), new OfflineMapManager.OfflineMapDownloadListener() {
            @Override
            public void onDownload(int i, int i1, String s) {
            }
            @Override
            public void onCheckUpdate(boolean b, String s) {
            }

            @Override
            public void onRemove(boolean b, String s, String s1) {
            }
        });
        ArrayList<OfflineMapCity> offlineMapCityList = amapManager.getOfflineMapCityList();
        map.put("cityList",offlineMapCityList);
        callback.invoke(map);
//        获取省列表
        ArrayList<OfflineMapProvince> offlineMapProvinceList = amapManager.getOfflineMapProvinceList();
//        获取已下载城市列表
        ArrayList<OfflineMapCity> downloadOfflineMapCityList = amapManager.getDownloadOfflineMapCityList();
//        获取正在或等待下载城市列表
        ArrayList<OfflineMapCity> downloadingCityList = amapManager.getDownloadingCityList();
    }
}