#import "SlimeLoadingLayer.h"


NSLock *slimeLoadingLock;

BOOL isInit;
CCScene * scene;

@implementation SlimeLoadingLayer
@synthesize currentLevel;

+ (CCScene *) scene {
    if (scene == nil) {
        scene = [CCScene node];
        CGSize s = [[CCDirector sharedDirector] winSize];
        float width = s.width;
        float height = s.height;
        
        CCLayerColor * colorLayer = [CCLayerColor layerWithColor:ccc4(0,0,0,255) width:width height:height];
        [scene addChild:colorLayer];
        [scene addChild:[[SlimeLoadingLayer alloc] init] ];
    }
    return scene;
}

- (id) init {
    if ((self = [super init])) {
        
        [self scheduleUpdate];
        slimeLoadingLock = [[NSLock alloc] init];
        
    }
    return self;
}

- (void) onEnter {
    [super onEnter];
    if (isInit == NO) {
        //spriteSheet = [SpriteSheetFactory getSpriteSheet:@"logo" isExcluded:YES];
        //[self addChild:spriteSheet];
        //CCSpriteFrame * spriteFrame = [[CCSpriteFrameCache sharedSpriteFrameCache]  spriteFrameByName:@"SlimeTitle.png"];
        
        
        //   sprite = [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:@"SlimeTitle.png"]];
        //CCSprite * my_sprite = [CCSprite spriteWithSpriteFrame:spriteFrame];
        //[spriteSheet addChild:my_sprite];
        //[sprite setPosition:ccp(-200 ,0)];
        
        // [sprite setPosition:ccp([[CCDirector sharedDirector] winSize].width / 2 ,[[CCDirector sharedDirector] winSize].height / 2)];
        [self unscheduleUpdate]; 
        //[NSThread detachNewThreadSelector:@selector(run) toTarget:self withObject:nil];
        [self run];
    }
    else {
        //[[currentLevel cameraManager] setCameraView];
    }
    [self scheduleUpdate];
}

- (void) update:(float)d {
    
    [slimeLoadingLock lock];
    if (isInit) {
        [self unscheduleUpdate]; 
        // [spriteSheet removeChild:sprite cleanup:YES];
      
        [self scheduleUpdate];
    }
    [slimeLoadingLock unlock];
}

- (void) run {
    //NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init];  
    [slimeLoadingLock lock];
    currentLevel = [Level get:LEVEL_HOME];
    [currentLevel retain];
      [[CCDirector sharedDirector] replaceScene:[currentLevel scene]];
    isInit = YES;
    [slimeLoadingLock unlock];
//    [pool release];
}

- (void) dealloc {
    /*
    [currentLevel release];
    [syncObj release];
    [spriteSheet release];
    [sprite release];
    //[nextCallback release];
    [super dealloc];
*/
     }

@end
