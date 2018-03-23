//
//  BlePeripheralManager.h
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreBluetooth/CoreBluetooth.h>
#import "BleManager.h"
@class RNTzBle;
//#import <React/RCTEventEmitter.h>


@interface BlePeripheralManager : NSObject
+ (id)shared;
-(instancetype) initialize;
@property(nonatomic,strong) CBPeripheralManager *peripheralManager;
@property(nonatomic,weak) RNTzBle *emitter;

#pragma mark - Peripheral Utils
-(void) createService:(NSString *)uuid primary:(BOOL)primary;
-(void) createCharacteristic:(NSString *)uuid properties:(CBCharacteristicProperties)properties permissions:(CBAttributePermissions) permissions value:(NSData *)data;
-(void) appendCharacteristic:(NSString *)characteristicId toService:(NSString *)serviceId;
-(void) addService:(NSString *)serviceId;
-(void) advertise;


@end
