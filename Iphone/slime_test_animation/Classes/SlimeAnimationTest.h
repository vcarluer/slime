#import "cocos2d.h"
#import "Box2D.h"

@interface SlimeAnimationTest : Activity {
  CCGLSurfaceView * mGLSurfaceView;
}

- (void) onCreate:(Bundle *)savedInstanceState;
- (void) onStart;
- (void) onPause;
- (void) onResume;
- (void) onStop;
@end
