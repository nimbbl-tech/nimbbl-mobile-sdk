//
//  ReactNativeModalBridge.m
//  NativeModulesCommunication
//
//  Created by Fabrizio Duroni on 05.11.18.
//

#import "ReactNativeModalBridge.h"

@implementation ReactNativeModalBridge
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(getResponse:(NSString *) url) {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"getResponse" object:url];
}

RCT_EXPORT_METHOD(showError:(NSString *) error) {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"showError" object:error];
}

@end
