#import "SlimeFactory.h"

BOOL isAttached;
SlimyFactory * slimy = [[[SlimyFactory alloc] init] autorelease];
SpawnPortalFactory * spawnPortal = [[[SpawnPortalFactory alloc] init] autorelease];
PlatformFactory * platform = [[[PlatformFactory alloc] init] autorelease];
GoalPortalFactory * goalPortal = [[[GoalPortalFactory alloc] init] autorelease];
BumperFactory * bumper = [[[BumperFactory alloc] init] autorelease];

@implementation SlimeFactory

+ (void) attachAll:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
	[Slimy attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[SpawnPortal Attach:attachNode];
	[Platform attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[GoalPortal attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[Bumper attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	isAttached = YES;
}

+ (void) destroyAll {
	[Slimy destroy];
	[SpawnPortal destroy];
	[Platform destroy];
	[GoalPortal destroy];
	[Bumper destroy];
	isAttached = NO;
}

@end
