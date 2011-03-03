//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "GameItemPhysicFactory.h"
#import "GoalPortal.h"

@interface GoalPortalFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (NSString *) getPlistPng;
+ (GoalPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(GoalPortal *)item;
@end
