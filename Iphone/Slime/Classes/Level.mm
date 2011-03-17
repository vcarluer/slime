#import "Level.h"
#import "SlimeFactory.h"
#import "HardCodedLevelBuilder.h"
#import "spriteSheetFactory.h"

NSString * LEVEL_HOME = @"Home";
NSString * LEVEL_1 = @"Level_1";
NSString * LEVEL_2 = @"Level_2";
Level * currentLevel;
int customZ = 2;
int hudZ = 1;
float worldRatio = 32.0f;

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
@synthesize backgroundSprite;

- (id) init {
	if ((self = [super init])) {
		
        scene = [CCScene node];
        levelLayer = [[LevelLayer alloc] initWithLevel:self];
        hudLayer = [[HudLayer alloc] init] ;
        backgroundLayer = [[BackgoundLayer alloc] init] ;
        gameLayer = [CCLayer node];
        [gameLayer addChild:backgroundLayer z:0];
        [gameLayer addChild:levelLayer z:1];        
        levelOrigin = ccp(0,0);
        [gameLayer setAnchorPoint:levelOrigin];
        [scene addChild:gameLayer z:0];
        isHudEnabled = YES;
        [scene addChild:hudLayer z:hudZ];
        items = [[NSMutableArray alloc] init] ;
        //todo
        //    cameraManager = [[[CameraManager alloc] init:gameLayer] autorelease];
        itemsToRemove = [[NSMutableArray alloc] init] ;
        itemsToAdd = [[NSMutableArray alloc] init] ;
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
        //remove AMZ autorelease
        currentLevel = [[Level alloc] init] ;
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
    
    //[items release];
    spawnPortal = nil;
    goalPortal = nil;
    [self removeCustomOverLayer];
    [self setIsTouchEnabled:YES];
    [self setIsHudEnabled:YES];
}

- (void) initLevel {
    
	// Box2D world
	b2Vec2 my_gravity(0, -10);
	gravity = my_gravity;
	world = new b2World(gravity, true);		
	contactManager = new ContactManager;
	world->SetContactListener(contactManager);
    
    // Main game item spritesheet
    //amz remove
    [SpriteSheetFactory add:@"labo"];
    
    // Background
    CGSize screenSize = [CCDirector sharedDirector].winSize;
    CCSpriteBatchNode * my_spriteSheet = [SpriteSheetFactory getSpriteSheet:@"decor" isExcluded:YES]; 
    [self->backgroundLayer addChild:my_spriteSheet];
    // Sprite too big for VM in UbuntuRox
    
    backgroundSprite = [CCSprite spriteWithSpriteFrame:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:@"decor.png"]];
    [backgroundSprite setAnchorPoint:CGPointZero];
    [my_spriteSheet addChild:backgroundSprite];

    //hud
    label = [CCLabelTTF labelWithString:@"Hud !" fontName:@"Marker Felt" fontSize:16];	
    [hudLayer addChild:label];
    label.position = ccp( screenSize.width/2, screenSize.height-20);
    [self attachToFactory];
  
	
	
	
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
        
        [itemsToRemove removeAllObjects];
        //[cameraManager tick:delta];
    }
}

- (void) addItemToRemove:(GameItem *)item {
    [itemsToRemove addObject:item];
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
    
    for (GameItem * item in items) {
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
	[items addObject:goalPortal];
}

- (void) addGameItem:(GameItem *)item {
    [items addObject:item];
}

- (void) removeGameItem:(GameItem *)item {
    if (item != nil) {
        if ([items containsObject:item]) {
            [item destroy];
            [items removeObject:item];
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
    //AMZ change customz by 2
	[scene addChild:customOverLayer z:2];
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

-(void) draw
{
	// Default GL states: GL_TEXTURE_2D, GL_VERTEX_ARRAY, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
	// Needed states:  GL_VERTEX_ARRAY, 
	// Unneeded states: GL_TEXTURE_2D, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
	glDisable(GL_TEXTURE_2D);
	glDisableClientState(GL_COLOR_ARRAY);
	glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	
	world->DrawDebugData();
	
	// restore default GL states
	glEnable(GL_TEXTURE_2D);
	glEnableClientState(GL_COLOR_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    
}
- (void) dealloc {
    /*
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
     */
}

@end
