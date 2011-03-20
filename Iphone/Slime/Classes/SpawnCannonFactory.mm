#import "SpawnCannonFactory.h"

@implementation SpawnCannonFactory

- (SpawnCannon *) create:(float)x y:(float)y width:(float)width height:(float)height {
  SpawnCannon * canon = [super create:x y:y width:width height:height];
  if (canon != nil) {
   //todo
    //  CCSprite * my_sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:canon.texture]];
   // [canon setSprite:my_sprite];
  }
  return canon;
}

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (SpawnCannon *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[SpawnCannon alloc] init:spriteSheet x:x y:y width:width height:height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(SpawnCannon *)item {
}

- (void) createSprite:(GameItemCocos *)gameItem {
    CCSprite * sprite = [CCSprite spriteWithSpriteFrame:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:spawncannon_texture]];
    
    [gameItem setSprite:sprite];
}

@end
