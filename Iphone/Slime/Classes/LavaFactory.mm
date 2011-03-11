#import "LavaFactory.h"

@implementation LavaFactory

- (void) createAnimList {
  [self createAnim:@"lava" param1:1 param2:0.5f];
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Lava *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Lava alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Lava *)item {
  [item initAnimation];
}

@end
