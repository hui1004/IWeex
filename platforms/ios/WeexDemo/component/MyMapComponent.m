//
//  MyMapComponent.m
//  xinao
//
//  Created by xindi on 2018/3/27.
//  Copyright © 2018年 郑江荣. All rights reserved.
//

#import "MyMapComponent.h"
#import <MAMapKit/MAMapKit.h>

@interface MyMapComponent()<MAMapViewDelegate>
@property (nonatomic,assign)Boolean mapLoaded;
@property (nonatomic,assign)Boolean showsUserLocation;
@property (nonatomic,assign)NSInteger mapType;
@property (nonatomic, strong) MAMapView *mapView;
@property (nonatomic,assign)Boolean markerCandrag;
@property (nonatomic,assign)Boolean markerCanclick;
@property (nonatomic,assign)Boolean canShowCallout;
@property (nonatomic,retain)NSMutableDictionary* dic;

@property (nonatomic, strong) MAGroundOverlay *groundOverlay;
//-(void)saveImage;
//-(void)addAnnotation;
//-(void)addCircleWithLat:(float)lattude Lng:(float)longitude radius:(int)r;
//-(void)addOverlay;
//-(void)alert;
@end


@implementation MyMapComponent

WX_EXPORT_METHOD(@selector(alert:));
WX_EXPORT_METHOD(@selector(saveImage)); // 暴露该方法给js
WX_EXPORT_METHOD(@selector(addMarker::::::::));
WX_EXPORT_METHOD(@selector(addCircle::::));
WX_EXPORT_METHOD(@selector(updateLocationView::));
WX_EXPORT_METHOD(@selector(setZoomTo:));


@synthesize mapLoaded=_mapLoaded;
@synthesize showsUserLocation=_showsUserLocation;
@synthesize mapType=_mapType;
@synthesize mapView=_mapView;
@synthesize markerCandrag=_markerCandrag;
@synthesize markerCanclick=_markerCanclick;
@synthesize canShowCallout=_canShowCallout;
@synthesize dic=_dic;

//实现 <MAMapViewDelegate> 协议中的 mapView:rendererForOverlay: 回调函数，设置折线的样式
- (MAOverlayRenderer *)mapView:(MAMapView *)mapView rendererForOverlay:(id <MAOverlay>)overlay
{
    if ([overlay isKindOfClass:[MAPolyline class]])
    {
        MAPolylineRenderer *polylineRenderer = [[MAPolylineRenderer alloc] initWithPolyline:overlay];
        
        polylineRenderer.lineWidth    = 8.f;
        polylineRenderer.strokeColor  = [UIColor colorWithRed:0 green:1 blue:0 alpha:0.6];
        polylineRenderer.lineJoinType = kMALineJoinRound;
        polylineRenderer.lineCapType  = kMALineCapRound;
        
        return polylineRenderer;
    }
    if ([overlay isKindOfClass:[MACircle class]])
    {
        MACircleRenderer *circleRenderer = [[MACircleRenderer alloc] initWithCircle:overlay];
        
        circleRenderer.lineWidth    = 5.f;
        circleRenderer.strokeColor  = [UIColor colorWithRed:0.15 green:0.55 blue:0.94 alpha:1];
        circleRenderer.fillColor    = [UIColor colorWithRed:0.15 green:0.55 blue:0.94 alpha:0.5];
        return circleRenderer;
    }
    return nil;
}
//地图移动时的回调
-(void)mapView:(MAMapView *)mapView regionDidChangeAnimated:(BOOL)animated{
    CGFloat lat=mapView.centerCoordinate.latitude;
    CGFloat lng=mapView.centerCoordinate.longitude;
    NSDictionary *nc=@{@"lat":[NSString stringWithFormat:@"%f",lat],@"lng":[NSString stringWithFormat:@"%f",lng]};
    [self fireEvent:@"onCameraChangeFinish" params:nc];
}
-(void)setZoomTo:(int)zoom{
    [self.mapView setZoomLevel:zoom animated:true];
}
//设置地图显示的中心点的坐标
-(void)updateLocationView:(CGFloat)lat :(CGFloat)lng{
    CLLocationCoordinate2D location=CLLocationCoordinate2DMake(lat, lng);
    [self.mapView setCenterCoordinate:location animated:YES];
}
- (UIImage *) scaleImage:(UIImage *)image toScale:(float)scaleSize {
    UIGraphicsBeginImageContext(CGSizeMake(image.size.width * scaleSize, image.size.height * scaleSize));
                                [image drawInRect:CGRectMake(0, 0, image.size.width * scaleSize, image.size.height * scaleSize)];
                                UIImage *scaledImage = UIGraphicsGetImageFromCurrentImageContext();
                                UIGraphicsEndImageContext();
                                return scaledImage;
                                }
//实现 <MAMapViewDelegate> 协议中的 mapView:viewForAnnotation:回调函数，设置标注样式
-(MAAnnotationView *)mapView:(MAMapView *)mapView viewForAnnotation:(id<MAAnnotation>)annotation{
    if ([annotation isKindOfClass:[MAPointAnnotation class]])
    {
        static NSString *pointReuseIndentifier = @"pointReuseIndentifier";
        MAPinAnnotationView*annotationView = (MAPinAnnotationView*)[mapView dequeueReusableAnnotationViewWithIdentifier:pointReuseIndentifier];
        if (annotationView == nil)
        {
            annotationView = [[MAPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:pointReuseIndentifier];
        }
        
        annotationView.canShowCallout= YES;       //设置气泡可以弹出，默认为NO
        annotationView.animatesDrop = YES;        //设置标注动画显示，默认为NO
        annotationView.draggable = NO;        //设置标注可以拖动，默认为NO
//        annotationView.clipsToBounds=_markerCanclick;
        annotationView.pinColor = MAPinAnnotationColorGreen;
        NSString * path = [_dic valueForKey:annotation.title];
        NSString *newPath=[NSString stringWithFormat:@"%@%@%@",[[NSBundle mainBundle]resourcePath],@"/",path];
        NSLog(@"newPath===%@",newPath);
//        UIImage* img= [UIImage imageWithContentsOfFile:newPath];
//        [self scaleImage:img toScale:0.1];
//        annotationView.image =img;
        
        //设置中心点偏移，使得标注底部中间点成为经纬度对应点
        annotationView.centerOffset = CGPointMake(0,0);
        return annotationView;
    }
    return nil;
}
//属性绑定
- (instancetype)initWithRef:(NSString *)ref type:(NSString *)type styles:(NSDictionary *)styles attributes:(NSDictionary *)attributes events:(NSArray *)events weexInstance:(WXSDKInstance *)weexInstance {
    if(self = [super initWithRef:ref type:type styles:styles attributes:attributes events:events weexInstance:weexInstance]) {
        
        if (attributes[@"showsUserLocation"]) {
            _showsUserLocation = [WXConvert BOOL: attributes[@"showsUserLocation"]];
        }
        if(attributes[@"mapType"]){
            _mapType=[WXConvert NSInteger:attributes[@"mapType"]];
        }
    }
    return self;
}
//更新属性
-(void)updateAttributes:(NSDictionary *)attributes{
    MAMapView* mapView=[self.view viewWithTag:101];
    if (attributes[@"showsUserLocation"]) {
        _showsUserLocation = [WXConvert BOOL: attributes[@"showsUserLocation"]];
        mapView.showsUserLocation=_showsUserLocation;
        mapView.userTrackingMode = MAUserTrackingModeFollow; // 追踪用户位置.
    }
    if(attributes[@"mapType"]){
        _mapType=[WXConvert NSInteger:attributes[@"mapType"]];
        if(_mapType==1){
            //标准地图
            [mapView setMapType:MAMapTypeStandard];
        }else if(_mapType==2){
            //卫星地图
            [mapView setMapType:MAMapTypeSatellite];
        }else if(_mapType==3){
            //夜景模式地图
            [mapView setMapType:MAMapTypeStandardNight];
        }else{
            //导航模式地图
            [mapView setMapType:MAMapTypeNavi];
        }
    }
}
//地图截屏
-(int)saveImage{
    MAMapView* mapView=[self.view viewWithTag:101];
    __block UIImage *screenshotImage = nil;
    __block NSInteger resState = 0;
//    CGRect* inRect=CGRectMake(0, 0, 400, 500);
    [mapView takeSnapshotInRect:mapView.frame withCompletionBlock:^(UIImage *resultImage, NSInteger state) {
        screenshotImage = resultImage;
        NSArray *paths=NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
        NSString *filePath=[[paths objectAtIndex:0]stringsByAppendingPaths:[NSString stringWithFormat:@"shortImage.png"]];
        BOOL result =[UIImagePNGRepresentation(screenshotImage)writeToFile:filePath atomically:YES]; // 保存成功会返回YES
        resState = state; // state表示地图此时是否完整，0-不完整，1-完整
      
    }];
      return resState;
}
//添加事件
-(void)addEvent:(NSString *)eventName{
    if ([eventName isEqualToString:@"mapLoaded"]) {
        _mapLoaded = YES;
    }
}
//拖动地图结束触发事件
-(void)mapView:(MAMapView *)mapView mapDidMoveByUser:(BOOL)wasUserAction{
//     [self fireEvent:@"onCameraChangeFinish" params:@{@"customKey":@"1"} domChanges:nil];
}
//在适宜的时间发事件通知,出发时间，这里是在component布局加载完成后触发
-(void)layoutDidFinish{
    if (_mapLoaded) {
        [self fireEvent:@"mapLoaded" params:@{@"customKey":@"customValue"} domChanges:nil];
    }
}
//生命周期函数，加载map的过程中可以做一些设置
-(void)viewDidLoad{
    MAMapView* mapView=[self.view viewWithTag:101];
     _dic=[[NSMutableDictionary alloc] init];
    mapView.showsUserLocation=_showsUserLocation;
    NSString *stringInt = [NSString stringWithFormat:@"%d",_mapType];
    NSLog([@"mapType=====================" stringByAppendingString:stringInt]);
    if(_mapType==1){
        //标准地图
        [mapView setMapType:MAMapTypeStandard];
    }else if(_mapType==2){
        //卫星地图
        [mapView setMapType:MAMapTypeSatellite];
    }else if(_mapType==3){
        //夜景模式地图
        [mapView setMapType:MAMapTypeStandardNight];
    }else{
        //导航模式地图
        [mapView setMapType:MAMapTypeNavi];
    }
}
//绘制点标记
-(void)addMarker:(float)lat :(float)lnt :(NSString*)title :(NSString*)content :(NSString*)src :(Boolean)canDrag :(Boolean)canClick :(Boolean)canShowCallout{
    //添加标注数据对象。
    MAMapView* mapView=[self.view viewWithTag:101];
    MAPointAnnotation *pointAnnotation = [[MAPointAnnotation alloc] init];
    pointAnnotation.coordinate = CLLocationCoordinate2DMake(lat, lnt);
    pointAnnotation.title = title;
    pointAnnotation.subtitle = content;
    
    [_dic setValue:src forKey:title];
    
    _canShowCallout=canShowCallout;
    _markerCanclick=canClick;
    _markerCandrag=canDrag;
    NSLog(@"%@",[NSString stringWithFormat:@"%d",_canShowCallout]);
    [mapView addAnnotation:pointAnnotation];
    mapView.showsScale=YES;
    mapView.showsScale=YES;
    mapView.showsCompass=YES;
    
}
-(void)alert:(NSString*)content{
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Title" message:content delegate:self cancelButtonTitle:@"cancel" otherButtonTitles:@"ok", nil];
       [alert show];
}
//绘制折线
-(void)addline:(NSArray*)array{
    CLLocationCoordinate2D commonPolylineCoords[2];
    int i=0;
    //json对象在ios中可以用NSDictionary接收
    for(NSDictionary* a in array){
//        NSLog(@"%@",a);
        commonPolylineCoords[i].latitude = [[a valueForKey:@"lat"] floatValue];
        commonPolylineCoords[i].longitude =[[a valueForKey:@"lnt"] floatValue];
        i+=1;
    }
    MAMapView* mapView=[self.view viewWithTag:101];
    //构造折线对象
    MAPolyline *commonPolyline = [MAPolyline polylineWithCoordinates:commonPolylineCoords count:2];
    
    //在地图上添加折线对象
    [mapView addOverlay: commonPolyline];
   
}
-(void)addCircle:(float)lattude :(float)longitude :(int)radius :(int)strock{
    MAMapView* mapView=[self.view viewWithTag:101];
    MACircle *circle = [MACircle circleWithCenterCoordinate:CLLocationCoordinate2DMake(lattude,longitude) radius:radius];
    
    //在地图上添加圆
    [mapView addOverlay: circle];
}
//加载视图对象
-(UIView*)loadView{
    _mapView=[[MAMapView alloc] initWithFrame:CGRectMake(0, 0, 400, 500)];
    _mapView.tag=101;
    _mapView.showsCompass=NO;
    //设置代理对象为自身
    self.mapView.delegate = self;
    return _mapView;
}
@end
