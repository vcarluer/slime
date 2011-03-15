#import "LevelEnd.h"
#import "GameItemPhysicFactory.h"

@interface LevelEndFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
- (LevelEnd *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(LevelEnd *)item;
@end
