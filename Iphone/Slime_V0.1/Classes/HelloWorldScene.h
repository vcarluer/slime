//
//  HelloWorldScene.h
//  Slime_V0.1
//
//  Created by antonio Munoz on 18/02/11.
//  Copyright none 2011. All rights reserved.
//


// When you import this file, you import all the cocos2d classes
#import "cocos2d.h"
#import "Box2D.h"
#import "GLES-Render.h"

// HelloWorld Layer
@interface HelloWorld : CCLayer
{
	b2World* world;
	GLESDebugDraw *m_debugDraw;
}

// returns a Scene that contains the HelloWorld as the only child
+(id) scene;

// adds a new sprite at a given coordinate
-(void) addNewSpriteWithCoords:(CGPoint)p;

@end
