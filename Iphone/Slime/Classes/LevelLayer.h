#import "CCTouchDispatcher.h"
#import "CCLayer.h"
#import "CCDirector.h"
@class Level;



@interface LevelLayer : CCLayer {

 
	
  Level * level;
  NSMutableArray * touchList;
  BOOL isZoomAction;
  float lastDistance;
  float lastZoomDelta;

}


- (id) initWithLevel:(Level *)my_level;
- (void) onEnter;
- (void) onExit;
- (void) tick:(float)delta;
//- (BOOL) ccTouchesMoved:(MotionEvent *)event;
//- (BOOL) ccTouchesEnded:(MotionEvent *)event;
//- (BOOL) ccTouchesBegan:(MotionEvent *)event;
	
@end
