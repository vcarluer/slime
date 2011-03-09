#import "GameItemPhysic.h"


extern float Default_Powa;
extern NSString * Texture_Wait;
extern NSString * Anim_Wait;

@interface Bumper : GameItemPhysic {
	float powa;
}

@property (nonatomic,readwrite) float powa;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) setPowa:(float)apowa;
- (void) initBody;
- (void) waitAnim;
- (void) setAngle:(float)angle;
@end
