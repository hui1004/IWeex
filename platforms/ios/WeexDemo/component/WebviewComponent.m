//
//  WebViewComponent.m
//  xinao
//
//  Created by xindi on 2018/4/17.
//  Copyright © 2018年 liuxinye. All rights reserved.
//

#import "WebviewComponent.h"
#import "UIWebView+AFNetworking.h"

@interface WebViewComponent()<UIWebViewDelegate>
@property (nonatomic,assign)Boolean webLoaded;
@property (nonatomic,assign)NSString *url;
@property (nonatomic, strong) UIWebView *webView;
@end

@implementation WebViewComponent
WX_EXPORT_METHOD(@selector(executeJsFunction::))
//webview加载完成的标识
@synthesize webLoaded=_webLoaded;
//要加载的本地HTML路径
@synthesize url=_url;
//webView
@synthesize webView=_webView;

//属性绑定
- (instancetype)initWithRef:(NSString *)ref type:(NSString *)type styles:(NSDictionary *)styles attributes:(NSDictionary *)attributes events:(NSArray *)events weexInstance:(WXSDKInstance *)weexInstance {
    if(self = [super initWithRef:ref type:type styles:styles attributes:attributes events:events weexInstance:weexInstance]) {
        
        if(attributes[@"path"]){
            _url=[WXConvert NSString:attributes[@"path"]];
        }
    }
    return self;
}
//更新属性
-(void)updateAttributes:(NSDictionary *)attributes{
    if (attributes[@"path"]) {
        _url = [WXConvert NSString: attributes[@"path"]];
        [self loadView];
    }
}
//添加事件
-(void)addEvent:(NSString *)eventName{
    if ([eventName isEqualToString:@"finish"]) {
        
    }
}
//在适宜的时间发事件通知,出发时间，这里是在component布局加载完成后触发
-(void)layoutDidFinish{
    
}
-(BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    // 拦截h5界面中要传递到weex的事件
    NSString *requestString = [[[request URL] absoluteString] stringByRemovingPercentEncoding];
    NSArray *requestsArr = [requestString componentsSeparatedByString:@"::"];
    if (requestsArr != nil && [requestsArr count] > 0) {
        NSString *pocotol = [requestsArr objectAtIndex:0];
        if ([pocotol isEqualToString:@"h5method"]) {
            NSString *commandStr = [requestsArr objectAtIndex:1];
            NSArray *commandArr = [commandStr componentsSeparatedByString:@"?"];
            if (commandArr != nil && [commandArr count] > 0) {
                NSString *command = [commandArr objectAtIndex:0];
                NSString *parameterStr = [commandArr objectAtIndex:1];
                NSArray *parameterArray = [parameterStr componentsSeparatedByString:@"&"];
                NSString *message;
                if (parameterArray && parameterArray.count > 0) {
                    message = parameterArray[0];
                }
                [self fireEvent:command params:@{@"name":message} domChanges:nil];
                //                if ([command isEqualToString:@"onItemClick"]) {
                //                    NSString *message;
                //                    if (parameterArray && parameterArray.count > 0) {
                //                        message = parameterArray[0];
                //                    }
                //                }
            }
            return NO;
        }
    }
    return YES;
}
-(void)webViewDidStartLoad:(UIWebView *)webView{
    
}
-(void)webViewDidFinishLoad:(UIWebView *)webView{
    _webLoaded=YES;
    [self fireEvent:@"finish" params:@{@"customKey":@"customValue"} domChanges:nil];
}
-(void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error{
    NSLog(@"UIWebViewNSError%@",error);
}
-(void)executeJsFunction:(NSString*)name :(NSString*)param{
    NSString *jsMethod=[NSString stringWithFormat:@"%@%@%@%@",name,@"(",param,@")"];
    [_webView stringByEvaluatingJavaScriptFromString:jsMethod];
}
//加载视图对象
-(UIView*)loadView{
    _webView=[[UIWebView alloc] initWithFrame:[UIScreen mainScreen].bounds];
    _webView.scrollView.bounces=NO;
    self.webView.delegate=self;
    NSString *mainBundlePath = [[NSBundle mainBundle] bundlePath];//文件根路径
    NSString *basePath = [NSString stringWithFormat:@"%@/html",mainBundlePath];//获取目标html文件夹路径
    NSURL *baseUrl = [NSURL fileURLWithPath:basePath isDirectory:YES];//转换成url
    NSString *htmlPath;
    htmlPath=[NSString stringWithFormat:@"%@/%@",basePath,_url];
    NSString *htmlString = [NSString stringWithContentsOfFile:htmlPath encoding:NSUTF8StringEncoding error:nil];//把html文件转换成string类型
    [_webView loadHTMLString:htmlString baseURL:baseUrl];//加载
    //    NSURL *url = [NSURL URLWithString:@"https://www.baidu.com"];
    //    NSURLRequest *request = [NSURLRequest requestWithURL:url];
    //    [_webView loadRequest:request];
    //    [_webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:@"www.baidu.com"]]];
    _webView.tag=101;
    return _webView;
}

@end


