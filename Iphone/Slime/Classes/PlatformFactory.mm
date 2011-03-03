//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "PlatformFactory.h"

@implementation PlatformFactory

- (Platform *) create:(float)x y:(float)y width:(float)width height:(float)height {
  Platform * platform = [super create:x y:y width:width height:height];
  if (platform != nil) {
 //todo
	  CCSprite * sprite; //= [CCSprite sprite:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:platform->texture()]];
    [platform setSprite:sprite];
  }
  return platform;
}

- (void) createAnimList {
}

- (NSString *) getPlist {
  return @"labo.plist";
}

- (NSString *) getPng {
  return @"labo.png";
}

- (Platform *) instantiate:(float)x y:(float)y width:(float)width height:(float)height {
  return [[[Platform alloc] init:spriteSheet x:x y:y width:width height:height world:world worldRatio:worldRatio] autorelease];
}

- (void) runFirstAnimations:(Platform *)item {
}

@end
