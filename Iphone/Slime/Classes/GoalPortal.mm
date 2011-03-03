//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "GoalPortal.h"
#import "Slimy.h"


NSString * Anim_Goal_Portal = @"redportal";
float Default_Width = 32.0f;
float Default_Height = 10.0f;

@implementation GoalPortal

@synthesize won;

- (id) init:(CCNode *)node x:(float)x y:(float)y width:(float)width height:(float)height world:(b2World *)world worldRatio:(float)worldRatio {
  if (self = [super init:node param1:x param2:y param3:width param4:height param5:world param6:worldRatio]) {
    if (width == 0 && height == 0) {
      width = Default_Width;
      height = Default_Height;
    }
	  super.bodyWidth = width;
	  super.bodyHeight = height;
     
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
	bodyDef->userData = self;
    super.body = super.world->CreateBody(bodyDef);
    b2Fixture * fix = super.body->CreateFixture(contactBox,1.0f);
    fix->SetSensor(YES);
  //}
}

- (void) createPortal {
  CCAction * animate = [CCRepeatForever actionWithAction:[CCAnimate actionWithAnimation:[super.animationList 	objectForKey:Anim_Goal_Portal] restoreOriginalFrame:NO]];
  [super.sprite runAction:animate];
}

- (void) contact:(NSObject *)with {
	//if ([oB isKindOfClass:[GameItemPhysic class]]){
  if ([with conformsToProtocol:@protocol(Slimy)]) {
    Slimy * slimy = (Slimy *)with;
    [slimy win];
    isWon = YES;
  }
}

- (CCAnimation *) getReferenceAnimation {
  return [super.animationList objectForKey:Anim_Goal_Portal];
}

@end
