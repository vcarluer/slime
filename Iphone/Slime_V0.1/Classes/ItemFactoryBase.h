#import "cocos2d.h"
#import "GameItem.h"

@interface ItemFactoryBase : GameItem {
	NSMutableDictionary * sharedAnimations;
	CCSpriteBatchNode * spriteSheet;
	BOOL isInit;
	BOOL isAttached;
	//CCNode * rootNode;
	float ratio;
}

- (id) init;
- (void) initAnimation;
- (void) createAnimList;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount;
- (NSString *) getPlistPng;
- (void) destroy;
- (id) create;
- (id) create:(float)my_x y:(float)my_y;
- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (id) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(id)item;
@end
