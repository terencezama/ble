//
//  BleCentralManager.m
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "BleCentralManager.h"
#import "BleManager.h"

@interface BleCentralManager()<CBCentralManagerDelegate>

@end

@implementation BleCentralManager
+ (id)shared {
    static BleCentralManager *sharedMyManager = nil;
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
        _centralManager      = [[CBCentralManager alloc] initWithDelegate:self queue:[[BleManager shared] queue]];
    }
    return self;
}
-(instancetype) initialize{
    
    return self;
    
}
#pragma mark - CBCentralManagerDelegate
- (void)centralManagerDidUpdateState:(CBCentralManager *)central{
    
}
- (void)centralManager:(CBCentralManager *)central willRestoreState:(NSDictionary<NSString *, id> *)dict{
    
}
- (void)centralManager:(CBCentralManager *)central didDiscoverPeripheral:(CBPeripheral *)peripheral advertisementData:(NSDictionary<NSString *, id> *)advertisementData RSSI:(NSNumber *)RSSI{
    
}
- (void)centralManager:(CBCentralManager *)central didConnectPeripheral:(CBPeripheral *)peripheral{
    
}
- (void)centralManager:(CBCentralManager *)central didFailToConnectPeripheral:(CBPeripheral *)peripheral error:(nullable NSError *)error{
    
}
- (void)centralManager:(CBCentralManager *)central didDisconnectPeripheral:(CBPeripheral *)peripheral error:(nullable NSError *)error{
    
}
@end
