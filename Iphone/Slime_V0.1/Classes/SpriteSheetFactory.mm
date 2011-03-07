#import "SpriteSheetFactory.h"

//NSMutableDictionary * SpriteSheetList = [[[NSMutableDictionary alloc] init] autorelease];

@implementation SpriteSheetFactory

@synthesize SpriteSheetList;

+ (void) add:(NSString *)plistPngName {
  if ([SpriteSheetList objectForKey:plistPngName] == nil) {
    [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:[plistPngName stringByAppendingString:@".plist"]];
    CCSpriteBatchNode * spriteSheet = [CCSpriteBatchNode batchNodeWithFile:[plistPngName stringByAppendingString:@".png"]];
    [SpriteSheetList setObject:spriteSheet forKey:plistPngName];
  }
}

+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName {
  [self add:plistPngName];
  return [SpriteSheetList objectForKey:plistPngName];
}

+ (void) destroy {
  [SpriteSheetList removeAllObjects];
}

@end
