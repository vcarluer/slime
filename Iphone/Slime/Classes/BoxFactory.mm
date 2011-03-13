#import "BoxFactory.h"

@implementation BoxFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Box *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Box alloc] init:spriteSheet x:x y:y width:width height:height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Box *)item {
}

- (Box *) create:(float)x y:(float)y width:(float)width height:(float)height {
  Box * my_box = [super create:x y:y width:width height:height];
  if (my_box != nil) {
    CCSprite * sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:my_box.texture]];
    [my_box setSprite:sprite];
  }
  return my_box;
}

@end
