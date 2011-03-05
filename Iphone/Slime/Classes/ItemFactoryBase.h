#import "CCAnimation.h"
#import "CCNode.h"
#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"

@interface ItemFactoryBase : NSObject {
  NSMutableDictionary * sharedAnimations;
  CCSpriteBatchNode * spriteSheet;
  BOOL isInit;
  BOOL isAttached;
  CCNode * rootNode;
  float ratio;
}

@property (nonatomic,assign) CCSpriteBatchNode * spriteSheet;

- (id) init;
- (void) initAnimation;
- (void) createAnimList;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount;
- (NSString *) getPlistPng;
- (id) create;
- (id) create:(float)x y:(float)y;
- (id) create:(float)x y:(float)y width:(float)width height:(float)height;
- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(id)item;




@end
