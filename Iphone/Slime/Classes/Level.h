#import "Box2D.h"
//#import "cocos2d.h"
#import "LevelLayer.h"
#import "ContactManager.h"
//#import "SpawnPortal.h"
#import "GoalPortal.h"
#import "HudLayer.h"
#import "BackgoundLayer.h"
//#import "GameItem.h"

/**
 * @author    vince
 * @uml.dependency   supplier="gamers.associate.Slime.GameItem"
 */

extern NSString * LEVEL_HOME;
//extern Level * currentLevel;

@class SpawnPortal;
@class GameItem;


@interface Level : NSObject {
	
  b2World * world;
  b2Vec2  gravity;
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
  ContactManager * contactManager;
  SpawnPortal * spawnPortal;
  GoalPortal * goalPortal;
  CCScene * scene;
  LevelLayer * levelLayer;
  HudLayer * hudLayer;
  BackgoundLayer * backgroundLayer;
  CCLayer * gameLayer;
  CCLayer * customOverLayer;
  int customZ;
  CCLabelTTF * label;
  float levelWidth;
  float levelHeight;
  CGPoint levelOrigin;
  //todo
	//CameraManager * cameraManager;
  NSString * currentLevelName;
}

@property(nonatomic, retain, readonly) NSString * currentLevelName;
@property(nonatomic, retain, readonly) CCScene * scene;
@property(nonatomic, retain, readonly) CCSprite * backgroundSprite;
//@property(nonatomic, retain, readonly) CameraManager * cameraManager;
@property(nonatomic,  readonly) b2World * world;
@property(nonatomic) float worlRatio;
@property(nonatomic, readonly) float levelWidth;
@property(nonatomic, readonly) float levelHeight;
@property(nonatomic, retain, readonly) SpawnPortal * spawnPortal;
- (id) init;
+ (Level *) get:(NSString *)levelName;
- (void) attachToFactory;
- (void) reload;
- (void) loadLevel:(NSString *)levelName;
- (void) initLevel;
- (void) tick:(float)delta;
- (void) SpawnSlime;
- (void) setGoalPortal:(GoalPortal *)portal;
- (void) addGameItem:(GameItem *)item;
- (void) setLevelSize:(float)width height:(float)height;
- (void) setIsTouchEnabled:(BOOL)value;
- (void) addCustomOverLayer:(CCLayer *)layer;
- (void) removeCustomOverLayer;
@end
