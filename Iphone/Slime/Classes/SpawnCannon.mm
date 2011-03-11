#import "SpawnCannon.h"
#import "SlimeFactory.h"

NSString * texture = @"metal1.png";
float Default_Width = 32.0f;
float Default_Height = 32.0f;

@implementation SpawnCannon

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio])) {
    spawnHeightShift = Slimy_Default_Height / 2;
    textureMode = Clip;
    if (width == 0 && height == 0) {
      bodyWidth = width = Default_Width;
      bodyHeight = height = Default_Height;
      [self transformTexture];
    }
    [self select];
    [self initBody];
  }
  return self;
}

- (void) initBody {
    b2BodyDef bodyDef;
    CGPoint spawnPoint;
  spawnPoint.x = position.x;
  spawnPoint.y = position.y;
  bodyDef.position.Set(spawnPoint.x / worldRatio,spawnPoint.y / worldRatio);
    b2PolygonShape staticBox;
    
    staticBox.SetAsBox(bodyWidth / worldRatio / 2, bodyHeight / worldRatio / 2);
   

//  @synchronized(world) 
//  {
    body = world->CreateBody(&bodyDef);
    bodyDef.userData = self;
    
    b2FixtureDef fixtureDef;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 1.0f;
    fixtureDef.restitution = 0.0f;
    fixtureDef.filter.categoryBits = Category_Static;
    body->CreateFixture(&fixtureDef);
//  }
}

- (Slimy *) spawnSlime:(CGPoint *)my_target {
  target = my_target;
  return [self spawnSlimeToCurrentTarget];
}

- (Slimy *) spawnSlimeToCurrentTarget {
  CGPoint spawn = [self  position];
  Slimy * my_slimy = [slimy  create:spawn.x param1:spawn.y param2:1.0f];
  b2Vec2 * pos = [[slimy body] position];
  b2Vec2 * impulse = [[[Vector2 alloc] init] autorelease];
  impulse.x = target.x / worldRatio - pos.x;
  impulse.y = target.y / worldRatio - pos.y;
  if ([impulse len] > 10f) {
    [[impulse nor] mul:10f];
  }
  [[slimy body] applyLinearImpulse:impulse param1:pos];
  return slimy;
}

- (void) select {
  selected = YES;
}

- (void) unselect {
  selected = NO;
  target = nil;
}

- (void) selectionMove:(CGPoint *)screenSelection {
  if (selected) {
    CGPoint * gameTarget = [[Level.currentLevel cameraManager] getGamePoint:screenSelection];
    target = [CGPoint make:gameTarget.x param1:gameTarget.y];
  }
}

- (void) draw:(GL10 *)gl {
  [super draw:gl];
  if (selected && target != nil) {
    [gl glEnable:GL10.GL_LINE_SMOOTH];
    [gl glColor4f:1.0f param1:0.0f param2:1.0f param3:1.0f];
    CGPoint * spawnPoint = [self spawnPoint];
    [CCDrawingPrimitives ccDrawLine:gl param1:spawnPoint param2:target];
    CGSize * s = [[CCDirector sharedDirector] winSize];
    [gl glDisable:GL10.GL_LINE_SMOOTH];
    [gl glLineWidth:2];
    [gl glColor4f:0.0f param1:1.0f param2:1.0f param3:1.0f];
    [CCDrawingPrimitives ccDrawCircle:gl param1:[self spawnPoint] param2:50 param3:[ccMacros CC_DEGREES_TO_RADIANS:90] param4:50 param5:YES];
  }
}

- (CGPoint *) getSpawnPoint {
  return [CGPoint make:[self position].x param1:[self position].y + spawnHeightShift];
}

- (void) dealloc {
  [target release];
  [super dealloc];
}

@end
