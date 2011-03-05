#import "SpawnPortal.h"
#import "SlimeFactory.h"



NSString * Anim_Spawn_Portal = @"blueportal";

@implementation SpawnPortal

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  if (self = [super init:node x:my_x y:my_y width:my_width height:my_height]) {
  }
  return self;
}

- (void) createPortal {
  CCAction * animate = [CCRepeatForever actionWithAction:[CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Spawn_Portal] restoreOriginalFrame:NO]];
  [sprite runAction:animate];
}

- (void) MovePortalInLine:(float)moveBy speed:(float)speed {
	CGPoint moveR;
  moveR.x = moveBy;
  moveR.y = 0;
	CGPoint moveL;
  moveL.x = -moveBy;
  moveL.y = 0;
	CCMoveBy * moveRight = [CCMoveBy actionWithDuration:speed/2 position:moveR];
  CCMoveBy * moveRightReverse = (CCMoveBy *)[moveRight reverse];
	CCMoveBy * moveLeft = [CCMoveBy actionWithDuration:speed / 2 position:moveL];
  CCMoveBy * moveLeftReverse = (CCMoveBy *)[moveLeft reverse];
  CCAction * moveSeq = [CCRepeatForever actionWithAction:[CCSequence actions:moveRight ,moveRightReverse , moveLeft , moveLeftReverse, nil]];
  [sprite runAction:moveSeq];
}

- (GameItem *) spawn {
  Slimy * my_slimy;
	//todo 
  my_slimy  = [slimy create:position.x y:position.y width:width height:height Ratio:1.5f];
  [my_slimy fall];
  return my_slimy;
}

- (CCAnimation *) getReferenceAnimation {
  return [animationList get:Anim_Spawn_Portal];
}

@end
