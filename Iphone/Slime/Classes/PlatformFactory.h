#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"
#import "Platform.h"

@interface PlatformFactory : GameItemPhysicFactory {
}

- (Platform *) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) createAnimList;
- (NSString *) getPlistPng;
- (Platform *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(Platform *)item;
@end
