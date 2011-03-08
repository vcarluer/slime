#import "GameItemFactory.h"


@implementation GameItemFactory

- (void) attach:(Level *)my_level {
	level = my_level;
	isAttached = YES;
}

- (void) detach {
	if (isAttached) {
		level = nil;
		isAttached = NO;
	}
}

- (id) create {
	return [self create:0 y:0];
}

- (id) create:(float)x y:(float)y {
	return [self create:x y:y width:0 height:0];
}

- (id) create:(float)x y:(float)y width:(float)width height:(float)height {
	if (isAttached) {
		id item = [self instantiate:x y:y width:width height:height];
		[level addItemToAdd:item];
		return item;
	}
	else {
		return nil;
	}
}

- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
}

- (void) dealloc {
	[level release];
  [super dealloc];
}

@end