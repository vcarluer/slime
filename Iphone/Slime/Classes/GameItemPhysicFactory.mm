//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "GameItemPhysicFactory.h"

@implementation GameItemPhysicFactory

- (void) Attach:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
  rootNode = attachNode;
  world = attachWorld;
  worldRatio = attachWorldRatio;
  [self initAnimation];
  [rootNode addChild:spriteSheet];
  isAttached = YES;
}

- (void) Detach {
  if (isAttached && spriteSheet != nil && rootNode != nil) {
    [rootNode removeChild:spriteSheet param1:YES];
    rootNode = nil;
    world = nil;
    worldRatio = 0.0f;
    isAttached = NO;
  }
}

- (void) dealloc {
  [world release];
  [super dealloc];
}

@end
