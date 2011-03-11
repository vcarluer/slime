#import "GameItem.h"

@interface CameraManager : NSObject {
  float minScale;
  float maxScale;
  float zoomSpeed;
  float currentZoom;
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
  CGPoint  levelOrigin;
  CGPoint  zoomAnchor;
  CGPoint  zoomScreenPin;
}

@property(nonatomic, readonly) float currentZoom;
- (id) initWithGameLayer:(CCLayer *)gameLayer;
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
- (CGPoint) getGamePoint:(CGPoint )screenPoint;
- (void) zoomCameraBy:(float)zoomDelta;
- (void) zoomCameraTo:(float)zoomValue;
- (void) setCameraView;
- (void) attachLevel:(float)levelWidth levelHeight:(float)levelHeight levelOrigin:(CGPoint *)levelOrigin;
@end
