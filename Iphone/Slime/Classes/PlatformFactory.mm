#import "PlatformFactory.h"


@implementation PlatformFactory

- (Platform *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  
    Platform * my_platform = [[Platform alloc ] init:rootNode x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio];;
   
  if (my_platform != nil) {
      CCSprite * sprite = [CCSprite spriteWithSpriteFrameName:@"metal2.png"];
      [my_platform setSprite:sprite];
  }
  return my_platform;
}

- (void) createAnimList {
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (Platform *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[Platform alloc]init:rootNode x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

/*
- (void) runFirstAnimations:(Platform *)item {
}
*/


@end
