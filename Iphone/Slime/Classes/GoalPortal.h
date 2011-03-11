//#import "cocos2d.h"
//#import "Box2D.h"
#import "GameItemPhysic.h"

extern NSString * Anim_Goal_Portal;


@interface GoalPortal : GameItemPhysic {
  BOOL isWon;
}

@property(nonatomic) BOOL won;
- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
- (void) createPortal;
- (void) contact:(NSObject *)with;
- (void) setWon:(BOOL)value;
- (CCAnimation *) getReferenceAnimation;
@end
