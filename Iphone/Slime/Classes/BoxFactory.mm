#import "BoxFactory.h"

@implementation BoxFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Box *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Box alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Box *)item {
}

- (Box *) create:(float)x y:(float)y width:(float)width height:(float)height {
  Box * box = [super create:x param1:y param2:width param3:height];
  if (box != nil) {
    CCSprite * sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:Box.texture]];
    [box setSprite:sprite];
  }
  return box;
}

@end
