#import "Level.h"
#import "CCLayer.h"
#import "CCMenu.h"
#import "CCMenuItem.h"
#import "CCMenuItemLabel.h"
#import "CCDirector.h"

@interface LevelSelectionLayer : CCLayer {
}

- (id) init;
- (void) doTest:(NSObject *)sender;
- (void) doTest2:(NSObject *)sender;
- (void) goBack:(NSObject *)sender;
@end
