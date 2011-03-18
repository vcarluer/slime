
#import "cocos2d.h"
//#import "Level.h"
#import "SpriteSheetFactory.h"

#import "CCLayer.h"
#import "CCScene.h"
#import "CCDirector.h"
#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"




extern BOOL isInit;

@interface SlimeLoadingLayer : CCLayer {
  Level * currentLevel;
  NSObject * syncObj;
  CCSpriteBatchNode * spriteSheet;
  CCSprite * sprite;
 // UpdateCallback * nextCallback;
}
@property(nonatomic, retain, readwrite) Level * currentLevel;


+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) run;
-(void) update: (ccTime) dt;


@end

