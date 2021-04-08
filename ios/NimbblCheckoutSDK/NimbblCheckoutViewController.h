//
//  NimbblCheckoutViewController.h
//  NimbblPaymentSDK
//
//  Created by Stany on 23/03/21.
//

#import <UIKit/UIKit.h>

@protocol NimbblCheckoutDelegate
- (void) onPaymentResponse: (NSDictionary*_Nullable) response;
@end

@interface NimbblCheckoutViewController : UIViewController

- (instancetype _Nonnull )initWithOptions:(NSDictionary<NSString *,id> *_Nonnull)options delegate:(id<NimbblCheckoutDelegate>_Nonnull)delegate;

@end


