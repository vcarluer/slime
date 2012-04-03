package gamers.associate.Slime.items.custom;

import org.cocos2d.config.ccMacros;
import org.cocos2d.types.CGPoint;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.IElectrificable;

public class Lightning extends GameItem {
	public static float DefaultLife = 0.5f;
	public static final float InfiniteLife = -666f;
	private static final int COUNT = 4;
	private static final String FRAME = "lightning";
	private static final String PLIST = "items";
	private static final float kCGPointEpsilon = 0.00000012f;
	private CGPoint tmp;
	private CGPoint ref;
	private CGPoint tmpDist;
	
	private static float LigthningHeightRatio = 32f / 171f;
	private GameItem sprite;
	private float life;
	
	private GameItem source;
	private IElectrificable target;
	
	private boolean isEnded;
	
	public Lightning(float x, float y, float width, float height) {
		super(x, y, width, height);
	}	

	public Lightning(GameItem source, IElectrificable target) {
		this(source, target, DefaultLife);
	}
	
	public Lightning(GameItem source, IElectrificable target, float life) {
		this(0, 0, 0, 0);
		this.source = source;
		this.target = target;
		this.life = life;
		this.ref = CGPoint.make(1, 0);
		float ln = CGPoint.ccpLength(this.ref);
		this.ref.x = this.ref.x /  ln;
		this.ref.y = this.ref.y /  ln;
		
		this.tmp = CGPoint.zero();
		this.tmpDist = new CGPoint();
		
		Level.currentLevel.addItemToAdd(this);
	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (this.life != InfiniteLife) {
			this.life -= delta;
		}
		
		if (this.life != InfiniteLife && this.life <= 0) {
			this.isEnded = true;
			if (this.sprite != null) {				
				Level.currentLevel.addItemToRemove(this.sprite);
			}
			
			Level.currentLevel.addItemToRemove(this);
		} else {
			CGPoint sourcePos = this.source.getPosition();
			CGPoint targetPos = this.target.getPosition();			
			// Get mid point (optimized)
			this.position.x = (sourcePos.x + targetPos.x) * 0.5f;
			this.position.y = (sourcePos.y + targetPos.y) * 0.5f;
			// end mid point
			
			// calculate angle (optimized)						
			this.tmp.x = targetPos.x - sourcePos.x;
			this.tmp.y = targetPos.y - sourcePos.y;
			float ln = CGPoint.ccpLength(this.tmp);
	        this.tmp.x = this.tmp.x / ln;
	        this.tmp.y = this.tmp.y / ln;
	        
	        float dot = this.ref.x * this.tmp.x + this.ref.y * this.tmp.y;	        
	        float angle = (float)Math.atan2(this.ref.x * this.tmp.y - this.ref.y * this.tmp.x, dot);
	        if( Math.abs(angle) < kCGPointEpsilon ) angle = 0.f;		
	        // end calculate angle
	        
			float newAngle = - ccMacros.CC_RADIANS_TO_DEGREES(angle);
			this.tmpDist.x = sourcePos.x - targetPos.x;
			this.tmpDist.y = sourcePos.y - targetPos.y;			
			float length = CGPoint.ccpLength(this.tmpDist);
			
			float size = length * LigthningHeightRatio;
			
			if (this.sprite == null) {
				this.sprite = SlimeFactory.Sprite.create("lg", this.position.x, this.position.y, length, size, PLIST, FRAME, COUNT);
			} else {
				this.sprite.setPosition(this.position);
				this.sprite.setSize(length, size);
			}
			
			this.sprite.setAngle(newAngle);
		}
	}

	public boolean isEnded() {
		return isEnded;
	}

	public void setEnded(boolean isEnded) {
		this.isEnded = isEnded;
	}
}
