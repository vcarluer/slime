#import "Level.h"
#import "SlimyFactory.h"
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
    levelLayer = [[[LevelLayer alloc] initWithLevel:self] autorelease];
    hudLayer = [[[HudLayer alloc] init] autorelease];
    backgroundLayer = [[[BackgoundLayer alloc] init] autorelease];
	levelOrigin = CGPointMake(0,0);
    //[scene addChild:backgroundLayer z:0];
	  
	  
	  
	  
    gameLayer = [CCLayer node];
    [gameLayer addChild:backgroundLayer z:0];
	//[gameLayer addChild:levelLayer z:1];
	[gameLayer setAnchorPoint:levelOrigin];
	
	[scene addChild:gameLayer z:0];
    [scene addChild:levelLayer z:1];
    [scene addChild:hudLayer z:2];
    items = [[[NSMutableArray alloc] init] autorelease];
    [self initLevel];
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
  //spawnPortal = [SlimeFactory.SpawnPortal createAndMove:levelWidth / 2 param1:levelHeight - 32 param2:levelWidth / 2 param3:5];
  //[items add:spawnPortal];
  currentLevelName = levelName;
}

- (void) resetLevel {
/*todo
  for (GameItem * item in items) {
    [item destroy];
  }

  [items clear];
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
   CGSize screenSize = [CCDirector sharedDirector].winSize;
	
	[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"decor.plist"];
	//CCSpriteSheet * spriteSheet = [CCSpriteSheet spriteSheet:@"decor.png"];
	
	
	CCSprite *spriteSheet = [CCSprite spriteWithFile:@"decor.png"];
	spriteSheet.position = ccp(500, 0);
	
	
	[backgroundLayer addChild:spriteSheet];
	//[backgroundLayer setRotation:-90.0f];
	[backgroundLayer setScale:0.8f];
	label = [CCLabelTTF labelWithString:@"Hud !" fontName:@"Marker Felt" fontSize:16];	
	[hudLayer addChild:label z:0];	
	label.position = ccp( screenSize.width/2, screenSize.height-20);	
    [SpriteSheetFactory add:@"labo"];	
	
	
				 }

- (void) tick:(float)delta {

//  @synchronized(world) 
//  {
    [world step:delta param1:6 param2:2];
//  }
/*todo
  for (GameItem * item in items) {
    [item render:delta];
  }

  [cameraManager tick:delta];
 */
}

- (void) SpawnSlime {
  GameItem * gi = [spawnPortal spawn];
  [items add:gi];
}

- (void) setGoalPortal:(GoalPortal *)portal {
  goalPortal = portal;
  [items add:goalPortal];
}

- (void) addGameItem:(GameItem *)item {
  [items add:item];
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
  [scene addChild:customOverLayer param1:customZ];
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
  [items release];
  [backgroundSprite release];
  [contactManager release];
  [spawnPortal release];
  [goalPortal release];
  [scene release];
  [levelLayer release];
  [hudLayer release];
  [backgroundLayer release];
//  [gameLayer release];
  [customOverLayer release];
  [label release];
 // [levelOrigin release];
 // [cameraManager release];
  [currentLevelName release];
  [super dealloc];
}

@end
