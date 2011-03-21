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

- (id) create:(float)my_x y:(float)my_y {
	return [self create:my_x y:my_y width:0 height:0];
}
/*
- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if (isAttached) {
		id * item = [self instantiate:my_x y:my_y width:my_width height:my_height];
		[level addItemToAdd:item];
		return item;
	}
	else {
		return nil;
	}
}

- (id) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
}
*/
- (void) dealloc {
    [level release];
    [super dealloc];
}

@end