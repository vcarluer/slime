#import "cocos2d.h"
#import "GameItemPhysic.h"
#import "Box2D.h"

extern NSString * box_texture;
extern BOOL chainMode;

@interface Box : GameItemPhysic {
}

+ (void) setChainMode:(BOOL)value;
- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
@end
