package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItemPhysic;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;


public class ContactManager implements ContactListener {
	
	@Override
	public void beginContact(Contact contact) {
		if (contact.getFixtureA() != null && contact.getFixtureB() != null) {
			Object oA = contact.getFixtureA().getBody().getUserData();
			Object oB = contact.getFixtureB().getBody().getUserData();
			if (oA instanceof GameItemPhysic)
			{
				GameItemPhysic item = (GameItemPhysic)oA;
				item.addContact(oB, contact.GetWorldManifold());
			}
			
			if (oB instanceof GameItemPhysic)
			{
				GameItemPhysic item = (GameItemPhysic)oB;
				item.addContact(oA, contact.GetWorldManifold());
			}
		}	
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}
}
