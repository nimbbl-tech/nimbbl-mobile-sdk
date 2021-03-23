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
  id<NimbblPaymentDelegate> paymentDelegate;
}

@end

@implementation NimbblCheckoutViewController

- (instancetype)initWithOptions:(NSDictionary<NSString *,id> *)options delegate:(id<NimbblPaymentDelegate>)delegate
{
    self = [super init];
    if (self){
      props = options;
      paymentDelegate = delegate;
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
  
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"getResponse" object:nil];
  [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(receiveNotification:) name:@"showError" object:nil];
  
  [self setUpReactNative];
}

- (NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
  return [[NSBundle bundleForClass:self.class] URLForResource:@"main" withExtension:@"jsbundle"];
}

- (void) setUpReactNative {
  RCTBridge *bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:nil];
  RCTRootView *rootView = [[RCTRootView alloc] initWithBridge:bridge moduleName:@"PaymentGateway" initialProperties:props];
  self.view = rootView;
}

-(void) receiveNotification:(NSNotification*)notification{
  if ([notification.name isEqualToString:@"getResponse"]) {
    NSString* response = notification.object;
    dispatch_async(dispatch_get_main_queue(), ^{
      [self dismissViewControllerAnimated:YES completion:^{
        [self->paymentDelegate onPaymentResponse: response];
      }];
      
    });
    
  }
  else if ([notification.name isEqualToString:@"showError"]) {
    dispatch_async(dispatch_get_main_queue(), ^{
      UIAlertController *alert = [UIAlertController alertControllerWithTitle:Nil message: notification.object preferredStyle:UIAlertControllerStyleAlert];
      UIAlertAction *action = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * action){
          // Ok action example
        [self dismissViewControllerAnimated:YES completion:Nil];
      }];
      
      [alert addAction:action];
      [self presentViewController:alert animated:YES completion:Nil];
    });
  }
}

@end
