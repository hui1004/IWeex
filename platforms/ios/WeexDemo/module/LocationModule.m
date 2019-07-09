//
//  LocationModule.m
//  weexplus
//
//  Created by xindi on 2018/5/8.
//  Copyright © 2018年 郑江荣. All rights reserved.
//

#import "LocationModule.h"
#import <WeexSDK/WeexSDK.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapLocationKit/AMapLocationKit.h>
#import <MAMapKit/MAMapKit.h>
#import <AMapSearchKit/AMapSearchKit.h>

@interface LocationModule() <WXModuleProtocol,AMapLocationManagerDelegate,AMapSearchDelegate>

@property (nonatomic,retain)AMapLocationManager *locationManager;
@property (nonatomic,retain)AMapSearchAPI *search;
@property (nonatomic,copy)WXModuleCallback callbacks;

@end

@implementation LocationModule

WX_EXPORT_METHOD(@selector(show:))
WX_EXPORT_METHOD(@selector(getLocation::))
WX_EXPORT_METHOD(@selector(getLocationInfo:::))
WX_EXPORT_METHOD(@selector(getLocationByName:::))
WX_EXPORT_METHOD(@selector(stopUpdatingLocation))
WX_EXPORT_METHOD(@selector(searchPlace:::::))
WX_EXPORT_METHOD(@selector(getDistance::))


@synthesize locationManager=_locationManager;
@synthesize weexInstance=_weexInstance;

-(void)show:(NSString*)param{
    UIAlertView *alert=[[UIAlertView alloc] initWithTitle:@"提示" message:param delegate:nil cancelButtonTitle:@"取消" otherButtonTitles:@"确定", nil];
    [alert show];
}
- (instancetype)init
{
    self = [super init];
    if (self) {
        [self initSelf];
    }
    return self;
}
//通过坐标获取地址信息
-(void)getLocationInfo:(float)latitude :(float)longgitude :(WXModuleCallback)callback{
   
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    
    regeo.location                    = [AMapGeoPoint locationWithLatitude:latitude longitude:longgitude];
    regeo.requireExtension            = YES;
    [self.search AMapReGoecodeSearch:regeo];
    self.callbacks=callback;
}
//通过地点名称和代码获取位置信息
-(void)getLocationByName:(NSString*)name :(NSString*)cityCode :(WXModuleCallback)callback{
    AMapGeocodeSearchRequest *geo = [[AMapGeocodeSearchRequest alloc] init];
    geo.address = name;
    geo.city=cityCode;
    [self.search AMapGeocodeSearch:geo];
    self.callbacks=callback;
}
/* 逆地理编码回调. */
- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response
{
    if (response.regeocode != nil)
    {
        //解析response获取地址描述，具体解析见 Demo
        AMapReGeocode *regeocode=response.regeocode;
//        NSMutableDictionary *
        NSDictionary *data=[self getObjectData:regeocode];
        if (@available(iOS 11.0, *)) {
                NSData  *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingSortedKeys error:nil];
                NSString *result=[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
//                NSLog(@"result=======>%@",result);
            self.callbacks(result);
        } else {
                 NSData  *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
                 NSString *result=[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
//                 NSLog(@"result=======>%@",result);
            self.callbacks(result);
        }
    }
}
//指定城市内搜索
-(void)searchPlace:(NSString*)address :(NSInteger)pageSize :(NSInteger)pageIndex :(NSString*)cityCode :(WXModuleCallback)callback{
    AMapPOIKeywordsSearchRequest *request = [[AMapPOIKeywordsSearchRequest alloc] init];
    
    request.keywords            = address;
    request.city                = cityCode;
//    request.types               = @"高等院校";
    request.requireExtension    = YES;
    
    /*  搜索SDK 3.2.0 中新增加的功能，只搜索本城市的POI。*/
    request.cityLimit           = YES;
    request.requireSubPOIs      = YES;
    [self.search AMapPOIKeywordsSearch:request];
    self.callbacks=callback;
}
/* POI 搜索回调. */
- (void)onPOISearchDone:(AMapPOISearchBaseRequest *)request response:(AMapPOISearchResponse *)response
{
    NSString *result;
    if (response.pois.count == 0)
    {
        result=@"null";
        self.callbacks(result);
        return;
    }
    NSDictionary *data=[self getObjectData:response];
    if (@available(iOS 11.0, *)) {
        NSData  *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingSortedKeys error:nil];
        result=[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        self.callbacks(result);
    } else {
        NSData  *jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
        result=[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        self.callbacks(result);
    }
}
- (void)onGeocodeSearchDone:(AMapGeocodeSearchRequest *)request response:(AMapGeocodeSearchResponse *)response
{
    if (response.geocodes.count == 0)
    {
        return;
    }
    AMapGeoPoint *location;
    for (AMapGeocode *geo in response.geocodes) {
        location=geo.location;
    }
//     NSLog(@"location:%@", location);
    NSDictionary *doc=@{@"lat":[NSString stringWithFormat:@"%f",location.latitude],
                        @"lng":[NSString stringWithFormat:@"%f",location.longitude]
                        };
//    WXModuleKeepAliveCallback callback=(WXModuleKeepAliveCallback)self.callbacks;
    self.callbacks(doc);
//    [_weexInstance fireGlobalEvent:@"getLocationByName" params:doc];
    
}
/**
 *  对象转换为字典
 *
 *  @param obj 需要转化的对象
 *
 *  @return 转换后的字典
 */
 -(NSDictionary*)getObjectData:(id)obj {
    
    NSMutableDictionary *dic = [NSMutableDictionary dictionary];
    unsigned int propsCount;
    
    objc_property_t *props = class_copyPropertyList([obj class], &propsCount);
    
    for(int i = 0;i < propsCount; i++) {
        
        objc_property_t prop = props[i];
        NSString *propName = [NSString stringWithUTF8String:property_getName(prop)];
        id value = [obj valueForKey:propName];
        if(value == nil) {
            
            value = [NSNull null];
        } else {
            value = [self getObjectInternal:value];
        }
        [dic setObject:value forKey:propName];
    }
    
    return dic;
}

-(id)getObjectInternal:(id)obj {
    
    if([obj isKindOfClass:[NSString class]]
       ||
       [obj isKindOfClass:[NSNumber class]]
       ||
       [obj isKindOfClass:[NSNull class]]) {
        
        return obj;
        
    }
    if([obj isKindOfClass:[NSArray class]]) {
        
        NSArray *objarr = obj;
        NSMutableArray *arr = [NSMutableArray arrayWithCapacity:objarr.count];
        
        for(int i = 0; i < objarr.count; i++) {
            
            [arr setObject:[self getObjectInternal:[objarr objectAtIndex:i]] atIndexedSubscript:i];
        }
        return arr;
    }
    if([obj isKindOfClass:[NSDictionary class]]) {
        
        NSDictionary *objdic = obj;
        NSMutableDictionary *dic = [NSMutableDictionary dictionaryWithCapacity:[objdic count]];
        
        for(NSString *key in objdic.allKeys) {
            
            [dic setObject:[self getObjectInternal:[objdic objectForKey:key]] forKey:key];
        }
        return dic;
    }
    return [self getObjectData:obj];
    
}
//获得两点间距离
-(void)getDistance:(NSMutableArray*) points :(WXModuleCallback)callback{
    NSDictionary *start=[points objectAtIndex:0];
    NSDictionary *end=[points objectAtIndex:1];
    float startLat=[[start objectForKey:@"lat"] floatValue];
    float startLng=[[start objectForKey:@"lng"] floatValue];
    float endLat=[[end objectForKey:@"lat"] floatValue];
    float endLng=[[end objectForKey:@"lng"] floatValue];
    //1.将两个经纬度点转成投影点
    MAMapPoint point1 = MAMapPointForCoordinate(CLLocationCoordinate2DMake(startLat,startLng));
    MAMapPoint point2 = MAMapPointForCoordinate(CLLocationCoordinate2DMake(endLat,endLng));
    //2.计算距离
    CLLocationDistance distance = MAMetersBetweenMapPoints(point1,point2);
     NSDictionary *dic=@{@"distance":[NSString stringWithFormat:@"%f",distance]};
    callback(dic);
}
//获取当前位置坐标
-(void)getLocation:(NSString*)accuracy :(WXModuleCallback)callback{
    if([accuracy isEqualToString:@"best"]){
        // 带逆地理信息的一次定位（返回坐标和地址信息）
        [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
        //   定位超时时间，最低2s，此处设置为10s
        self.locationManager.locationTimeout =10;
        //   逆地理请求超时时间，最低2s，此处设置为10s
        self.locationManager.reGeocodeTimeout = 10;
    }else if([accuracy isEqualToString:@"normal"]){
        // 带逆地理信息的一次定位（返回坐标和地址信息）
        [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
        //   定位超时时间，最低2s，此处设置为2s
        self.locationManager.locationTimeout =2;
        //   逆地理请求超时时间，最低2s，此处设置为2s
        self.locationManager.reGeocodeTimeout = 2;
    }else{
        // 带逆地理信息的一次定位（返回坐标和地址信息）
        [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
        //   定位超时时间，最低2s，此处设置为2s
        self.locationManager.locationTimeout =2;
        //   逆地理请求超时时间，最低2s，此处设置为2s
        self.locationManager.reGeocodeTimeout = 2;
    }
    
    // 带逆地理（返回坐标和地址信息）。将下面代码中的 YES 改成 NO ，则不会返回地址信息。
    [self.locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
        if (error)
        {
            NSLog(@"locError:{%ld - %@};", (long)error.code, error.localizedDescription);
            NSDictionary *doc=@{@"result":[NSString stringWithFormat:@"%d",-1],
                                @"errorInfo":error.localizedDescription
                                };
            callback(doc);
        }else{
            NSDictionary *data=[self getObjectData:regeocode];
            NSData  *jsonData=[NSData alloc];
            if (@available(iOS 11.0, *)) {
                jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingSortedKeys error:nil];
            } else {
                jsonData = [NSJSONSerialization dataWithJSONObject:data options:NSJSONWritingPrettyPrinted error:nil];
            }
            NSString *result=[[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
            NSDictionary *doc=@{@"lat":[NSString stringWithFormat:@"%f",location.coordinate.latitude],
                                @"lng":[NSString stringWithFormat:@"%f",location.coordinate.longitude],
                                @"result":[NSString stringWithFormat:@"%d",1],
                                @"locationInfo":result
                                };
            NSLog(@"locError:{%ld - %@};", (long)error.code, result);
            callback(doc);
        }
    }];
}
-(void)stopUpdatingLocation{
    [self.locationManager stopUpdatingLocation];
}
-(void)initSelf{
    self.search = [[AMapSearchAPI alloc] init];
    [self.search setDelegate:self];
    _locationManager=[[AMapLocationManager alloc] init];
    [self.locationManager setDelegate:self];
    //最小更新距离，单位米
//    self.locationManager.distanceFilter=50;
//    //是否返回逆地理信息
//    self.locationManager.locatingWithReGeocode=YES;
//
//    //iOS 9（不包含iOS 9） 之前设置允许后台定位参数，保持不会被系统挂起
//    [self.locationManager setPausesLocationUpdatesAutomatically:NO];
//
//    //iOS 9（包含iOS 9）之后新特性：将允许出现这种场景，同一app中多个locationmanager：一些只能在前台定位，另一些可在后台定位，并可随时禁止其后台定位。
//    if ([[[UIDevice currentDevice] systemVersion] floatValue] >= 9) {
//        self.locationManager.allowsBackgroundLocationUpdates = YES;
//    }
//    //开始持续定位
//    [self.locationManager startUpdatingLocation];
}

//位置发生变化的监听
-(void)amapLocationManager:(AMapLocationManager *)manager didUpdateLocation:(CLLocation *)location{
    
}
@end
