//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "ItemFactoryBase.h"
#import "GameItem.h"

@implementation ItemFactoryBase

- (id) init {
  if (self = [super init]) {
    isInit = NO;
    isAttached = NO;
    ratio = 1.0f;
  }
  return self;
}

- (void) initAnimation {
  if (!isInit) {
    [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFrames:[[self getPlistPng] stringByAppendingString:@".plist"]];
    spriteSheet = [CCSpriteSheet spriteSheet:[[self getPlistPng] stringByAppendingString:@".png"]];
    sharedAnimations = [[[NSMutableDictionary alloc] init] autorelease];
    [self createAnimList];
    isInit = YES;
  }
}

- (void) createAnimList {
}

- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
  [sharedAnimations put:animName param1:[GameItem createAnim:animName param1:frameCount]];
}

- (NSString *) getPlist {
}

- (NSString *) getPng {
}

- (id) create {
  return [self create:0 y:0];
}

- (id) create:(float)my_x y:(float)my_y {
  return [self create:my_x y:my_y width:0 height:0];
}

- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  if (isAttached) {
    id item = [self instantiate:my_x y:my_y width:my_width height:my_height];
    [item setAnimationList:sharedAnimations];
    [self runFirstAnimations:item];
    return item;
  }
   else {
    return nil;
  }
}

- (NSString *) getPlistPng {
	return @"labo";
}

- (id) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
}

- (void) runFirstAnimations:(id)item {
}

- (void) dealloc {
  [sharedAnimations release];
  [spriteSheet release];
  [rootNode release];
  [super dealloc];
}

@end
