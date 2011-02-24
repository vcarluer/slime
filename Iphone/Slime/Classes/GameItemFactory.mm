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
    [rootNode removeChild:spriteSheet param1:YES];
    rootNode = nil;
    isAttached = NO;
  }
}

@end
