//
//  BleManager.m
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "BleManager.h"

@implementation BleManager
+ (id)shared {
    static BleManager *sharedMyManager = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        sharedMyManager = [[self alloc] init];
        sharedMyManager.queue = dispatch_queue_create("com.terence.tz_ble_manager.queue", 0);
    });
    return sharedMyManager;
}
@end
