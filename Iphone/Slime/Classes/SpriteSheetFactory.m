#import "SpriteSheetFactory.h"

 //NSMutableDictionary * SpriteSheetList = [[[NSMutableDictionary alloc] init] autorelease];

@implementation SpriteSheetFactory

+ (void) add:(NSString *)plistPngName {
  if ([SpriteSheetList objectForKey:plistPngName] == nil) {
    [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFrames:[plistPngName stringByAppendingString:@".plist"]];
    CCSpriteSheet * spriteSheet = [CCSpriteSheet spriteSheet:[plistPngName stringByAppendingString:@".png"]];
    [SpriteSheetList setObject:plistPngName param1:spriteSheet];
  }
}

+ (CCSpriteSheet *) getSpriteSheet:(NSString *)plistPngName {
  [self add:plistPngName];
  return [SpriteSheetList objectForKey:plistPngName];
}

+ (void) destroy {
  [SpriteSheetList removeAllObjects];
}

@end
