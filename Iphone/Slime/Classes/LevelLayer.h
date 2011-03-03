//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCTouchDispatcher.h"
#import "CCLayer.h"
#import "CCDirector.h"
@class Level;


/*
class LevelLayer_Anon1 : public UpdateCallback {
};

- (void) update:(float)d;
//@end
*/
/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.LevelFactory"
 */

@interface LevelLayer : CCLayer {

  /**
   * @uml.property  name="level"
   * @uml.associationEnd
 */
	
  Level * level;
  NSMutableArray * touchList;
  BOOL isZoomAction;
  float lastDistance;
  float lastZoomDelta;
 // UpdateCallback * tickCallback;
}

- (id) initWithLevel:(Level *)level;
- (void) onEnter;
- (void) onExit;
- (void) tick:(float)delta;
//- (BOOL) ccTouchesMoved:(MotionEvent *)event;
//- (BOOL) ccTouchesEnded:(MotionEvent *)event;
//- (BOOL) ccTouchesBegan:(MotionEvent *)event;
	
@end
