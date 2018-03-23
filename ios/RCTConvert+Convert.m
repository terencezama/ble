//
//  RCTConvert+Convert.m
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RCTConvert+Convert.h"
#import <CoreBluetooth/CoreBluetooth.h>
@implementation RCTConvert (Convert)

RCT_ENUM_CONVERTER(CBCharacteristicProperties, (
                                                @{@"CharacteristicBroadcast" : @(CBCharacteristicPropertyBroadcast),
                                                  @"CharacteristicRead" : @(CBCharacteristicPropertyRead),
                                                  @"CharacteristicWriteWithoutResponse" : @(CBCharacteristicPropertyWriteWithoutResponse),
                                                  @"CharacteristicWrite" : @(CBCharacteristicPropertyWrite),
                                                  @"CharacteristicNotify" : @(CBCharacteristicPropertyNotify),
                                                  @"CharacteristicIndicate" : @(CBCharacteristicPropertyIndicate),
                                                  @"CharacteristicAuthenticatedSignedWrites" : @(CBCharacteristicPropertyAuthenticatedSignedWrites),
                                                  @"CharacteristicExtendedProperties" : @(CBCharacteristicPropertyExtendedProperties)}
                                                ),CBCharacteristicPropertyRead,integerValue);
RCT_ENUM_CONVERTER(CBAttributePermissions, (
                        @{@"PermissionsReadable" : @(CBAttributePermissionsReadable),
                          @"PermissionsWriteable" : @(CBAttributePermissionsWriteable),
                          @"PermissionsReadEncryptionRequired" : @(CBAttributePermissionsReadEncryptionRequired),
                          @"PermissionsWriteEncryptionRequired" : @(CBAttributePermissionsWriteEncryptionRequired)}
                                                ),CBCharacteristicPropertyRead,integerValue);
RCT_ENUM_CONVERTER(CBManagerState, (@{
                                      @"ManagerStateUnknown" : @(CBManagerStateUnknown),
                                      @"ManagerStateResetting" : @(CBManagerStateResetting),
                                      @"ManagerStateUnsupported" : @(CBManagerStateUnsupported),
                                      @"ManagerStateUnauthorized" : @(CBManagerStateUnauthorized),
                                      @"ManagerStatePoweredOff" : @(CBManagerStatePoweredOff),
                                      @"ManagerStatePoweredOn" : @(CBManagerStatePoweredOn)
                                      }), CBManagerStateUnknown, integerValue);
@end
