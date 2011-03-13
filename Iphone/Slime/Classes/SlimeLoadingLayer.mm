#import "SlimeLoadingLayer.h"
/**
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
*/
BOOL isInit;
CCScene * scene;

@implementation SlimeLoadingLayer

+ (CCScene *) scene {
  if (scene == nil) {
    scene = [CCScene node];
    CGSize s = [[CCDirector sharedDirector] winSize];
    float width = s.width;
    float height = s.height;
    
    CCLayerColor * colorLayer = [CCLayerColor layerWithColor:ccc4(0,0,0,255) width:width height:height];
    [scene addChild:colorLayer];
    [scene addChild:[[[SlimeLoadingLayer alloc] init] autorelease]];
  }
  return scene;
}

- (id) init {
  if ((self = [super init])) {
  //todo
  //  nextCallback = [[[SlimeLoadingLayer_Anon1 alloc] init] autorelease];
  //  syncObj = [[[NSObject alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  if (isInit == NO) {
    spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo" isExcluded:YES];
    [self addChild:spriteSheet];
              CCSpriteFrame * spriteFrame = [[CCSpriteFrameCache sharedSpriteFrameCache]  spriteFrameByName:@"SlimeTitle.png"];
    
      
   //   sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:@"SlimeTitle.png"]];
      CCSprite * my_sprite = [CCSprite spriteWithSpriteFrame:spriteFrame];
    [spriteSheet addChild:my_sprite];
   [sprite setPosition:ccp([[CCDirector sharedDirector] winSize].width / 2 ,[[CCDirector sharedDirector] winSize].height / 2)];
  //todo
      //  InitThread * initThread = [[[InitThread alloc] init] autorelease];
   // [initThread start];
  }
   else {
    //[[currentLevel cameraManager] setCameraView];
  }
//  [self schedule:nextCallback];
}

- (void) dealloc {
  [currentLevel release];
  [syncObj release];
  [spriteSheet release];
  [sprite release];
  //[nextCallback release];
  [super dealloc];
}

@end
