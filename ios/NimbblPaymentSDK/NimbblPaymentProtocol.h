//
//  NimbblPaymentProtocol.h
//  NimbblPaymentSDK
//
//  Created by Stany on 24/03/21.
//
#import <Foundation/Foundation.h>

@protocol NimbblPaymentDelegate
- (void) onPaymentResponse: (NSDictionary*_Nullable) response;
@end
