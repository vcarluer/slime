#import "cocos2d.h"
#import "Level.h"

@interface ItemFactoryBase : NSObject {
  NSMutableDictionary * sharedAnimations;
  CCSpriteBatchNode * spriteSheet;
  BOOL isInit;
  BOOL isAttached;
  CCNode * rootNode;
  float ratio;
  Level * level;
}

@property (nonatomic,assign) CCSpriteBatchNode * spriteSheet;

- (id) init;
- (void) initAnimation;
- (void) createAnimList;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount interval:(float)interval;
- (NSString *) getPlistPng;
- (id) create;
- (id) create:(float)x y:(float)y;
- (id) create:(float)x y:(float)y width:(float)width height:(float)height;
- (id) createBL:(float)x y:(float)y width:(float)width height:(float)height;
- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(id)item;




@end
