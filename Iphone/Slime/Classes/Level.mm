//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "Level.h"
#import "SlimyFactory.h"
#import "SpawnPortalFactory.h"
#import "SlimeFactory.h"
#import "HardCodedLevelBuilder.h"
#import "spriteSheetFactory.h"


NSString * LEVEL_HOME = @"Home";
Level * currentLevel;

@implementation Level

@synthesize currentLevelName;
@synthesize scene;
//@synthesize cameraManager;
@synthesize world;
@synthesize worlRatio;
@synthesize levelWidth;
@synthesize levelHeight;
@synthesize spawnPortal;

- (id) init {
  if (self = [super init]) {
    worldRatio = 32.0f;
    customZ = 2;
    scene = CCScene.node;
    levelLayer =[[LevelLayer alloc] initWithLevel:self];
    hudLayer = [[HudLayer alloc] init];
    backgroundLayer = [[BackgoundLayer alloc] init];
	levelOrigin = CGPointMake(0,0);
    //[scene addChild:backgroundLayer z:0];
     gameLayer = [CCLayer node];
    [gameLayer addChild:backgroundLayer z:0];
	[gameLayer addChild:levelLayer z:1];
	[gameLayer setAnchorPoint:levelOrigin];
	
	[scene addChild:gameLayer z:0];
    //[scene addChild:levelLayer z:1];
    [scene addChild:hudLayer z:2];
    my_items = [[NSMutableArray alloc] init];

    [self initLevel];
	  [self loadLevel:LEVEL_HOME];
  }
  return self;
}

+ (Level *) get:(NSString *)levelName {
  if (currentLevel == nil) {
    currentLevel = [[[Level alloc] init] autorelease];
  }
/* todo
	if (!SlimeFactory.isAttached) {
    [currentLevel attachToFactory];
  }
 */
  if ([currentLevel currentLevelName] != levelName) {
    [currentLevel loadLevel:levelName];
  }
  [[currentLevel cameraManager] setCameraView];
  return currentLevel;
}

- (void) attachToFactory {
	//todo
  //[slimeFactory attachAll:levelLayer param1:world param2:worldRatio];
}

- (void) reload {
  [currentLevel loadLevel:currentLevelName];
	//todo
  //[[currentLevel cameraManager] setCameraView];
}

- (void) loadLevel:(NSString *)levelName {
  [self resetLevel];
	//todo

  [HardCodedLevelBuilder build:self levelName:levelName];	
  //spawnPortal = [SpawnPortalFactory createAndMove:levelWidth / 2 y:levelHeight - 32 moveBy:levelWidth / 2 speed:5];

  currentLevelName = levelName;
}

- (void) resetLevel {
/*todo
  for (GameItem * item in my_items) {
    [item destroy];
  }

  [my_items clear];
	*/
  spawnPortal = nil;
  goalPortal = nil;
  [self removeCustomOverLayer];
}

- (void) initLevel {
  
   b2Vec2 my_gravity(0, -10);
	gravity = my_gravity;
   world = new b2World(gravity, true);		
   contactManager = new ContactManager;
   world->SetContactListener(contactManager);
  //[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"decor.plist"];
  //CCSpriteSheet * spriteSheet = [CCSpriteSheet spriteSheet:@"decor.png"];
	CCSpriteFrameCache * cache = [CCSpriteFrameCache sharedSpriteFrameCache];
	[cache addSpriteFramesWithFile:@"decor.plist"];
	CCSpriteBatchNode *spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"decor.png"];  
	//[self addChild:spriteSheet];
	
	[backgroundLayer addChild:spriteSheet];
	//[backgroundLayer setRotation:-90.0f];
	[backgroundLayer setScale:0.8f];
	label = [CCLabelTTF labelWithString:@"Hud !" fontName:@"Marker Felt" fontSize:16];	
	[hudLayer addChild:label z:0];
	CGSize screenSize = [CCDirector sharedDirector].winSize;
	label.position = ccp( screenSize.width/2, screenSize.height-20);	
	[SpriteSheetFactory  add:@"labo"];	
//	Slimy * slimy;
	//todo 
//	slimy  = [SlimyFactory createSlimy:screenSize.width/2 y:screenSize.height worldRatio:1.5f];
//	[slimy fall];
//	[hudLayer addChild:slimy ];
  //todo	
 // [spriteSheetFactory add:@"labo"];
				 }

- (void) tick:(float)delta {

//  @synchronized(world) 
//  {
   	world->Step(delta, 6, 2);
//  }
//todo
	if (my_items != nil){
		if ([my_items count] != 0) {
			for (GameItem * item in my_items) {
				[item render:delta];
			}
		}
	}	

  //[cameraManager tick:delta];

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
			myActor.position = CGPointMake( b->GetPosition().x * worlRatio, b->GetPosition().y * worlRatio);
			myActor.rotation = -1 * CC_RADIANS_TO_DEGREES(b->GetAngle());
		}	
	}
}
*/
- (void) SpawnSlime {
  GameItem * gi = [spawnPortal spawn];
  [my_items add:gi];
}

- (void) setGoalPortal:(GoalPortal *)portal {
  goalPortal = portal;
  [my_items add:goalPortal];
}

- (void) addGameItem:(GameItem *)item {
  [my_items add:item];
}

- (void) setLevelSize:(float)width height:(float)height {
  levelWidth = width;
  levelHeight = height;
}

- (void) setIsTouchEnabled:(BOOL)value {
  [levelLayer setIsTouchEnabled:value];
}

- (void) addCustomOverLayer:(CCLayer *)layer {
  customOverLayer = layer;
  [scene addChild:customOverLayer z:customZ];
}

- (void) removeCustomOverLayer {
  if (customOverLayer != nil) {
    [scene removeChild:customOverLayer param1:YES];
    customOverLayer = nil;
  }
}

- (void) dealloc {
  [world release];
  //[gravity release];
  [my_items release];
  [backgroundSprite release];
  [contactManager release];
  [spawnPortal release];
  [goalPortal release];
  [scene release];
  [levelLayer release];
  [hudLayer release];
  [backgroundLayer release];
  [gameLayer release];
  [customOverLayer release];
  [label release];
 // [levelOrigin release];
 // [cameraManager release];
  [currentLevelName release];
  [super dealloc];
}

@end
