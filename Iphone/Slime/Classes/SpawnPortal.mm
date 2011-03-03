//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
#import "SpawnPortal.h"
#import "SlimeFactory.h"
#import "Slimy.h"


NSString * Anim_Spawn_Portal = @"blueportal";

@implementation SpawnPortal

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height {
  if (self = [super init:node param1:x param2:y param3:width param4:height]) {
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
	//slimy  = [SlimeFactory.Slimy init:node param1:position.x param2:position.y param3:width param4:height param5:world param6:1.5f]];
  [slimy fall];
  return slimy;
}

- (CCAnimation *) getReferenceAnimation {
  return [animationList get:Anim_Spawn_Portal];
}

@end
