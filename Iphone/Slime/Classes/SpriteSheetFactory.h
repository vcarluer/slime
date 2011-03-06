//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"

@interface SpriteSheetFactory : NSObject {
	//NSMutableDictionary * SpriteSheetList;
}

+ (void) add:(NSString *)plistPngName;
+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName;
+ (void) destroy;
@end
