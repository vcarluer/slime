//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
//#import "cocos2d.h"
//#import "Box2D.h"
#import "GameItem.h"

extern NSString * Anim_Spawn_Portal;

@interface SpawnPortal : GameItem {
}

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height;
- (void) createPortal;
- (void) MovePortalInLine:(float)moveBy speed:(float)speed;
- (GameItem *) spawn;
- (CCAnimation *) getReferenceAnimation;
@end
