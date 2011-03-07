#import "GALogoLayer.h"

/*
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
    nextCallback = [[[GALogoLayer_Anon1 alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  if (!isInit) {
    CCSpriteSheet * spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo"];
    [self addChild:spriteSheet];
    CCSpriteFrame * spriteFrame = [[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:@"JulenGarciaGA.png"];
    CCSprite * sprite = [CCSprite sprite:spriteFrame];
    [spriteSheet addChild:sprite];
    [sprite setPosition:[CGPoint make:[[CCDirector sharedDirector] winSize].width / 2 param1:[[CCDirector sharedDirector] winSize].height / 2]];
  }
  [self schedule:nextCallback];
  onEnterTime = [System currentTimeMillis];
}


- (void) update:(float)d {
	long elapsed = ([System currentTimeMillis] - onEnterTime) / 1000;
	if (elapsed > waitLogoSec) {
		[self unschedule:nextCallback];
		CCScene * nextScene = [SlimeLoadingLayer scene];
		[[CCDirector sharedDirector] replaceScene:nextScene];
	}
}


- (void) dealloc {
  [nextCallback release];
  [super dealloc];
}
*/
@end
