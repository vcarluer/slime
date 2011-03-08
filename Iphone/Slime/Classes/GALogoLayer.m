#import "GALogoLayer.h"
#import "SpriteSheetFactory.h"

CCScene * scene;

@implementation GALogoLayer

+ (CCScene *) scene {
  if (scene == nil) {
    scene = [CCScene node];
    [scene addChild:[[[GALogoLayer alloc] init] autorelease]];
  }
  return scene;
}

- (id) init {
  if (self = [super init]) {
    waitLogoSec = 2;
//    nextCallback = [[[GALogoLayer_Anon1 alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  if (!isInit) {
    CCSpriteBatchNode * spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo"];
    [self addChild:spriteSheet];
    CCSpriteFrame * spriteFrame = [[CCSpriteFrameCache sharedSpriteFrameCache]  spriteFrameByName:@"JulenGarciaGA.png"];
    CCSprite * sprite = [CCSprite spriteWithSpriteFrame:spriteFrame];
    [spriteSheet addChild:sprite];
	[sprite setPosition:ccp([[CCDirector sharedDirector] winSize].width / 2 ,[[CCDirector sharedDirector] winSize].height / 2)];
  }
	
 // [self schedule:nextCallback];
 // onEnterTime = [System currentTimeMillis];
}


- (void) tick:(ccTime) dt{
	long elapsed = 10.0;// ([ System currentTimeMillis] - onEnterTime) / 1000;
	if (elapsed > waitLogoSec) {
//		[self unschedule:nextCallback];
//		CCScene * nextScene = [SlimeLoadingLayer scene];
	//	[[CCDirector sharedDirector] replaceScene:nextScene];
	}
}
/*

-(void) tick: (ccTime) dt
{
	//It is recommended that a fixed time step is used with Box2D for stability
	//of the simulation, however, we are using a variable time step here.
	//You need to make an informed choice, the following URL is useful
	//http://gafferongames.com/game-physics/fix-your-timestep/
	
	int32 velocityIterations = 8;
	int32 positionIterations = 1;
	
	// Instruct the world to perform a single step of simulation. It is
	// generally best to keep the time step and iterations fixed.
	world->Step(dt, velocityIterations, positionIterations);
	
	
	//Iterate over the bodies in the physics world
	for (b2Body* b = world->GetBodyList(); b; b = b->GetNext())
	{
		if (b->GetUserData() != NULL) {
			//Synchronize the AtlasSprites position and rotation with the corresponding body
			CCSprite *myActor = (CCSprite*)b->GetUserData();
			myActor.position = CGPointMake( b->GetPosition().x * PTM_RATIO, b->GetPosition().y * PTM_RATIO);
			myActor.rotation = -1 * CC_RADIANS_TO_DEGREES(b->GetAngle());
		}	
	}
}
*/
 
- (void) dealloc {
  //[nextCallback release];
  [super dealloc];
}

@end
