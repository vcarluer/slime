#import "GameItem.h"

@implementation GameItem

@synthesize position;

- (id) init:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	self = [super init];
	if (self != nil) {
	  	my_id =  [[NSProcessInfo processInfo] globallyUniqueString];
		CGPoint tempposition;
		tempposition.x =my_x;
		tempposition.y =my_y;
		position = tempposition;
		angle = 0.0f;
		width = my_width;
		height = my_height;

	}
	return self;
}

- (void) destroy {
}

- (void) render:(float)delta {
}

//TODO
/*
- (void) draw:(GL10 *)gl {
}
*/
- (void) setPause:(BOOL)value {
	if (value) {
		if (!isPaused) {
			[self pause];
		}
	}
	else {
		if (isPaused) {
			[self resume];
		}
	}
	isPaused = value;
}
/*
- (void) pause {
}

- (void) resume {
}
*/
- (void) dealloc {
  //[super dealloc];
}

@end

