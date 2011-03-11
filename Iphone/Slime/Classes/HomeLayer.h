#import "cocos2d.h"


@interface HomeLayer : CCLayer {
}

+ (HomeLayer *) get;
- (id) init;

//bool replace void in java
- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;
- (void) onEnter;
- (void) selectPlay:(NSObject *)sender;
@end
