#import "Slime.h"
/*
@implementation Slime

+ (void) initialize {
  [System loadLibrary:@"gdx"];
}


/**
 * Called when the activity is first created.
 */
/*
- (void) onCreate:(Bundle *)savedInstanceState {
  [super onCreate:savedInstanceState];
  [self requestWindowFeature:Window.FEATURE_NO_TITLE];
  [[self window] setFlags:WindowManager.LayoutParams.FLAG_FULLSCREEN param1:WindowManager.LayoutParams.FLAG_FULLSCREEN];
  [[self window] setFlags:WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON param1:WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON];
  mGLSurfaceView = [[[CCGLSurfaceView alloc] init:self] autorelease];
  [self setContentView:mGLSurfaceView];
}

- (void) onStart {
  [super onStart];
  [[CCDirector sharedDirector] attachInView:mGLSurfaceView];
  [[CCDirector sharedDirector] setDeviceOrientation:CCDirector.kCCDeviceOrientationPortrait];
  [[CCDirector sharedDirector] setDisplayFPS:YES];
  [[CCDirector sharedDirector] setAnimationInterval:1.0f / 60];
  scene = [[LevelFactory GetLevel:@"Level1"] scene];
  [[CCDirector sharedDirector] runWithScene:scene];
}

- (void) onPause {
  [super onPause];
  [[CCDirector sharedDirector] onPause];
}

- (void) onResume {
  [super onResume];
  [[CCDirector sharedDirector] onResume];
}

- (void) onStop {
  [super onStop];
  [[CCDirector sharedDirector] end];
}

- (void) dealloc {
  [mGLSurfaceView release];
  [scene release];
  [super dealloc];
}
*/
@end
