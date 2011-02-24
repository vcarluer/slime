#import "CCNode.h"
#import "ItemFactoryBase.h"

@interface GameItemFactory : ItemFactoryBase {
}

- (void) Attach:(CCNode *)attachNode;
- (void) Detach;
@end
