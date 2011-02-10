package gamers.associate.Slime;

import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCNode;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameItemPhysic extends GameItem{
	protected World world;
	protected Body body; 
	protected float bodyWidth;
	protected float bodyHeight;
	protected float worldRatio;
	
	public GameItemPhysic(CCNode node, float x, float y, World world, float worldRatio) {		
		super(node, x, y);
		this.world = world;
		this.worldRatio = worldRatio;		
		this.init();		
	}
	
	protected void init() {		
		this.sprite.setScale(this.scale);
		this.initBody();
	}
	
	protected abstract void initBody();
	
	@Override
	public void render(float delta) {
		if (this.sprite != null && this.body != null) {
			this.position.x = this.body.getPosition().x;
			this.position.y = this.body.getPosition().y;
			this.sprite.setPosition(this.body.getPosition().x * this.worldRatio, this.body.getPosition().y * this.worldRatio);
			this.sprite.setRotation(-1.0f * ccMacros.CC_RADIANS_TO_DEGREES(this.body.getAngle()));
		}
	}
	
	public void contact() {
	}
}
