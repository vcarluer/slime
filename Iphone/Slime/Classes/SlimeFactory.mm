#import "SlimeFactory.h"

BOOL isAttached;
SlimyFactory * Slimy = [[[SlimyFactory alloc] init] autorelease];
SpawnPortalFactory * SpawnPortal = [[[SpawnPortalFactory alloc] init] autorelease];
PlatformFactory * Platform = [[[PlatformFactory alloc] init] autorelease];
GoalPortalFactory * GoalPortal = [[[GoalPortalFactory alloc] init] autorelease];
BumperFactory * Bumper = [[[BumperFactory alloc] init] autorelease];
SpawnCannonFactory * Cannon = [[[SpawnCannonFactory alloc] init] autorelease];
LevelEndFactory * LevelEnd = [[[LevelEndFactory alloc] init] autorelease];
HomeLevelHandlerFactory * HomeLevelHandler = [[[HomeLevelHandlerFactory alloc] init] autorelease];
LavaFactory * Lava = [[[LavaFactory alloc] init] autorelease];
BoxFactory * Box = [[[BoxFactory alloc] init] autorelease];

@implementation SlimeFactory

+ (void) attachAll:(Level *)level attachNode:(CCNode *)attachNode attachWorld:(World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
  [Slimy attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [SpawnPortal attach:level param1:attachNode];
  [Platform attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [GoalPortal attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [Bumper attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [Cannon attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [LevelEnd attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [HomeLevelHandler attach:level];
  [Lava attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [Box attach:level param1:attachNode param2:attachWorld param3:attachWorldRatio];
  [SpriteSheetFactory attachAll:attachNode];
  isAttached = YES;
}

+ (void) detachAll {
  [Slimy detach];
  [SpawnPortal detach];
  [Platform detach];
  [GoalPortal detach];
  [Bumper detach];
  [Cannon detach];
  [LevelEnd detach];
  [HomeLevelHandler detach];
  [Lava detach];
  [Box detach];
  [SpriteSheetFactory detachAll];
  isAttached = NO;
}

+ (void) destroyAll {
  [Slimy destroy];
  [SpawnPortal destroy];
  [Platform destroy];
  [GoalPortal destroy];
  [Bumper destroy];
  [Cannon destroy];
  [LevelEnd destroy];
  [Lava destroy];
  [Box destroy];
  [SpriteSheetFactory destroy];
  isAttached = NO;
}

@end
