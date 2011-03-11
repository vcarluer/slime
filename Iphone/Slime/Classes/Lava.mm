#import "Lava.h"
#import "IBurnable.h"

NSString * Anim_Init = @"lava";

@implementation Lava

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio ])) {
    [self initBody];
    textureMode = Clip;
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
    fixtureDef.shape  =&staticBox;
    fixtureDef.isSensor = YES;
    fixtureDef.filter.categoryBits = Category_Static;
    body->CreateFixture(&fixtureDef);
//  }
}

- (void) handleContact:(GameItemPhysic *)item {
  [super handleContact:item];
  //TODO
   //  if ([item isKindOfClass:[IBurnable class]]) {
  //  [((IBurnable *)item) burn];
  //}
}

- (void) initAnimation {
  CCAnimate * animation = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Init] restoreOriginalFrame:NO];
  currentAction = animation;
  [sprite runAction:currentAction];
}

@end
