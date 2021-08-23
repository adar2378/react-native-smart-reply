#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(SmartReply, NSObject)

RCT_EXTERN_METHOD(generateSmartReply:(NSArray) messages
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

@end
