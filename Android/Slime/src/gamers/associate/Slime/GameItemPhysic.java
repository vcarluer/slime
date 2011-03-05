package gamers.associate.Slime;

import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysic extends GameItem{
	public static short Category_Static = 0x0001;
	public static short Category_InGame = 0x0002;
	public static short Category_OutGame = 0x0003;
	
	protected World world;
	protected Body body; 
	protected float bodyWidth;
	protected float bodyHeight;
	protected float worldRatio;	
	
	public GameItemPhysic(CCNode node, float x, float y, float width, float height, World world, float worldRatio) {		
		super(node, x, y, width, height);
		this.world = world;
		this.worldRatio = worldRatio;
		this.bodyWidth = this.width;
		this.bodyHeight = this.height;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		this.world.destroyBody(this.body);
		this.world = null;
		this.body = null;
	}
	
	protected abstract void initBody();
	
	public Body getBody() {
		return this.body;
	}
	
	@Override
	public void render(float delta) {
		if (this.sprite != null && this.body != null) {			
			this.sprite.setPosition(this.body.getPosition().x * this.worldRatio, this.body.getPosition().y * this.worldRatio);
			this.sprite.setRotation(-1.0f * ccMacros.CC_RADIANS_TO_DEGREES(this.body.getAngle()));
		}
		
		super.render(delta);
	}
	
	public void contact(Object with) {
	}
}
