//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "SpriteSheetFactory.h"

int Included_For_Attach = 0;
int Excluded_For_Attach = 1;
CCNode * rootNode;
BOOL isAttached;

static NSMutableDictionary *SpriteSheetList()
{
    static NSMutableDictionary *dict = NULL;
    if(dict == NULL)
    {
        dict = [[NSMutableDictionary alloc] init];
    }
    return [[dict retain] autorelease];
}


@implementation SpriteSheetFactory

//static NSMutableDictionary * SpriteSheetList = [[NSMutableDictionary alloc] init];
+ (void) add:(NSString *)plistPngName {
  [self add:plistPngName isExcluded:NO];
}
+ (void) add:(NSString *)plistPngName {
	if ([SpriteSheetList() objectForKey:plistPngName] == nil) {
		[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:[plistPngName stringByAppendingString:@".plist"]];
		CCSpriteBatchNode * spriteSheet = [CCSpriteBatchNode batchNodeWithFile:[plistPngName stringByAppendingString:@".png"]];
		[SpriteSheetList() setObject:spriteSheet forKey:plistPngName];	

	}
}
+ (CCSpriteSheet *) getSpriteSheet:(NSString *)plistPngName {
  return [self getSpriteSheet:plistPngName isExcluded:NO];
}

+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName {
	[self add:plistPngName];
	return [SpriteSheetList() objectForKey:plistPngName];
}
+ (CCSpriteSheet *) getSpriteSheet:(NSString *)plistPngName isExcluded:(BOOL)isExcluded {
  if (plistPngName != @"") {
    [self add:plistPngName isExcluded:isExcluded];
    return [SpriteSheetList objectForKey:plistPngName];
  }
   else {
    return nil;
  }
}

- (void) Attach:(CCNode *)attachNode {
	rootNode = attachNode;
	[rootNode addChild:spriteSheet];
	isAttached = YES;
}

+ (void) detachAll {
  if (isAttached && rootNode != nil) {

    for (CCSpriteSheet * spriteSheet in [SpriteSheetList allValues]) {
      if ([spriteSheet tag] == Included_For_Attach) {
        [rootNode removeChild:spriteSheet param1:YES];
      }
    }

  }
}

@end
