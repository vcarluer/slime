package gamers.associate.Slime.game;

import gamers.associate.Slime.items.base.GameItemPhysic;

import com.badlogic.gdx.physics.box2d.WorldManifold;

public class ContactInfo {
	private GameItemPhysic contactWith;
	
	private WorldManifold manifold;

	/**
	 * @return the contactWith
	 */
	public GameItemPhysic getContactWith() {
		return contactWith;
	}

	/**
	 * @param contactWith the contactWith to set
	 */
	public void setContactWith(GameItemPhysic contactWith) {
		this.contactWith = contactWith;
	}

	/**
	 * @return the manifold
	 */
	public WorldManifold getManifold() {
		return manifold;
	}

	/**
	 * @param manifold the manifold to set
	 */
	public void setManifold(WorldManifold manifold) {
		this.manifold = manifold;
	}
}
