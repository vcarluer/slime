//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
#import "BumperFactory.h"

@implementation BumperFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Bumper *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[Bumper alloc] init:rootNode x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio ] autorelease];
}



- (void) runFirstAnimations:(Bumper *)item {
  [item waitAnim];
}

- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [self create:my_x y:my_y width:my_width height:my_height powa:Default_Powa];
}

- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa {
  Bumper * bumper = [Bumper init:my_x y:my_y width:my_width my_height:my_height];
  [bumper setPowa:my_powa];
  if (bumper != nil) {
    CCSprite * sprite = [CCSprite sprite:Texture_Wait];
    [bumper setSprite:sprite];
  }
  return bumper;
}

@end
