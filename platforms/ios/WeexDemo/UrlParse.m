//
//  UrlParse.m
//  WeexDemo
//
//  Created by xindi on 2019/3/14.
//  Copyright © 2019年 taobao. All rights reserved.
//

#import "UrlParse.h"

@implementation UrlParse

+(NSString *)getRelaseUrl:(NSString *)preUrl :(NSString *)url{
    if ([url containsString:@"src:"]) {
        if([preUrl containsString:@"http"]){
            return url;

        }else{
            return url;

        }
    }else if ([url containsString:@"../"]){
        
        NSArray *p1 = [preUrl componentsSeparatedByString:@"/"];
        NSString *p1Str=@"";
        for (int i = 0; i < [p1 count]-2; i++) {
            [p1Str stringByAppendingString:[[p1 objectAtIndex:i] stringByAppendingString:@"/"]];
        }
        NSString *p2Str=[p1Str stringByAppendingString:[[url componentsSeparatedByString:@"../"] objectAtIndex:1]];
        return p2Str;

    }else if ([url containsString:@"./"]){
        NSArray *p1 = [preUrl componentsSeparatedByString:@"/"];
        NSString *p1Str=@"";
        for (int i = 0; i < [p1 count]-1; i++) {
           p1Str=[p1Str stringByAppendingString:[[p1 objectAtIndex:i] stringByAppendingString:@"/"]];
        }
        NSString *p2Str=[p1Str stringByAppendingString:[[url componentsSeparatedByString:@"./"] objectAtIndex:1]];
        return p2Str;
    }else{
        return url;
    }
}

@end
