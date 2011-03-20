#import "GameItemPhysicFx.h"



@implementation GameItemPhysicFx

@synthesize world, worldRatio;

- (id) init:(CCNode *)my_node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:my_node x:my_x y:my_y width:my_width height:my_height])) {
    world = my_world;
    worldRatio = my_worldRatio;
  }
  return self;
}

- (void) destroy {
  world = nil;
  [super destroy];
}

- (void) dealloc {
 // [world release];
  [super dealloc];
}

@end
