//
//  WXUtilModule.m
//  weexplus
//
//  Created by xindi on 2018/6/25.
//  Copyright © 2018年 郑江荣. All rights reserved.
//
//#import <Foundation/Foundation.h>
#import "AppDelegate.h"
#import "WXUtilModule.h"
#import <AddressBook/AddressBook.h>
#import <AddressBookUI/AddressBookUI.h>
#import <ContactsUI/ContactsUI.h>
#import "sys/utsname.h"

#import <AVFoundation/AVCaptureDevice.h>
#import <AVFoundation/AVMediaFormat.h>
#import <AssetsLibrary/AssetsLibrary.h>
#import <farwolf.h>

#import "BDSASRDefines.h"
#import "BDSASRParameters.h"
#import "BDSEventManager.h"
#import "fcntl.h"
#import "AudioInputStream.h"
#import "JPUSHService.h"
//#error "请在官网新建应用，配置包名，并在此填写应用的 api key, secret key, appid(即appcode)"
const NSString* API_KEY = @"utQuGeXEfvX9GS5NE5tbMPY0";
const NSString* SECRET_KEY = @"6OvaMEyjg9G3zg4HbQgnbFiHwVN5d1LG";
const NSString* APP_ID = @"11543954";
@interface WXUtilModule() <ABPeoplePickerNavigationControllerDelegate,CNContactPickerDelegate,BDSClientASRDelegate>
@property (strong, nonatomic) BDSEventManager *asrManager;
@property(nonatomic, assign) BOOL continueToVR;
@property(atomic,assign) int resultCode;
@property(nonatomic, strong) NSFileHandle *fileHandler;
@property(nonatomic, assign) TBDVoiceRecognitionOfflineEngineType curOfflineEngineType;
@property(nonatomic, copy) WXModuleKeepAliveCallback callback_start;
@property(nonatomic, copy) WXModuleKeepAliveCallback callback_running;
@property(nonatomic, copy) WXModuleKeepAliveCallback callback_result;
@property(nonatomic, copy) WXModuleKeepAliveCallback callback_end;
@property(nonatomic, copy) WXModuleKeepAliveCallback callback_error;
@property(nonatomic,assign)BOOL isGet;
@end
@implementation WXUtilModule
#define Is_up_Ios_9             ([[UIDevice currentDevice].systemVersion floatValue] >= 9.0)
@synthesize weexInstance;
WX_EXPORT_METHOD(@selector(turnToContact:))
WX_EXPORT_METHOD(@selector(openvoiceCog::::::))
WX_EXPORT_METHOD(@selector(IOSLogJson:))
WX_EXPORT_METHOD(@selector(IOSLogString:))
WX_EXPORT_METHOD(@selector(JPushLogin:))
WX_EXPORT_METHOD(@selector(requestPermissions::))
WX_EXPORT_METHOD(@selector(openUrlBySafari:))
WX_EXPORT_METHOD(@selector(call:))
WX_EXPORT_METHOD(@selector(setNotification:))
WX_EXPORT_METHOD(@selector(exitApp))
WX_EXPORT_METHOD(@selector(getPhoneInfo:))
WX_EXPORT_METHOD_SYNC(@selector(getDevice))
- (instancetype)init
{
    self = [super init];
    if (self) {
       
    }
    return self;
}
-(void)IOSLogJson:(NSDictionary*)data{
    NSLog(@"weexLog-->%@",data);
}
-(void)IOSLogString:(NSString*)data{
    NSLog(@"weexLog-->%@",data);
}
+(void)postNotify:(NSString*)notify{
    NSMutableDictionary *result = [NSMutableDictionary dictionary];
    NSMutableDictionary *param = [NSMutableDictionary dictionary];
    
    result[@"key"] = notify;
    result[@"param"] = param;
    
    [self notifyDict:@"notify" value:result];
}
-(void)JPushLogin:(NSString*)userCode{
    [JPUSHService setAlias:userCode completion:^(NSInteger iResCode, NSString *iAlias, NSInteger seq) {
        if (iResCode==0) {
            NSLog(@"JPush====>极光登录成功");
        }
    } seq:1];
}
#pragma mark ---- 调用系统通讯录
- (void)JudgeAddressBookPower {
    ///获取通讯录权限，调用系统通讯录
    [self CheckAddressBookAuthorization:^(bool isAuthorized , bool isUp_ios_9) {
        if (isAuthorized) {
            [self callAddressBook:isUp_ios_9];
        }else {
            NSLog(@"请到设置>隐私>通讯录打开本应用的权限设置");
        }
    }];
}
-(void)requestPermissions:(NSString*)type :(WXModuleCallback)callback{
    if([type isEqualToString:@"定位"]){
        NSDictionary *dc=@{@"result":@"100"};
        callback(dc);
    }else{
        AVAuthorizationStatus videoAuthStatus = [AVCaptureDevice authorizationStatusForMediaType:AVMediaTypeAudio];
        
        if (videoAuthStatus == AVAuthorizationStatusNotDetermined) {// 未询问用户是否授权
            [AVCaptureDevice requestAccessForMediaType:AVMediaTypeAudio completionHandler:^(BOOL granted) {
                if (granted){// 用户同意授权
                    NSDictionary *dc=@{@"result":@"100"};
                   callback(dc);
                }else {// 用户拒绝授权
                    NSDictionary *dc=@{@"result":@"-1"};
                    callback(dc);
                }
            }];
        }else if(videoAuthStatus == AVAuthorizationStatusRestricted || videoAuthStatus == AVAuthorizationStatusDenied) {// 未授权
            [AVCaptureDevice requestAccessForMediaType:AVMediaTypeAudio completionHandler:^(BOOL granted) {
                if (granted){// 用户同意授权
                    NSDictionary *dc=@{@"result":@"100"};
                    callback(dc);
                }else {// 用户拒绝授权
                    NSDictionary *dc=@{@"result":@"-1"};
                    callback(dc);
                }
            }];
        }else{// 已授权
            NSDictionary *dc=@{@"result":@"100"};
            callback(dc);
        }
    }
}
-(void)openvoiceCog:(NSString*)type :(WXModuleKeepAliveCallback)callback_start :(WXModuleKeepAliveCallback)callback_running
:(WXModuleKeepAliveCallback)callback_end :(WXModuleKeepAliveCallback)callback_result :(WXModuleKeepAliveCallback)callback_error{
    //配置语音识别
    [self configVoiceRecognitionClient];
//    NSLog(@"Current SDK version: %@", [self.asrEventManager libver]);
    if([type isEqualToString:@"open"]){
        NSLog(@"====>open");
        [self.asrManager sendCommand:BDS_ASR_CMD_START];
    }else if([type isEqualToString:@"stop"]){
         NSLog(@"====>stop");
        NSString *state=@"end";
        self.callback_end(state, YES);
        [self.asrManager sendCommand:BDS_ASR_CMD_STOP];
    }
    self.callback_start=callback_start;
    self.callback_running=callback_running;
    self.callback_result=callback_result;
    self.callback_end=callback_end;
    self.callback_error=callback_error;
}
- (void)VoiceRecognitionClientWorkStatus:(int)workStatus obj:(id)aObj {
    switch (workStatus) {
        case EVoiceRecognitionClientWorkStatusNewRecordData: {
            [self.fileHandler writeData:(NSData *)aObj];
            break;
        }
            
        case EVoiceRecognitionClientWorkStatusStartWorkIng: {
            NSDictionary *logDic = [self parseLogToDic:aObj];
           NSLog(@"====>WorkIng");
             NSString *state=@"start";
            self.callback_start(state, YES);
            break;
        }
        case EVoiceRecognitionClientWorkStatusStart: {
//            [self printLogTextView:@"CALLBACK: detect voice start point.\n"];
    
            NSLog(@"====>start");
            break;
        }
        case EVoiceRecognitionClientWorkStatusEnd: {
//            [self printLogTextView:@"CALLBACK: detect voice end point.\n"];
           
            break;
        }
        case EVoiceRecognitionClientWorkStatusFlushData: {
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: partial result - %@.\n\n", [self getDescriptionForDic:aObj]]];
//            NSString *state=@"end";
            NSLog(@"====>running%@",[self getDescriptionForDic:aObj]);
            self.callback_running([self getDescriptionForDic:aObj], YES);
            break;
        }
        case EVoiceRecognitionClientWorkStatusFinish: {
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: final result - %@.\n\n", [self getDescriptionForDic:aObj]]];
        
            break;
        }
        case EVoiceRecognitionClientWorkStatusMeterLevel: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusCancel: {
//            [self printLogTextView:@"CALLBACK: user press cancel.\n"];
        
            break;
        }
        case EVoiceRecognitionClientWorkStatusError: {
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: encount error - %@.\n", (NSError *)aObj]];
            NSLog(@"====>error");
            NSString *state=@"error";
            self.callback_error(state, YES);
            break;
        }
        case EVoiceRecognitionClientWorkStatusLoaded: {
//            [self printLogTextView:@"CALLBACK: offline engine loaded.\n"];
            break;
        }
        case EVoiceRecognitionClientWorkStatusUnLoaded: {
//            [self printLogTextView:@"CALLBACK: offline engine unLoaded.\n"];
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkThirdData: {
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: Chunk 3-party data length: %lu\n", (unsigned long)[(NSData *)aObj length]]];
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkNlu: {
            NSString *nlu = [[NSString alloc] initWithData:(NSData *)aObj encoding:NSUTF8StringEncoding];
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: Chunk NLU data: %@\n", nlu]];
            NSLog(@"ChunkNlu%@", nlu);
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkEnd: {
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK: Chunk end, sn: %@.\n", aObj]];
         NSLog(@"Chunk end%@", aObj);
            break;
        }
        case EVoiceRecognitionClientWorkStatusFeedback: {
            NSDictionary *logDic = [self parseLogToDic:aObj];
//            [self printLogTextView:[NSString stringWithFormat:@"CALLBACK Feedback: %@\n", logDic]];
            break;
        }
        case EVoiceRecognitionClientWorkStatusRecorderEnd: {
//            [self printLogTextView:@"CALLBACK: recorder closed.\n"]
            NSLog(@"====>RecorderEnd%@",[self getDescriptionForDic:aObj]);
            break;
        }
        case EVoiceRecognitionClientWorkStatusLongSpeechEnd: {
//            [self printLogTextView:@"CALLBACK: Long Speech end.\n"];
            
             NSLog(@"====>LongSpeechEnd");
            break;
        }
        default:
            break;
    }
}
- (NSString *)getDescriptionForDic:(NSDictionary *)dic {
    if (dic) {
        return [[NSString alloc] initWithData:[NSJSONSerialization dataWithJSONObject:dic
                                                                              options:NSJSONWritingPrettyPrinted
                                                                                error:nil] encoding:NSUTF8StringEncoding];
    }
    return nil;
}
-(void)openUrlBySafari:(NSString*)urlStr{
    NSURL *url = [NSURL URLWithString:urlStr];
    if([[UIDevice currentDevice].systemVersion floatValue] >= 10.0){
        if ([[UIApplication sharedApplication] respondsToSelector:@selector(openURL:options:completionHandler:)]) {
            [[UIApplication sharedApplication] openURL:url options:@{}
                                     completionHandler:^(BOOL success) {
                                         NSLog(@"Open %d",success);
                                     }];
        } else {
            BOOL success = [[UIApplication sharedApplication] openURL:url];
            NSLog(@"Open  %d",success);
        }
        
    } else{
        bool can = [[UIApplication sharedApplication] canOpenURL:url];
        if(can){
            [[UIApplication sharedApplication] openURL:url];
        }
    }
}
- (NSDictionary *)parseLogToDic:(NSString *)logString
{
    NSArray *tmp = NULL;
    NSMutableDictionary *logDic = [[NSMutableDictionary alloc] initWithCapacity:3];
    NSArray *items = [logString componentsSeparatedByString:@"&"];
    for (NSString *item in items) {
        tmp = [item componentsSeparatedByString:@"="];
        if (tmp.count == 2) {
            [logDic setObject:tmp.lastObject forKey:tmp.firstObject];
        }
    }
    return logDic;
}
- (void)configVoiceRecognitionClient {
    //创建相关接口对象
    self.asrManager = [BDSEventManager createEventManagerWithName:BDS_ASR_NAME];
    //设置代理
    [self.asrManager setDelegate:self];
    
    //配置参数
    //1.设置DEBUG_LOG的级别
    [self.asrManager setParameter:@(EVRDebugLogLevelTrace) forKey:BDS_ASR_DEBUG_LOG_LEVEL];
    //2.配置API_KEY 和 SECRET_KEY 和 APP_ID
    [self.asrManager setParameter:@[API_KEY, SECRET_KEY] forKey:BDS_ASR_API_SECRET_KEYS];
    [self.asrManager setParameter:APP_ID forKey:BDS_ASR_OFFLINE_APP_CODE];
    //3.配置端点检测
    NSString *modelVAD_filepath = [[NSBundle mainBundle] pathForResource:@"bds_easr_basic_model" ofType:@"dat"];
    NSLog(@"modelVAD_filepath = %@",modelVAD_filepath);
    [self.asrManager setParameter:modelVAD_filepath forKey:BDS_ASR_MODEL_VAD_DAT_FILE];
    [self.asrManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_MODEL_VAD];
    //4.开启语义理解
    [self.asrManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_NLU];
    [self.asrManager setParameter:@"15361" forKey:BDS_ASR_PRODUCT_ID];
    
    //长按设置
    [self.asrManager setParameter:@(YES) forKey:BDS_ASR_VAD_ENABLE_LONG_PRESS];
    [self.asrManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_LONG_SPEECH];
    [self.asrManager setParameter:@(NO) forKey:BDS_ASR_NEED_CACHE_AUDIO];
    [self.asrManager setParameter:@"" forKey:BDS_ASR_OFFLINE_ENGINE_TRIGGERED_WAKEUP_WORD];
    [self.asrManager setParameter:nil forKey:BDS_ASR_AUDIO_FILE_PATH];
    [self.asrManager setParameter:nil forKey:BDS_ASR_AUDIO_INPUT_STREAM];

}
//- (void) enablePunctuation {
//    // ---- 开启标点输出 -----
//    [self.asrEventManager setParameter:@(NO) forKey:BDS_ASR_DISABLE_PUNCTUATION];
//    // 普通话标点
//        [self.asrEventManager setParameter:@"1537" forKey:BDS_ASR_PRODUCT_ID];
//    // 英文标点
////    [self.asrEventManager setParameter:@"1737" forKey:BDS_ASR_PRODUCT_ID];
//
//}
//- (void)configModelVAD {
//    NSString *modelVAD_filepath = [[NSBundle mainBundle] pathForResource:@"bds_easr_basic_model" ofType:@"dat"];
//    [self.asrEventManager setParameter:modelVAD_filepath forKey:BDS_ASR_MODEL_VAD_DAT_FILE];
//    [self.asrEventManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_MODEL_VAD];
//}
//- (void)configDNNMFE {
//    NSString *mfe_dnn_filepath = [[NSBundle mainBundle] pathForResource:@"bds_easr_mfe_dnn" ofType:@"dat"];
//    [self.asrEventManager setParameter:mfe_dnn_filepath forKey:BDS_ASR_MFE_DNN_DAT_FILE];
//    NSString *cmvn_dnn_filepath = [[NSBundle mainBundle] pathForResource:@"bds_easr_mfe_cmvn" ofType:@"dat"];
//    [self.asrEventManager setParameter:cmvn_dnn_filepath forKey:BDS_ASR_MFE_CMVN_DAT_FILE];
//
//    [self.asrEventManager setParameter:@(NO) forKey:BDS_ASR_ENABLE_MODEL_VAD];
//    // MFE支持自定义静音时长
//    //    [self.asrEventManager setParameter:@(500.f) forKey:BDS_ASR_MFE_MAX_SPEECH_PAUSE];
//    //    [self.asrEventManager setParameter:@(500.f) forKey:BDS_ASR_MFE_MAX_WAIT_DURATION];
//}

- (void)callAddressBook:(BOOL)isUp_ios_9 {
    if (isUp_ios_9) {
        CNContactPickerViewController *contactPicker = [[CNContactPickerViewController alloc] init];
        contactPicker.delegate = self;
        contactPicker.displayedPropertyKeys = @[CNContactPhoneNumbersKey];
        
        
        UIViewController *vc=self.weexInstance.viewController;
        [vc presentViewController:contactPicker animated:YES completion:nil];
    }else {
        ABPeoplePickerNavigationController *peoplePicker = [[ABPeoplePickerNavigationController alloc] init];
        peoplePicker.peoplePickerDelegate = self;
        [self.weexInstance.viewController presentViewController:peoplePicker animated:YES completion:nil];
        
    }
}
- (void)CheckAddressBookAuthorization:(void (^)(bool isAuthorized , bool isUp_ios_9))block {
    
    if (Is_up_Ios_9) {
        CNContactStore * contactStore = [[CNContactStore alloc]init];
        if ([CNContactStore authorizationStatusForEntityType:CNEntityTypeContacts] == CNAuthorizationStatusNotDetermined) {
            [contactStore requestAccessForEntityType:CNEntityTypeContacts completionHandler:^(BOOL granted, NSError * __nullable error) {
                if (error)
                {
                    NSLog(@"Error: %@", error);
                }
                else if (!granted)
                {
                    
                    block(NO,YES);
                }
                else
                {
                    block(YES,YES);
                }
            }];
        }
        else if ([CNContactStore authorizationStatusForEntityType:CNEntityTypeContacts] == CNAuthorizationStatusAuthorized){
            block(YES,YES);
        }
        else {
            NSLog(@"请到设置>隐私>通讯录打开本应用的权限设置");
        }
    }else {
        ABAddressBookRef addressBook = ABAddressBookCreateWithOptions(NULL, NULL);
        ABAuthorizationStatus authStatus = ABAddressBookGetAuthorizationStatus();
        
        if (authStatus == kABAuthorizationStatusNotDetermined)
        {
            ABAddressBookRequestAccessWithCompletion(addressBook, ^(bool granted, CFErrorRef error) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    if (error)
                    {
                        NSLog(@"Error: %@", (__bridge NSError *)error);
                    }
                    else if (!granted)
                    {
                        
                        block(NO,NO);
                    }
                    else
                    {
                        block(YES,NO);
                    }
                });
            });
        }else if (authStatus == kABAuthorizationStatusAuthorized)
        {
            block(YES,NO);
        }else {
            NSLog(@"请到设置>隐私>通讯录打开本应用的权限设置");
        }
    }
}
#pragma mark -- CNContactPickerDelegate
- (void)contactPicker:(CNContactPickerViewController *)picker didSelectContactProperty:(CNContactProperty *)contactProperty {
    CNPhoneNumber *phoneNumber = (CNPhoneNumber *)contactProperty.value;
    [self.weexInstance.viewController dismissViewControllerAnimated:YES completion:^{
        /// 联系人
        NSString *text1 = [NSString stringWithFormat:@"%@%@",contactProperty.contact.familyName,contactProperty.contact.givenName];
        /// 电话
        NSString *text2 = phoneNumber.stringValue;
        //        text2 = [text2 stringByReplacingOccurrencesOfString:@"-" withString:@""];
        NSDictionary *doc=@{@"resultCode":[NSString stringWithFormat:@"%d",self.resultCode],
                            @"name":text1,
                            @"phoneNumber":text2
                            };
        [self.weexInstance fireGlobalEvent:@"onPrePageResult" params:doc];
        NSLog(@"联系人：%@, 电话：%@",text1,text2);
    }];
}

#pragma mark -- ABPeoplePickerNavigationControllerDelegate
- (void)peoplePickerNavigationController:(ABPeoplePickerNavigationController*)peoplePicker didSelectPerson:(ABRecordRef)person property:(ABPropertyID)property identifier:(ABMultiValueIdentifier)identifier {
    
    ABMultiValueRef valuesRef = ABRecordCopyValue(person, kABPersonPhoneProperty);
    CFIndex index = ABMultiValueGetIndexForIdentifier(valuesRef,identifier);
    CFStringRef value = ABMultiValueCopyValueAtIndex(valuesRef,index);
    CFStringRef anFullName = ABRecordCopyCompositeName(person);
    
    [self.weexInstance.viewController dismissViewControllerAnimated:YES completion:^{
        /// 联系人
        NSString *text1 = [NSString stringWithFormat:@"%@",anFullName];
        /// 电话
        NSString *text2 = (__bridge NSString*)value;
        //        text2 = [text2 stringByReplacingOccurrencesOfString:@"-" withString:@""];
        NSDictionary *doc=@{@"name":text1,
                            @"phoneNumber":text2
                            };
        [self.weexInstance fireGlobalEvent:@"onPrePageResult" params:doc];
        NSLog(@"联系人：%@, 电话：%@",text1,text2);
    }];
}
-(void)turnToContact:(int)resultCode{
    self.resultCode=resultCode;
    [self JudgeAddressBookPower];
}
-(void)call:(NSString*)phoneNumber{
    NSMutableString* str=[[NSMutableString alloc] initWithFormat:@"tel:%@",phoneNumber];
    
    UIWebView * callWebview = [[UIWebView alloc] init];
    
    [callWebview loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:str]]];
    
    [self.weexInstance.rootView addSubview:callWebview];
    
}
-(void)setNotification:(NSInteger)number{
    [JPUSHService setBadge:number];
    [[UIApplication sharedApplication] setApplicationIconBadgeNumber:number];
}
-(void)exitApp{
    
    AppDelegate *app = (AppDelegate *)[UIApplication sharedApplication].delegate;
    UIWindow *window = app.window;
    
    [UIView animateWithDuration:1.0f animations:^{
        window.alpha = 0;
        window.frame = CGRectMake(0, window.bounds.size.width, 0, 0);
    } completion:^(BOOL finished) {
        exit(0);
    }];
}
-(void)getPhoneInfo:(WXModuleCallback)callback{
    UIDevice *device=[UIDevice currentDevice];
    NSString *phoneModel=[self deviceString];
    NSString *osVersion=device.systemVersion;
    NSString *osName=device.systemName;
    NSDictionary *doc=@{@"phoneModel":phoneModel,
                        @"osName":osName,
                        @"osVersion":osVersion,
                        @"brand":@"Apple"
                        };
    callback(doc);
}
-(id)getDevice{
    NSMutableDictionary *dc=[[NSMutableDictionary alloc] init];
    CGRect statusRect = [[UIApplication sharedApplication] statusBarFrame];
    CGRect rect = [[UIScreen mainScreen] bounds];
    CGSize size = rect.size;
    CGFloat width = size.width;
    CGFloat height = size.height;
    CGFloat statusBarHeight=statusRect.size.height;
    [dc setValue:[NSString stringWithFormat:@"%f",statusBarHeight/width*750] forKey:@"statusBarHeight"];
    //[dc setValue:[NSString stringWithFormat:@"%f",width] forKey:@"screenWidth"];
    
    [dc setValue:[NSString stringWithFormat:@"%f",height/width*750] forKey:@"screenHeight"];
    return dc;
}
- (NSString *)deviceString {
    // 需要#import "sys/utsname.h"
    struct utsname systeminfo;
    uname(&systeminfo);
    
    NSString *deviceString = [NSString stringWithCString:systeminfo.machine encoding:NSUTF8StringEncoding];
    
    //iPhone
    if ([deviceString isEqualToString:@"iPhone1,1"]) return @"iPhone 1G";
    if ([deviceString isEqualToString:@"iPhone1,2"]) return @"iPhone 3G";
    if ([deviceString isEqualToString:@"iPhone2,1"]) return @"iPhone 3GS";
    if ([deviceString isEqualToString:@"iPhone3,1"]) return @"iPhone 4";
    if ([deviceString isEqualToString:@"iPhone3,2"]) return @"Verizon iPhone 4";
    if ([deviceString isEqualToString:@"iPhone4,1"]) return @"iPhone 4S";
    
    if ([deviceString isEqualToString:@"iPhone5,1"]) return @"iPhone 5";
    if ([deviceString isEqualToString:@"iPhone5,2"]) return @"iPhone 5";
    if ([deviceString isEqualToString:@"iPhone5,3"]) return @"iPhone 5C";
    if ([deviceString isEqualToString:@"iPhone5,4"]) return @"iPhone 5C";
    
    if ([deviceString isEqualToString:@"iPhone6,1"]) return @"iPhone 5S";
    if ([deviceString isEqualToString:@"iPhone6,2"]) return @"iPhone 5S";
    
    if ([deviceString isEqualToString:@"iPhone7,1"]) return @"iPhone 6 Plus";
    if ([deviceString isEqualToString:@"iPhone7,2"]) return @"iPhone 6";
    
    if ([deviceString isEqualToString:@"iPhone8,1"]) return @"iPhone 6s";
    if ([deviceString isEqualToString:@"iPhone8,2"]) return @"iPhone 6s Plus";
    if([deviceString isEqualToString:@"iPhone8,4"])  return@"iPhone SE";
    
    
    if([deviceString isEqualToString:@"iPhone9,1"])  return@"iPhone 7";
    if([deviceString isEqualToString:@"iPhone9,3"])  return@"iPhone 7";
    if([deviceString isEqualToString:@"iPhone9,2"])  return@"iPhone 7 Plus";
    if([deviceString isEqualToString:@"iPhone9,4"])  return@"iPhone 7 Plus";
    
    
    if([deviceString isEqualToString:@"iPhone10,1"]) return@"iPhone 8";
    if([deviceString isEqualToString:@"iPhone10,4"]) return@"iPhone 8";
    if([deviceString isEqualToString:@"iPhone10,2"]) return@"iPhone 8 Plus";
    if([deviceString isEqualToString:@"iPhone10,5"]) return@"iPhone 8 Plus";
    
    if ([deviceString isEqualToString:@"iPhone10,3"])   return @"iPhone X";
    if ([deviceString isEqualToString:@"iPhone10,6"])   return @"iPhone X";
    
    if ([deviceString isEqualToString:@"iPhone11,8"])   return @"iPhone XR";
    if ([deviceString isEqualToString:@"iPhone11,2"])   return @"iPhone XS";
    
    if ([deviceString isEqualToString:@"iPhone11,6"])   return @"iPhone XS Max";
    if ([deviceString isEqualToString:@"iPhone11,4"])   return @"iPhone XS Max";
    
    
    return @"iPhone new";
    
    }
@end
