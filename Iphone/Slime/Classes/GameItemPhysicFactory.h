//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//
#import "CCNode.h"
#import "Box2D.h"
#import "ItemFactoryBase.h"

@interface GameItemPhysicFactory : ItemFactoryBase {
  b2World * world;
  float worldRatio;
}

- (void) Attach:(CCNode *)attachNode attachWorld:(b2World *)attachWorld attachWorldRatio:(float)attachWorldRatio;
- (void) Detach;
@end
