#import "SlimeFactory.h"
#import "SpriteSheetFactory.h"

BOOL isAttached;
SlimyFactory * slimy = [[[SlimyFactory alloc] init] autorelease];
SpawnPortalFactory * spawnPortal = [[[SpawnPortalFactory alloc] init] autorelease];
PlatformFactory * platform = [[[PlatformFactory alloc] init] autorelease];
GoalPortalFactory * goalPortal = [[[GoalPortalFactory alloc] init] autorelease];
BumperFactory * bumper = [[[BumperFactory alloc] init] autorelease];
SpawnCannonFactory * cannon = [[[SpawnCannonFactory alloc] init] autorelease];
LevelEndFactory * levelEnd = [[[LevelEndFactory alloc] init] autorelease];
HomeLevelHandlerFactory * homeLevelHandler = [[[HomeLevelHandlerFactory alloc] init] autorelease];
LavaFactory * lava = [[[LavaFactory alloc] init] autorelease];
BoxFactory * box = [[[BoxFactory alloc] init] autorelease];

@implementation SlimeFactory

+ (void) attachAll:(Level *)level attachNode:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
  [slimy attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
    [spawnPortal attach:level];
    //TODO
    //attach:level param1:attachNode];
  [platform attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [goalPortal attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [bumper attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [cannon attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [levelEnd attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [homeLevelHandler attach:level];
  [lava attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [box attach:level attachNode:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
  [SpriteSheetFactory attachAll:attachNode];
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
  [lava detach];
  [box detach];
  [SpriteSheetFactory detachAll];
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
  [SpriteSheetFactory destroy];
  isAttached = NO;
}

@end
