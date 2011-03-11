#import "BoxFactory.h"
#import "BumperFactory.h"
#import "GoalPortalFactory.h"
#import "HomeLevelHandlerFactory.h"
#import "LavaFactory.h"
#import "LevelEndFactory.h"
#import "PlatformFactory.h"
#import "SlimyFactory.h"
#import "SpawnCannonFactory.h"
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
extern SpawnCannonFactory * cannon;
extern LevelEndFactory * levelEnd;
extern HomeLevelHandlerFactory * homeLevelHandler;
extern LavaFactory * lava;
extern BoxFactory * box;

@interface SlimeFactory : NSObject {
}

+ (void) attachAll:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio;
+ (void) detachAll;
+ (void) destroyAll;
@end
