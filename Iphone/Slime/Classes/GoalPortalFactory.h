
#import "GameItemPhysicFactory.h"
#import "GoalPortal.h"

@interface GoalPortalFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
- (GoalPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(GoalPortal *)item;
@end
