#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"
#import "Bumper.h"

@interface BumperFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlist;
- (NSString *) getPng;
- (Bumper *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(Bumper *)item;
- (Bumper *) create:(float)x y:(float)y width:(float)width height:(float)height;
- (Bumper *) create:(float)x y:(float)y width:(float)width height:(float)height powa:(float)powa;
@end
