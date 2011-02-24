#import "CCLayer.h"


@interface HomeLayer : CCLayer {
}

+ (HomeLayer *) get;
- (id) init;

//bool replace void in java
- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;
@end
