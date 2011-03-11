#import "GameItemPhysic.h"
//#import "ccMacros.h"

short Category_Static = 0x0001;
short Category_InGame = 0x0002;
short Category_OutGame = 0x0003;

@implementation GameItemPhysic
@synthesize bodyWidth, bodyHeight,  body ;


- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
	self = [super init:node x:my_x y:my_y width:my_width height:my_height];
	if (self != nil) {
		bodyWidth = my_width;
		bodyHeight = my_height;
		contacts = [[[NSMutableArray alloc] init] autorelease];
	}
	return self;
}

- (void) destroy {
	[world destroyBody:body];
	world = nil;
	body = nil;
	[super destroy];
}

- (void) initBody {
}



- (void) render:(float)delta {
	[self handleContacts];
	if (sprite != nil && body != nil) {
		[sprite position] = ccp(body->GetPosition().x * worldRatio,body->GetPosition().y * worldRatio);
		[sprite setRotation:-1.0f * CC_RADIANS_TO_DEGREES(body->GetAngle())];
	}
	[super render:delta];
}

- (void) addContact:(NSObject *)with {
	if ([with isKindOfClass:[GameItemPhysic class]]) {
		GameItemPhysic * item = (GameItemPhysic *)with;
		[contacts add:item];
	}
}

- (void) handleContacts {
	
	for (GameItemPhysic * item in contacts) {
		[self handleContact:item];
	}
	
	[contacts release];
}

- (void) handleContact:(GameItemPhysic *)item {
}

- (void) dealloc {
	[body release];
	[contacts release];
	[super dealloc];
}



@end
