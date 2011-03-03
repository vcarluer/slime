#import "SpawnPortal.h"
#import "SlimeFactory.h"
#import "Slimy.h"


NSString * Anim_Spawn_Portal = @"blueportal";

@implementation SpawnPortal

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  if (self = [super init:node x:my_x y:my_y width:my_width height:my_height]) {
  }
  return self;
}

- (void) createPortal {
  CCAction * animate = [CCRepeatForever action:[CCAnimate action:[animationList get:Anim_Spawn_Portal] param1:NO]];
  [sprite runAction:animate];
}

- (void) MovePortalInLine:(float)moveBy speed:(float)speed {
	CGPoint moveR;
  moveR.x = moveBy;
  moveR.y = 0;
	CGPoint moveL;
  moveL.x = -moveBy;
  moveL.y = 0;
  CCMoveBy * moveRight = [CCMoveBy action:speed / 2 param1:moveR];
  CCMoveBy * moveRightReverse = [moveRight reverse];
  CCMoveBy * moveLeft = [CCMoveBy action:speed / 2 param1:moveL];
  CCMoveBy * moveLeftReverse = [moveLeft reverse];
  CCAction * moveSeq = [CCRepeatForever action:[CCSequence actions:moveRight param1:moveRightReverse param2:moveLeft param3:moveLeftReverse]];
  [sprite runAction:moveSeq];
}

- (GameItem *) spawn {
  Slimy * slimy;
	//todo 
//	slimy  = [SlimeFactory.Slimy create:node x:position.x y:position.y width:width height:height world:world worldRatio:1.5f]];
  [slimy fall];
  return slimy;
}

- (CCAnimation *) getReferenceAnimation {
  return [animationList get:Anim_Spawn_Portal];
}

@end
