#import "HomeLayer.h"

HomeLayer * layer;

@implementation HomeLayer

+ (HomeLayer *) get {
  if (layer == nil) {
    layer = [[[HomeLayer alloc] init] autorelease];
  }
  return layer;
}

- (id) init {
  if (self = [super init]) {
    [self setIsTouchEnabled:YES];
  }
  return self;
}



- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	return [super ccTouchesEnded:touches withEvent:event];
}



@end
