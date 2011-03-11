//#import "Level.h"
//#import "GL10.h"
#import "GameItemPhysic.h"
#import "Slimy.h"
#import "cocos2d.h"
#import "Box2D.h"


extern NSString * texture;
extern float Default_Width;
extern float Default_Height;

@interface SpawnCannon : GameItemPhysic {
  float spawnHeightShift;
  CGPoint target;
  BOOL selected;
}

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
- (Slimy *) spawnSlime:(CGPoint )my_target;
- (Slimy *) spawnSlimeToCurrentTarget;
- (void) select;
- (void) unselect;
- (void) selectionMove:(CGPoint *)screenSelection;
//TODO
//- (void) draw:(GL10 *)gl;
@end
