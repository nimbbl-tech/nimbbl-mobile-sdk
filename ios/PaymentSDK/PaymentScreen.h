//
//  PaymentScreen.h
//  PaymentSDK
//
//  Created by Stany on 12/03/21.
//


#import <UIKit/UIKit.h>


@protocol PaymentDelegate
- (void) getResponse: (NSString*_Nullable) response;
@end

NS_ASSUME_NONNULL_BEGIN

@interface PaymentScreen : UIViewController

@property (nonatomic, strong) NSString* orderID;
@property (nonatomic, strong) NSString* accessKey;
@property (nonatomic, strong) id<PaymentDelegate> delegate;

@end

NS_ASSUME_NONNULL_END
