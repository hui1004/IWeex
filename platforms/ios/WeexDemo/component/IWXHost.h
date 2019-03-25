//
//  IWXHost.h
//  WeexDemo
//
//  Created by xindi on 2019/3/25.
//  Copyright © 2019年 taobao. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <WeexSDK/WeexSDK.h>

@interface IWXHost : WXComponent

@property(nonatomic,strong) NSArray *pages;
@property(nonatomic) NSInteger index;
@property (nonatomic, strong) UIView *pView;

@end
