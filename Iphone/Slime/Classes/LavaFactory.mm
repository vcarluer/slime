#import "LavaFactory.h"

@implementation LavaFactory

- (void) createAnimList {
  [Lava createAnim:@"lava" frameCount:1 interval:0.5f];
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Lava *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Lava alloc] init:spriteSheet x:x y:y width:width height:height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Lava *)item {
  [item initAnimation];
}

@end
