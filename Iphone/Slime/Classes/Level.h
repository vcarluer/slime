//#import "Box2D.h"
//#import "cocos2d.h"
#import "LevelLayer.h"
#import "ContactManager.h"
#import "SpawnPortal.h"
#import "SpawnCannon.h"
#import "GoalPortal.h"
#import "HudLayer.h"
#import "BackgoundLayer.h"
//#import "GameItem.h"



extern BOOL isInit;
extern NSString * LEVEL_HOME;
extern NSString * LEVEL_1;
extern NSString * LEVEL_2;
extern Level * currentLevel;

//@class SpawnPortal;
//@class GameItem;


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
  SpawnCannon * spawnCannon;
  GoalPortal * goalPortal;
  CCScene * scene;
  LevelLayer * levelLayer;
  HudLayer * hudLayer;
  BackgoundLayer * backgroundLayer;
  CCLayer * gameLayer;
  CCLayer * customOverLayer;
  int customZ;
  int hudZ;
  BOOL isHudEnabled;
  CCLabelTTF * label;
  float levelWidth;
  float levelHeight;
  CGPoint levelOrigin;
  //todo
  //CameraManager * cameraManager;
  NSString * currentLevelName;
  NSMutableArray * itemsToRemove;
  NSMutableArray * itemsToAdd;
  BOOL isPaused;
}

@property(nonatomic, retain, readonly) NSString * currentLevelName;
@property(nonatomic, retain, readonly) CCScene * scene;
//@property(nonatomic, retain, readonly) CameraManager * cameraManager;
@property(nonatomic, readwrite ) b2World * world;
@property(nonatomic, readonly) float worlRatio;
@property(nonatomic, readonly) float levelWidth;
@property(nonatomic, readonly) float levelHeight;
@property(nonatomic, retain) SpawnPortal * spawnPortal;
@property(nonatomic, retain) SpawnCannon * spawnCannon;
- (id) init;
+ (Level *) get:(NSString *)levelName;
+ (Level *) get:(NSString *)levelName forceReload:(BOOL)forceReload;
- (void) attachToFactory;
- (void) reload;
- (void) attachLevelToCamera;
- (void) loadLevel:(NSString *)levelName;
- (void) resetLevel;
- (void) initLevel;
- (void) tick:(float)delta;
- (void) addItemToRemove:(GameItem *)item;
- (void) addItemToAdd:(GameItem *)item;
- (void) setPause:(BOOL)value;
- (void) togglePause;
- (void) spawnSlime;
- (void) spawnSlime:(CGPoint)screenTarget;
- (void) setGoalPortal:(GoalPortal *)portal;
- (void) addGameItem:(GameItem *)item;
- (void) removeGameItem:(GameItem *)item;
- (void) setLevelSize:(float)width height:(float)height;
- (void) setIsTouchEnabled:(BOOL)value;
- (void) addCustomOverLayer:(CCLayer *)layer;
- (void) removeCustomOverLayer;
- (void) setIsHudEnabled:(BOOL)value;
- (void) setSpawnCannon:(SpawnCannon *)cannon;
- (void) setSpawnPortal:(SpawnPortal *)portal;
//- (void) draw:(GL10 *)gl;
@end
