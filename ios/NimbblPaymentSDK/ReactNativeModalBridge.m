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
    [[NSNotificationCenter defaultCenter] postNotificationName:@"onResponse" object:response];
}

RCT_EXPORT_METHOD(onErrorPopUp) {
    [[NSNotificationCenter defaultCenter] postNotificationName:@"onErrorPopUp" object:nil];
}

@end
