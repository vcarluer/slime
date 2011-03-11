
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
- (Slimy *) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
+ (Slimy *) create:(float)x y:(float)y ratio:(float)ratio;
@end
