//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "SpriteSheetFactory.h"


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
	if ([SpriteSheetList() objectForKey:plistPngName] == nil) {
		[[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFramesWithFile:[plistPngName stringByAppendingString:@".plist"]];
		CCSpriteBatchNode * spriteSheet = [CCSpriteBatchNode batchNodeWithFile:[plistPngName stringByAppendingString:@".png"]];
		[SpriteSheetList() setObject:spriteSheet forKey:plistPngName];	

	}
}

+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName {
	[self add:plistPngName];
	return [SpriteSheetList() objectForKey:plistPngName];
}


+ (void) Attach:(CCNode *)attachNode {
	rootNode = attachNode;
	[rootNode addChild:spriteSheet];
	isAttached = YES;
}

+ (void) destroy {
	[SpriteSheetList() removeAllObjects];
}

@end
