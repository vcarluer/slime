//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
#import "SlimeLoadingLayer.h"


BOOL isInit;
CCScene * scene;

@implementation SlimeLoadingLayer

+ (CCScene *) scene {
  if (scene == nil) {
    scene = [CCScene node];
    [scene addChild:[[[SlimeLoadingLayer alloc] init] autorelease]];
  }
  return scene;
}

- (id) init {
  if (self = [super init]) {
    //nextCallback = [[[SlimeLoadingLayer_Anon1 alloc] init] autorelease];
    syncObj = [[[NSObject alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  if (isInit == NO) {
    CCSpriteSheet * spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo"];
    [self addChild:spriteSheet];
    CCSprite * sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:@"TitleLoading.png"]];
    [spriteSheet addChild:sprite];
	sprite.position = ccp([[CCDirector sharedDirector] winSize].width / 2 ,[[CCDirector sharedDirector] winSize].height / 2);
    float scaleW = [[CCDirector sharedDirector] winSize].width / 240;
    float scaleH = [[CCDirector sharedDirector] winSize].height / 400;
    float scale = MAX(scaleW , scaleH);
    [sprite setScale:scale];
    
	//TODO  
	//InitThread * initThread = [[[InitThread alloc] init] autorelease];
    //[initThread start];
  }
   else {
  //  [[currentLevel cameraManager] setCameraView];
  }
  //[self schedule:nextCallback];
}

- (void) update:(float)d {
	
	@synchronized(syncObj) 
	{
		if (isInit) {
		//	[self unschedule:nextCallback];
			[[CCDirector sharedDirector] replaceScene:[currentLevel scene]];
		}
	}
}




- (void) dealloc {
  [currentLevel release];
  [syncObj release];
//  [nextCallback release];
  [super dealloc];
}



- (void) run {
	//currentLevel = [Level get:Level->LEVEL_HOME];
	isInit = YES;
}


@end
