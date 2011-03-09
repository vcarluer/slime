#import "GoalPortal.h"
#import "Slimy.h"


NSString * Anim_Goal_Portal = @"redportal";
float Default_Width = 32.0f;
float Default_Height = 10.0f;

@implementation GoalPortal

@synthesize won;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  if ((self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio])) {
    if (my_width == 0 && my_height == 0) {
      width = Default_Width;
      height = Default_Height;
    }
	  super.bodyWidth = my_width;
	  super.bodyHeight = my_height;
     
    isWon = NO;
    [self initBody];
  }
  return self;
}

- (void) initBody {
  b2BodyDef *bodyDef;
  CGPoint spawnPoint;
  spawnPoint.x = super.position.x;
  spawnPoint.y = super.position.y;
  bodyDef->position.Set(spawnPoint.x / super.worldRatio, spawnPoint.y / super.worldRatio);
  b2PolygonShape * contactBox = new b2PolygonShape;
  contactBox->SetAsBox(super.bodyWidth / super.worldRatio / 2 ,super.bodyHeight / super.worldRatio / 2);

  //@synchronized(world) 
  //{
   super.body = super.world->CreateBody(bodyDef);
   bodyDef->userData = self;
   b2Fixture * fix = super.body->CreateFixture(contactBox,1.0f);
   fix->SetSensor(YES);
  //}
}

- (void) createPortal {
  CCAction * animate = [CCRepeatForever actionWithAction:[CCAnimate actionWithAnimation:[super.animationList 	objectForKey:Anim_Goal_Portal] restoreOriginalFrame:NO]];
  [super.sprite runAction:animate];
}

- (void) contact:(NSObject *)with {
  if ([with isKindOfClass:[Slimy class]]) {
    Slimy * slimy = (Slimy *)with;
    [slimy win];
    isWon = YES;
  }
}

- (CCAnimation *) getReferenceAnimation {
  return [super.animationList objectForKey:Anim_Goal_Portal];
}

@end
