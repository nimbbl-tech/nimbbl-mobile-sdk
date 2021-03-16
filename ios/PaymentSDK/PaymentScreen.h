//
//  PaymentScreen.h
//  PaymentSDK
//
//  Created by Stany on 12/03/21.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface PaymentScreen : UIViewController

@property (nonatomic, strong) NSString* orderID;
@property (nonatomic, strong) NSString* accessKey;

@end

NS_ASSUME_NONNULL_END
