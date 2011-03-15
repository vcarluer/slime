#import "HomeLayer.h"
#import "SpriteSheetFactory.h"
#import "LevelSelection.h"


HomeLayer * layer;

@implementation HomeLayer

+ (HomeLayer *) get {
    if (layer == nil) {
        layer = [[[HomeLayer alloc] init] autorelease];
    }
    return layer;
}

- (id) init {
    if ((self = [super init])) {
        //AMZ remove
        CCSpriteBatchNode  * my_spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo" isExcluded:YES ];
        //[self addChild:my_spriteSheet];
        CCSprite * sprite = [CCSprite spriteWithSpriteFrame:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:@"SlimeTitle.png"]];     
        [my_spriteSheet addChild:sprite];
        CGSize s = [[CCDirector sharedDirector] winSize];
        [sprite setPosition:ccp(s.width / 2, s.height / 2)];
        float shiftMenu = -100.0f;
        CCLabelTTF *playLabel = [CCLabelTTF labelWithString:@"Play" fontName:@"Marker Felt" fontSize:32];
        CCMenuItem * playMenu = [CCMenuItemLabel itemWithLabel:playLabel target:self selector:@selector(selectPlay:)];
        [playMenu setPosition:ccp(0, shiftMenu)];
        CCMenu * menu = [CCMenu menuWithItems:playMenu , nil];
        [self addChild:menu];
    }
    return self;
}



- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	return [super ccTouchesEnded:touches withEvent:event];
}

- (void) onEnter {
    [super onEnter];
}

- (void) selectPlay:(NSObject *)sender {
     [[CCDirector sharedDirector] replaceScene:[[LevelSelection get] scene]];
}

@end
