#import "cocos2d.h"
#import <OpenGLES/ES1/glext.h>
#import "CCGL.h"
#import <OpenGLES/ES1/glext.h>


//  Slime_V2
//
//  Created by antonio Munoz on 07/03/11.
//  Copyright none 2011. All rights reserved.
//		


@interface GameItem : NSObject  {
	
	
	CGPoint position;
	float angle;
	float width;
	float height;
	BOOL  isPaused;
	/*
	NSMutableDictionary * animationList;
	CCAction * currentAction;
	CCSprite * sprite;
	CCNode * rootNode;
	 */
}

@property (nonatomic,readwrite) CGPoint position;
@property (nonatomic,readwrite) float angle;
@property (nonatomic,readwrite) float width;
@property (nonatomic,readwrite) float height;

/*	
@property (nonatomic,assign) NSMutableDictionary *animationList;
@property (nonatomic,assign) CCAction *currentAction;
@property (nonatomic,assign) CCSprite *sprite;
@property (nonatomic,assign) CCNode *rootNode;
*/


- (id) init:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) destroy;
- (void) render:(float)delta;
//- (void) draw:(GL10 *)gl;
- (void) setPause:(BOOL)value;
- (void) pause;
- (void) resume;


/*
- (void) setSprite:(CCSprite *)affectSprite;
- (CCAnimation *) getReferenceAnimation;
- (void) transformTexture;
- (void) setAnimationList:(NSMutableDictionary *)animations;
- (void) render:(float)delta;
- (void) addAnim:(NSString *)animName frameCount:(int)frameCount;
+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount;
*/

@end
