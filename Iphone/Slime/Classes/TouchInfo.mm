#import "TouchInfo.h"

@implementation TouchInfo
/*
@synthesize moveBeganAt;
@synthesize lastMoveReference;
@synthesize lastMoveTime;
@synthesize lastMoveDelta;
*/
 @synthesize moving;
@synthesize pointerId;

- (id) initWithPointerId:(int)my_pointerId {
  if ((self = [super init])) {
	  
    pointerId = my_pointerId;
    /*
	moveBeganAt = [[[CGPoint alloc] init] autorelease];
    lastMoveReference = [[[CGPoint alloc] init] autorelease];
    lastMoveDelta = [[[CGPoint alloc] init] autorelease];
  */
  }
  return self;
}

- (void) dealloc {
  [moveBeganAt release];
  [lastMoveReference release];
  [lastMoveDelta release];
  [super dealloc];
}

@end
