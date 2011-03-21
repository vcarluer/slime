#import "b2Contact.h"
#import "b2WorldCallbacks.h"
#import "GameItemPhysic.h"
/*
class ContactManager : public b2ContactListener {
    
    void beginContact(b2Contact* contact);
    
    void endContact(b2Contact *contact);
	
};
*/
class ContactManager : public b2ContactListener {
public:    
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
                [item addContact:oB];
            }
            if ([oB isKindOfClass:[GameItemPhysic class]]){
                GameItemPhysic * item = (GameItemPhysic *)oB;
                [item addContact:oA];
            }
        }
    }
    
    void EndContact(b2Contact *contact) {
    }
    
};

