
#import "RNTzBle.h"
#import <React/RCTLog.h>
#import <React/RCTConvert.h>
#import "BlePeripheralManager.h"

@implementation RNTzBle{
    bool hasListeners;
}
- (dispatch_queue_t)methodQueue
{
    return dispatch_queue_create("com.terence.tz_ble_manager", DISPATCH_QUEUE_SERIAL);
}
- (NSDictionary *)constantsToExport
{
    return @{
             @"CharacteristicBroadcast" : @(CBCharacteristicPropertyBroadcast),
             @"CharacteristicRead" : @(CBCharacteristicPropertyRead),
             @"CharacteristicWriteWithoutResponse" : @(CBCharacteristicPropertyWriteWithoutResponse),
             @"CharacteristicWrite" : @(CBCharacteristicPropertyWrite),
             @"CharacteristicNotify" : @(CBCharacteristicPropertyNotify),
             @"CharacteristicIndicate" : @(CBCharacteristicPropertyIndicate),
             @"CharacteristicAuthenticatedSignedWrites" : @(CBCharacteristicPropertyAuthenticatedSignedWrites),
             @"CharacteristicExtendedProperties" : @(CBCharacteristicPropertyExtendedProperties),
             
             @"PermissionsReadable" : @(CBAttributePermissionsReadable),
             @"PermissionsWriteable" : @(CBAttributePermissionsWriteable),
             @"PermissionsReadEncryptionRequired" : @(CBAttributePermissionsReadEncryptionRequired),
             @"PermissionsWriteEncryptionRequired" : @(CBAttributePermissionsWriteEncryptionRequired),
             
             @"ManagerStateUnknown" : @(CBManagerStateUnknown),
             @"ManagerStateResetting" : @(CBManagerStateResetting),
             @"ManagerStateUnsupported" : @(CBManagerStateUnsupported),
             @"ManagerStateUnauthorized" : @(CBManagerStateUnauthorized),
             @"ManagerStatePoweredOff" : @(CBManagerStatePoweredOff),
             @"ManagerStatePoweredOn" : @(CBManagerStatePoweredOn)
             };
}
- (NSArray<NSString *> *)supportedEvents
{
    return @[@"PeripheralManager"];
}
#pragma mark - Events
// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

-(void) pm_notify:(id)body{
    if(hasListeners){
        [self sendEventWithName:@"PeripheralManager" body:body];
    }else{
        RCTLog(@"Fail to notify pm_notify");
    }
}

RCT_EXPORT_MODULE()
RCT_EXPORT_METHOD(addEvent:(NSString *)name location:(NSString *)location)
{
    RCTLogInfo(@"Pretending to nice an event %@ at %@", name, location);
}

#pragma mark - Peripheral Manager
RCT_EXPORT_METHOD(pm_init)
{
    [[[BlePeripheralManager shared] initialize] setEmitter:self];
}
RCT_EXPORT_METHOD(pm_createService:(NSString *)uuid primary:(BOOL)primary)
{
    [[BlePeripheralManager shared] createService:uuid primary:primary];
}
RCT_EXPORT_METHOD(pm_createCharacteristic:(NSString *)uuid properties:(CBCharacteristicProperties)properties permissions:(CBAttributePermissions) permissions value:(NSDictionary *)sdata)
{
    NSData *data    = [NSJSONSerialization dataWithJSONObject:sdata options:0 error:nil];
    [[BlePeripheralManager shared] createCharacteristic:uuid properties:properties permissions:permissions value:data];
}
RCT_EXPORT_METHOD(pm_appendCharacteristic:(NSString *)characteristicId toService:(NSString *)serviceId)
{
    [[BlePeripheralManager shared] appendCharacteristic:characteristicId toService:serviceId];
}
RCT_EXPORT_METHOD(pm_addService:(NSString *)serviceId)
{
    [[BlePeripheralManager shared] addService:serviceId];
}
RCT_EXPORT_METHOD(pm_advertise)
{
    [[BlePeripheralManager shared] advertise];
}
@end
  
