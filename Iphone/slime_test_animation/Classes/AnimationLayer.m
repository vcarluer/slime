#import "AnimationLayer.h"

@implementation AnimationLayer

+ (CCScene *) scene {
  CCScene * scene = [CCScene node];
  CCLayer * layer = [[[AnimationLayer alloc] init] autorelease];
  [scene addChild:layer];
  return scene;
}

- (id) init {
  if (self = [super init]) {
    size = 32;
  }
  return self;
}

- (void) onEnter {
  [super onEnter];
  [[CCSpriteFrameCache sharedSpriteFrameCache] addSpriteFrames:@"labo.plist"];
  spriteSheet = [CCSpriteSheet spriteSheet:@"labo.png"];
  [self addChild:spriteSheet];
  [self createAnim:@"blueportal" frameCount:4];
  [self createAnim:@"burned-wait" frameCount:2];
  [self createAnim:@"burning" frameCount:5];
  [self createAnim:@"falling" frameCount:3];
  [self createAnim:@"landing-h" frameCount:3];
  [self createAnim:@"landing-v" frameCount:3];
  [self createAnim:@"redportal" frameCount:4];
  [self createAnim:@"wait-h" frameCount:5];
  [self createAnim:@"wait-v" frameCount:5];
}

- (void) createAnim:(NSString *)animName frameCount:(int)frameCount {
  NSMutableArray * animArray = [[[NSMutableArray alloc] init] autorelease];

  for (int i = 0; i < frameCount; i++) {
	 NSString* myNewString = [NSString stringWithFormat:@"%d", i + 1];
	 NSString * frameName = [[[animName stringByAppendingString:@"-"] stringByAppendingString:myNewString] stringByAppendingString:@".png"];
    [animArray addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:frameName]];
  }

  CCAnimation * animation = [CCAnimation animation:animName param1:0.1f param2:animArray];
  CCSprite * sprite = [CCSprite sprite:[CCSpriteFrameCache spriteFrameByName:[animName stringByAppendingString:@"-1.png"]]];
  float left = (spriteCount + 1) * size - size / 2;
  float top = [[CCDirector sharedDirector] winSize].height - size / 2;
  [sprite setPosition:left param1:top];
  CCAnimate * animate = [CCAnimate action:animation param1:NO];
  CCAnimate * reverse = [animate reverse];
  CCAction * action = [CCRepeatForever action:[CCSequence actions:animate param1:reverse]];
  [sprite runAction:action];
  [spriteSheet addChild:sprite];
  spriteCount++;
}

- (void) onExit {
  [super onExit];
}

- (void) tick:(float)delta {
}

- (void) dealloc {
  [spriteSheet release];
  [slime release];
  [super dealloc];
}

@end
