#import "HudLayer.h"
#import "cocos2d.h"
#import "LevelSelection.h"


@implementation HudLayer

- (id) init {
    if ((self = [super init])) {
        
        CCLabelTTF *backLabel = [CCLabelTTF labelWithString:@"Back" fontName:@"Marker Felt" fontSize:32];
        CCMenuItem * itemBack = [CCMenuItemLabel itemWithLabel:backLabel target:self selector:@selector(goBack:)];
        CCLabelTTF *pauseLabel = [CCLabelTTF labelWithString:@"Back" fontName:@"Marker Felt" fontSize:32];
        
        CCMenuItem * itemPause = [CCMenuItemLabel itemWithLabel:pauseLabel target:self selector:@selector(goPause:)];
        CGSize s = [[CCDirector sharedDirector] winSize];
        [itemBack setPosition:ccp(-50, s.height/ 2 - 20)];
        [itemPause setPosition:ccp(50, s.height / 2 - 20)];
        CCMenu * menu = [CCMenu menuWithItems:itemBack, itemPause, nil];
        [self addChild:menu];
    }
    return self;
}

- (void) goBack:(NSObject *)sender {
    [[CCDirector sharedDirector] replaceScene:[[LevelSelection get] scene]];
}

- (void) goPause:(NSObject *)sender {
    [currentLevel togglePause];
}

@end
