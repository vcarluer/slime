#import "Level.h"

extern float LAND_HEIGHT;

@interface HardCodedLevelBuilder : NSObject {
}

@property(nonatomic, readonly) float heightRatio;
+ (void) build:(Level *)level levelName:(NSString *)levelName;
+ (void) buildHome:(Level *)level;
+ (void) buildLevel1:(Level *)level;
+ (void) buildLevel2:(Level *)level;
+ (void) createLand:(Level *)level;

@end
