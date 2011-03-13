#import "Box.h"

NSString * box_texture = @"wood2.png";
BOOL chainMode;

@implementation Box

+ (void) setChainMode:(BOOL)value {
  chainMode = value;
}

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio])) {
    [self initBody];
  }
  return self;
}

- (void) initBody {
	b2BodyDef bodyDef;
	bodyDef.type = b2_dynamicBody;
	
	CGPoint spawnPoint;
	spawnPoint.x = position.x;
	spawnPoint.y = position.y;
	
	bodyDef.position.Set(spawnPoint.x / worldRatio,spawnPoint.y / worldRatio);	
	b2PolygonShape  dynamicBox;
    dynamicBox.SetAsBox(bodyWidth / worldRatio / 2 ,bodyHeight / worldRatio / 2);

 // @synchronized(world) 
 // {
	bodyDef.userData = self;
    body = world->CreateBody(&bodyDef);
    b2FixtureDef fixtureDef;
    fixtureDef.shape = &dynamicBox;
    if (chainMode) {
      fixtureDef.density = 5.0f;
      fixtureDef.friction = 0.5f;
      fixtureDef.restitution = 0.2f;
    }
     else {
      fixtureDef.density = 1.0f;
      fixtureDef.friction = 0.5f;
      fixtureDef.restitution = 0.2f;
    }
    fixtureDef.filter.categoryBits = Category_InGame;
   body->CreateFixture(&fixtureDef);
  //}
}

@end
