
#import "GameItemPhysicFactory.h"
#import "GoalPortal.h"

@interface GoalPortalFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlist;
- (NSString *) getPng;
- (GoalPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(GoalPortal *)item;
@end
