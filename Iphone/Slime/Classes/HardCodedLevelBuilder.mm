#import "HardCodedLevelBuilder.h"
#import "SlimeFactory.h"
#import "HomeLayer.h"


float LAND_HEIGHT = 64.0f;

#define PTM_RATIO 32
@implementation HardCodedLevelBuilder

@synthesize heightRatio;

+ (void) build:(Level *)level levelName:(NSString *)levelName {
    if ([levelName isEqualToString:LEVEL_HOME]) {
        [self buildHome:level];
    }
    if ([levelName isEqualToString:LEVEL_1]) {
        [self buildLevel1:level];
    }
    if ([levelName isEqualToString:LEVEL_2]) {
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
    // probleme with levelend
    [levelEnd  create:w2 y:h + m width:w height:si];
    [levelEnd create:w + m y:h2 width:si height:h];
    [levelEnd create:w2 y:-m width:w height:si];
    [levelEnd create:-m y:h2 width:si height:h];
     
    
}

+ (void) createLand:(Level *)level {
    CGSize s = CGSizeMake([level levelWidth],[level levelHeight]);
    [platform create:s.width / 2 y:LAND_HEIGHT / 2 width:s.width height:LAND_HEIGHT];
}


+ (void) buildHome:(Level *)level {
    CGSize s = [[CCDirector sharedDirector] winSize]; 
    [level setLevelSize:s.width  height:s.height];
    [self createGroundBox:level];
    [self createLand:level];
    
    CGSize s1 = CGSizeMake([level levelWidth],[level levelHeight]);
    SpawnPortal * my_spawnPortal = [spawnPortal createAndMove:30 y:200 moveBy:level.levelWidth / 2 speed:5];
    [level setSpawnPortal:my_spawnPortal];
    float goalPlatH = 20.0f;
    float goalPlatW = 100.0f;
    [platform create:0 y:35  width:480 height:10];
    //GoalPortal * my_goalPortal = [goalPortal  create:s1.width / 2 y:LAND_HEIGHT + goalPlatH + 15];
    //[level setGoalPortal:my_goalPortal];
    [bumper create:30 y:s1.height / 2 width:60 height:120 powa:2.0f];
    [homeLevelHandler create];
    [level addCustomOverLayer:[HomeLayer get]];
    [level setIsHudEnabled:NO];
    [level setIsTouchEnabled:NO];
}

+ (void) buildLevel1:(Level *)level {
    
    CGSize s1 = [[CCDirector sharedDirector] winSize]; 
    [level setLevelSize:s1.width * 2 height:s1.height * 2];
    [self createGroundBox:level];
    [self createLand:level];
     CGSize s = CGSizeMake([level levelWidth],[level levelHeight]);
    SpawnPortal * my_spawnPortal = [spawnPortal createAndMove:level.levelWidth / 2 y:level.levelHeight - 32 moveBy:level.levelWidth / 2 speed:5];
    [level setSpawnPortal:my_spawnPortal];
    float goalPlatH = 20.0f;
    float goalPlatW = 100.0f;
    //[platform create:s.width / 2 y:LAND_HEIGHT + goalPlatH / 2 width:goalPlatW height:goalPlatH];
    GoalPortal * my_goalPortal = [goalPortal  create:s1.width / 2 y:LAND_HEIGHT + goalPlatH + 15];
    [level setGoalPortal:my_goalPortal];
    [bumper create:30 y:s.height / 2 width:60 height:120 powa:2.0f];
}

+ (float) heightRatio {
    return [[CCDirector sharedDirector] winSize].height / [[CCDirector sharedDirector] winSize].width;
}

+ (void) buildLevel2:(Level *)level {
    float width = 950;
    [level  setLevelSize:width height:width * [self heightRatio]];
    [self createGroundBox:level];
    CGSize s = CGSizeMake([level levelWidth],[level levelHeight]);
    float cX = 0;
    float cY = 0;
    [platform  createBL:cX y:cY width:100 height:100];
    SpawnCannon * my_spawnCannon = [cannon  create:100 - spawncannon_Default_Width / 2 y:100 + spawncannon_Default_Height / 2];
    cX += 100;
  //  [lava createBL:cX y:cY width:200 height:50];
    cX += 200;
    [platform createBL:cX y:cY width:50 height:100];
    cX += 50;
    [platform createBL:cX y:cY width:50 height:50];
    [bumper createBL:cX y:50 width:50 height:50];
    cX += 50;
  //  [lava createBL:cX y:cY width:50 height:50];
    cX += 50;
    [platform createBL:cX y:cY width:128 height:80];
    cX += 128;
//    [box createBL:cX y:80 width:40 height:40];
//    [box createBL:cX y:120 width:40 height:40];
    [platform createBL:cX y:cY width:72 height:80];
    cX += 72;
   // [lava createBL:cX y:cY width:200 height:50];
    cX += 200;
    //[platform createBL:cX y:cY width:100 height:50];
    cX += 40;
    [[bumper createBL:cX y:50 width:50 height:50 powa:0.8f] setAngle:90];
    cX += 50;
    //[platform createBL:cX y:50 width:10 height:128];
    cX = 400;
    cY = 250;
    //[platform createBL:cX y:cY width:128 height:20];
//    [box createBL:cX y:cY + 20 width:10 height:80];
    cX += 128;
    //[platform createBL:cX y:cY width:128 height:20];
    //[platform createBL:cX y:cY + 20 width:20 height:128];
    cX += 64;
    GoalPortal * my_goalPortal = [goalPortal create:cX y:cY + 40];
    [level setGoalPortal:my_goalPortal];
    cX += 108;
    cX = 0;
    cY = 398;
    //[platform createBL:cX y:cY width:128 height:20];
    cX += 128;
    //[platform createBL:cX y:cY width:128 height:20];
    cX += 128;
//    [box createBL:cX y:cY + 20 width:60 height:60];
    //[platform createBL:cX y:cY width:128 height:20];
    //Platform * my_platform = [platform create:cX y:cY - 5 width:30 height:10];
    //cY -= 10;
    /*
    [Box setChainMode:YES];
    CGSize segSize = CGSizeMake(3,15);
    RevoluteJointDef * joint = [[[RevoluteJointDef alloc] init] autorelease];
    Body * link = [my_platform body];
    float count = 10;
    
    for (int i = 0; i < count; i++) {
        Box * box = [SlimeFactory.Box create:cX y:cY - (i + 1) * (height *)+segSize.height / 2 width:segSize.width height:segSize.height];
        Vector2 * linkPoint = [[[Vector2 alloc] init] autorelease];
        linkPoint.x = [[box body] position].x;
        linkPoint.y = [[box body] position].y + (segSize.height / 2 / [level worlRatio]);
        [joint initialize:link y:[box body] width:linkPoint];
        [[level world] createJoint:joint];
        link = [box body];
    }
    
    [Box setChainMode:NO];
    float boxSize = 20f;
    Box * box = [SlimeFactory.Box create:cX y:cY - (count * segSize.height) - boxSize / 2 width:boxSize height:boxSize];
    Vector2 * linkPoint = [[[Vector2 alloc] init] autorelease];
    linkPoint.x = [[box body] position].x;
    linkPoint.y = [[box body] position].y + (boxSize / 2 / [level worlRatio]);
    [joint initialize:link y:[box body] width:linkPoint];
    [[level world] createJoint:joint];
    [[box body] applyLinearImpulse:[[[Vector2 alloc] init:5.0f y:0f] autorelease] y:[[box body] position]];
    */
     cY += 10;
    cX += 128;
    [platform createBL:cX y:cY width:128 height:128];
    cX += 128;
    [platform createBL:cX y:cY width:128 height:128];
    cX += 128;
    [platform createBL:cX - 20 y:cY + 128 width:20 height:128];
    [level setSpawnCannon:my_spawnCannon];
}

@end
