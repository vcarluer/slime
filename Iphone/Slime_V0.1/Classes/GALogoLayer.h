#import "cocos2d.h"


@interface GALogoLayer_Anon1 : NSObject <UpdateCallback> {
}

- (void) update:(float)d;
@end

@interface GALogoLayer : CCLayer {
  long waitLogoSec;
  long onEnterTime;
  BOOL isInit;
  UpdateCallback * nextCallback;
}

+ (CCScene *) scene;
- (id) init;
- (void) onEnter;
@end
