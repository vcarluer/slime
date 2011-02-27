#import "SlimyFactory.h"

@implementation SlimyFactory

- (void) createAnimList {
  [self createAnim:Anim_Burned_Wait param1:2];
  [self createAnim:Anim_Burning param1:5];
  [self createAnim:Anim_Falling param1:3];
  [self createAnim:Anim_Landing_H param1:3];
  [self createAnim:Anim_Landing_V param1:3];
  [self createAnim:Anim_Wait_H param1:5];
  [self createAnim:Anim_Wait_V param1:5];
}

- (void) runFirstAnimations:(Slimy *)item {
  [item waitAnim];
  [item fadeIn];
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (Slimy *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Slimy alloc] init:spriteSheet param1:x param2:y param3:width param4:height param5:world param6:worldRatio] autorelease];
}

- (Slimy *) create:(float)x y:(float)y ratio:(float)ratio {
  Slimy * slimy = [self create:x param1:y param2:Slimy_Default_Width * ratio param3:Slimy_Default_Height * ratio];
  return slimy;
}

@end
