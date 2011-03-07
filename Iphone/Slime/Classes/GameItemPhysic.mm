#import "GameItemPhysic.h"
//#import "ccMacros.h"

short Category_Static = 0x0001;
short Category_InGame = 0x0002;
short Category_OutGame = 0x0003;

@implementation GameItemPhysic
@synthesize bodyWidth, bodyHeight, worldRatio, body, world;


- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
	self = [super init:node x:my_x y:my_y width:my_width height:my_height];
	if (self != nil) {
    world = my_world;
    worldRatio = my_worldRatio;
    bodyWidth = my_width;
    bodyHeight = my_height;
  }
  return self;
}

- (void) initBody {
}

- (void) render:(float)delta {
  if (sprite != nil && body != nil) {
	   
	    [sprite position] = ccp(body->GetPosition().x * worldRatio,body->GetPosition().y * worldRatio);
	//  ccMacros *macro = [[ccMacros alloc] init];
    [sprite setRotation:-1.0f * CC_RADIANS_TO_DEGREES(body->GetAngle())];
  }
  [super render:delta];
}

- (void) contact:(NSObject *)with {
}

- (void) dealloc {
  //[world release];
  //[body release];
  [super dealloc];
}

@end
