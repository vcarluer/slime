#import "CameraManager.h"


@implementation CameraManager


- (id) init:(CCLayer *)my_gameLayer levelWidth:(float)my_levelWidth levelHeight:(float)my_levelHeight levelOrigin:(CGPoint )my_levelOrigin {
	self = [super init];
	if (self != nil) {
    screenW2 = [[CCDirector sharedDirector] winSize].width * [[CCDirector sharedDirector] winSize].width;
    screenH2 = [[CCDirector sharedDirector] winSize].height * [[CCDirector sharedDirector] winSize].height;
    gameLayer = my_gameLayer;
    levelWidth = my_levelWidth;
    levelHeight = my_levelHeight;
    levelOrigin = my_levelOrigin;
    //moveCameraBy = [[[CGPoint alloc] init] autorelease];
    virtualCameraPos = CGPointZero;
    [self setCameraView];
  }
  return self;
}

- (void) tick:(float)delta {
  if (isCameraOnContinuousMove) {
    [self moveCameraBy:moveCameraBy];
  }
  if (followed != nil) {
    [self centerCameraOn:[followed position]];
  }
}

- (void) follow:(GameItem *)item {
  followed = item;
}

- (void) normalizePosition {
  float scale = [gameLayer scale];
  BOOL isNormalized = NO;
  CGPoint  position = [gameLayer position];
  float maxLeft =CGRectGetMinX(cameraView);
  float left = position.x;
  if (left > maxLeft) {
    position.x += maxLeft - left;
    isNormalized = YES;
  }
  float minRight = CGRectGetMaxX(cameraView);
  float right = position.x + levelWidth * scale;
  if (right < minRight) {
    position.x += minRight - right;
    isNormalized = YES;
  }
  float maxBottom = CGRectGetMinY(cameraView);
  float bottom = position.y;
  if (bottom > maxBottom) {
    position.y += maxBottom - bottom;
    isNormalized = YES;
  }
  float minTop = CGRectGetMaxY(cameraView);
  float top = position.y + levelHeight * scale;
  if (top < minTop) {
    position.y += minTop - top;
    isNormalized = YES;
  }
  [gameLayer setPosition:position];
  virtualCameraPos = ccp(-position.x,-position.y);
  if (isNormalized) {
    isCameraOnContinuousMove = NO;
  }
}

- (void) centerCameraOn:(CGPoint )center {
  [self keepPointAt:center screenPin:ccp(cameraView.size.width / 2 ,cameraView.size.height / 2)];
}

- (void) keepPointAt:(CGPoint )gamePoint screenPin:(CGPoint)screenPin {
  float scale = [gameLayer scale];
  CGPoint  position = ccp(gamePoint.x * scale - screenPin.x, gamePoint.y * scale - screenPin.y);
  [self setLayerPosition:position];
}

- (void) setLayerPosition:(CGPoint )position {
  [gameLayer setPosition:position];
  [self normalizePosition];
}

- (void) setCameraPosition:(CGPoint )origin {
  float scale = [gameLayer scale];
  CGPoint  position = ccp(-origin.x * scale, -origin.y * scale);
  [self setLayerPosition:position];
}

- (void) moveCameraBy:(CGPoint )move {
  float scale = [gameLayer scale];
  CGPoint  position = ccp([gameLayer position].x , [gameLayer position].y);
  position.x += -move.x * scale;
  position.y += -move.y * scale;
  [self setLayerPosition:position];
}

- (void) continuousMoveCameraBy:(CGPoint )delta {
  float maxMovePos = 10.0f;
  float maxMoveNeg = -10.0f;
  float minMove = 1.0f;
  if (abs(delta.x) > minMove) {
    if (delta.x > maxMovePos)
      delta.x = maxMovePos;
    if (delta.x < maxMoveNeg)
      delta.x = maxMoveNeg;
  }
   else {
    delta.x = 0.0f;
  }
  if (abs(delta.y) > minMove) {
    if (delta.y > maxMovePos)
      delta.y = maxMovePos;
    if (delta.y < maxMoveNeg)
      delta.y = maxMoveNeg;
  }
   else {
    delta.y = 0.0f;
  }
  moveCameraBy.x = delta.x;
  moveCameraBy.y = delta.y;
  isCameraOnContinuousMove = YES;
}

- (void) stopContinousMoving {
  isCameraOnContinuousMove = NO;
}

- (void) zoomCameraByScreenRatio:(float)zoomDelta {
  double max = sqrt(screenW2 + screenH2);
  float zoom = (float)(zoomDelta * maxScale / max) * zoomSpeed;
  [self zoomCameraBy:zoom];
}

- (void) setZoomPoint:(CGPoint )zoomPoint {
  zoomScreenPin = ccp(zoomPoint.x, zoomPoint.y);
  float scale = [gameLayer scale];
  CGPoint zoomScaled = CGPointZero;
  zoomScaled.x = (zoomPoint.x - [gameLayer position].x) / scale;
  zoomScaled.y = (zoomPoint.y - [gameLayer position].y) / scale;
  zoomAnchor = zoomScaled;
}

- (void) zoomCameraBy:(float)zoomDelta {
  float scale = [gameLayer scale] + zoomDelta;
  [self zoomCameraTo:scale];
}

- (void) zoomCameraTo:(float)zoomValue {
  if (zoomValue <= minScale) {
    zoomValue = minScale;
  }
  if (zoomValue >= maxScale) {
    zoomValue = maxScale;
  }
  [gameLayer setScale:zoomValue];
  [self keepPointAt:zoomAnchor screenPin:zoomScreenPin];
  [self normalizePosition];
}

- (void) setCameraView {
  CGPoint  origin = levelOrigin;
  cameraView = CGRectMake(origin.x, origin.y ,[[CCDirector sharedDirector] winSize].width , [[CCDirector sharedDirector] winSize].height);
  zoomAnchor = CGPointZero;
  zoomScreenPin = CGPointZero;
  float minScaleW = 1 / (levelWidth / CGRectGetWidth(cameraView));
  float minScaleH = 1 / (levelHeight / CGRectGetHeight(cameraView));
  minScale = MAX(minScaleW, minScaleH);
  maxScale = 2.0f;
  zoomSpeed = 3.0f;
  [self zoomCameraBy:0];
  [self normalizePosition];
}

- (void) dealloc {
  [gameLayer release];
 
  [followed release];
    [super dealloc];
}

@end
