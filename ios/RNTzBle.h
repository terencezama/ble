
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RNTzBle : RCTEventEmitter <RCTBridgeModule>
-(void) pm_notify:(id)body;
@end
  
