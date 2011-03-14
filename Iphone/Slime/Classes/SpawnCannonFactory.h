#import "CCSprite.h"
#import "CCSpriteFrameCache.h"
#import "GameItemPhysicFactory.h"

@interface SpawnCannonFactory : GameItemPhysicFactory {
}

- (SpawnCannon *) create:(float)x y:(float)y width:(float)width height:(float)height;
- (void) createAnimList;
- (NSString *) getPlistPng;
- (SpawnCannon *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(SpawnCannon *)item;
@end
