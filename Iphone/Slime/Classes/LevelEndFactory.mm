#import "LevelEndFactory.h"

@implementation LevelEndFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"";
}

- (LevelEnd *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[LevelEnd alloc]  init:spriteSheet x:x y:y width:width height:height world:world worldRatio:32] autorelease];
}

- (void) runFirstAnimations:(LevelEnd *)item {
}

@end
