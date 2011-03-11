#import "LevelSelectionLayer.h"

@implementation LevelSelectionLayer

- (id) init {
  if (self = [super init]) {
    CCMenuItem * goBackMenu = [CCMenuItemLabel item:@"Back" param1:self param2:@"goBack"];
    CCMenuItem * testMenu1 = [CCMenuItemLabel item:@"Level 1" param1:self param2:@"doTest"];
    CCMenuItem * testMenu2 = [CCMenuItemLabel item:@"Level 2" param1:self param2:@"doTest2"];
    CCMenuItem * testMenu3 = [CCMenuItemLabel item:@"Level 3" param1:self param2:@"doTest"];
    CCMenu * menu = [CCMenu menu:goBackMenu param1:testMenu3 param2:testMenu2 param3:testMenu1];
    [menu alignItemsInColumns:[NSArray arrayWithObjects:1, 3, nil]];
    [self addChild:menu];
  }
  return self;
}

- (void) doTest:(NSObject *)sender {
  Level * level = [Level get:Level.LEVEL_1 param1:YES];
  [[CCDirector sharedDirector] replaceScene:[level scene]];
}

- (void) doTest2:(NSObject *)sender {
  Level * level = [Level get:Level.LEVEL_2 param1:YES];
  [[CCDirector sharedDirector] replaceScene:[level scene]];
}

- (void) goBack:(NSObject *)sender {
  Level * level = [Level get:Level.LEVEL_HOME param1:YES];
  [[CCDirector sharedDirector] replaceScene:[level scene]];
}

@end
