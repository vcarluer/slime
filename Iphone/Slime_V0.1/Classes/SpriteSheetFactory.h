#import "cocos2d.h"

@interface SpriteSheetFactory : NSObject {
	NSMutableDictionary * SpriteSheetList;
}

@property (nonatomic,assign) NSMutableDictionary *SpriteSheetList;

+ (void) add:(NSString *)plistPngName;
+ (CCSpriteBatchNode *) getSpriteSheet:(NSString *)plistPngName;
+ (void) destroy;
@end
