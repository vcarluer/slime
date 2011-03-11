#import "HardCodedLevelBuilder.h"
#import "SlimeFactory.h"
float LAND_HEIGHT = 64.0f;

#define PTM_RATIO 32
@implementation HardCodedLevelBuilder

@synthesize heightRatio;

+ (void) build:(Level *)level levelName:(NSString *)levelName {
    if (levelName == LEVEL_HOME) {
        [self buildHome:level];
    }
    if (levelName == LEVEL_1) {
        [self buildLevel1:level];
    }
    if (levelName == LEVEL_2) {
        [self buildLevel2:level];
    }
}

+ (void) createGroundBox:(Level *)level {
  CGSize s = CCDirector.sharedDirector.winSize;
  float si = 10;
  float m = [level worlRatio] * 2;
  float w = s.width;
  float w2 = s.width / 2;
  float h = s.height;
  float h2 = s.height / 2;
  [levelEnd  create:w2 y:h + m width:w height:si];
  [levelEnd create:w + m y:h2 width:si height:h];
  [levelEnd create:w2 y:-m width:w height:si];
  [levelEnd create:-m y:h2 width:si height:h];
}
/*
+ (void) createLand:(Level *)level {
  CGSize * s = [CGSize make:[level levelWidth] param1:[level levelHeight]];
  [SlimeFactory.Platform create:s.width / 2 param1:LAND_HEIGHT / 2 param2:s.width param3:LAND_HEIGHT];
}

+ (void) buildHome:(Level *)level {
  [level setLevelSize:[[[CCDirector sharedDirector] winSize] width] * 2 param1:[[[CCDirector sharedDirector] winSize] height] * 2];
  [self createGroundBox:level];
  [self createLand:level];
  CGSize * s = [CGSize make:[level levelWidth] param1:[level levelHeight]];
  SpawnPortal * spawnPortal = [SlimeFactory.SpawnPortal createAndMove:level.levelWidth / 2 param1:level.levelHeight - 32 param2:level.levelWidth / 2 param3:5];
  [level setSpawnPortal:spawnPortal];
  float goalPlatH = 20f;
  float goalPlatW = 100f;
  [SlimeFactory.Platform create:s.width / 2 param1:LAND_HEIGHT + goalPlatH / 2 param2:goalPlatW param3:goalPlatH];
  GoalPortal * goalPortal = [SlimeFactory.GoalPortal create:s.width / 2 param1:LAND_HEIGHT + goalPlatH + 15];
  [level setGoalPortal:goalPortal];
  [SlimeFactory.Bumper create:30 param1:s.height / 2 param2:60 param3:120 param4:2.0f];
  [SlimeFactory.HomeLevelHandler create];
  [level addCustomOverLayer:[HomeLayer get]];
  [level setIsHudEnabled:NO];
  [level setIsTouchEnabled:NO];
}

+ (void) buildLevel1:(Level *)level {
  [level setLevelSize:[[[CCDirector sharedDirector] winSize] width] * 2 param1:[[[CCDirector sharedDirector] winSize] height] * 2];
  [self createGroundBox:level];
  [self createLand:level];
  CGSize * s = [CGSize make:[level levelWidth] param1:[level levelHeight]];
  SpawnPortal * spawnPortal = [SlimeFactory.SpawnPortal createAndMove:level.levelWidth / 2 param1:level.levelHeight - 32 param2:level.levelWidth / 2 param3:5];
  [level setSpawnPortal:spawnPortal];
  float goalPlatH = 20f;
  float goalPlatW = 100f;
  [SlimeFactory.Platform create:s.width / 2 param1:LAND_HEIGHT + goalPlatH / 2 param2:goalPlatW param3:goalPlatH];
  GoalPortal * goalPortal = [SlimeFactory.GoalPortal create:s.width / 2 param1:LAND_HEIGHT + goalPlatH + 15];
  [level setGoalPortal:goalPortal];
  [SlimeFactory.Bumper create:30 param1:s.height / 2 param2:60 param3:120 param4:2.0f];
}

+ (float) heightRatio {
  return [[CCDirector sharedDirector] winSize].height / [[CCDirector sharedDirector] winSize].width;
}

+ (void) buildLevel2:(Level *)level {
  float width = 950;
  [level setLevelSize:width param1:width * [self heightRatio]];
  [self createGroundBox:level];
  CGSize * s = [CGSize make:[level levelWidth] param1:[level levelHeight]];
  float cX = 0;
  float cY = 0;
  [SlimeFactory.Platform createBL:cX param1:cY param2:100 param3:100];
  SpawnCannon * spawnCannon = [SlimeFactory.Cannon create:100 - SpawnCannon.Default_Width / 2 param1:100 + SpawnCannon.Default_Height / 2];
  cX += 100;
  [SlimeFactory.Lava createBL:cX param1:cY param2:200 param3:50];
  cX += 200;
  [SlimeFactory.Platform createBL:cX param1:cY param2:50 param3:100];
  cX += 50;
  [SlimeFactory.Platform createBL:cX param1:cY param2:50 param3:50];
  [SlimeFactory.Bumper createBL:cX param1:50 param2:50 param3:50];
  cX += 50;
  [SlimeFactory.Lava createBL:cX param1:cY param2:50 param3:50];
  cX += 50;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:80];
  cX += 128;
  [SlimeFactory.Box createBL:cX param1:80 param2:40 param3:40];
  [SlimeFactory.Box createBL:cX param1:120 param2:40 param3:40];
  [SlimeFactory.Platform createBL:cX param1:cY param2:72 param3:80];
  cX += 72;
  [SlimeFactory.Lava createBL:cX param1:cY param2:200 param3:50];
  cX += 200;
  [SlimeFactory.Platform createBL:cX param1:cY param2:100 param3:50];
  cX += 40;
  [[SlimeFactory.Bumper createBL:cX param1:50 param2:50 param3:50 param4:0.8f] setAngle:90];
  cX += 50;
  [SlimeFactory.Platform createBL:cX param1:50 param2:10 param3:128];
  cX = 400;
  cY = 250;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:20];
  [SlimeFactory.Box createBL:cX param1:cY + 20 param2:10 param3:80];
  cX += 128;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:20];
  [SlimeFactory.Platform createBL:cX param1:cY + 20 param2:20 param3:128];
  cX += 64;
  GoalPortal * goalPortal = [SlimeFactory.GoalPortal create:cX param1:cY + 40];
  [level setGoalPortal:goalPortal];
  cX += 108;
  cX = 0;
  cY = 398;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:20];
  cX += 128;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:20];
  cX += 128;
  [SlimeFactory.Box createBL:cX param1:cY + 20 param2:60 param3:60];
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:20];
  Platform * platform = [SlimeFactory.Platform create:cX param1:cY - 5 param2:30 param3:10];
  cY -= 10;
  [Box setChainMode:YES];
  CGSize * segSize = [CGSize make:3 param1:15];
  RevoluteJointDef * joint = [[[RevoluteJointDef alloc] init] autorelease];
  Body * link = [platform body];
  float count = 10;

  for (int i = 0; i < count; i++) {
    Box * box = [SlimeFactory.Box create:cX param1:cY - (i + 1) * (height *)+segSize.height / 2 param2:segSize.width param3:segSize.height];
    Vector2 * linkPoint = [[[Vector2 alloc] init] autorelease];
    linkPoint.x = [[box body] position].x;
    linkPoint.y = [[box body] position].y + (segSize.height / 2 / [level worlRatio]);
    [joint initialize:link param1:[box body] param2:linkPoint];
    [[level world] createJoint:joint];
    link = [box body];
  }

  [Box setChainMode:NO];
  float boxSize = 20f;
  Box * box = [SlimeFactory.Box create:cX param1:cY - (count * segSize.height) - boxSize / 2 param2:boxSize param3:boxSize];
  Vector2 * linkPoint = [[[Vector2 alloc] init] autorelease];
  linkPoint.x = [[box body] position].x;
  linkPoint.y = [[box body] position].y + (boxSize / 2 / [level worlRatio]);
  [joint initialize:link param1:[box body] param2:linkPoint];
  [[level world] createJoint:joint];
  [[box body] applyLinearImpulse:[[[Vector2 alloc] init:5.0f param1:0f] autorelease] param1:[[box body] position]];
  cY += 10;
  cX += 128;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:128];
  cX += 128;
  [SlimeFactory.Platform createBL:cX param1:cY param2:128 param3:128];
  cX += 128;
  [SlimeFactory.Platform createBL:cX - 20 param1:cY + 128 param2:20 param3:128];
  [level setSpawnCannon:spawnCannon];
}
*/
@end
