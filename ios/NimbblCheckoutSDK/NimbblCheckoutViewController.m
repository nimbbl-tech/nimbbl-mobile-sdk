//
//  NimbblCheckoutViewController.m
//  NimbblPaymentSDK
//
//  Created by Stany on 23/03/21.
//

#import "NimbblCheckoutViewController.h"
#import <React/RCTBridge.h>
#import <React/RCTRootView.h>
#import <React/RCTBridgeDelegate.h>
#import <React/RCTBundleURLProvider.h>


@interface NimbblCheckoutViewController ()<RCTBridgeDelegate> {
  NSDictionary<NSString*,id> *props;
  id<NimbblCheckoutDelegate> checkoutDelegate;
}

@end

@implementation NimbblCheckoutViewController

- (void)dealloc
{
  [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (instancetype)initWithOptions:(NSDictionary<NSString *,id> *)options delegate:(id<NimbblCheckoutDelegate>)delegate
{
    self = [super init];
    if (self){
      props = options;
      checkoutDelegate = delegate;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"onPaymentSuccess" object:nil];
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"onError" object:nil];
  
  [self setUpReactNative];
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
  return [[NSBundle bundleForClass:self.class] URLForResource:@"main" withExtension:@"jsbundle"];
}

- (void) setUpReactNative {
  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:nil];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:@"Nimbbl" initialProperties:props];
  self.view = rootView;
}

-(void) receiveNotification:(NSNotification*)notification{
  if ([notification.name isEqualToString:@"onPaymentSuccess"]) {
    NSDictionary* response = notification.object;
    dispatch_async(dispatch_get_main_queue(), ^{
      [self dismissViewControllerAnimated:NO completion:^{
        [self->checkoutDelegate onPaymentSuccess: response];
      }];
    });
  }
  else if ([notification.name isEqualToString:@"onError"]) {
    NSString* error = notification.object;
    dispatch_async(dispatch_get_main_queue(), ^{
      [self dismissViewControllerAnimated:NO completion:^{
        [self->checkoutDelegate onError:error];
      }];
    });
  }
}

@end
