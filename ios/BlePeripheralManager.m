//
//  BlePeripheralManager.m
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "BlePeripheralManager.h"
#import <React/RCTLog.h>
#import "RNTzBle.h"
@interface BlePeripheralManager()<CBPeripheralManagerDelegate>
@property(nonatomic,strong) NSMutableDictionary<NSString *,CBMutableService *> *services;
@property(nonatomic,strong) NSMutableDictionary<NSString *,CBMutableCharacteristic *> *characteristics;
@end
@implementation BlePeripheralManager
+ (id)shared {
    static BlePeripheralManager *sharedMyManager = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedMyManager = [[self alloc] init];
    });
    return sharedMyManager;
}

#pragma mark - Initializaton

-(instancetype)init{
    self = [super init];
    if(self){
        //Initizations
        self.services                   = [[NSMutableDictionary alloc] initWithCapacity:10];
        self.characteristics            = [[NSMutableDictionary alloc] initWithCapacity:10];
    }
    return self;
}
-(instancetype) initialize{
    if(self.peripheralManager){
        [self.services removeAllObjects];
        [self.characteristics removeAllObjects];
        [self.peripheralManager stopAdvertising];
        [self.peripheralManager removeAllServices];
        self.peripheralManager          = nil;
        self.peripheralManager          = [[CBPeripheralManager alloc] initWithDelegate:self queue:[[BleManager shared] queue]];
    }else{
        self.peripheralManager          = [[CBPeripheralManager alloc] initWithDelegate:self queue:[[BleManager shared] queue]];
    }
    return self;
    
}
#pragma mark - Peripheral Manager Delegate
- (void)peripheralManagerDidUpdateState:(CBPeripheralManager *)peripheral{
    [_emitter pm_notify:@{
                          @"didUpdateState":@{
                                  @"state":@(peripheral.state),
                                  @"desc":[self managerStateDesc:peripheral.state]
                                  },
                          @"event":@"didUpdateState"
                          }];
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral willRestoreState:(NSDictionary<NSString *, id> *)dict;{

}
- (void)peripheralManagerDidStartAdvertising:(CBPeripheralManager *)peripheral error:(nullable NSError *)error{
    NSMutableDictionary *body       = [[NSMutableDictionary alloc] initWithCapacity:3];
    if(error){
        body[@"error"]              = [error localizedDescription];
        body[@"desc"]               = [error localizedDescription];
    }else{
        body[@"error"]              = @(false);
        body[@"desc"]               = @"success";
    }
    [_emitter pm_notify:@{
                          @"didStartAdvertising":body,
                          @"event":@"didStartAdvertising"
                          }];
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral didAddService:(CBService *)service error:(nullable NSError *)error{
    NSMutableDictionary *body       = [[NSMutableDictionary alloc] initWithDictionary:@{
                                                                                        @"uuid":service.UUID.UUIDString
                                                                                        }];
    if(error){
        body[@"error"]              = [error localizedDescription];
        body[@"desc"]               = [error localizedDescription];
    }else{
        body[@"error"]              = @(false);
        body[@"desc"]               = @"success";
    }
    [_emitter pm_notify:@{
                          @"didAddService":body,
                          @"event":@"didAddService"
                          }];
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral central:(CBCentral *)central didSubscribeToCharacteristic:(CBCharacteristic *)characteristic{
    
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral central:(CBCentral *)central didUnsubscribeFromCharacteristic:(CBCharacteristic *)characteristic{
    
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral didReceiveReadRequest:(CBATTRequest *)request;{
    
}
- (void)peripheralManager:(CBPeripheralManager *)peripheral didReceiveWriteRequests:(NSArray<CBATTRequest *> *)requests;{
    
}
- (void)peripheralManagerIsReadyToUpdateSubscribers:(CBPeripheralManager *)peripheral;{
    
}
#pragma mark - Peripheral Utils
-(void) createService:(NSString *)uuid primary:(BOOL)primary{
    CBUUID *serviceUuid = [CBUUID UUIDWithString:uuid];
    CBMutableService *service   = [[CBMutableService alloc] initWithType:serviceUuid primary:primary];
    self.services[uuid]         = service;
}

-(void) createCharacteristic:(NSString *)uuid properties:(CBCharacteristicProperties)properties permissions:(CBAttributePermissions) permissions value:(NSData *)data{
    CBUUID *charUuid = [CBUUID UUIDWithString:uuid];
    CBMutableCharacteristic *charactheristic = [[CBMutableCharacteristic alloc] initWithType:charUuid properties:properties value:data permissions:permissions];
    self.characteristics[uuid]  = charactheristic;
}
-(void) appendCharacteristic:(NSString *)characteristicId toService:(NSString *)serviceId{
    CBMutableService *service                   = self.services[serviceId];
    CBMutableCharacteristic *characteristic     = self.characteristics[characteristicId];
    NSMutableArray *mcharacteristics            = [[NSMutableArray alloc] initWithArray:service.characteristics];
    [mcharacteristics addObject:characteristic];
    service.characteristics                     = mcharacteristics;
}
-(void) addService:(NSString *)serviceId{
    CBMutableService *service                   = self.services[serviceId];
    [self.peripheralManager addService:service];
}
-(void) advertise{
    NSMutableArray *servicesUUIDS               = [[NSMutableArray alloc] initWithCapacity:self.services.count];
    for(NSString *key in self.services){
        CBMutableService *service = self.services[key];
        [servicesUUIDS addObject:service.UUID];
    }
    [self.peripheralManager startAdvertising:@{CBAdvertisementDataServiceUUIDsKey:servicesUUIDS}];
}
#pragma mark - Enum Utils
-(NSString *) managerStateDesc:(CBManagerState) state{
    switch (state) {
        case CBManagerStateUnknown:
            return @"ManagerStateUnknown";
        case CBManagerStateResetting:
            return @"ManagerStateResetting";
        case CBManagerStateUnsupported:
            return @"ManagerStateUnsupported";
        case CBManagerStateUnauthorized:
            return @"ManagerStateUnauthorized";
        case CBManagerStatePoweredOff:
            return @"ManagerStatePoweredOff";
        case CBManagerStatePoweredOn:
            return @"ManagerStatePoweredOn";
    }
    return nil;
}
@end
