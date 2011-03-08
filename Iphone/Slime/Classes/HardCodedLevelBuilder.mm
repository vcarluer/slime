#import "HardCodedLevelBuilder.h"
#import "SlimeFactory.h"

#define PTM_RATIO 32
@implementation HardCodedLevelBuilder

+ (void) build:(Level *)level levelName:(NSString *)levelName {
	//if (levelName == Level.LEVEL_HOME) {
    [self buildHome:level levelName:levelName];
	//}
}

+ (void) buildHome:(Level *)level levelName:(NSString *)levelName {
	
	
    CGSize s = CCDirector.sharedDirector.winSize;
    [level setLevelSize:s.width * 2 height:s.height * 2];	
	
	b2BodyDef * bxGroundBodyDef = new b2BodyDef;
	bxGroundBodyDef->position.Set(0.0f, 0.0f);
	b2Body * groundBody = [level world]->CreateBody(bxGroundBodyDef);
	b2PolygonShape * groundBox = new b2PolygonShape;
	
	float scaledWidth = s.width / PTM_RATIO;
	float scaledHeight = s.height / PTM_RATIO;
	b2Vec2 bottomLeft(0.0f,0.0f);
	b2Vec2 topLeft(0.0f, scaledHeight);
	b2Vec2 topRight(scaledWidth, scaledHeight);
	b2Vec2 bottomRight(scaledWidth,0.0f);
	groundBox->SetAsEdge(bottomLeft, bottomRight);
	groundBody->CreateFixture(groundBox,0);
	groundBox->SetAsEdge(topLeft,topRight);
	groundBody->CreateFixture(groundBox,0);
	groundBox->SetAsEdge(topLeft,bottomLeft);
	groundBody->CreateFixture(groundBox,0);
	groundBox->SetAsEdge(topRight,bottomRight);
	groundBody->CreateFixture(groundBox,0);
	float si = 10;
	float m = si / 2;
	float w = s.width;
	float w2 = s.width / 2;
	float h = s.height;
	float h2 = s.height / 2;
	
	//[platform create:w2 y:h - m width:w height:si];
	//[platform create:w - m y:h2 width:si height:h];
	//[platform create:w2 y:m width:w height:si];
	//[platform create:m y:h2 width:si height:h];
	float goalPlatH = 20.0f;
	float goalPlatW = 100.0f;
	//[platform create:s.width / 2 y:si + goalPlatH / 2 width:goalPlatW height:goalPlatH];
 // GoalPortal * my_goalPortal = [goalPortal  create:s.width / 2 y:si + goalPlatH + 15];
 // [level setGoalPortal:my_goalPortal];
//	Bumper * my_bumper = [bumper create:[level worlRatio] + si y:h2 width:60 height:120 powa:2.0f];
 // [level addGameItem:my_bumper];
  //[[level cameraManager] zoomCameraTo:0f];
  //[level addCustomOverLayer:[HomeLayer get]];
}

@end
