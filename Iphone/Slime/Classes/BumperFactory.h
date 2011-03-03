//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"		
#import "Bumper.h"

@interface BumperFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
- (Bumper *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(Bumper *)item;
- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa;
@end
