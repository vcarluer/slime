//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCAnimation.h"
#import "CCNode.h"
#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"

@interface ItemFactoryBase : NSObject {
  NSMutableDictionary * sharedAnimations;
  CCSpriteSheet * spriteSheet;
  BOOL isInit;
  BOOL isAttached;
  CCNode * rootNode;
  float ratio;
}

- (id) init;
- (void) initAnimation;
- (void) createAnimList;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount;
- (NSString *) getPlistPng;
- (id) create;
- (id) create:(float)my_x y:(float)my_y;
- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (id) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(id)item;
@end
