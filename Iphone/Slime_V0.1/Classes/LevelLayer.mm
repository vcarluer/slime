#import "LevelLayer.h"


@implementation LevelLayer
/*
+(id) scene
{
	// 'scene' is an autorelease object.
	CCScene *scene = [CCScene node];
	
	// 'layer' is an autorelease object.
	LevelLayer*layer = [LevelLayer node];
	
	// add layer as a child to scene
	[scene addChild: layer ];
	
	// return the scene
	return scene;
}
*/

#define PTM_RATIO 32

enum {
	kTagTileMap = 1,
	kTagBatchNode = 1,
	kTagAnimation1 = 1,
	kTagSceneNode =2
};
- (id) initWithLevel:(Level *)level {
	self = [super init];
  if (self != nil) {
    my_level = level;
    [self setIsTouchEnabled:YES];
    touchList = [[[NSMutableArray alloc] init] autorelease];
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  //[self schedule:tickCallback];
}

- (void) onExit {
  [super onExit];
  //[self unschedule:tickCallback];
}

- (void) tick:(float)delta {
  [my_level tick:delta];
}


- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	//Add a new body/atlas sprite at the touched location
	for( UITouch *touch in touches ) {
		CGPoint location = [touch locationInView: [touch view]];
		
		location = [[CCDirector sharedDirector] convertToGL: location];
		
		
		[self addNewSpriteWithCoords: location];
	}
}
-(void) addNewSpriteWithCoords:(CGPoint)p
{
	CCLOG(@"Add sprite %0.2f x %02.f",p.x,p.y);
	CCSpriteBatchNode *batch = (CCSpriteBatchNode*) [self getChildByTag:kTagBatchNode];
	 
	 
	 
	 //We have a 64x64 sprite sheet with 4 different 32x32 images.  The following code is
	 //just randomly picking one of the images
	 int idx = (CCRANDOM_0_1() > .5 ? 0:1);
	 int idy = (CCRANDOM_0_1() > .5 ? 0:1);
	 CCSprite *sprite = [CCSprite spriteWithBatchNode:batch rect:CGRectMake(32 * idx,32 * idy,32,32)];
	 [batch addChild:sprite];
	 
	 sprite.position = ccp( p.x, p.y);
	 
	 // Define the dynamic body.
	 //Set up a 1m squared box in the physics world
	 b2BodyDef bodyDef;
	 bodyDef.type = b2_dynamicBody;
	 
	 bodyDef.position.Set(p.x/PTM_RATIO, p.y/PTM_RATIO);
	 bodyDef.userData = sprite;
	//b2World my_world =  my_level->world;
	// b2Body *body = my_world->CreateBody(&bodyDef);
	 
	 // Define another box shape for our dynamic body.
	 b2PolygonShape dynamicBox;
	 dynamicBox.SetAsBox(.5f, .5f);//These are mid points for our 1m box
	 
	 // Define the dynamic body fixture.
	 b2FixtureDef fixtureDef;
	 fixtureDef.shape = &dynamicBox;	
	 fixtureDef.density = 1.0f;
	 fixtureDef.friction = 0.3f;
	// body->CreateFixture(&fixtureDef);
	
	//Slimy *testslime = [Slimy init:[self getChildByTag:kTagSceneNode] x:p.x y:p.y width:0 height:0 world:world worldRatio:PTM_RATIO];
}

/*
- (BOOL)ccTouchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
	
	
	for( UITouch *touch in touches ) {
		if (touch != nil) {
	//		[touch setMoving:YES];
			[touch lastMoveDelta].x = [touch lastMoveReference].x - [event getX:[touch pointerId]];
			[touch lastMoveDelta].y = [event y] - [touch lastMoveReference].y;
			[touch lastMoveReference].x = [event getX:[touch pointerId]];
			[touch lastMoveReference].y = [event getY:[touch pointerId]];
			[touch setLastMoveTime:[event eventTime]];
		}
	}
	

  for (int i = 0; i < [event pointerCount]; i++) {
    TouchInfo * touch = [self getTouch:event idx:i];
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

- (BOOL)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
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
    lastDistance = 0f;
    lastZoomDelta = 0f;
  }
  [touchList remove:touch];
  return CCTouchDispatcher.kEventHandled;
}

- (BOOL)ccTouchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
  TouchInfo * touch = [[[TouchInfo alloc] init:[self getPId:event]] autorelease];
  [touch moveBeganAt].x = [event getX:[touch pointerId]];
  [touch moveBeganAt].y = [event getY:[touch pointerId]];
  [touch lastMoveReference].x = [event getX:[touch pointerId]];
  [touch lastMoveReference].y = [event getY:[touch pointerId]];
  [touchList add:touch];
  [[self cameraManager] stopContinousMoving];
  if ([touchList size] == 2) {
    isZoomAction = YES;
    lastZoomDelta = 0f;
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
  [my_level release];
  [touchList release];
  //[tickCallback release];
  [super dealloc];
}

@end
