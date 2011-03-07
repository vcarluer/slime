
#import "cocos2d.h"
#import "Box2D.h"
#import "GameItemPhysicFactory.h"
#import "Slimy.h"

@interface SlimyFactory : GameItemPhysicFactory {
}

- (void) createAnimList;
- (void) runFirstAnimations:(Slimy *)item;
- (NSString *) getPlist;
- (NSString *) getPng;
- (Slimy *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (Slimy *) create:(float)my_x y:(float)my_y ratio:(float)my_ratio;
@end
