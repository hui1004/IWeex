//
//  IWXNavigatorImpl.m
//  WeexDemo
//
//  Created by xindi on 2019/3/14.
//  Copyright © 2019年 taobao. All rights reserved.
//

#import "IWXNavigatorImpl.h"
#import "WXDemoViewController.h"
#import <WeexSDK/WeexSDK.h>
#import "UrlParse.h"

@implementation IWXNavigatorImpl

//用不到
- (void)clearNavigationItemWithParam:(NSDictionary *)param position:(WXNavigationItemPosition)position completion:(WXNavigationResultBlock)block withContainer:(UIViewController *)container {
}

- (id)navigationControllerOfContainer:(UIViewController *)container {
    return container;
}

- (void)popViewControllerWithParam:(NSDictionary *)param completion:(WXNavigationResultBlock)block withContainer:(UIViewController *)container {
    [container.navigationController popViewControllerAnimated:YES];
}

- (void)pushViewControllerWithParam:(NSDictionary *)param completion:(WXNavigationResultBlock)block withContainer:(UIViewController *)container {
     NSString *preUrl=@"";
    preUrl= [((WXDemoViewController *)container).url absoluteString];
    NSString *url=[param objectForKey:@"url"];
    NSString *realUrl=[UrlParse getRelaseUrl:preUrl :url];
    WXDemoViewController *wxController=[[WXDemoViewController alloc]init];
    wxController.url=[NSURL URLWithString:realUrl];
    [container.navigationController pushViewController:wxController animated:YES];
}
//用不到
- (void)setNavigationBackgroundColor:(UIColor *)backgroundColor withContainer:(UIViewController *)container {
}
//用不到
- (void)setNavigationBarHidden:(BOOL)hidden animated:(BOOL)animated withContainer:(UIViewController *)container {
}
//用不到
- (void)setNavigationItemWithParam:(NSDictionary *)param position:(WXNavigationItemPosition)position completion:(WXNavigationResultBlock)block withContainer:(UIViewController *)container {
}

@end
