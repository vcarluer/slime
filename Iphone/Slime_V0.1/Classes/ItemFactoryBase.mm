#import "ItemFactoryBase.h"
#import "SpriteSheetFactory.h"

@implementation ItemFactoryBase

- (id) init {
	self = [super init];
	if (self != nil) {
		isInit = NO;
		isAttached = NO;
		ratio = 1.0f;
	}
	return self;
}

- (void) initAnimation {
	if (!isInit) {
		spriteSheet = [SpriteSheetFactory getSpriteSheet:[self getPlistPng]];
		sharedAnimations = [[[NSMutableDictionary alloc] init] autorelease];
		[self createAnimList];
		isInit = YES;
	}
}

- (void) createAnimList {
}

- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
	[sharedAnimations setObject:[GameItem createAnim:animName frameCount:frameCount] forKey:animName ];
}

- (NSString *) getPlistPng {
}

- (void) destroy {
	isInit = NO;
}

- (id) create {
	return [self create:0 y:0];
}

- (id) create:(float)my_x y:(float)my_y {
	return [self create:my_x y:my_y width:0 height:0];
}

- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if (isAttached) {
		id item = [self instantiate:my_x y:my_y width:my_width height:my_height];
		[item setAnimationList:sharedAnimations];
		[self runFirstAnimations:item];
		return item;
	}
	else {
		return nil;
	}
}

- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
}

- (void) runFirstAnimations:(id)item {
}

- (void) dealloc {
	[sharedAnimations release];
	[spriteSheet release];
	[rootNode release];
	[super dealloc];
}

@end
