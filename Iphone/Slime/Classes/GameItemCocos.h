#import "cocos2d.h"
#import "GameItem.h"
#import "TextureMode.h"

@interface GameItemCocos : GameItem {
  NSMutableDictionary * animationList;
  CCAction * currentAction;
  CCSprite * sprite;
  CCNode * rootNode;
  CCTexture2D * texture;
  //AMZ pas sure pour texturmode    
  TextureMode textureMode;
}

@property(nonatomic,assign) CCSprite * sprite;
@property (nonatomic,assign) NSMutableDictionary *animationList;
@property (nonatomic,assign) CCAction *currentAction;
@property (nonatomic,assign) CCNode *rootNode;
//@property (nonatomic,retain, readwrite) CCTexture2D *texture;


- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) destroy;
- (void) setSprite:(CCSprite *)affectSprite;
- (CCAnimation *) getReferenceAnimation;
- (void) transformTexture;
- (void) setTexture:(CCTexture2D *)my_texture;
//- (void) draw:(GL10 *)gl;
- (CGSize) getTextureSize;
- (void) setAnimationList:(NSMutableDictionary *)animations;
- (void) render:(float)delta;
- (void) addAnim:(NSString *)animName frameCount:(int)frameCount;
+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount;
+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount interval:(float)interval;
- (void) pause;
- (void) resume;
@end
