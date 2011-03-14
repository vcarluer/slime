#import "b2World.h"
#import "cocos2d.h"
#import "GameItemPhysic.h"


extern NSString * platform_texture;

@interface Platform : GameItemPhysic {
}

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
@end
