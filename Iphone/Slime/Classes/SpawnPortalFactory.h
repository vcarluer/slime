//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "cocos2d.h"
#import "Box2D.h"
#import "GameItemFactory.h"
#import "SpawnPortal.h"

@interface SpawnPortalFactory : GameItemFactory {
}

+ (SpawnPortal *) createAndMove:(float)my_x y:(float)my_y moveBy:(float)moveBy speed:(float)speed;
- (void) createAnimList;
- (NSString *) getPlist;
- (NSString *) getPng;
- (SpawnPortal *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
- (void) runFirstAnimations:(SpawnPortal *)item;
@end
