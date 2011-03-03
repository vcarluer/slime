#import "ItemFactoryBase.h"
#import "GameItem.h"

@implementation ItemFactoryBase

- (id) init {
  if (self = [super init]) {
    isInit = NO;
    isAttached = NO;
    ratio = 1.0f;
  }
  return self;
}

- (void) initAnimation {
  if (!isInit) {
    [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFrames:[self plist]];
    spriteSheet = [CCSpriteSheet spriteSheet:[self png]];
    sharedAnimations = [[[NSMutableDictionary alloc] init] autorelease];
    [self createAnimList];
    isInit = YES;
  }
}

- (void) createAnimList {
}

- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
  [sharedAnimations put:animName param1:[GameItem createAnim:animName param1:frameCount]];
}

- (NSString *) getPlist {
}

- (NSString *) getPng {
}

- (id) create {
  return [self create:0 y:0];
}

- (id) create:(float)x y:(float)y {
  return [self create:x y:y width:0 height:0];
}

- (id) create:(float)x y:(float)y width:(float)width height:(float)height {
  if (isAttached) {
    id item = [self instantiate:x y:y width:width height:height];
    [item setAnimationList:sharedAnimations];
    [self runFirstAnimations:item];
    return item;
  }
   else {
    return nil;
  }
}

- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
}

- (void) runFirstAnimations:(id)item {
}

- (void) dealloc {
  [sharedAnimations release];
  [spriteSheet release];
  [rootNode release];
  [super dealloc];
}

@end
