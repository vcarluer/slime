#import "GameItemCocosFactory.h"

@implementation GameItemCocosFactory

- (void) attach:(Level *)level attachNode:(CCNode *)attachNode {
  level = level;
  rootNode = attachNode;
  [self initAnimation];
  isAttached = YES;
}

- (void) detach {
  if (isAttached && spriteSheet != nil && rootNode != nil) {
    level = nil;
    rootNode = nil;
    isAttached = NO;
  }
}

@end
