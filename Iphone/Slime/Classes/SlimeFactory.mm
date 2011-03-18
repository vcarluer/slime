#import "SlimeFactory.h"


BOOL isAttached;
SlimyFactory * slimy = [[SlimyFactory alloc] init] ;
SpawnPortalFactory * spawnPortal = [[SpawnPortalFactory alloc] init] ;
PlatformFactory * platform = [[PlatformFactory alloc] init] ;
GoalPortalFactory * goalPortal = [[GoalPortalFactory alloc] init] ;
BumperFactory * bumper = [[BumperFactory alloc] init] ;
SpawnCannonFactory * cannon = [[SpawnCannonFactory alloc] init] ;
LevelEndFactory * levelEnd = [[LevelEndFactory alloc] init] ;
HomeLevelHandlerFactory * homeLevelHandler = [[HomeLevelHandlerFactory alloc] init];
//LavaFactory * lava = [[[LavaFactory alloc] init] autorelease];
//BoxFactory * box = [[[BoxFactory alloc] init] autorelease];
SpriteSheetFactory * spriteSheetFactory = [[SpriteSheetFactory alloc] init] ;

@implementation SlimeFactory

+ (void) attachAll:(Level *)level attachNode:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
  [slimy attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
    [spawnPortal attach:level];
  [platform attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [goalPortal attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [bumper attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [cannon attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [levelEnd attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [homeLevelHandler attach:level];
  //[lava attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  //[box attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [spriteSheetFactory attachAll:attachNode];
  isAttached = YES;
}

+ (void) detachAll {
  [slimy detach];
  [spawnPortal detach];
  [platform detach];
  [goalPortal detach];
  [bumper detach];
  [cannon detach];
  [levelEnd detach];
  [homeLevelHandler detach];
 // [lava detach];
 // [box detach];
  [spriteSheetFactory detachAll];
  isAttached = NO;
}

+ (void) destroyAll {
    /*
  [slimy destroy];
  [spawnPortal destroy];
  [platform destroy];
  [goalPortal destroy];
  [bumper destroy];
  [cannon destroy];
  [levelEnd destroy];
  [lava destroy];
  [box destroy];
     */
 // [SpriteSheetFactory destroy];
  isAttached = NO;
}

@end
