#import "Slimy.h"

NSString * Anim_Burned_Wait = @"burned-wait";
NSString * Anim_Burning = @"burning";
NSString * Anim_Falling = @"falling";
NSString * Anim_Landing_H = @"landing-h";
NSString * Anim_Landing_V = @"landing-v";
NSString * Anim_Wait_H = @"wait-h";
NSString * Anim_Wait_V = @"wait-v";
 
float Default_Width = 24.0f;
float Default_Height = 26.0f;
float Default_Body_Width = 16.0f;
float Default_Body_Height = 23.0f;

@implementation Slimy
@synthesize isLanded, waitAction, isBurned;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
  	self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio];
	if (self != nil){  
		if (width == 0 && height == 0) {
			width = Default_Width;
			height = Default_Height;
			[self transformTexture];
		}
    bodyWidth = Default_Body_Width * my_width / Default_Width;
    bodyHeight = Default_Body_Height * my_height / Default_Height;
    isLanded = NO;
    isBurned = NO;
    [self initBody];
  }
  return self;
}


+ (id) createSlimy:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio{
	
	return [[[self alloc] init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio] autorelease];
}



- (void) initBody {
	
	b2BodyDef bodyDef;
	bodyDef.type = b2_dynamicBody;
	
	CGPoint spawnPoint;
	spawnPoint.x = position.x;
	spawnPoint.y = position.y;
	
	bodyDef.position.Set(spawnPoint.x / worldRatio,spawnPoint.y / worldRatio);	
	b2PolygonShape  dynamicBox;
	//dynamicBox.SetAsBox(bodyWidth / worldRatio / 2, bodyHeight / worldRatio / 2) ;
	dynamicBox.SetAsBox(.5f, .5f);//These are mid points for our 1m box  //@synchronized(world) 
  
	//{
	bodyDef.userData = self->sprite;
    body = world->CreateBody(&bodyDef);
    //body->userData = self;
    
	
	b2FixtureDef fixtureDef;
    fixtureDef.shape = &dynamicBox;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.3f;
    fixtureDef.restitution = 0.1f;
    fixtureDef.filter.categoryBits = Category_InGame;
    body->CreateFixture(&fixtureDef);
  //}
	

	
}

- (void) fadeIn {

  [sprite runAction:[CCFadeIn actionWithDuration:0.5f]];
}

- (void) waitAnim {
 
	
	CCAnimate * animate = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Wait_V] restoreOriginalFrame:NO];
	CCAnimate * reverse = [CCAnimate actionWithDuration:3.0f animation:(CCAnimation *)[animate reverse] restoreOriginalFrame:NO];
	waitAction = [CCSequence actions:animate,  reverse, nil];
    
  [sprite runAction:waitAction];
}

- (void) fall {
	//CCAnimate * animate = [CCAnimate actionWithAnimation:[self wecreateAnim:Anim_Falling frameCount:3] restoreOriginalFrame:NO];
  CCAnimate * animate = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Falling] restoreOriginalFrame:NO];
  CCAnimate * reverse = (CCAnimate *)[animate reverse];
  CCAction * action = [CCRepeatForever actionWithAction:[CCSequence actionOne:animate two:reverse]];
  currentAction = action;
  [sprite runAction:currentAction];
}



- (void) land {
  if (!isLanded && !isBurned && sprite != nil) {
    if (currentAction != nil) {
      [sprite stopAction:currentAction];
    }
    CCAnimate * animate = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Landing_V] restoreOriginalFrame:NO];
    currentAction = animate;
    [sprite runAction:currentAction];
    isLanded = YES;
  }
}

- (void) render:(float)delta {
  [super render:delta];
}

- (void) contact:(NSObject *)with {
  [self land];
}

- (void) win {
  [self burn];
}

- (void) burn {
  if (!isBurned) {
    if (currentAction != nil) {
      [sprite stopAction:currentAction];
    }
    if (waitAction != nil) {
      [sprite stopAction:waitAction];
    }
    CCAnimate * animateBlink = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Burned_Wait] restoreOriginalFrame:NO];
	CCAnimate * blinkSeq = [CCAnimate actionWithDuration:3.0f animation:(CCAnimation *)[animateBlink reverse] restoreOriginalFrame:NO];
	  //waitAction = [CCSequence actions:animate,  blinkSeq, nil];
	waitAction = [CCRepeatForever actionWithAction:blinkSeq];
    [sprite runAction:waitAction];
    CCAnimate * animBurn = [CCAnimate actionWithAnimation:[animationList objectForKey:Anim_Burning] restoreOriginalFrame:NO];
    currentAction = animBurn;
    [sprite runAction:currentAction];
    b2Filter filter;
    filter.categoryBits = Category_OutGame;
    filter.maskBits = Category_Static;
    filter.groupIndex = -1;
	for (b2Fixture* fix = body->GetFixtureList(); fix; fix = fix->GetNext()){
   // for (b2Fixture * fix in [body fixtureList]) {
      fix->SetFilterData(filter);
      fix->SetRestitution(0.0f);
      fix->SetFriction(1.0f);
      fix->SetDensity(10.0f);
      body->ResetMassData();
		
    }

    isBurned = YES;
  }
}

- (CCAnimation *) getReferenceAnimation {
  return [animationList objectForKey:Anim_Wait_V];
}

- (void) dealloc {
  [waitAction release];
  [super dealloc];
}

@end
