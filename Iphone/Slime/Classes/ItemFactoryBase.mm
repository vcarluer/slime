#import "ItemFactoryBase.h"
#import "GameItem.h"
#import "SpriteSheetFactory.h"

@implementation ItemFactoryBase
@synthesize spriteSheet;

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
	
	spriteSheet =  [SpriteSheetFactory getSpriteSheet:[self getPlistPng]];
    sharedAnimations = [[[NSMutableDictionary alloc] init] autorelease];
    [self createAnimList];
    isInit = YES;
  }
}

- (void) createAnimList {
}

- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
  [sharedAnimations setObject:[GameItem createAnim:animName frameCount:frameCount] forKey:animName];
}

- (NSString *) getPlistPng {
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
