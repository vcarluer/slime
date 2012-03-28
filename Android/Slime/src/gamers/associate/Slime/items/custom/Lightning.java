package gamers.associate.Slime.items.custom;

import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.IElectrificable;

public class Lightning extends GameItem {
	private static final int COUNT = 4;
	private static final String FRAME = "lightning";
	private static final String PLIST = "items";
	public static float DefaultLife = 0.5f;
	private CGPoint tmp;
	private CGPoint ref;
	
	private static float LigthningHeightRatio = 32f / 171f;
	private GameItem sprite;
	private float life;
	
	public Lightning(float x, float y, float width, float height) {
		super(x, y, width, height);
	}	

	public Lightning(CGPoint source, IElectrificable target) {
		this(source, target, DefaultLife);
	}
	
	public Lightning(CGPoint source, IElectrificable target, float life) {
		this(0, 0, 0, 0);
		this.life = life;
		this.ref = CGPoint.make(1, 0);		
		this.tmp = new CGPoint();
		
		CGPoint pos = CGPoint.ccpMidpoint(source, target.getPosition());
		this.tmp.x = target.getPosition().x - source.x;
		this.tmp.y = target.getPosition().y - source.y;
		float angle = CGPoint.ccpAngleSigned(this.ref, this.tmp);
		float newAngle = - ccMacros.CC_RADIANS_TO_DEGREES(angle);			
		float length = CGPoint.ccpDistance(source, target.getPosition());
		this.sprite = SlimeFactory.Sprite.create("lg", pos.x, pos.y, length, length * LigthningHeightRatio, PLIST, FRAME, COUNT);
		this.sprite.setAngle(newAngle);
		
		Level.currentLevel.addItemToAdd(this);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		this.life -= delta;
		if (this.life <= 0) {
			Level.currentLevel.addItemToRemove(this.sprite);
			Level.currentLevel.addItemToRemove(this);
		}
	}
}
