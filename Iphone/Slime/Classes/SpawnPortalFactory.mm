#import "SpawnPortalFactory.h"

@implementation SpawnPortalFactory

- (SpawnPortal *) createAndMove:(float)my_x y:(float)my_y moveBy:(float)moveBy speed:(float)speed {
    SpawnPortal * portal = [self instantiate:my_x y:my_y width:0 height:0];
    //SpawnPortal * portal = [self create:my_x y:my_y];
  if (portal != nil) {
    [portal MovePortalInLine:moveBy speed:speed];
  }
  return portal;
}

- (void) createAnimList {
  [self->my_itemfactorybase  createAnim:Anim_Spawn_Portal frameCount:4];
}

- (NSString *) getPlistPng {
  return @"labo";
}


- (SpawnPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[SpawnPortal alloc] init:self->my_itemfactorybase.spriteSheet x:my_x y:my_y width:my_width height:my_height] autorelease];
 //   return nil;
}

- (void) runFirstAnimations:(SpawnPortal *)item {
  [item createPortal];
}

- (SpawnPortal *) create:(float)my_x y:(float)my_y {
	return [self create:my_x y:my_y width:0 height:0];
}
/*
- (SpawnPortal *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if (isAttached) {
		SpawnPortal * item = [self instantiate:my_x y:my_y width:my_width height:my_height];
        //[self createSprite:(GameItemCocos *)item];
		[level addItemToAdd:item];
		return item;
	}
	else {
		return nil;
	}
}
*/
- (void) createSprite:(GameItemCocos *)gameItem {
    CCSprite * sprite = [CCSprite spriteWithSpriteFrame:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:Anim_Spawn_Portal]];
    
    [gameItem setSprite:sprite];
}

@end
