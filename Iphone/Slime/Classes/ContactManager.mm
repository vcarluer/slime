#import "ContactManager.h"
#import "GameItemPhysic.h"

//@implementation ContactManager

void beginContact(b2Contact* contact) {
	b2Fixture* fixtureA = contact->GetFixtureA();
	b2Fixture* fixtureB = contact->GetFixtureB();
    if (fixtureA != nil && fixtureB != nil) {
        
        b2Body *bodyA = fixtureA->GetBody();
        b2Body *bodyB = fixtureB->GetBody();
        
        id oA = (id)bodyA->GetUserData();
        id oB = (id)bodyB->GetUserData();
        if ([oA isKindOfClass:[GameItemPhysic class]]){
            GameItemPhysic * item = (GameItemPhysic *)oA;
            [item addContact:oB];
        }
        if ([oB isKindOfClass:[GameItemPhysic class]]){
            GameItemPhysic * item = (GameItemPhysic *)oB;
            [item addContact:oA];
        }
    }
}

void endContact(b2Contact *contact) {
}

//@end


