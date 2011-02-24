#import "Box2D.h"
#import "cocos2d.h"
#import "GameItemPhysic.h"


extern NSString * texture;

@interface Platform : GameItemPhysic {
}

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio;
- (void) initBody;
@end
