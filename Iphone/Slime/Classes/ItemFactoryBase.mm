#import "ItemFactoryBase.h"
#import "GameItem.h"
#import "SpriteSheetFactory.h"

@implementation ItemFactoryBase
@synthesize spriteSheet;

- (id) init {
  if ((self = [super init])) {
    isInit = NO;
    isAttached = NO;
    ratio = 1.0f;
  }
  return self;
}

- (void) initAnimation {
  if (!isInit) {
	
	spriteSheet =  [SpriteSheetFactory getSpriteSheet:[self getPlistPng]];
    sharedAnimations = [[NSMutableDictionary alloc] init];
    [self createAnimList];
    isInit = YES;
  }
}
/*
- (void) createAnimList {
}
*/
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
  [sharedAnimations setObject:[GameItemCocos createAnim:animName frameCount:frameCount] forKey:animName];
}
/*
- (void) createAnim:(NSString *)animName frameCount:(int)frameCount interval:(float)interval{
    
}

- (NSString *) getPlistPng {
}
*/
- (void) destroy {
  isInit = NO;
}

- (id) create {
  return [self create:0 y:0];
}

- (id) create:(float)x y:(float)y {
  return [self create:x y:y width:0 height:0];
}

- (id) create:(float)x y:(float)y width:(float)width height:(float)height {
  if (isAttached) {
    GameItemCocos * item = [self instantiate:x y:y width:width height:height];
    [self createSprite:item];
    [item setAnimationList:sharedAnimations];
    [self runFirstAnimations:item];
    [level addItemToAdd:item];
    return item;
  }
   else {
    return nil;
  }
}

- (void) createSprite:(GameItemCocos *)gameItem  {
    
}

- (id) createBL:(float)x y:(float)y width:(float)width height:(float)height {
  return [self create:x + width / 2 y:y + height / 2 width:width height:height];
}
/*
- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
}

- (void) runFirstAnimations:(id)item {
}
*/
- (void) dealloc {
    
  [sharedAnimations release];
  [spriteSheet release];
  [rootNode release];
  [level release];
  [super dealloc];
    
}

@end
