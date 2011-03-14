#import "LevelSelection.h"

LevelSelection * levelSelection;

@implementation LevelSelection

@synthesize scene;

+ (LevelSelection *) get {
  if (levelSelection == nil) {
    levelSelection = [[[LevelSelection alloc] init] autorelease];
  }
  return levelSelection;
}

- (id) init {
  if ((self = [super init])) {
    scene = [CCScene node];
    selectionLayer = [[[LevelSelectionLayer alloc] init] autorelease];
    [scene addChild:selectionLayer];
  }
  return self;
}

- (void) dealloc {
  [scene release];
  [selectionLayer release];
  [backgroundLayer release];
  [super dealloc];
}

@end
