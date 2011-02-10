package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysicFactory<T extends GameItemPhysic> extends ItemFactoryBase<T> {
	protected World world;
	protected float worldRatio;
	
	public void Attach(CCNode attachNode, World attachWorld, float attachWorldRatio) {
		this.rootNode = attachNode;
		this.world = attachWorld;
		this.worldRatio = attachWorldRatio;
		this.initAnimation();
		this.rootNode.addChild(spriteSheet);
		this.isAttached = true;
	}
	
	public void Detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			// true here?
			this.rootNode.removeChild(this.spriteSheet, true);
			this.rootNode = null;
			this.world = null;
			this.worldRatio = 0f;
			this.isAttached = false;
		}
	}		
}
