#import "cocos2d.h"
#import "Box2D.h"
#import "SlimyFactory.h"
#import "SpawnPortalFactory.h"
#import "GoalPortalFactory.h"
#import "BumperFactory.h"
#import "PlatformFactory.h"


extern BOOL isAttached;
extern SlimyFactory * slimy;
extern SpawnPortalFactory * spawnPortal;
extern PlatformFactory * platform;
extern GoalPortalFactory * goalPortal;
extern BumperFactory * bumper;

@interface SlimeFactory : NSObject {
}

+ (void) attachAll:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio;
+ (void) destroyAll;
@end
