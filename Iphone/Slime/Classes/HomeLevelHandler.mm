#import "HomeLevelHandler.h"

@implementation HomeLevelHandler

- (id) init:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
    if ((self = [super init:my_x y:my_y width:my_width height:my_height ])) {
        maxSlime = 30;
        minSpawn = 0.5;
        maxSpawn = 3;
        // TODO    
        //   startHome = [system  currentTimeMillis];
        slimeCount = 0;
    }
    return self;
}

- (void) render:(float)delta {
    if (slimeCount < maxSlime) {
        Level * level =    currentLevel;
        if (level != nil) {
            //    long elapsed = ([System currentTimeMillis] - startHome) / 1000;
            //  nextRand = [MathLib random:minSpawn param1:maxSpawn];
            //   if (elapsed > nextRand && !isPaused) {
            [level spawnSlime];
            slimeCount++;
            //     startHome = [System currentTimeMillis];
        }
    }
}


@end
