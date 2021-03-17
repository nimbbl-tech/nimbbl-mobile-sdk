//
//  PaymentScreen.m
//  PaymentSDK
//
//  Created by Stany on 12/03/21.
//

#import "PaymentScreen.h"
#import <React/RCTBridge.h>
#import <React/RCTRootView.h>
#import <React/RCTBridgeDelegate.h>



@interface PaymentScreen () <RCTBridgeDelegate>

@end

@implementation PaymentScreen


- (void)viewDidLoad {
  [super viewDidLoad];
  // Do any additional setup after loading the view.
  
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"getUrl" object:nil];
  
  [self setUpReactNative];
  
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
  return [[NSBundle bundleForClass:self.class] URLForResource:@"main" withExtension:@"jsbundle"];
}

- (void) setUpReactNative {
  NSDictionary *props = @{ @"orderID": self.orderID, @"accessKey" : self.accessKey};
  
  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:nil];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:@"PaymentGateway" initialProperties:props];
  self.view = rootView;
}

-(void) receiveNotification:(NSNotification*)notification{
  if ([notification.name isEqualToString:@"getUrl"])
  {
    NSString* url = notification.object;
    [self.delegate getURL:url];
  }
}

@end
