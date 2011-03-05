#import "BumperFactory.h"

@implementation BumperFactory

- (void) createAnimList {
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (Bumper *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Bumper alloc] init:rootNode x:x y:y width:width height:height world:world worldRatio:worldRatio ] autorelease];
}



- (void) runFirstAnimations:(Bumper *)item {
  [item waitAnim];
}

- (Bumper *) create:(float)x y:(float)y width:(float)width height:(float)height {
  return [self create:x y:y width:width height:height powa:Default_Powa];
}

- (Bumper *) create:(float)x y:(float)y width:(float)width height:(float)height powa:(float)powa {
  Bumper * bumper = [[Bumper alloc ] init:rootNode x:x y:y width:width height:height world:world worldRatio:worldRatio];
  [bumper setPowa:powa];
  if (bumper != nil) {
    CCSprite * sprite = [CCSprite spriteWithSpriteFrameName:Texture_Wait];
    [bumper setSprite:sprite];
  }
  return bumper;
}

@end
