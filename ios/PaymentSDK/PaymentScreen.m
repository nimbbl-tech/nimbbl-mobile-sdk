//
//  PaymentScreen.m
//  PaymentSDK
//
//  Created by Stany on 12/03/21.
//

#import "PaymentScreen.h"
#import <React/RCTBridge.h>
#import <React/RCTBridgeDelegate.h>
#import <React/RCTRootView.h>

@interface PaymentScreen () <RCTBridgeDelegate>

@end

@implementation PaymentScreen

- (void)viewDidLoad {
  [super viewDidLoad];
  // Do any additional setup after loading the view.
  
  NSDictionary *props = @{ @"orderID": self.orderID, @"accessKey" : self.accessKey};
  
  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:nil];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:@"PaymentGateway" initialProperties:props];
  self.view = rootView;
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
  return [[NSBundle bundleForClass:self.class] URLForResource:@"main" withExtension:@"jsbundle"];
}


@end
