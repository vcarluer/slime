#import "cocos2d.h"
#import "Box2D.h"
#import "GameItemFactory.h"
#import "SpawnPortal.h"

@interface SpawnPortalFactory : GameItemFactory {
}

- (SpawnPortal *) createAndMove:(float)my_x y:(float)my_y moveBy:(float)moveBy speed:(float)speed;
- (void) createAnimList;
- (NSString *) getPlist;
- (NSString *) getPng;
- (SpawnPortal *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) runFirstAnimations:(SpawnPortal *)item;
@end
