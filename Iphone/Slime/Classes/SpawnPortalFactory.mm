#import "SpawnPortalFactory.h"

@implementation SpawnPortalFactory

- (SpawnPortal *) createAndMove:(float)x y:(float)y moveBy:(float)moveBy speed:(float)speed {
  SpawnPortal * portal = [self create:x param1:y];
  if (portal != nil) {
    [portal MovePortalInLine:moveBy param1:speed];
  }
  return portal;
}

- (void) createAnimList {
  [self createAnim:Anim_Spawn_Portal param1:4];
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (SpawnPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[SpawnPortal alloc] init:spriteSheet param1:x param2:y param3:width param4:height] autorelease];
}

- (void) runFirstAnimations:(SpawnPortal *)item {
  [item createPortal];
}

@end
