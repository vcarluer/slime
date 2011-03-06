package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteSheet;

import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysicFxFactory<T extends GameItemPhysicFx> extends ItemFactoryBase<T> {
	protected World world;
	protected float worldRatio;		
		
	public void attach(Level level, CCNode attachNode, World attachWorld, float attachWorldRatio) {
		this.level = level;
		this.rootNode = attachNode;
		this.world = attachWorld;
		this.worldRatio = attachWorldRatio;
		this.initAnimation();
		this.rootNode.addChild(spriteSheet);
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			this.level = null;
			this.rootNode.removeChild(this.spriteSheet, true);
			this.rootNode = null;
			this.world = null;
			this.worldRatio = 0f;
			this.isAttached = false;
		}
	}		
}
