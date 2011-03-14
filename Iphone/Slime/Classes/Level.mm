#import "Level.h"
#import "SlimeFactory.h"
#import "HardCodedLevelBuilder.h"
#import "spriteSheetFactory.h"

NSString * LEVEL_HOME = @"Home";
NSString * LEVEL_1 = @"Home";
NSString * LEVEL_2 = @"Home";
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
@synthesize spawnCannon;

- (id) init {
	if ((self = [super init])) {
		worldRatio = 32.0f;
		customZ = 2;
    hudZ = 1;
    scene = [CCScene node];
    levelLayer = [[[LevelLayer alloc] initWithLevel:self] autorelease];
    hudLayer = [[[HudLayer alloc] init] autorelease];
    backgroundLayer = [[[BackgoundLayer alloc] init] autorelease];
    gameLayer = [CCLayer node];
    [gameLayer addChild:backgroundLayer z:0];
    [gameLayer addChild:levelLayer z:1];
        levelOrigin = CGPointZero;
    [gameLayer setAnchorPoint:levelOrigin];
    [scene addChild:gameLayer z:0];
    isHudEnabled = YES;
    [scene addChild:hudLayer z:hudZ];
    items = [[[NSMutableDictionary alloc] init] autorelease];
//todo
        //    cameraManager = [[[CameraManager alloc] init:gameLayer] autorelease];
    itemsToRemove = [[[NSMutableArray alloc] init] autorelease];
    itemsToAdd = [[[NSMutableArray alloc] init] autorelease];
		[self initLevel];
    isInit = YES;
	}
	return self;
}



+ (Level *) get:(NSString *)levelName {
  return [self get:levelName forceReload:NO];
}

+ (Level *) get:(NSString *)levelName forceReload:(BOOL)forceReload {
  if (currentLevel == nil || isInit == NO) {
    currentLevel = [[[Level alloc] init] autorelease];
  }
  if (forceReload || [currentLevel currentLevelName] != levelName) {
    [currentLevel loadLevel:levelName];
  }
  [currentLevel attachLevelToCamera];
  return currentLevel;
}

- (void) attachToFactory {
  [SlimeFactory attachAll:self attachNode:levelLayer attachWorld:world attachWorldRatio:worldRatio ];
}

- (void) reload {
  [currentLevel loadLevel:currentLevelName];
   //TODO 
 // [[currentLevel cameraManager] setCameraView];
}

- (void) attachLevelToCamera {
 // [cameraManager attachLevel:levelWidth param1:levelHeight param2:levelOrigin];
 // [cameraManager setCameraView];
 // [[self cameraManager] zoomCameraTo:0f];
}

- (void) loadLevel:(NSString *)levelName {
  [self resetLevel];
  [HardCodedLevelBuilder build:self levelName:levelName];
  currentLevelName = levelName;
  isPaused = NO;
}

- (void) resetLevel {
  currentLevelName = @"";

  for (GameItem * item in items) {
    [item destroy];
  }

  [items release];
  spawnPortal = nil;
  goalPortal = nil;
  [self removeCustomOverLayer];
  [self setIsTouchEnabled:YES];
  [self setIsHudEnabled:YES];
}

- (void) initLevel {
	
	b2Vec2 my_gravity(0, -10);
	gravity = my_gravity;
	world = new b2World(gravity, true);		
	contactManager = new ContactManager;
	world->SetContactListener(contactManager);
  [SpriteSheetFactory add:@"labo"];
		CGSize screenSize = [CCDirector sharedDirector].winSize;
//	[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:@"decor.plist"];
  CCSpriteBatchNode * my_spriteSheet = [SpriteSheetFactory getSpriteSheet:@"decor" isExcluded:YES];
	
	
	CCSprite *my_sprite = [CCSprite spriteWithFile:@"decor.png"];
	my_sprite.position = ccp(500, 0);
	
	
  [backgroundLayer addChild:my_spriteSheet];
   // backgroundSprite = [[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:@"decor"]   
  //backgroundSprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache]  getSpriteFrame:@"decor.png"]];
  //[backgroundSprite setAnchorPoint:CGPointZero];
  //[spriteSheet addChild:backgroundSprite];
  label = [CCLabelTTF labelWithString:@"Hud !" fontName:@"Marker Felt" fontSize:16];	
  [hudLayer addChild:label];
  label.position = ccp( screenSize.width/2, screenSize.height-20);
  [self attachToFactory];
//	[levelLayer addChild:spriteSheet];
	
	
	
}

- (void) tick:(float)delta {
  if (!isPaused) {

    for (GameItem * item in itemsToAdd) {
      [self addGameItem:item];
    }

    [itemsToAdd release];

    //@synchronized(world) 
    //{
     world->Step(delta, 6, 2);
    //}

    for (GameItem * item in items) {
      [item render:delta];
    }


    for (GameItem * item in itemsToRemove) {
      [self removeGameItem:item];
    }

    [itemsToRemove clear];
    //[cameraManager tick:delta];
  }
}

- (void) addItemToRemove:(GameItem *)item {
  [itemsToRemove add:item];
}

- (void) addItemToAdd:(GameItem *)item {
  [itemsToAdd addObject:item];
}

- (void) setPause:(BOOL)value {
  if (value) {
    if (!isPaused) {
      [levelLayer pauseSchedulerAndActions];
    }
  }
   else {
    if (isPaused) {
      [levelLayer resumeSchedulerAndActions];
    }
  }

  for (GameItem * item in [items values]) {
    [item setPause:value];
  }

  isPaused = value;
  [self setIsTouchEnabled:!isPaused];
}

- (void) togglePause {
  [self setPause:!isPaused];
}

- (void) spawnSlime {
  [self spawnSlime:ccp(0,0)];
}

- (void) spawnSlime:(CGPoint)screenTarget {
  if (spawnCannon != nil) {
      CGPoint gameTarget;// = [cameraManager getGamePoint:screenTarget];
    [spawnCannon spawnSlime:gameTarget];
  }
   else {
    [spawnPortal spawn];
  }
}

- (void) setGoalPortal:(GoalPortal *)portal {
	goalPortal = portal;
	[items add:goalPortal];
}

- (void) addGameItem:(GameItem *)item {
  [items put:[item id] param1:item];
}

- (void) removeGameItem:(GameItem *)item {
  if (item != nil) {
    if ([items containsKey:[item id]]) {
      [item destroy];
      [items remove:[item id]];
    }
  }
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
		[scene  removeChild:customOverLayer cleanup:YES];
		customOverLayer = nil;
	}
}

- (void) setIsHudEnabled:(BOOL)value {
  if (!value) {
    if (isHudEnabled) {
      [scene removeChild:hudLayer cleanup:NO];
    }
  }
   else {
    if (!isHudEnabled) {
      [scene addChild:hudLayer z:hudZ];
    }
  }
  isHudEnabled = value;
}
/*TODO
- (void) draw:(GL10 *)gl {

  for (GameItem * item in items values) {
    [item draw:gl];
  }

}
*/
- (void) dealloc {
  //[world release];
 // [gravity release];
  [items release];
  [backgroundSprite release];
 // [contactManager release];
  [spawnPortal release];
  [spawnCannon release];
  [goalPortal release];
  [scene release];
  [levelLayer release];
  [hudLayer release];
  [backgroundLayer release];
  [gameLayer release];
  [customOverLayer release];
  [label release];
  //[levelOrigin release];
  //[cameraManager release];
  [currentLevelName release];
  [itemsToRemove release];
  [itemsToAdd release];
  [super dealloc];
}

@end
