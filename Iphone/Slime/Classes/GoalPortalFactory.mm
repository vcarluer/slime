#import "GoalPortalFactory.h"

@implementation GoalPortalFactory

- (void) createAnimList {
  [self createAnim:Anim_Goal_Portal param1:4];
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (GoalPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[GoalPortal alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (void) runFirstAnimations:(GoalPortal *)item {
  [item createPortal];
}

@end
