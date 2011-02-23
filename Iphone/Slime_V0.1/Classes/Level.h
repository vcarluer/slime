#import "cocos2d.h"
#import "Box2D.h"
#import "LevelLayer.h"

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */

extern NSString * LEVEL_HOME;
//extern Level * currentLevel;

@interface Level : NSObject {
@public	
  b2World* world;
  b2Vec2 gravity;
  float worldRatio;
  NSMutableArray * items;

  /**
   * @uml.property  name="slimyFactory"
   * @uml.associationEnd
 */
  CCSprite * backgroundSprite;

  /**
   * @uml.property  name="contactManager"
   * @uml.associationEnd
 */
  //ContactManager * contactManager;
  //SpawnPortal * spawnPortal;
  //GoalPortal * goalPortal;
  CCScene * scene;
  LevelLayer * my_levelLayer;
  //HudLayer * hudLayer;
  //BackgoundLayer * backgroundLayer;
  CCLayer * gameLayer;
  CCLayer * customOverLayer;
  int customZ;
  CCLabelTTF * label;
  float levelWidth;
  float levelHeight;
  CGPoint levelOrigin;
  //CameraManager * cameraManager;
  NSString * currentLevelName;
}

@property(nonatomic, retain, readonly) NSString * currentLevelName;
//@property(nonatomic, retain, readonly) CCScene * scene;
//@property(nonatomic, retain, readonly) CameraManager * cameraManager;
@property(nonatomic, readonly) float worlRatio;
@property(nonatomic, readonly) float levelWidth;
@property(nonatomic, readonly) float levelHeight;
//@property(nonatomic, retain, readonly) SpawnPortal * spawnPortal;
- (id) init;
+ (Level *) get:(NSString *)levelName;
- (void) attachToFactory;
- (void) reload;
- (void) loadLevel:(NSString *)levelName;
- (void) initLevel;
- (void) tick:(float)delta;
- (void) SpawnSlime;
//- (void) setGoalPortal:(GoalPortal *)portal;
//- (void) addGameItem:(GameItem *)item;
- (void) setLevelSize:(float)width height:(float)height;
- (void) setIsTouchEnabled:(BOOL)value;
- (void) addCustomOverLayer:(CCLayer *)layer;
- (void) removeCustomOverLayer;
- (void) resetLevel;
@end
