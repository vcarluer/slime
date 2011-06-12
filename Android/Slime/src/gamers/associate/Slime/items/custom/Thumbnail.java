package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;
import gamers.associate.Slime.items.base.ISelectable;
import gamers.associate.Slime.items.base.SpriteType;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

public class Thumbnail extends GameItemCocos implements ISelectable {	
	
	private ISelectable target;	
	private boolean isSelected;
	
	public Thumbnail(float x, float y, float width, float height, ISelectable target) {
		super(x, y, width, height);
		// this.spriteType = SpriteType.UNKNOWN;
		this.spriteType = SpriteType.SINGLE_SCALE;
		this.target = target;
		this.setRootNode(this.target.getRootNode());
		this.setSprite(this.target.getThumbail());		
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
}