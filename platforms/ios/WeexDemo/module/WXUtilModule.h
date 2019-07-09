//
//  WXUtilModule.h
//  weexplus
//
//  Created by xindi on 2018/6/25.
//  Copyright © 2018年 郑江荣. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <WeexSDK/WeexSDK.h>
@interface WXUtilModule : NSObject <WXModuleProtocol>

+(void)postNotify:(NSString*)notify;
@end
