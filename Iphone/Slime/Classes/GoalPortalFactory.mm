//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "GoalPortalFactory.h"

@implementation GoalPortalFactory

- (void) createAnimList {
  [self createAnim:Anim_Goal_Portal framecount:4];
}

- (NSString *) getPlistPng {
  return @"labo";
}

+ (GoalPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[GoalPortal alloc] init:spriteSheet x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(GoalPortal *)item {
  [item createPortal];
}

@end
