#import "Level.h"
#import "CCNode.h"
#import "ItemFactoryBase.h"

@interface GameItemCocosFactory : ItemFactoryBase {
}

//- (void) attach:(Level *)my_level attachNode:(CCNode *)attachNode;
- (void) attach:(Level *)my_level attachNode:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio;
- (void) detach;
@end
