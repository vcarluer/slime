//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
#import "GameItemFactory.h"

@implementation GameItemFactory

- (void) Attach:(CCNode *)attachNode {
  rootNode = attachNode;
  [self initAnimation];
  [rootNode addChild:spriteSheet];
  isAttached = YES;
}

- (void) Detach {
  if (isAttached && spriteSheet != nil && rootNode != nil) {
    [rootNode removeChild:spriteSheet cleanup:YES];
    rootNode = nil;
    isAttached = NO;
  }
}

@end
