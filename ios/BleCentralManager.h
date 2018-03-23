//
//  BleCentralManager.h
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreBluetooth/CoreBluetooth.h>
@interface BleCentralManager : NSObject
@property(nonatomic,strong) CBCentralManager *centralManager;
@end
