//
//  BleManager.h
//  RNTzBle
//
//  Created by Terence Zama on 21/03/2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BleManager : NSObject
@property(nonatomic,strong) dispatch_queue_t queue;

+ (id)shared ;
@end
