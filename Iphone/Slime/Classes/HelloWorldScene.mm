//
//  HelloWorldScene.mm
//  test_slime
//
//  Created by antonio Munoz on 06/03/11.
//  Copyright none 2011. All rights reserved.
//


// Import the interfaces
#import "HelloWorldScene.h"
#import "Slimy.h"

//Pixel to metres ratio. Box2D uses metres as the unit for measurement.
//This ratio defines how many pixels correspond to 1 Box2D "metre"
//Box2D is optimized for objects of 1x1 metre therefore it makes sense
//to define the ratio so that your most common object type is 1x1 metre.
#define PTM_RATIO 32

// enums that will be used as tags
enum {
	kTagTileMap = 1,
	kTagBatchNode = 1,
	kTagAnimation1 = 1,
};


// HelloWorld implementation
@implementation HelloWorld

+(id) scene
{
	// 'scene' is an autorelease object.
	CCScene *scene = [CCScene node];
	
	// 'layer' is an autorelease object.
	HelloWorld *layer = [HelloWorld node];
	
	// add layer as a child to scene
	[scene addChild: layer];
	
	// return the scene
	return scene;
}

// initialize your instance here
-(id) init
{
	if( (self=[super init])) {
		
		// enable touches
		self.isTouchEnabled = YES;
		
		// enable accelerometer
		self.isAccelerometerEnabled = YES;
		
		CGSize screenSize = [CCDirector sharedDirector].winSize;
		CCLOG(@"Screen width %0.2f screen height %0.2f",screenSize.width,screenSize.height);
		
		// Define the gravity vector.
		b2Vec2 gravity;
		gravity.Set(0.0f, -10.0f);
		
		// Do we want to let bodies sleep?
		// This will speed up the physics simulation
		bool doSleep = true;
		
		// Construct a world object, which will hold and simulate the rigid bodies.
		world = new b2World(gravity, doSleep);
		
		world->SetContinuousPhysics(true);
		
		// Debug Draw functions
		//m_debugDraw = new GLESDebugDraw( PTM_RATIO );
		//world->SetDebugDraw(m_debugDraw);
		
		uint32 flags = 0;
		//flags += b2DebugDraw::e_shapeBit;
		//		flags += b2DebugDraw::e_jointBit;
		//		flags += b2DebugDraw::e_aabbBit;
		//		flags += b2DebugDraw::e_pairBit;
		//		flags += b2DebugDraw::e_centerOfMassBit;
		//m_debugDraw->SetFlags(flags);		
		
		
		// Define the ground body.
		b2BodyDef groundBodyDef;
		groundBodyDef.position.Set(0, 0); // bottom-left corner
		
		// Call the body factory which allocates memory for the ground body
		// from a pool and creates the ground box shape (also from a pool).
		// The body is also added to the world.
		b2Body* groundBody = world->CreateBody(&groundBodyDef);
		
		// Define the ground box shape.
		b2PolygonShape groundBox;		
		
		// bottom
		groundBox.SetAsEdge(b2Vec2(0,0), b2Vec2(screenSize.width/PTM_RATIO,0));
		groundBody->CreateFixture(&groundBox,0);
		
		// top
		groundBox.SetAsEdge(b2Vec2(0,screenSize.height/PTM_RATIO), b2Vec2(screenSize.width/PTM_RATIO,screenSize.height/PTM_RATIO));
		groundBody->CreateFixture(&groundBox,0);
		
		// left
		groundBox.SetAsEdge(b2Vec2(0,screenSize.height/PTM_RATIO), b2Vec2(0,0));
		groundBody->CreateFixture(&groundBox,0);
		
		// right
		groundBox.SetAsEdge(b2Vec2(screenSize.width/PTM_RATIO,screenSize.height/PTM_RATIO), b2Vec2(screenSize.width/PTM_RATIO,0));
		groundBody->CreateFixture(&groundBox,0);
		
		
		
		//Set up anim
		
		
		CCSpriteFrameCache * cache = [CCSpriteFrameCache sharedSpriteFrameCache];
		[cache addSpriteFramesWithFile:@"labo.plist"];
		//CCSpriteBatchNode *spriteSheet = [CCSpriteBatchNode batchNodeWithFile:@"labo.png"];
		CCSpriteSheet *spriteSheet = [CCSpriteSheet spriteSheetWithFile:@"labo.png"];
		
		[self addChild:spriteSheet z:0 tag:kTagAnimation1];
		/*
		 [self createAnim:@"blueportal" frameCount:4];
		 [self createAnim:@"burned-wait" frameCount:2];
		 [self createAnim:@"burning" frameCount:5];
		 [self createAnim:@"falling" frameCount:3];
		 [self createAnim:@"landing-h" frameCount:3];
		 [self createAnim:@"landing-v" frameCount:3];
		 [self createAnim:@"redportal" frameCount:4];
		 [self createAnim:@"wait-h" frameCount:5];
		 [self createAnim:@"wait-v" frameCount:5];
		 
		 */
		
		
		//Set up sprite
		
		
		
		
		/*
		 CCSpriteBatchNode *batch = [CCSpriteBatchNode batchNodeWithFile:@"blocks.png" capacity:150];
		 [self addChild:batch z:0 tag:kTagBatchNode];
		 
		 [self addNewSpriteWithCoords:ccp(screenSize.width/2, screenSize.height/2)];
		 
		 */
		
		CCLabelTTF *label = [CCLabelTTF labelWithString:@"Tap screen" fontName:@"Marker Felt" fontSize:32];
		[self addChild:label z:0];
		[label setColor:ccc3(0,0,255)];
		label.position = ccp( screenSize.width/2, screenSize.height-50);
		
		[self schedule: @selector(tick:)];
	}
	return self;
}

-(void) draw
{
	// Default GL states: GL_TEXTURE_2D, GL_VERTEX_ARRAY, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
	// Needed states:  GL_VERTEX_ARRAY, 
	// Unneeded states: GL_TEXTURE_2D, GL_COLOR_ARRAY, GL_TEXTURE_COORD_ARRAY
	glDisable(GL_TEXTURE_2D);
	glDisableClientState(GL_COLOR_ARRAY);
	glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	
	world->DrawDebugData();
	
	// restore default GL states
	glEnable(GL_TEXTURE_2D);
	glEnableClientState(GL_COLOR_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	
}

-(void) addNewSpriteWithCoords:(CGPoint)p
{
	CCLOG(@"Add sprite %0.2f x %02.f",p.x,p.y);
	//CCSpriteBatchNode *batch = (CCSpriteBatchNode*) [self getChildByTag:kTagBatchNode];
	CCSpriteSheet *batch = (CCSpriteSheet*) [self getChildByTag:kTagBatchNode];
	
	//We have a 64x64 sprite sheet with 4 different 32x32 images.  The following code is
	//just randomly picking one of the images
	
	
	/*
	 int idx = (CCRANDOM_0_1() > .5 ? 0:1);
	 int idy = (CCRANDOM_0_1() > .5 ? 0:1);
	 
	 
	 CCSprite *sprite = [CCSprite spriteWithBatchNode:batch rect:CGRectMake(32 * idx,32 * idy,32,32)];
	 
	 */
	
	
	Slimy *my_slyme = [[Slimy alloc] init:batch x:p.x y:p.y width:24.0f height:26.0f world:world worldRatio:PTM_RATIO ];
	
	
	/*
	CCSprite *sprite = [self createAnim:@"falling" frameCount:3];
	
	
	[batch addChild:sprite];
	
	sprite.position = ccp( p.x, p.y);
	
	// Define the dynamic body.
	//Set up a 1m squared box in the physics world
	b2BodyDef bodyDef;
	bodyDef.type = b2_dynamicBody;
	
	bodyDef.position.Set(p.x/PTM_RATIO, p.y/PTM_RATIO);
	bodyDef.userData = sprite;
	b2Body *body = world->CreateBody(&bodyDef);
	
	// Define another box shape for our dynamic body.
	b2PolygonShape dynamicBox;
	dynamicBox.SetAsBox(11.0f/PTM_RATIO, 12.0f/PTM_RATIO);//These are mid points for our 1m box
	
	// Define the dynamic body fixture.
	
	b2FixtureDef fixtureDef;
    fixtureDef.shape = &dynamicBox;
    fixtureDef.density = 1.0f;
    fixtureDef.friction = 0.3f;
    fixtureDef.restitution = 0.1f;
    //fixtureDef.filter.categoryBits = Category_InGame;
    body->CreateFixture(&fixtureDef);
	/*
	 
	 b2FixtureDef fixtureDef;
	 fixtureDef.shape = &dynamicBox;	
	 fixtureDef.density = 1.0f;
	 fixtureDef.friction = 0.3f;
	 body->CreateFixture(&fixtureDef);
	 
	 */
}

- (CCSprite *) createAnim:(NSString *)animName frameCount:(int)frameCount {
	
	CCSpriteBatchNode *spriteSheet = (CCSpriteBatchNode*) [self getChildByTag:kTagAnimation1];
	NSMutableArray * animArray = [[[NSMutableArray alloc] init] autorelease];
	
	for (int i = 0; i < frameCount; i++) {
		NSString* myNewString = [NSString stringWithFormat:@"%d", i + 1];
		NSString * frameName = [[[animName stringByAppendingString:@"-"] stringByAppendingString:myNewString] stringByAppendingString:@".png"];
		//CCSpriteFrameCache * tempcache = [CCSpriteFrameCache sharedSpriteFrameCache];
		// [animArray addObject:[tempcache getSpriteFrame:frameName]];
		[animArray addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] spriteFrameByName:frameName]];
		//[animArray addObject:[[CCSpriteFrameCache sharedSpriteFrameCache] getSpriteFrame:frameName]];
	}
	int spriteCount;
	int size = 32;
	//CCAnimation * animation = [CCAnimation animation:animName param1:0.1f param2:animArray];
	CCAnimation *animation = [CCAnimation  animationWithName:animName delay:0.1f frames:animArray];
	
	CCSprite * sprite = [CCSprite spriteWithSpriteFrameName:[animName stringByAppendingString:@"-1.png"]];
	
	float left = (spriteCount + 1) * size - size / 2;
	float top = [[CCDirector sharedDirector] winSize].height - size / 2;
	sprite.position = ccp(left, top); 
  	CCAnimate * animate = [CCAnimate actionWithAnimation:animation restoreOriginalFrame:NO];
	CCAnimate * reverse = animate.reverse;
	
	CCAction * action = [CCRepeatForever actionWithAction:animate];
	[sprite runAction:action];
	//[spriteSheet addChild:sprite];
	spriteCount++;
	return sprite;
}


-(void) tick: (ccTime) dt
{
	//It is recommended that a fixed time step is used with Box2D for stability
	//of the simulation, however, we are using a variable time step here.
	//You need to make an informed choice, the following URL is useful
	//http://gafferongames.com/game-physics/fix-your-timestep/
	
	int32 velocityIterations = 8;
	int32 positionIterations = 1;
	
	// Instruct the world to perform a single step of simulation. It is
	// generally best to keep the time step and iterations fixed.
	world->Step(dt, velocityIterations, positionIterations);
	
	
	//Iterate over the bodies in the physics world
	for (b2Body* b = world->GetBodyList(); b; b = b->GetNext())
	{
		if (b->GetUserData() != NULL) {
			//Synchronize the AtlasSprites position and rotation with the corresponding body
			CCSprite *myActor = (CCSprite*)b->GetUserData();
			myActor.position = CGPointMake( b->GetPosition().x * PTM_RATIO, b->GetPosition().y * PTM_RATIO);
			myActor.rotation = -1 * CC_RADIANS_TO_DEGREES(b->GetAngle());
		}	
	}
}

- (void)ccTouchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	//Add a new body/atlas sprite at the touched location
	for( UITouch *touch in touches ) {
		CGPoint location = [touch locationInView: [touch view]];
		
		location = [[CCDirector sharedDirector] convertToGL: location];
		
		[self addNewSpriteWithCoords: location];
	}
}

- (void)accelerometer:(UIAccelerometer*)accelerometer didAccelerate:(UIAcceleration*)acceleration
{	
	static float prevX=0, prevY=0;
	
	//#define kFilterFactor 0.05f
#define kFilterFactor 1.0f	// don't use filter. the code is here just as an example
	
	float accelX = (float) acceleration.x * kFilterFactor + (1- kFilterFactor)*prevX;
	float accelY = (float) acceleration.y * kFilterFactor + (1- kFilterFactor)*prevY;
	
	prevX = accelX;
	prevY = accelY;
	
	// accelerometer values are in "Portrait" mode. Change them to Landscape left
	// multiply the gravity by 10
	b2Vec2 gravity( -accelY * 10, accelX * 10);
	
	world->SetGravity( gravity );
}

// on "dealloc" you need to release all your retained objects
- (void) dealloc
{
	// in case you have something to dealloc, do it in this method
	delete world;
	world = NULL;
	
	delete m_debugDraw;

	// don't forget to call "super dealloc"
	[super dealloc];
}
@end
