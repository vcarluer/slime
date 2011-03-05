#import "GoalPortalFactory.h"

@implementation GoalPortalFactory

- (void) createAnimList {
  [self createAnim:Anim_Goal_Portal frameCount:4];
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (GoalPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[GoalPortal alloc] init:spriteSheet x:x y:y width:width height:height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(GoalPortal *)item {
  [item createPortal];
}


@end
