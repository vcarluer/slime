#import "SlimeLoadingLayer.h"

@implementation SlimeLoadingLayer_Anon1

- (void) update:(float)d {

  @synchronized(syncObj) 
  {
    if (isInit) {
      [self unschedule:nextCallback];
      [spriteSheet removeChild:sprite param1:YES];
      [[CCDirector sharedDirector] replaceScene:[currentLevel scene]];
    }
  }
}

@end

@implementation InitThread

- (void) run {
  currentLevel = [Level get:Level.LEVEL_HOME];

  @synchronized(syncObj) 
  {
    isInit = YES;
  }
}

@end

BOOL isInit;
CCScene * scene;

@implementation SlimeLoadingLayer

+ (CCScene *) scene {
  if (scene == nil) {
    scene = [CCScene node];
    float width = [[[CCDirector sharedDirector] winSize] width];
    float height = [[[CCDirector sharedDirector] winSize] height];
    CCColorLayer * colorLayer = [CCColorLayer node:[[[ccColor4B alloc] init:0 param1:0 param2:0 param3:255] autorelease] param1:width param2:height];
    [scene addChild:colorLayer];
    [scene addChild:[[[SlimeLoadingLayer alloc] init] autorelease]];
  }
  return scene;
}

- (id) init {
  if (self = [super init]) {
    nextCallback = [[[SlimeLoadingLayer_Anon1 alloc] init] autorelease];
    syncObj = [[[NSObject alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  if (isInit == NO) {
    spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo" param1:YES];
    [self addChild:spriteSheet];
    sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:@"SlimeTitle.png"]];
    [spriteSheet addChild:sprite];
    [sprite setPosition:[CGPoint make:[[CCDirector sharedDirector] winSize].width / 2 param1:[[CCDirector sharedDirector] winSize].height / 2]];
    InitThread * initThread = [[[InitThread alloc] init] autorelease];
    [initThread start];
  }
   else {
    [[currentLevel cameraManager] setCameraView];
  }
  [self schedule:nextCallback];
}

- (void) dealloc {
  [currentLevel release];
  [syncObj release];
  [spriteSheet release];
  [sprite release];
  [nextCallback release];
  [super dealloc];
}

@end
