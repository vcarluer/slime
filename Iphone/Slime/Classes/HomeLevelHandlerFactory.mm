#import "HomeLevelHandlerFactory.h"


@implementation HomeLevelHandlerFactory

- (HomeLevelHandler *) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
  return [[[HomeLevelHandler alloc] init:my_x y:my_y width:my_width height:my_height] autorelease];
}

@end
