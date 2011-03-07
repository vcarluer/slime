#import "GameItemPhysic.h"

extern NSString * Anim_Burned_Wait;
extern NSString * Anim_Burning;
extern NSString * Anim_Falling;
extern NSString * Anim_Landing_H;
extern NSString * Anim_Landing_V;
extern NSString * Anim_Wait_H;
extern NSString * Anim_Wait_V;
extern float Default_Width;
extern float Default_Height;
@protocol Slimy;

@interface Slimy : GameItemPhysic {
  BOOL isLanded;
  CCAction * waitAction;
  BOOL isBurned;
}


@property (nonatomic,assign) CCAction * waitAction;
@property (nonatomic,assign) BOOL isLanded;
@property (nonatomic,assign) BOOL isBurned;


- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
+ (id) createSlimy:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
- (void) fadeIn;
- (void) waitAnim;
- (void) fall;
- (void) land;
- (void) render:(float)delta;
- (void) contact:(NSObject *)with;
- (void) win;
- (void) burn;
- (CCAnimation *) getReferenceAnimation;
@end
