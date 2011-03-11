#import "Level.h"
#import "gameitem.h"


@interface HomeLevelHandler : GameItem {
  long startHome;
  double nextRand;
  int maxSlime;
  double minSpawn;
  double maxSpawn;
  int slimeCount;
  //TODO
    //BOOL isPaused;
}

- (id) init:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
- (void) render:(float)delta;
@end
