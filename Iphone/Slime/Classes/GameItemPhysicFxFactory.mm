#import "GameItemPhysicFxFactory.h"

@implementation GameItemPhysicFxFactory
- (void) attach:(Level *)my_level attachNode:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
  //level = level;
  rootNode = attachNode;
  world = attachWorld;
  worldRatio = attachWorldRatio;
  [self initAnimation];
  //[rootNode addChild:spriteSheet];
  isAttached = YES;
}

- (void) detach {
  if (isAttached && spriteSheet != nil && rootNode != nil) {
    [rootNode removeChild:spriteSheet cleanup:YES];
    rootNode = nil;
    world = nil;
    worldRatio = 0.0f;
    isAttached = NO;
  }
}

- (void) dealloc {
 // [world release];
  [super dealloc];
}


@end
