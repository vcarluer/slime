#import "GoalPortalFactory.h"

@implementation GoalPortalFactory

- (void) createAnimList {
  [self createAnim:Anim_Goal_Portal frameCount:4];
}

- (NSString *) getPlistPng {
  return @"labo";
}

- (GoalPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[GoalPortal alloc] init:spriteSheet x:my_x y:my_y width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(GoalPortal *)item {
  [item createPortal];
}

- (GoalPortal *) create {
	return [self create:0 y:0];
}

- (GoalPortal *) create:(float)my_x y:(float)my_y {
	return [self create:my_x y:my_y width:0 height:0];
}

- (GoalPortal *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if (isAttached) {
		GoalPortal *  item = [self instantiate:my_x y:my_y width:my_width height:my_height];
		[level addItemToAdd:item];
		return item;
	}
	else {
		return nil;
	}
}

@end
