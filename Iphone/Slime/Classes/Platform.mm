#import "Platform.h"


NSString * texture = @"metal.png";

@implementation Platform

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio {
  if (self = [super init:node param1:x param2:y param3:width param4:height param5:world param6:worldRatio]) {
    [self initBody];
  }
  return self;
}

- (void) initBody {
  b2BodyDef *bodyDef;
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
    [body createFixture:fixtureDef];
//  }
}

@end
