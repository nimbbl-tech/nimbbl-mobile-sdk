//
//  ReactNativeModalBridge.m
//  NativeModulesCommunication
//
//  Created by Fabrizio Duroni on 05.11.18.
//

#import "ReactNativeModalBridge.h"
#import <React/RCTLog.h>

@implementation ReactNativeModalBridge
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getUrl:(NSString *) url) {
    RCTLogInfo(@"Url in SDK: %@", url);
    [[NSNotificationCenter defaultCenter] postNotificationName:@"getUrl" object:url];
}

RCT_EXPORT_METHOD(showError:(NSString *) error) {
    RCTLogInfo(@"Error: %@", error);
    [[NSNotificationCenter defaultCenter] postNotificationName:@"showError" object:error];
}

@end
