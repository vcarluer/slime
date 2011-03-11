#import "Level.h"
#import "ItemFactoryBase.h"

@interface GameItemPhysicFxFactory : ItemFactoryBase {
  b2World * world;
  float worldRatio;
}

- (void) attach:(Level *)my_level attachNode:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio;
- (void) detach;
@end
