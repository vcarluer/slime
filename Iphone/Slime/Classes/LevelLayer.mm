#import "LevelLayer.h"

@implementation LevelLayer

- (id) initWithLevel:(Level *)level {
  if (self = [super init]) {
//    tickCallback = [[[LevelLayer_Anon1 alloc] init] autorelease];
    level = level;
    [self setIsTouchEnabled:YES];
    touchList = [[[NSMutableArray alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
 // [self schedule:tickCallback];
}

- (void) onExit {
  [super onExit];
 // [self unschedule:tickCallback];
}

- (void) tick:(float)delta {
  [level tick:delta];
}

/*

- (BOOL) ccTouchesMoved:(MotionEvent *)event {

  for (int i = 0; i < [event pointerCount]; i++) {
    TouchInfo * touch = [self getTouch:event idx:i];
    if (touch != nil) {
      [touch setMoving:YES];
      [touch lastMoveDelta].x = [touch lastMoveReference].x - [event getX:[touch pointerId]];
      [touch lastMoveDelta].y = [event y] - [touch lastMoveReference].y;
      [touch lastMoveReference].x = [event getX:[touch pointerId]];
      [touch lastMoveReference].y = [event getY:[touch pointerId]];
      [touch setLastMoveTime:[event eventTime]];
    }
  }

  if ([touchList size] == 1) {
    TouchInfo * touch = [self getTouch:event idx:0];
    [[level cameraManager] moveCameraBy:[touch lastMoveDelta]];
  }
  if (isZoomAction) {
    CGPoint * touch1Ref = [[touchList get:0] lastMoveReference];
    CGPoint * touch2Ref = [[touchList get:1] lastMoveReference];
    float distance = [CGPoint ccpDistance:touch1Ref param1:touch2Ref];
    lastZoomDelta = distance - lastDistance;
    lastDistance = distance;
    [[self cameraManager] zoomCameraByScreenRatio:lastZoomDelta];
  }
  return CCTouchDispatcher.kEventHandled;
}

- (BOOL) ccTouchesEnded:(MotionEvent *)event {
  TouchInfo * touch = [touchList get:[self getPId:event]];
  if (![touch moving]) {
    if ([touch pointerId] == 0) {
      [level SpawnSlime];
    }
  }
   else {
    if (!isZoomAction) {
      if ([event eventTime] - [touch lastMoveTime] < 300) {
        [[self cameraManager] continuousMoveCameraBy:[touch lastMoveDelta]];
      }
      [touch setMoving:NO];
    }
  }
  if (isZoomAction) {
    isZoomAction = NO;
    lastDistance = 0.0f;
    lastZoomDelta = 0.0f;
  }
  [touchList remove:touch];
  return CCTouchDispatcher.kEventHandled;
}

- (BOOL) ccTouchesBegan:(MotionEvent *)event {
  TouchInfo * touch = [[[TouchInfo alloc] init:[self getPId:event]] autorelease];
  [touch moveBeganAt].x = [event getX:[touch pointerId]];
  [touch moveBeganAt].y = [event getY:[touch pointerId]];
  [touch lastMoveReference].x = [event getX:[touch pointerId]];
  [touch lastMoveReference].y = [event getY:[touch pointerId]];
  [touchList add:touch];
  [[self cameraManager] stopContinousMoving];
  if ([touchList size] == 2) {
    isZoomAction = YES;
    lastZoomDelta = 0.0f;
    CGPoint * touch1Ref = [[touchList get:0] lastMoveReference];
    CGPoint * touch2Ref = [[touchList get:1] lastMoveReference];
    lastDistance = [CGPoint ccpDistance:touch1Ref param1:touch2Ref];
    CGPoint * midPoint = [CGPoint ccpMidpoint:touch1Ref param1:touch2Ref];
    CGPoint * anchorZoom = [CGPoint make:midPoint.x param1:[[CCDirector sharedDirector] winSize].height - midPoint.y];
    [[self cameraManager] setZoomPoint:anchorZoom];
  }
   else {
    isZoomAction = NO;
  }
  return CCTouchDispatcher.kEventHandled;
}

- (CameraManager *) getCameraManager {
  return [level cameraManager];
}

- (int) getPId:(MotionEvent *)event {
  int pId = [event action] >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
  return pId;
}

- (TouchInfo *) getTouch:(MotionEvent *)event idx:(int)idx {
  TouchInfo * returnTouch = nil;
  if (idx <= [event pointerCount]) {
    int pId = [event getPointerId:idx];

    for (TouchInfo * touch in touchList) {
      if ([touch pointerId] == pId) {
        returnTouch = touch;
        break;
      }
    }

  }
  return returnTouch;
}
*/
- (void) dealloc {
  [level release];
  [touchList release];
 // [tickCallback release];
  [super dealloc];
}

@end
