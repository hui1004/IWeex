//
//  IWXHost.m
//  WeexDemo
//
//  Created by xindi on 2019/3/25.
//  Copyright © 2019年 taobao. All rights reserved.
//

#import "IWXHost.h"
#import "UrlParse.h"
#import "WXDemoViewController.h"
@implementation IWXHost

@synthesize pages=_pages;
@synthesize index=_index;
@synthesize weexInstance=_weexInstance;
@synthesize pView=_pView;
//WX_EXPORT_METHOD(@selector(<#selector#>))

-(UIView *)loadView{
    for (NSString *url in _pages) {
        WXSDKInstance *pageInstance=[[WXSDKInstance alloc] init];
//        pageInstance.viewController=_weexInstance.viewController;
        pageInstance.onCreate = ^(UIView *subView) {
            [_pView addSubview:subView];
        };
        pageInstance.onFailed = ^(NSError *error) {
            NSLog(@"%@",error);
        };
//        _weexInstance.onS
        NSString *preUrl=[_weexInstance.scriptURL absoluteString];
        NSString *realUrl=[UrlParse getRelaseUrl:preUrl :url];
        NSURL *renderUrl= [NSURL URLWithString:realUrl];
        [pageInstance renderWithURL:renderUrl options:@{@"bundleUrl":renderUrl.absoluteString} data:nil];
    }
    return _pView;
}
-(instancetype)initWithRef:(NSString *)ref type:(NSString *)type styles:(NSDictionary *)styles attributes:(NSDictionary *)attributes events:(NSArray *)events weexInstance:(WXSDKInstance *)weexInstance{
    _weexInstance=weexInstance;
    if(self = [super initWithRef:ref type:type styles:styles attributes:attributes events:events weexInstance:weexInstance]) {
        if (attributes[@"pages"]) {
            _pages=attributes[@"pages"];
//            NSString*replacedStr = [josnStr stringByReplacingOccurrencesOfString:@"["withString:@""];
//            NSString*replacedStr1 = [replacedStr stringByReplacingOccurrencesOfString:@"]"withString:@""];
//            _pages=[replacedStr1 componentsSeparatedByString:@","];
            
        }
        if (attributes[@"index"]) {
            _index=[WXConvert NSInteger:attributes[@"index"]];
        }
    }
    return self;
}
-(void)viewDidLoad{
//    viewDidLoad 对组件 view 需要做一些配置，比如设置 delegate，可以在 viewDidLoad 生命周期做。如果当前 view 没有添加 subview 的话，不要设置 view 的 frame，WeexSDK 会根据 style 进行排版后设置。
//    在生命期事件中记得将属性值同步给地图控件
    
    
    
}
//当属性更新时，同步给控件
- (void)updateAttributes:(NSDictionary *)attributes {
    if (attributes[@"showsTraffic"]) {
//        _pages = [WXConvert NSString: attributes[@"showsTraffic"]];
//        ((MKMapView*)self.view).showsTraffic = _showsTraffic;
    }
}
-(void)addEvent:(NSString *)eventName{
    
}
-(void)insertSubview:(WXComponent *)subcomponent atIndex:(NSInteger)index{
    
}
@end
