package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysicFx extends GameItemCocos {	
	protected World world;	
	protected float worldRatio;
	
	public GameItemPhysicFx(CCNode node, float x, float y, float width, float height, World world, float worldRatio) {		
		super(node, x, y, width, height);
		this.world = world;
		this.worldRatio = worldRatio;		
	}
	
	@Override
	public void destroy() {			
		this.world = null;	
		super.destroy();
	}
}
