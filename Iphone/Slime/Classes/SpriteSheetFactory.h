//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"
#import	"ItemFactoryBase.h"

extern int Included_For_Attach;
extern int Excluded_For_Attach;

@interface SpriteSheetFactory : ItemFactoryBase {
	//NSMutableDictionary * SpriteSheetList;
}

+ (void) add:(NSString *)plistPngName;
+ (void) add:(NSString *)plistPngName isExcluded:(BOOL)isExcluded;
+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName;
+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName isExcluded:(BOOL)isExcluded;
+ (void) destroy;
- (void) attachAll:(CCNode *)attachNode;
+ (void) detachAll;
@end
