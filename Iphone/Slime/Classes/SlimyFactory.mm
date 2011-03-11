#import "SlimyFactory.h"

@implementation SlimyFactory

- (void) createAnimList {
  [self createAnim:Anim_Burned_Wait frameCount:2];
  [self createAnim:Anim_Burning frameCount:5];
  [self createAnim:Anim_Falling frameCount:3];
  [self createAnim:Anim_Landing_H frameCount:3];
  [self createAnim:Anim_Landing_V frameCount:3];
  [self createAnim:Anim_Wait_H frameCount:5];
  [self createAnim:Anim_Wait_V frameCount:5];
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

- (Slimy *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[Slimy alloc] init:spriteSheet x:my_x y:my_x width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

+ (Slimy *) create:(float)x y:(float)y ratio:(float)my_ratio {
  Slimy * my_slimy = [self  instantiate:x y:y width:Slimy_Default_Width * my_ratio height:Slimy_Default_Height * my_ratio];
  return my_slimy;
}

@end
