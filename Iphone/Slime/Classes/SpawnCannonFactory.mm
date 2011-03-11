#import "SpawnCannonFactory.h"

@implementation SpawnCannonFactory

- (SpawnCannon *) create:(float)x y:(float)y width:(float)width height:(float)height {
  SpawnCannon * canon = [super create:x param1:y param2:width param3:height];
  if (canon != nil) {
    CCSprite * sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:SpawnCannon.texture]];
    [canon setSprite:sprite];
  }
  return canon;
}

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (SpawnCannon *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[SpawnCannon alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(SpawnCannon *)item {
}

@end
