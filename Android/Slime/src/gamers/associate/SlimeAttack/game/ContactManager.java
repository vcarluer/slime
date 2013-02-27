package gamers.associate.SlimeAttack.game;

import gamers.associate.SlimeAttack.items.base.GameItemPhysic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;


public class ContactManager implements ContactListener {
	
	public void beginContact(Contact contact) {
		if (contact.getFixtureA() != null && contact.getFixtureB() != null) {
			Object oA = contact.getFixtureA().getBody().getUserData();
			Object oB = contact.getFixtureB().getBody().getUserData();
			if (oA != null && oA instanceof GameItemPhysic)
			{
				GameItemPhysic item = (GameItemPhysic)oA;
				item.addContact(oB, contact.getWorldManifold());
			}
			
			if (oB != null && oB instanceof GameItemPhysic)
			{
				GameItemPhysic item = (GameItemPhysic)oB;
				item.addContact(oA, contact.getWorldManifold());
			}
		}	
	}

	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
