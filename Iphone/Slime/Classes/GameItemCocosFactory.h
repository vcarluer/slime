#import "Level.h"
#import "CCNode.h"

@interface GameItemCocosFactory : ItemFactoryBase {
}

- (void) attach:(Level *)level attachNode:(CCNode *)attachNode;
- (void) detach;
@end
