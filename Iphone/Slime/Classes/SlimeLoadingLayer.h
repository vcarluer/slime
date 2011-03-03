//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "cocos2d.h"
//#import "Level.h"
#import "SpriteSheetFactory.h"


@class	Level;
extern BOOL isInit;

@interface SlimeLoadingLayer : CCLayer {
  Level * currentLevel;
  NSObject * syncObj;
  //UpdateCallback * nextCallback;
}

+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) update:(float)d;
- (void) run;

@end
