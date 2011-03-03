//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCNode.h"
#import "ItemFactoryBase.h"

@interface GameItemFactory : ItemFactoryBase {
}

- (void) Attach:(CCNode *)attachNode;
- (void) Detach;
@end
