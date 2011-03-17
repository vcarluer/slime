#import "Platform.h"
#import "Box2D.h"


NSString * platform_texture = @"metal2.png";

@implementation Platform

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio])) {
      width = my_width; 
      height = my_height;
      
    [self initBody];
    textureMode = Clip;
  }
  return self;
}

- (void) initBody {
  b2BodyDef *bodyDef = new b2BodyDef;
  CGPoint spawnPoint;
  spawnPoint.x = position.x;
  spawnPoint.y = position.y;
  bodyDef->position.Set(spawnPoint.x / worldRatio, spawnPoint.y / worldRatio);
	b2PolygonShape * staticBox = new b2PolygonShape;
  staticBox->SetAsBox(bodyWidth / worldRatio / 2, bodyHeight / worldRatio / 2);

//  @synchronized(world) 
//  {
	  bodyDef->userData = self;
	  super.body = world->CreateBody(bodyDef);
	  
	  b2FixtureDef * fixtureDef = new b2FixtureDef;

    fixtureDef->shape = staticBox;
    fixtureDef->density = 1.0f;
    fixtureDef->friction = 1.0f;
    fixtureDef->restitution = 0.0f;
    fixtureDef->filter.categoryBits = Category_Static;
    body->CreateFixture(fixtureDef);
//  }
}

@end
