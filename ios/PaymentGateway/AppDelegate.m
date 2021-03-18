#import "AppDelegate.h"


#import <PaymentSDK/PaymentScreen.h>


@interface AppDelegate () <PaymentDelegate>

@end

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
  PaymentScreen *rootViewController = [[PaymentScreen alloc] init];
  rootViewController.orderID = @"rQv9VOzxn8Xnq3zg";
  rootViewController.accessKey = @"access_key_1MwvMkKkweorz0ry";
  rootViewController.delegate = self;
  self.window.rootViewController = rootViewController;
  [self.window makeKeyAndVisible];
  return YES;
}



- (void)getResponse:(NSString *)url{
  NSLog(@"Response: %@", url);
}

@end
