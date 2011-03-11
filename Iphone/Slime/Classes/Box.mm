#import "Box.h"

NSString * texture = @"wood2.png";
BOOL chainMode;

@implementation Box

+ (void) setChainMode:(BOOL)value {
  chainMode = value;
}

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(World *)world worldRatio:(float)worldRatio {
  if (self = [super init:node param1:x param2:y param3:width param4:height param5:world param6:worldRatio]) {
    [self initBody];
  }
  return self;
}

- (void) initBody {
  BodyDef * bodyDef = [[[BodyDef alloc] init] autorelease];
  bodyDef.type = BodyType.DynamicBody;
  CGPoint * spawnPoint = [[[CGPoint alloc] init] autorelease];
  spawnPoint.x = position.x;
  spawnPoint.y = position.y;
  [bodyDef.position set:spawnPoint.x / worldRatio param1:spawnPoint.y / worldRatio];
  PolygonShape * dynamicBox = [[[PolygonShape alloc] init] autorelease];
  [dynamicBox setAsBox:bodyWidth / worldRatio / 2 param1:bodyHeight / worldRatio / 2];

  @synchronized(world) 
  {
    body = [world createBody:bodyDef];
    [body setUserData:self];
    FixtureDef * fixtureDef = [[[FixtureDef alloc] init] autorelease];
    fixtureDef.shape = dynamicBox;
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
    fixtureDef.filter.categoryBits = GameItemPhysic.Category_InGame;
    [body createFixture:fixtureDef];
  }
}

@end
