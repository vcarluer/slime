#import "SlimeFactory.h"

BOOL isAttached;
SlimyFactory * slimy = [[[SlimyFactory alloc] init] autorelease];
SpawnPortalFactory * spawnPortal = [[[SpawnPortalFactory alloc] init] autorelease];
PlatformFactory * platform = [[[PlatformFactory alloc] init] autorelease];
GoalPortalFactory * goalPortal = [[[GoalPortalFactory alloc] init] autorelease];
BumperFactory * bumper = [[[BumperFactory alloc] init] autorelease];

@implementation SlimeFactory

+ (void) attachAll:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio {
	
	[slimy Attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[spawnPortal Attach:attachNode];
	[platform Attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[goalPortal Attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	[bumper Attach:attachNode attachWorld:attachWorld attachWorldRatio:attachWorldRatio];
	isAttached = YES;
}

+ (void) destroyAll {
	[slimy dealloc];
	[spawnPortal dealloc];
	[platform dealloc];
	[goalPortal dealloc];
	[bumper dealloc];
	isAttached = NO;
}

@end
