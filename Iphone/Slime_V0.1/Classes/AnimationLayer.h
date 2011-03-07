//  Slime
//
//  Created by antonio Munoz on 14/02/11.
//  Copyright 2011 none. All rights reserved.
//
#import "cocos2d.h"

@interface AnimationLayer : CCLayer {
  CCSpriteBatchNode * spriteSheet;
  CCSprite * slime;
  int spriteCount;
  float size;
}

@property (nonatomic,readwrite) int spriteCount;
@property (nonatomic,readwrite) float size;
@property (nonatomic,assign) CCSpriteBatchNode * spriteSheet;
@property (nonatomic,assign) CCSprite * slime;


+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) onExit;
- (void) tick:(float)delta;
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount;
@end
