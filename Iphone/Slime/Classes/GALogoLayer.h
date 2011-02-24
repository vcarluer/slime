#import "cocos2d.h"

@interface GALogoLayer : CCLayer {
  long waitLogoSec;
  long onEnterTime;
  BOOL isInit;
	//todo
  //UpdateCallback * nextCallback;
}

+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
- (void) update:(float)d;
@end
