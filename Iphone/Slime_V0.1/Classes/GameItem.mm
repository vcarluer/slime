#import "GameItem.h"

@implementation GameItem

@synthesize position, angle, width, height;
@synthesize currentAction, sprite, rootNode, animationList;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	self = [super init];
	if (self != nil) {
		animationList = [[[NSMutableDictionary alloc] init] autorelease];
		
		CCSpriteBatchNode * spriteSheet;
		CCSpriteFrameCache * cache = [CCSpriteFrameCache sharedSpriteFrameCache];
		[cache addSpriteFramesWithFile:@"labo.plist"];
		spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"labo.png"];
		//spriteSheet = testSheet;
		
		//[self addChild:spriteSheet];
		[self addAnim:@"blueportal" frameCount:4];
		[self addAnim:@"burned-wait" frameCount:2];
		[self addAnim:@"burning" frameCount:5];
		[self addAnim:@"falling" frameCount:3];
		[self addAnim:@"landing-h" frameCount:3];
		[self addAnim:@"landing-v" frameCount:3];
		[self addAnim:@"redportal" frameCount:4];
		[self addAnim:@"wait-h" frameCount:5];
		[self addAnim:@"wait-v" frameCount:5];
		
		
		CGPoint tempposition;
		tempposition.x =my_x;
		tempposition.y =my_y;
		position = tempposition;
		
		angle = 0.0f;
		rootNode = node;
		width = my_width;
		height = my_height;
		CGRect my_rect = CGRectMake(my_x, 0, my_width, my_y);
		
		//CCSprite * my_sprite = [[[CCSprite alloc] initWithBatchNode:node rect:[(CGRect)spriteSheet rect]] retain] ;
		
		CCSprite * my_sprite = [CCSprite spriteWithSpriteFrameName:@"labo.plist"];
		
		
		[self setSprite:my_sprite];
	}
	return self;
}

- (void) setSprite:(CCSprite *)affectSprite {
	if (sprite != nil) {
		[rootNode removeChild:sprite cleanup:YES];
	}
	sprite = affectSprite;
//	[sprite useBatchNode];
	//[rootNode addChild:sprite];
	[sprite setPosition:position];
	[sprite setRotation:angle];
	[self transformTexture];
}

- (CGSize) textureSize {
	
	
	
	CGSize size;
	if ([sprite textureRect].size.width != 0) {
		size = [sprite textureRect].size;
	}
	else {
		CCAnimation * anim = [self getReferenceAnimation];
		if (anim != nil) {
			size = [[[anim frames] objectAtIndex:0] rect].size;
		}
	}
	return size;
}

- (CCAnimation *) getReferenceAnimation {
	return nil;
}

- (void) transformTexture {
	if (width != 0 && height != 0) {
		CGSize size = [self textureSize];
		if (size.width != 0 && size.height != 0) {
			float wScale = width / size.width;
			float hScale = height / size.height;
			[sprite setScaleX:wScale];
			[sprite setScaleY:hScale];
		}
	}
}


/**
 * @param animations
 * @uml.property  name="animationList"
 */
- (void) setAnimationList:(NSMutableDictionary *)animations {
	animationList = animations;
	[self transformTexture];
}

- (void) render:(float)delta {
	if (sprite != nil) {
		position = [sprite position];
	}
}

- (void) addAnim:(NSString *)animName frameCount:(int)frameCount {
	if (animationList != nil) {
		[animationList setValue:[GameItem createAnim:animName frameCount:frameCount] forKey:animName];
	}
}

+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount {
	NSMutableArray * animArray = [[[NSMutableArray alloc] init] autorelease];
	
	for (int i = 0; i < frameCount; i++) {
		NSString* myNewString = [NSString stringWithFormat:@"%d", i + 1];
		NSString * frameName = [[[animName stringByAppendingString:@"-"] stringByAppendingString:myNewString] stringByAppendingString:@".png"];
		CCSpriteFrame * frame = [[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:frameName];
		if (frame != nil) {
			[animArray addObject:frame];
		}
	}
	//CCAnimation *animation = [CCAnimation animationWithFrames:animArray delay:0.1f];
	CCAnimation *animation = [CCAnimation animationWithName:animName delay:0.1f frames:animArray];
	
	CCSprite * sprite = [CCSprite spriteWithSpriteFrameName:[animName stringByAppendingString:@"-1.png"]];
	return animation;
}

- (void) dealloc {
	// [position release];
	[animationList release];
	[currentAction release];
	[sprite release];
	[rootNode release];
	[super dealloc];
}

@end
