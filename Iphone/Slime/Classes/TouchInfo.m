//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

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

- (id) initWithPointerId:(int)pointerId {
  if (self = [super init]) {
	  
    pointerId = pointerId;
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
