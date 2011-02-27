#import "ContactManager.h"
#import "GameItemPhysic.h"

//@implementation ContactManager

void BeginContact(b2Contact* contact) {
	b2Fixture* fixtureA = contact->GetFixtureA();
	b2Fixture* fixtureB = contact->GetFixtureB();
  if (fixtureA != nil && fixtureB != nil) {
    
	  b2Body *bodyA = fixtureA->GetBody();
	  b2Body *bodyB = fixtureB->GetBody();
	  
	  id oA = (id)bodyA->GetUserData();
	  id oB = (id)bodyB->GetUserData();
	  if ([oA isKindOfClass:[GameItemPhysic class]]){
      GameItemPhysic * item = (GameItemPhysic *)oA;
      [item contact:oB];
    }
	  if ([oB isKindOfClass:[GameItemPhysic class]]){
      GameItemPhysic * item = (GameItemPhysic *)oB;
      [item contact:oA];
    }
  }
}

void endContact(b2Contact *contact) {
}


