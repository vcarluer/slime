#import "LevelSelectionLayer.h"
#import "CCLayer.h"
#import "CCScene.h"

@interface LevelSelection : NSObject {
  CCScene * scene;
  CCLayer * selectionLayer;
  CCLayer * backgroundLayer;
}

@property(nonatomic, retain, readonly) CCScene * scene;
+ (LevelSelection *) get;
- (id) init;
@end
