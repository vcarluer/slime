#import "LevelSelectionLayer.h"

@implementation LevelSelectionLayer

- (id) init {
    if ((self = [super init])) {
        
        CCLabelTTF *backLabel = [CCLabelTTF labelWithString:@"Back" fontName:@"Marker Felt" fontSize:32];        
        CCMenuItem * goBackMenu = [CCMenuItemLabel itemWithLabel:backLabel target:self selector:@selector(goBack:)];
        
        CCLabelTTF *level1Label = [CCLabelTTF labelWithString:@"Level 1" fontName:@"Marker Felt" fontSize:32];
        CCMenuItem * testMenu1 = [CCMenuItemLabel itemWithLabel:level1Label target:self selector:@selector(doTest:)];
        
        CCLabelTTF *level2Label = [CCLabelTTF labelWithString:@"Level 2" fontName:@"Marker Felt" fontSize:32];
        CCMenuItem * testMenu2 = [CCMenuItemLabel  itemWithLabel:level2Label target:self selector:@selector(doTest2:)];
        
        CCLabelTTF *level3Label = [CCLabelTTF labelWithString:@"Level 3" fontName:@"Marker Felt" fontSize:32];
        CCMenuItem * testMenu3 = [CCMenuItemLabel itemWithLabel:level3Label target:self selector:@selector(doTest:)];
          CCMenu * menu = [CCMenu menuWithItems:goBackMenu, testMenu1, testMenu2, testMenu3, nil];
        //todo
        // 
       // [menu alignItemsInColumns:[NSArray arrayWithObjects:1, 3, nil]];
        [self addChild:menu];
    }
    return self;
}

- (void) doTest:(NSObject *)sender {
    Level * level = [Level get:LEVEL_1 forceReload:YES];
    [[CCDirector sharedDirector] replaceScene:[level scene]];
}

- (void) doTest2:(NSObject *)sender {
    Level * level = [Level get:LEVEL_2 forceReload:YES];
    [[CCDirector sharedDirector] replaceScene:[level scene]];
}

- (void) goBack:(NSObject *)sender {
    Level * level = [Level get:LEVEL_HOME forceReload:YES];
    [[CCDirector sharedDirector] replaceScene:[level scene]];
}

@end
