//#import "cocos2d.h"
//#import "Box2D.h"
#import "GameItem.h"

extern NSString * Anim_Spawn_Portal;

@interface SpawnPortal : GameItem {
}

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) createPortal;
- (void) MovePortalInLine:(float)moveBy speed:(float)speed;
- (GameItem *) spawn;
- (CCAnimation *) getReferenceAnimation;
@end
