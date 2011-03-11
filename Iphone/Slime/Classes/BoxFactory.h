#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"
#import "Box.h"

@interface BoxFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
- (Box *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(Box *)item;
- (Box *) create:(float)x y:(float)y width:(float)width height:(float)height;
@end
