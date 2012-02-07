package gamers.associate.Slime.items.custom;

import javax.microedition.khronos.opengles.GL10;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.Util;
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
	public static String Thumbnail_back = "thumbnail";
	
	private ISelectable target;	
	private boolean isSelected;
	private CCSprite targetThumbnail;
	private CGRect selectionRect;
	private CGRect scaledRect;	
	
	private static float Default_Selection_Width = 50f;
	private static float Default_Selection_Height = 50f;
	
	public Thumbnail(float x, float y, float width, float height) {
		super(x, y, width, height);
		// this.spriteType = SpriteType.UNKNOWN;
		this.spriteType = SpriteType.SINGLE_SCALE_DIRECT;
		this.zOrder = Level.zTop;
		this.selectionRect = CGRect.make(0, 0, Default_Selection_Width, Default_Selection_Height);		
		this.scaledRect = CGRect.zero();
	}
	
	public Thumbnail(float x, float y, float width, float height, ISelectable target) {
		this(x, y, width, height);		
		this.setTarget(target);
	}
	
	public void setTarget(ISelectable target) {
		this.target = target;
		this.targetThumbnail = this.target.getThumbail();
		this.isActive = !this.target.isThumbnailAwaysOn();
		target.getRootNode().addChild(this.targetThumbnail, Level.zTop);
		this.targetThumbnail.setPosition(this.position);		
	}

	public boolean canSelect(CGPoint gameReference) {
		return this.isActive() && CGRect.containsPoint(this.getSelectionRect(), gameReference);
	}

	public CGRect getSelectionRect() {
		this.selectionRect.origin.x = this.position.x - Default_Selection_Width / 2;
		this.selectionRect.origin.y = this.position.y - Default_Selection_Height / 2;		
		
		Util.getScaledRect(this.selectionRect, this.width, this.height, Level.currentLevel.getCameraManager().getCurrentZoom(), this.scaledRect);		
		return this.scaledRect;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void select() {
		this.isSelected = true;
		
	}

	public void select(CGPoint gameReference) {
		this.isSelected = true;
	}

	public void selectionMove(CGPoint gameReference) {
		// Do nothing
	}

	public void selectionStop(CGPoint gameReference) {
		if (this.isSelected) {
			if (this.target instanceof GameItem) {
				Level.currentLevel.getCameraManager().moveInterpolateTo((GameItem)this.target, 0.3f);
			}
		}
	}

	public boolean trySelect(CGPoint gameReference) {		
		if (this.canSelect(gameReference))
		{
			this.select();
		}
		
		return this.isSelected();
	}

	public void unselect() {
		this.isSelected = false;
	}

	public CCSprite getThumbail() {
		return target.getThumbail();
	}

	public CCNode getRootNode() {
		return target.getRootNode();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#setPosition(org.cocos2d.types.CGPoint)
	 */
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
		return this.isActive || Level.currentLevel.isPaused();
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItem#draw(javax.microedition.khronos.opengles.GL10)
	 */
	@Override
	public void draw(GL10 gl) {		
		super.draw(gl);
		if (Level.DebugMode) {
			if (this.scaledRect != null && this.isActive()) {			
				// Drawing selection rectangle
				Util.draw(gl, this.scaledRect, 1.0f, 0, 0, 1, 1);
			}
		}
	}

	public boolean simpleSelect() {
		this.selectionStop(null);
		return false;
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#pause()
	 */
	@Override
	protected void pause() {
		// no pause
	}

	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.GameItemCocos#resume()
	 */
	@Override
	protected void resume() {
		// no resume
	}

	@Override
	public boolean isThumbnailAwaysOn() {
		return false;
	}	
}