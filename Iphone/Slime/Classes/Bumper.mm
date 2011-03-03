#import "Bumper.h"

float Default_Powa = 1.5f;
NSString * Texture_Wait = @"metal_tri.png";
NSString * Anim_Wait = @"bumper_wait";
float Default_Bumper_Width = 64.0f;
float Default_Bumper_Height = 64.0f;

@implementation Bumper
@synthesize powa;

- (id) init:(CCNode *)node x:(float)my_x y:(float)my_y width:(float)my_width height:(float)my_height world:(b2World *)my_world worldRatio:(float)my_worldRatio {
	self = [super init:node x:my_x y:my_y width:my_width height:my_height world:my_world worldRatio:my_worldRatio];
	if (self != nil){
		if (width == 0 && height == 0) {
			width = bodyWidth = Default_Bumper_Width;
			height = bodyHeight = Default_Bumper_Height;
			[self transformTexture];
		}
		powa = Default_Powa;
		[self initBody];
	}
	return self;
}

- (void) setPowa:(float)apowa {
	powa = apowa;
	for (b2Fixture* f = body->GetFixtureList(); f; f = f->GetNext()){
		//for (b2Fixture *fix in body.GetFixtureList()) {
		f->SetRestitution(powa);
	}
	
}

- (void) initBody {
	b2BodyDef *bodyDef = new b2BodyDef;
	CGPoint spawnPoint;
	spawnPoint.x = position.x;
	spawnPoint.y = position.y;
	bodyDef->position.Set(spawnPoint.x / worldRatio, spawnPoint.y / worldRatio);
	b2PolygonShape * bumperShape = new b2PolygonShape;
	float bw2 = bodyWidth / 2 / worldRatio;
	float bh2 = bodyHeight / 2 / worldRatio;
	b2Vec2 vertemp;
	vertemp.Set(bw2, -bh2);
	bumperShape->m_vertexCount = 3;
	//shape.SetAsBox(1, 1);
	bumperShape->m_vertices[0].Set(bw2, -bh2);
	bumperShape->m_vertices[1].Set(-bw2, bh2);
	bumperShape->m_vertices[2].Set(-bw2, -bh2);
	
	
	//@synchronized(world) 
	//{
	bodyDef->userData = self;
    body = world->CreateBody(bodyDef);
    //body->userData = self;
    b2FixtureDef * fixtureDef = new b2FixtureDef;
    fixtureDef->shape = bumperShape;
    fixtureDef->density = 1.0f;
    fixtureDef->friction = 1.0f;
    fixtureDef->restitution = powa;
	//  short tempo = GameItemPhysic.bodyCategory_InGame
    fixtureDef->filter.categoryBits = Category_InGame;
    body->CreateFixture(fixtureDef);
	
	//}
}

- (void) waitAnim {
}

@end
