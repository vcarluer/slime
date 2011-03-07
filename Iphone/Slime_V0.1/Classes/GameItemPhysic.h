
#import "Box2D.h"
#import "cocos2d.h"
#import "GameItem.h"


extern short Category_Static;
extern short Category_InGame;
extern short Category_OutGame;

@interface GameItemPhysic : GameItem {
	@public
  b2World *world;
  b2Body *body;
  float bodyWidth;
  float bodyHeight;
  float worldRatio;
}
@property (nonatomic,readwrite) float bodyWidth;
@property (nonatomic,readwrite) float bodyHeight;
@property (nonatomic,readwrite) float worldRatio;

@property (nonatomic,assign) b2Body *body;
@property (nonatomic,assign) b2World *world;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
- (void) render:(float)delta;
- (void) contact:(NSObject *)with;
@end
