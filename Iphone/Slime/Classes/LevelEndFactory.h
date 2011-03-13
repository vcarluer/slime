#import "LevelEnd.h"
#import "GameItemPhysicFactory.h"

@interface LevelEndFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
- (LevelEnd *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(LevelEnd *)item;
@end
