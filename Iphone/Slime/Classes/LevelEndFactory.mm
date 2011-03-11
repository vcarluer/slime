#import "LevelEndFactory.h"

@implementation LevelEndFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"";
}

- (LevelEnd *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[LevelEnd alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(LevelEnd *)item {
}

@end
