#import "Box2D.h"
#import "cocos2d.h"
#import	"GameItemCocos.h"

@interface GameItemPhysicFx : GameItemCocos {
  b2World * world;
  float worldRatio;
}

@property (nonatomic,assign) b2World *world;

- (id) init:(CCNode *)my_node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) destroy;
@end
