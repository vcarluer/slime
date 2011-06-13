package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.config.ccMacros;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class Thumbnail extends GameItemCocos implements ISelectable {	
	public static String Thumbnail_back = "thumbnail_back";
	
	private ISelectable target;	
	private boolean isSelected;
	private CCSprite targetThumbnail;
	
	public Thumbnail(float x, float y, float width, float height) {
		super(x, y, width, height);
		// this.spriteType = SpriteType.UNKNOWN;
		this.spriteType = SpriteType.SINGLE_SCALE_DIRECT;
		this.zOrder = Level.zFront;
	}
	
	public Thumbnail(float x, float y, float width, float height, ISelectable target) {
		this(x, y, width, height);		
		this.setTarget(target);
	}
	
	public void setTarget(ISelectable target) {
		this.target = target;
		this.targetThumbnail = this.target.getThumbail();
		target.getRootNode().addChild(this.targetThumbnail, Level.zFront);
		this.targetThumbnail.setPosition(this.position);		
	}

	@Override
	public boolean canSelect(CGPoint gameReference) {
		return CGRect.containsPoint(this.getSelectionRect(), gameReference);
	}

	@Override
	public CGRect getSelectionRect() {		
		return CGRect.make(this.position.x - this.width / 2, this.position.y - this.height / 2, this.width, this.height);
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void select() {
		this.isSelected = true;
		
	}

	@Override
	public void select(CGPoint gameReference) {
		this.isSelected = true;
	}

	@Override
	public void selectionMove(CGPoint gameReference) {
		// Do nothing
	}

	@Override
	public void selectionStop(CGPoint gameReference) {
		if (this.isSelected) {
			if (this.target instanceof GameItem) {
				Level.currentLevel.getCameraManager().moveInterpolateTo((GameItem)this.target, 0.3f);
			}
		}
	}

	@Override
	public boolean trySelect(CGPoint gameReference) {		
		if (this.canSelect(gameReference))
		{
			this.select();
		}
		
		return this.isSelected();
	}

	@Override
	public void unselect() {
		this.isSelected = false;
	}

	@Override
	public CCSprite getThumbail() {
		return target.getThumbail();
	}

	@Override
	public CCNode getRootNode() {
		return target.getRootNode();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#setPosition(org.cocos2d.types.CGPoint)
	 */
	@Override
	public void setPosition(CGPoint position) {		
		super.setPosition(position);
		if (this.targetThumbnail != null) {
			this.targetThumbnail.setPosition(position);
		}
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#setAngle(float)
	 */
	@Override
	public void setAngle(float angle) {
		float degreeAngle = -1.0f * ccMacros.CC_RADIANS_TO_DEGREES(angle) + 90;		
		super.setAngle(degreeAngle);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#destroy()
	 */
	@Override
	public void destroy() {		
		super.destroy();
		this.target.getRootNode().removeChild(this.targetThumbnail, true);
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#setScale(float)
	 */
	@Override
	public void setScale(float scale) {
		super.setScale(scale);
		if (this.targetThumbnail != null) {
			this.targetThumbnail.setScale(scale);
		}
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.GameItemCocos#getReferenceAnimationName()
	 */
	@Override
	protected String getReferenceAnimationName() {		
		return Thumbnail.Thumbnail_back;
	}

	@Override
	public boolean isActive() {
		return this.target.isActive();
	}	
}