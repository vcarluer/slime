#import "SpawnPortalFactory.h"

@implementation SpawnPortalFactory

- (SpawnPortal *) createAndMove:(float)my_x y:(float)my_y moveBy:(float)moveBy speed:(float)speed {
  SpawnPortal * portal = [self create:my_x y:my_y];
  if (portal != nil) {
    [portal MovePortalInLine:moveBy speed:speed];
  }
  return portal;
}

- (void) createAnimList {
  [self createAnim:Anim_Spawn_Portal frameCount:4];
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (SpawnPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
 // return [[[SpawnPortal alloc] init:spriteSheet x:my_x y:my_y width:my_width height:my_height] autorelease];
    return nil;
}

- (void) runFirstAnimations:(SpawnPortal *)item {
  [item createPortal];
}

@end
