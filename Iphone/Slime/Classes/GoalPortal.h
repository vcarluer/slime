//#import "cocos2d.h"
//#import "Box2D.h"
#import "GameItemPhysic.h"

extern NSString * Anim_Goal_Portal;


@interface GoalPortal : GameItemPhysic {
  BOOL isWon;
}

@property(nonatomic) BOOL won;
- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio;
- (void) initBody;
- (void) createPortal;
- (void) contact:(NSObject *)with;
- (void) setWon:(BOOL)value;
- (CCAnimation *) getReferenceAnimation;
@end
