#import "HardCodedLevelBuilder.h"

@implementation HardCodedLevelBuilder
/*
+ (void) build:(Level *)level levelName:(NSString *)levelName {
  if (levelName == Level.LEVEL_HOME) {
    [self buildHome:level levelName:levelName];
  }
}

+ (void) buildHome:(Level *)level levelName:(NSString *)levelName {
  [level setLevelSize:[[[CCDirector sharedDirector] winSize] width] * 2 param1:[[[CCDirector sharedDirector] winSize] height] * 2];
  BodyDef * bxGroundBodyDef = [[[BodyDef alloc] init] autorelease];
  [bxGroundBodyDef.position set:0.0f param1:0.0f];
  Body * groundBody = [[level world] createBody:bxGroundBodyDef];
  PolygonShape * groundBox = [[[PolygonShape alloc] init] autorelease];
  CGSize * s = [CGSize make:[level levelWidth] param1:[level levelHeight]];
  float scaledWidth = s.width / [level worlRatio];
  float scaledHeight = s.height / [level worlRatio];
  Vector2 * bottomLeft = [[[Vector2 alloc] init:0f param1:0f] autorelease];
  Vector2 * topLeft = [[[Vector2 alloc] init:0f param1:scaledHeight] autorelease];
  Vector2 * topRight = [[[Vector2 alloc] init:scaledWidth param1:scaledHeight] autorelease];
  Vector2 * bottomRight = [[[Vector2 alloc] init:scaledWidth param1:0f] autorelease];
  [groundBox setAsEdge:bottomLeft param1:bottomRight];
  [groundBody createFixture:groundBox param1:0];
  [groundBox setAsEdge:topLeft param1:topRight];
  [groundBody createFixture:groundBox param1:0];
  [groundBox setAsEdge:topLeft param1:bottomLeft];
  [groundBody createFixture:groundBox param1:0];
  [groundBox setAsEdge:topRight param1:bottomRight];
  [groundBody createFixture:groundBox param1:0];
  float si = 10;
  float m = si / 2;
  float w = s.width;
  float w2 = s.width / 2;
  float h = s.height;
  float h2 = s.height / 2;
  [SlimeFactory.Platform create:w2 param1:h - m param2:w param3:si];
  [SlimeFactory.Platform create:w - m param1:h2 param2:si param3:h];
  [SlimeFactory.Platform create:w2 param1:m param2:w param3:si];
  [SlimeFactory.Platform create:m param1:h2 param2:si param3:h];
  float goalPlatH = 20f;
  float goalPlatW = 100f;
  [SlimeFactory.Platform create:s.width / 2 param1:si + goalPlatH / 2 param2:goalPlatW param3:goalPlatH];
  GoalPortal * goalPortal = [SlimeFactory.GoalPortal create:s.width / 2 param1:si + goalPlatH + 15];
  [level setGoalPortal:goalPortal];
  Bumper * bumper = [SlimeFactory.Bumper create:[level worlRatio] + si param1:h2 param2:60 param3:120 param4:2.0f];
  [level addGameItem:bumper];
  [[level cameraManager] zoomCameraTo:0f];
  [level addCustomOverLayer:[HomeLayer get]];
}
*/
@end
