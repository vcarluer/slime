#import "HudLayer.h"

@implementation HudLayer

- (id) init {
  if (self = [super init]) {
    CCMenuItem * itemBack = [CCMenuItemLabel item:@"Back" param1:self param2:@"goBack"];
    CCMenuItem * itemPause = [CCMenuItemLabel item:@"Pause" param1:self param2:@"goPause"];
    [itemBack setPosition:[CGPoint make:-50 param1:[[[CCDirector sharedDirector] winSize] height] / 2 - 20]];
    [itemPause setPosition:[CGPoint make:50 param1:[[[CCDirector sharedDirector] winSize] height] / 2 - 20]];
    CCMenu * menu = [CCMenu menu:itemBack param1:itemPause];
    [self addChild:menu];
  }
  return self;
}

- (void) goBack:(NSObject *)sender {
  [[CCDirector sharedDirector] replaceScene:[[LevelSelection get] scene]];
}

- (void) goPause:(NSObject *)sender {
  [Level.currentLevel togglePause];
}

@end
