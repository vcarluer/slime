//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "SpriteSheetFactory.h"

int Included_For_Attach = 0;
int Excluded_For_Attach = 1;
CCNode * rootNode;
BOOL spritsheet_isAttached;

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



+ (void) add:(NSString *)plistPngName isExcluded:(BOOL)isExcluded {
    
    if(plistPngName !=@"")
    {
        if ([SpriteSheetList() objectForKey:plistPngName] == nil) {
            [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:[plistPngName stringByAppendingString:@".plist"]];
            CCSpriteBatchNode * spriteSheet = [CCSpriteBatchNode batchNodeWithFile:[plistPngName stringByAppendingString:@".png"]];
            int tag = Included_For_Attach;
            if (isExcluded){
                tag = Excluded_For_Attach;
            }
            spriteSheet.tag = tag;
            [SpriteSheetList() setObject:spriteSheet forKey:plistPngName];	
            
        }
    }
}


+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName {
	[self add:plistPngName];
	return [SpriteSheetList() objectForKey:plistPngName];
}

+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName isExcluded:(BOOL)isExcluded {
  if (plistPngName != @"") {
    [self add:plistPngName isExcluded:isExcluded];
    return [SpriteSheetList() objectForKey:plistPngName];
  }
   else {
    return nil;
  }
}


- (void) attachAll:(CCNode *)attachNode {
	rootNode = attachNode;
    if (rootNode != nil) {
        [SpriteSheetFactory detachAll];
       // NSMutableDictionary * temp_SpriteSheetList = SpriteSheetList();
        /*
        int i;
        for (i=0; i<= [temp_SpriteSheetList count]; i++) {
            CCSpriteBatchNode * my_spriteSheet = [temp_SpriteSheetList 
        }
         */
        for (id key in SpriteSheetList()) {
            CCSpriteBatchNode * my_spriteSheet = [SpriteSheetList() objectForKey:key];
            if ([my_spriteSheet tag] == Included_For_Attach) {
                [rootNode addChild:my_spriteSheet];
            }
        }
    }
	
	isAttached = YES;
}

+ (void) detachAll {
  if (spritsheet_isAttached && rootNode != nil) {

    for (CCSpriteBatchNode * my_spriteSheet in [SpriteSheetList() allValues]) {
      if ([my_spriteSheet tag] == Included_For_Attach) {
        [rootNode removeChild:my_spriteSheet cleanup:YES];
      }
    }

  }
}

@end
