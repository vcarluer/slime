
#import "cocos2d.h"
//#import "Level.h"
#import "SpriteSheetFactory.h"
#import "UpdateCallback.h"
#import "CCColorLayer.h"
#import "CCLayer.h"
#import "CCScene.h"
#import "CCDirector.h"
#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"
#import "CGPoint.h"
#import "ccColor4B.h"

@interface SlimeLoadingLayer_Anon1 : NSObject <UpdateCallback> {
}

- (void) update:(float)d;
@end

@interface InitThread : Thread {
}

- (void) run;
@end

extern BOOL isInit;

@interface SlimeLoadingLayer : CCLayer {
  Level * currentLevel;
  NSObject * syncObj;
  CCSpriteSheet * spriteSheet;
  CCSprite * sprite;
  UpdateCallback * nextCallback;
}

+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) update:(float)d;
- (void) run;

@end

