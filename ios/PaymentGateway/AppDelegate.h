#import <React/RCTBridgeDelegate.h>
#import <React/RCTBridgeModule.h>
#import <UIKit/UIKit.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, RCTBridgeDelegate, RCTBridgeModule>

@property (nonatomic, strong) UIWindow *window;


@end
