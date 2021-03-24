//
//  NimbblCheckoutViewController.h
//  NimbblPaymentSDK
//
//  Created by Stany on 23/03/21.
//

#import <UIKit/UIKit.h>

@protocol NimbblPaymentDelegate
- (void) onPaymentResponse: (NSString*_Nullable) response;
- (void) onPaymentCancel;
@end


@interface NimbblCheckoutViewController : UIViewController

- (instancetype _Nonnull )initWithOptions:(NSDictionary<NSString *,id> *_Nonnull)options delegate:(id<NimbblPaymentDelegate>_Nonnull)delegate;

@end


