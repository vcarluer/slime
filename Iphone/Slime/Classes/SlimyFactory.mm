//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

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


+ (Slimy *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[Slimy alloc] init:spriteSheet x:my_x y:my_x width:my_width height:my_height world:world worldRatio:worldRatio] autorelease];
}

+ (Slimy *) createSlimy:(float)my_x y:(float)my_y ratio:(float)my_ratio {
	Slimy * my_slimy = [SlimyFactory instantiate:my_x y:my_y width:Slimy_Default_Width * my_ratio height:Slimy_Default_Height * my_ratio];
  return my_slimy;

}


@end
