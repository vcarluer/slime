#import "GameItemCocos.h"

@implementation GameItemCocos

@synthesize sprite, rootNode, currentAction, animationList, texture;


- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height {
	if ((self = [super init:my_x y:my_y width:my_width height:my_height])) {
		animationList = [[[NSMutableDictionary alloc] init] autorelease];
		rootNode = node;
		textureMode = Scale;
		CCSprite * my_sprite = [[[CCSprite alloc] init] autorelease];
		[self setSprite:my_sprite];
	}
	return self;
}

- (void) destroy {
	if (sprite != nil) {
		if (rootNode != nil) {
			[rootNode removeChild:sprite cleanup:YES];
		}
	}
	[super destroy];
}

- (void) setSprite:(CCSprite *)affectSprite {
	if (sprite != nil) {
		if (rootNode != nil) {
			[rootNode removeChild:sprite cleanup:YES];
		}
	}
	sprite = affectSprite;
	if (rootNode != nil) {
		[rootNode addChild:sprite];
	}
	[sprite setPosition:position];
	[sprite setRotation:angle];
	[self transformTexture];
}

- (CGSize ) getTextureSize {
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
	if (textureMode == Scale) {
		if (width != 0 && height != 0) {
			CGSize size = [self getTextureSize];
			if (size.width != 0 && size.height != 0) {
				float wScale = width / size.width;
				float hScale = height / size.height;
				[sprite setScaleX:wScale];
				[sprite setScaleY:hScale];
			}
		}
	}
}

- (void) setTexture:(CCTexture2D *)my_texture {
	texture = my_texture;
}
/*
 - (void) draw:(GL10 *)gl {
 if (textureMode == TextureMode.Clip) {
 if (width != 0 && height != 0) {
 if (texture != nil) {
 CGRect * rect = [CGRect make:position.x - width / 2 param1:position.y - height / 2 param2:width param3:height];
 [texture drawRepeatInRect:gl param1:rect];
 }
 }
 }
 [super draw:gl];
 }
 
 */
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
		angle = [sprite rotation];
		if (textureMode == Clip) {
			[self transformTexture];
		}
	}
}

- (void) addAnim:(NSString *)animName frameCount:(int)frameCount {
	if (animationList != nil) {
		[animationList setObject:[GameItemCocos createAnim:animName frameCount:frameCount] forKey:animName];
	}
}

+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount {
	return [self createAnim:animName frameCount:frameCount interval:0.1f];
}

+ (CCAnimation *) createAnim:(NSString *)animName frameCount:(int)frameCount interval:(float)interval {
	NSMutableArray * animArray = [[[NSMutableArray alloc] init] autorelease];
	
	for (int i = 0; i < frameCount; i++) {
		NSString* myNewString = [NSString stringWithFormat:@"%d", i + 1];
		NSString * frameName = [[[animName stringByAppendingString:@"-"] stringByAppendingString:myNewString] stringByAppendingString:@".png"];
		CCSpriteFrame * frame = [[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:frameName];
		if (frame != nil) {
			[animArray addObject:frame];
		}    
		
	}
	//CCAnimation *animation = [CCAnimation animationWithName:animName delay:interval frames:animArray];
    CCAnimation * my_animation = [CCAnimation animationWithFrames:animArray delay:interval];
    my_animation.name = animName;
	return my_animation;
	
}

- (void) pause {
	[super pause];
	if (sprite != nil) {
		[sprite pauseSchedulerAndActions];
	}
}

- (void) resume {
	[super resume];
	if (sprite != nil) {
		[sprite resumeSchedulerAndActions];
	}
}

- (void) dealloc {
	[animationList release];
	[currentAction release];
	[sprite release];
	[rootNode release];
	[texture release];
	[super dealloc];
}

@end
