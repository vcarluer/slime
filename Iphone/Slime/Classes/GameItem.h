#import "cocos2d.h"

//  Slime_V0.1
//
//  Created by antonio Munoz on 18/02/11.
//  Copyright none 2011. All rights reserved.
//


@interface GameItem : NSObject  {
	
	
	CGPoint position;
	float angle;
	float width;
	float height;
	NSMutableDictionary * animationList;
	CCAction * currentAction;
	CCSprite * sprite;
	CCNode * rootNode;
}

@property (nonatomic,readwrite) CGPoint position;
@property (nonatomic,readwrite) float angle;
@property (nonatomic,readwrite) float width;
@property (nonatomic,readwrite) float height;

@property (nonatomic,assign) NSMutableDictionary *animationList;
@property (nonatomic,assign) CCAction *currentAction;
@property (nonatomic,assign) CCSprite *sprite;
@property (nonatomic,assign) CCNode *rootNode;



//@property(nonatomic, retain, readonly) CGPoint position;
//@property(nonatomic, retain, readonly) CGSize * textureSize;
- (id) init:(CCNode *)my_node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) setSprite:(CCSprite *)affectSprite;
- (CCAnimation *) getReferenceAnimation;
- (void) transformTexture;
- (void) setAnimationList:(NSMutableDictionary *)animations;
- (void) render:(float)delta;
- (void) addAnim:(NSString *)animName frameCount:(int)frameCount;
+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount;
@end
