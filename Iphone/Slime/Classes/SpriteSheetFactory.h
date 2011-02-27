#import "CCSpriteFrameCache.h"
#import "CCSpriteSheet.h"

@interface SpriteSheetFactory : NSObject {
	@private
	NSMutableDictionary * SpriteSheetList;
}

+ (void) add:(NSString *)plistPngName;
+ (CCSpriteSheet *) getSpriteSheet:(NSString *)plistPngName;
+ (void) destroy;
@end
