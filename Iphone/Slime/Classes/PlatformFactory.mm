#import "PlatformFactory.h"

@implementation PlatformFactory

- (Platform *) create:(float)x y:(float)y width:(float)width height:(float)height {
  Platform * platform = [super create:x param1:y param2:width param3:height];
  if (platform != nil) {
 //todo
	  CCSprite * sprite; //= [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:platform->texture()]];
    [platform setSprite:sprite];
  }
  return platform;
}

- (void) createAnimList {
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (Platform *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Platform alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Platform *)item {
}

@end
