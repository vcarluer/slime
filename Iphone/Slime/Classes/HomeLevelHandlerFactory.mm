#import "HomeLevelHandlerFactory.h"


@implementation HomeLevelHandlerFactory

- (HomeLevelHandler *) create {
	return [self create:0 y:0];
}

- (HomeLevelHandler *) create:(float)my_x y:(float)my_y {
	return [self create:my_x y:my_y width:0 height:0];
}

- (HomeLevelHandler *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if (isAttached) {
		HomeLevelHandler * item = [self instantiate:my_x y:my_y width:my_width height:my_height];
		[level addItemToAdd:item];
		return item;
	}
	else {
		return nil;
	}
}

- (HomeLevelHandler *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[HomeLevelHandler alloc] init:my_x y:my_y width:my_width height:my_height] autorelease];
}

@end
