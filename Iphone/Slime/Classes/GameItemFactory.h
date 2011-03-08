#import "CCNode.h"
#import "Level.h"

@interface GameItemFactory : NSObject {
	BOOL isAttached;
	Level * level;
}

- (void) attach:(Level *)my_level;
- (void) detach;
- (id) create;
- (id) create:(float)x y:(float)y;
- (id) create:(float)x y:(float)y width:(float)width height:(float)height;
- (id) instantiate:(float)x y:(float)y width:(float)width height:(float)height;
@end

