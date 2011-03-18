#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"
#import "Bumper.h"

@interface BumperFactory : GameItemPhysicFactory {
}

- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (Bumper *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa;
- (Bumper *) createBL:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height powa:(float)my_powa;
- (void) createAnimList;
- (NSString *) getPlistPng;
- (Bumper *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(Bumper *)item;

@end
