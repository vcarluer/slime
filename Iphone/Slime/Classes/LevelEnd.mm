#import "LevelEnd.h"

@implementation LevelEnd

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio])) {
    [self initBody];
  }
  return self;
}

- (void) initBody {
    b2BodyDef bodyDef;
    CGPoint spawnPoint;
    spawnPoint.x = position.x;
    spawnPoint.y = position.y;
    //TEMP AMZ 
    worldRatio = 32;
    bodyDef.position.Set(spawnPoint.x / worldRatio,spawnPoint.y / worldRatio);
    b2PolygonShape staticBox;
    
    staticBox.SetAsBox(bodyWidth / worldRatio / 2, bodyHeight / worldRatio / 2);


 // @synchronized(world) 
 // {
      bodyDef.userData = self.sprite;
      body = world->CreateBody(&bodyDef);
      
      
      b2FixtureDef fixtureDef;
    fixtureDef.shape = &staticBox;
    fixtureDef.isSensor = YES;
    fixtureDef.filter.categoryBits = Category_Static;
    body->CreateFixture(&fixtureDef);
  //}
}

- (void) handleContact:(GameItemPhysic *)item {
  [super handleContact:item];
  [currentLevel addItemToRemove:item];
}

@end
