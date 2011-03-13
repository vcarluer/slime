#import "cocos2d.h"
/*
@interface GALogoLayer_Anon1 : NSObject <UpdateCallback> {
}




- (void) update:(float)d;
@end
*/
extern BOOL GA_isInit;
@interface GALogoLayer : CCLayer {
    long waitLogoSec;
    long onEnterTime;
    CCSpriteBatchNode * spriteSheet;
    CCSprite * sprite;
    //TODO
    //  UpdateCallback * nextCallback;
}

+ (id) scene;
- (id) init;
- (void) onEnter;
- (void) update:(float)d;
@end
