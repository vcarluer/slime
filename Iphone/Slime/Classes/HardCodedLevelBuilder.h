#import "Level.h"


@interface HardCodedLevelBuilder : NSObject {
}

+ (void) build:(Level *)level levelName:(NSString *)levelName;
+ (void) buildHome:(Level *)level levelName:(NSString *)levelName;
@end
