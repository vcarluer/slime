#import "BumperFactory.h"

@implementation BumperFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Bumper *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[Bumper alloc] init:rootNode x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio ];
}



- (void) runFirstAnimations:(Bumper *)item {
  [item waitAnim];
}

- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [self create:my_x y:my_y width:my_width height:my_height powa:Default_Powa];
}

- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa {
  Bumper * bumper = [[Bumper alloc ] init:rootNode x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio];
  [bumper setPowa:my_powa];
  if (bumper != nil) {
    CCSprite * sprite = [CCSprite spriteWithSpriteFrameName:Texture_Wait];
    [bumper setSprite:sprite];
  }
  return bumper;
}

- (Bumper *) createBL:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa {
  return [self create:my_x + my_width / 2 y:my_y + my_height / 2 width:my_width height:my_height powa:my_powa];
}

@end
