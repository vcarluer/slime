//#import "cocos2d.h"
#import "Box2D.h"
#import "GameItemPhysic.h"

extern NSString * Anim_Burned_Wait;
extern NSString * Anim_Burning;
extern NSString * Anim_Falling;
extern NSString * Anim_Landing_H;
extern NSString * Anim_Landing_V;
extern NSString * Anim_Wait_H;
extern NSString * Anim_Wait_V;
extern float Slimy_Default_Width;
extern float Slimy_Default_Height;
@protocol Slimy;

@interface Slimy : GameItemPhysic {
  BOOL isLanded;
  CCAction * waitAction;
  BOOL isBurned;
}


@property (nonatomic,assign) CCAction * waitAction;
@property (nonatomic,assign) BOOL isLanded;
@property (nonatomic,assign) BOOL isBurned;


- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio;
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
- (void) handleContact:(GameItemPhysic *)item;
@end
