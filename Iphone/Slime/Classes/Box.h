#import "cocos2d.h"
#import "GameItemPhysic.h"

extern NSString * texture;
extern BOOL chainMode;

@interface Box : GameItemPhysic {
}

+ (void) setChainMode:(BOOL)value;
- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio;
- (void) initBody;
@end
