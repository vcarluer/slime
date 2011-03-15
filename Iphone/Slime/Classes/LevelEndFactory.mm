#import "LevelEndFactory.h"

@implementation LevelEndFactory

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"";
}

- (LevelEnd *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[LevelEnd alloc]  init:spriteSheet x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(LevelEnd *)item {
}

@end
