//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"
#import	"ItemFactoryBase.h"


@interface SpriteSheetFactory : ItemFactoryBase {
	//NSMutableDictionary * SpriteSheetList;
}

+ (void) add:(NSString *)plistPngName;
+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName;
+ (void) destroy;
@end
