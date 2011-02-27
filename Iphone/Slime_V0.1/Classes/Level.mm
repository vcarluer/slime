#import "Level.h"
#import "GameItem.h"
#import "SpriteSheetFactory.h"

NSString * LEVEL_HOME = @"Home";
Level * currentLevel;

@implementation Level

@synthesize currentLevelName;
//@synthesize scene;
//@synthesize cameraManager;
//@synthesize world;
@synthesize worlRatio;
@synthesize levelWidth;
@synthesize levelHeight;
//@synthesize spawnPortal;


- (id) init {
	self = [super init];
  if (self != nil) {
    worldRatio = 32.0f;
    customZ = 2;
	  scene = [CCScene node];
	  
	  my_levelLayer = [[[LevelLayer alloc] initWithLevel:self] autorelease];
	  //    hudLayer = [[[HudLayer alloc] init] autorelease];
	  //    backgroundLayer = [[[BackgoundLayer alloc] init] autorelease];
	  gameLayer = [CCLayer node];
	  //    [gameLayer addChild:backgroundLayer param1:0];
	  [gameLayer addChild:my_levelLayer z:1];
	  levelOrigin = CGPointMake(0, 0);
	  [gameLayer setAnchorPoint:levelOrigin];
	  [scene addChild:gameLayer z:0];
	  //    [scene addChild:hudLayer param1:1];
    items = [[[NSMutableArray alloc] init] autorelease];
//    cameraManager = [[[CameraManager alloc] init:gameLayer param1:levelWidth param2:levelHeight param3:levelOrigin] autorelease];
  //  [self init];
  }
  return self;
}

+ (Level *) get:(NSString *)levelName {
  if (currentLevel == nil) {
    currentLevel = [[[Level alloc] init] autorelease];
  }
/*	
  if (!SlimeFactory.isAttached) {
    [currentLevel attachToFactory];
  }
*/
  if ([currentLevel currentLevelName] != levelName) {
    [currentLevel loadLevel:levelName];
  }

//  [[currentLevel cameraManager] setCameraView];
  return currentLevel;
}

- (void) attachToFactory {
//  [SlimeFactory attachAll:levelLayer param1:world param2:worldRatio];
}

- (void) reload {
  [currentLevel loadLevel:currentLevelName];
//  [[currentLevel cameraManager] setCameraView];
}

- (void) loadLevel:(NSString *)levelName {
  [self resetLevel];
//  [HardCodedLevelBuilder build:self param1:levelName];
//  spawnPortal = [SlimeFactory.SpawnPortal createAndMove:levelWidth / 2 param1:levelHeight - 32 param2:levelWidth / 2 param3:5];
//  [items add:spawnPortal];
  currentLevelName = levelName;
}

- (void) resetLevel {

  for (GameItem * item in items) {
    [item dealloc];
  }

  [items dealloc];
//  spawnPortal = nil;
//  goalPortal = nil;
  [self removeCustomOverLayer];
}

- (void) initLevel {
  gravity.Set(0.0f, -10.0f);
  bool doSleep = true;
  world = new b2World (gravity, doSleep);
  //contactManager = [[[ContactManager alloc] init] autorelease];
  //[world setContactListener:contactManager];
  [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"decor.plist"];
  CCSpriteBatchNode * spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"decor.png"];
 // [backgroundLayer addChild:spriteSheet];
 // [backgroundLayer setRotation:-90f];
 // [backgroundLayer setScale:2.0f];
  backgroundSprite = [CCSprite initWithSpriteFrame:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:@"decor.png"]];
  backgroundSprite.anchorPoint =  CGPointMake(0, 0);
  [spriteSheet addChild:backgroundSprite];
  label = [CCLabelTTF labelWithString:@"Hud !" param1:@"Marker Felt" param2:16];
//  [hudLayer addChild:label param1:0];
//  [label setPosition:[CGPoint ccp:[[[CCDirector sharedDirector] winSize] width] / 2 param1:[[[CCDirector sharedDirector] winSize] height] - 20]];
  [SpriteSheetFactory add:@"labo"];
}

- (void) tick:(float)delta {

 // @synchronized(world) 
 // {
    world->Step(delta, 6, 2);
 // }

  for (GameItem * item in items) {
    [item render:delta];
  }

 // [cameraManager tick:delta];
}
/*
- (void) SpawnSlime {
  GameItem * gi = [spawnPortal spawn];
  [items add:gi];
}

- (void) setGoalPortal:(GoalPortal *)portal {
  goalPortal = portal;
  [items add:goalPortal];
}
*/
- (void) addGameItem:(GameItem *)item {
  [items addObject:item];
}

- (void) setLevelSize:(float)width height:(float)height {
  levelWidth = width;
  levelHeight = height;
}

- (void) setIsTouchEnabled:(BOOL)value {
  [my_levelLayer setIsTouchEnabled:value];
}

- (void) addCustomOverLayer:(CCLayer *)layer {
  customOverLayer = layer;
  [scene addChild:customOverLayer z:customZ];
}

- (void) removeCustomOverLayer {
  if (customOverLayer != nil) {
    [scene removeChild:customOverLayer cleanup:YES];
    customOverLayer = nil;
  }
}

- (void) dealloc {
	delete world;
	world = NULL;
  //[gravity release];
  [items release];
  [backgroundSprite release];
  /*
	[contactManager release];
  [spawnPortal release];
  [goalPortal release]; 
   [hudLayer release];
  [backgroundLayer release];
   [levelOrigin release];
  [cameraManager release];

   */
  [scene release];
  [my_levelLayer release];
 
  [gameLayer release];
  [customOverLayer release];
  [label release];
    [currentLevelName release];
  [super dealloc];
}

@end
