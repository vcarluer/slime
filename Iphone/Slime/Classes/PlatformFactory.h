#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"
#import "Platform.h"

@interface PlatformFactory : GameItemPhysicFactory {
}

- (Platform *) create:(float)x y:(float)y width:(float)width height:(float)height;
- (void) createAnimList;
- (NSString *) getPlist;
- (NSString *) getPng;
- (Platform *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(Platform *)item;
@end
