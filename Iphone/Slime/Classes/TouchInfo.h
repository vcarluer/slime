//  Slime
//
//  Created by antonio Munoz on 02/03/11.
//  Copyright none 2011. All rights reserved.
//

#import "cocos2d.h"

@interface TouchInfo : NSObject {
  CGPoint * moveBeganAt;
  CGPoint * lastMoveReference;
  float lastMoveTime;
  CGPoint * lastMoveDelta;
  BOOL isMoving;
  int pointerId;
}
/*
@property(nonatomic, retain) CGPoint * moveBeganAt;
@property(nonatomic, retain) CGPoint * lastMoveReference;
@property(nonatomic) float lastMoveTime;
@property(nonatomic) CGPoint * lastMoveDelta;
 */
@property(nonatomic) BOOL moving;
@property(nonatomic) int pointerId;
- (id) initWithPointerId:(int)pointerId;
- (void) setMoveBeganAt:(CGPoint *)moveBeganAt;
- (void) setLastMoveReference:(CGPoint *)lastMoveReference;
- (void) setLastMoveTime:(float)lastMoveTime;
- (void) setLastMoveDelta:(CGPoint *)lastMoveDelta;
- (void) setMoving:(BOOL)isMoving;
- (void) setPointerId:(int)pointerId;
@end
