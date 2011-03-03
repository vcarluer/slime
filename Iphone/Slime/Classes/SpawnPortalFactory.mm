//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "SpawnPortalFactory.h"

@implementation SpawnPortalFactory


+ (SpawnPortal *) createAndMove:(float)my_x y:(float)my_y moveBy:(float)moveBy speed:(float)speed {
	CCNode * tempNode;
	SpawnPortal * portal = [[SpawnPortal alloc] init:tempNode x:my_x y:my_y width:0 height:0];

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
