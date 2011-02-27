#import "HardCodedLevel.h"
#import "SlimeFactory.h"
#import "Bumper.h"

@implementation HardCodedLevel

- (void) init {
  if( (self = [super init]) ) { 
  b2BodyDef * bxGroundBodyDef = new b2BodyDef;
  bxGroundBodyDef->position.Set(0.0f, 0.0f);
  b2Body * groundBody = world->CreateBody(bxGroundBodyDef);
  b2PolygonShape * groundBox = new b2PolygonShape;
  CGSize s = CCDirector.sharedDirector.winSize;
  float scaledWidth = s.width / worldRatio;
  float scaledHeight = s.height / worldRatio;
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
	   
  [platform create:w2 y:h - m width:w height:si];
  [platform create:w - m y:h2 width:si height:h];
  [platform create:w2 y:m width:w height:si];
  [platform create:m y:h2 width:si height:h];
  float goalPlatH = 20.0f;
  float goalPlatW = 100.0f;
  [platform create:s.width / 2 y:si + goalPlatH / 2 width:goalPlatW height:goalPlatH];
  goalPortal = [GoalPortal :s.width / 2 x:si + goalPlatH + 15];
  [items add:goalPortal];
 // Bumper * bumper = [SlimeFactory.Bumper create:worldRatio + si param1:h2 param2:60 param3:120 param4:2.0f];
 // [items add:bumper];
  }
}

@end
