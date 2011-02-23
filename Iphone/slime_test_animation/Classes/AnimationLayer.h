#import "cocos2d.h"
#import "Box2D.h"

@interface AnimationLayer : CCLayer {
  CCSpriteSheet * spriteSheet;
  CCSprite * slime;
  int spriteCount;
  float size;
}

+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) onExit;
- (void) tick:(float)delta;
@end
