#import "CCNode.h"
#import "Level.h"
#import "GameItem.h"
#import "ItemFactoryBase.h"

@interface GameItemFactory : NSObject {
    GameItem * my_gameitem;
    ItemFactoryBase * my_itemfactorybase;
	BOOL isAttached;
	Level * level;
}

- (void) attach:(Level *)my_level;
- (void) detach;
- (id) create;
- (id) create:(float)my_x y:(float)my_y;
- (id) create:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;

- (id) instantiate:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height;
 
@end

