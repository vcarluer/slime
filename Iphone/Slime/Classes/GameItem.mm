#import "GameItem.h"

@implementation GameItem

@synthesize position;

- (id) init:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	self = [super init];
	if (self != nil) {
	  	my_id =  [[NSProcessInfo processInfo] globallyUniqueString];
		CGPoint tempposition;
		tempposition.x =my_x;
		tempposition.y =my_y;
		position = tempposition;
		angle = 0.0f;
		width = my_width;
		height = my_height;

	}
	return self;
}

- (void) destroy {
}

- (void) render:(float)delta {
}

//TODO
/*
- (void) draw:(GL10 *)gl {
}
*/
- (void) setPause:(BOOL)value {
	if (value) {
		if (!isPaused) {
			[self pause];
		}
	}
	else {
		if (isPaused) {
			[self resume];
		}
	}
	isPaused = value;
}

- (void) pause {
}

- (void) resume {
}

- (void) dealloc {
  [super dealloc];
}

@end

/*
- (void) setSprite:(CCSprite *)affectSprite {
	/*
	if (sprite != nil) {
		[rootNode removeChild:sprite cleanup:YES];
	}
	 
	sprite = affectSprite;
	[rootNode addChild:sprite];
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
	CCAnimation *animation = [CCAnimation animationWithFrames:animArray delay:0.1f];
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
*/