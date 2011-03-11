#import "GameItemFactory.h"
#import "HomeLevelHandler.h"

@interface HomeLevelHandlerFactory : GameItemFactory {
}

- (HomeLevelHandler *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
@end
