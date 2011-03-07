#import "cocos2d.h"
#import "Box2D.h"
//#import "Level.h"
@class Level;

@interface LevelLayer : CCLayer {

  /**
   * @uml.property  name="level"
   * @uml.associationEnd
 */
  Level * my_level;
  NSMutableArray * touchList;
  BOOL isZoomAction;
  float lastDistance;
  float lastZoomDelta;
  //UpdateCallback * tickCallback;
}
//+(id) scene;
- (id) initWithLevel:(Level *)level;
- (void) onEnter;
- (void) onExit;
- (void) tick:(float)delta;
- (BOOL)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event;
- (BOOL)ccTouchesMoved:(NSSet *)touches withEvent:(UIEvent *)event;
- (BOOL)ccTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event;

@end
