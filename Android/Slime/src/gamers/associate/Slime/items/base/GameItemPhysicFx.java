package gamers.associate.Slime.items.base;

import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysicFx extends GameItemCocos {	
	protected World world;	
	protected float worldRatio;
	
	public GameItemPhysicFx(String name, float x, float y, float width, float height, World world, float worldRatio) {		
		super(name, x, y, width, height);
		this.world = world;
		this.worldRatio = worldRatio;		
	}
	
	public GameItemPhysicFx(float x, float y, float width, float height, World world, float worldRatio) {
		this(null, x, y, width, height, world, worldRatio);
	}
	
	@Override
	public void destroy() {			
		this.world = null;	
		super.destroy();
	}
}
