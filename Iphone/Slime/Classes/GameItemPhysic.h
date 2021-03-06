#import "b2Body.h"
#import "cocos2d.h"
#import "GameItemPhysicFx.h"



extern short Category_Static;
extern short Category_InGame;
extern short Category_OutGame;

@interface GameItemPhysic : GameItemPhysicFx {

  b2Body *body;
  float bodyWidth;
  float bodyHeight;
  NSMutableArray * contacts;
}
@property (nonatomic,readwrite) float bodyWidth;
@property (nonatomic,readwrite) float bodyHeight;
@property (nonatomic,assign) b2Body *body;


- (id) init:(CCNode *)my_node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio;
- (void) destroy;
- (void) initBody;
- (void) render:(float)delta;
- (void) addContact:(NSObject *)with;
- (void) handleContacts;
- (void) handleContact:(GameItemPhysic *)item;
@end
