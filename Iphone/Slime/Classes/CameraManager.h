#import "GameItem.h"

@interface CameraManager : NSObject {
  float minScale;
  float maxScale;
  float zoomSpeed;
  float screenW2;
  float screenH2;
  CCLayer * gameLayer;
  float levelWidth;
  float levelHeight;
  BOOL isCameraOnContinuousMove;
  CGPoint  moveCameraBy;
  CGRect  cameraView;
  CGPoint  virtualCameraPos;
  GameItem * followed;
  CGPoint levelOrigin;
  CGPoint  zoomAnchor;
  CGPoint  zoomScreenPin;
}

- (id) init:(CCLayer *)my_gameLayer levelWidth:(float)my_levelWidth levelHeight:(float)my_levelHeight levelOrigin:(CGPoint)my_levelOrigin;
- (void) tick:(float)delta;
- (void) follow:(GameItem *)item;
- (void) normalizePosition;
- (void) centerCameraOn:(CGPoint )center;
- (void) keepPointAt:(CGPoint )gamePoint screenPin:(CGPoint)screenPin;
- (void) setCameraPosition:(CGPoint )origin;
- (void) setLayerPosition:(CGPoint )position;
- (void) moveCameraBy:(CGPoint )move;
- (void) continuousMoveCameraBy:(CGPoint )delta;
- (void) stopContinousMoving;
- (void) zoomCameraByScreenRatio:(float)zoomDelta;
- (void) setZoomPoint:(CGPoint )zoomPoint;
- (void) zoomCameraBy:(float)zoomDelta;
- (void) zoomCameraTo:(float)zoomValue;
- (void) setCameraView;
@end
