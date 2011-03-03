#import "CCScene.h"
#import "CCDirector.h"
#import "CCGLSurfaceView.h"
#import "Activity.h"
#import "Bundle.h"
#import "Window.h"
#import "WindowManager.h"

@interface SlimeAnimationTest : Activity {
  CCGLSurfaceView * mGLSurfaceView;
}

- (void) onCreate:(Bundle *)savedInstanceState;
- (void) onStart;
- (void) onPause;
- (void) onResume;
- (void) onStop;
@end
