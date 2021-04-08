//
//  ReactNativeModalBridge.m
//  NativeModulesCommunication
//
//  Created by Fabrizio Duroni on 05.11.18.
//
#import <Foundation/Foundation.h>
#import "ReactNativeModalBridge.h"

@implementation ReactNativeModalBridge
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(onResponse:(NSDictionary *) response) {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"onPaymentSuccess" object:response];
}

RCT_EXPORT_METHOD(onError:(NSString *) error) {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"onError" object:error];
}

@end
