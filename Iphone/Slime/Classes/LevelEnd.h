#import "Level.h"
#import "cocos2d.h"

@interface LevelEnd : GameItemPhysic {
}

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) initBody;
- (void) handleContact:(GameItemPhysic *)item;
@end
