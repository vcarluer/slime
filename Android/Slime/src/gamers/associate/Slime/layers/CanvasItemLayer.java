package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

import android.view.MotionEvent;

public abstract class CanvasItemLayer extends CCLayer {	
	protected static final int HEIGHT = 533;
	protected static final int WIDTH = 215;
	
	protected float width;
	protected float height;
	
	protected float paddingX;	
	protected float paddingY;
	
	protected float labelY;
	protected float labelX;
	protected CCLabel label;
	protected CCSprite canvas;
	protected float scaleX;
	protected float scaleY;
	
	public CanvasItemLayer(float padding, float numberPerPage, boolean fitInHeight, float referenceHeight) {
		this.paddingX = this.getPadding(padding);		
		this.paddingY = this.paddingX;
		this.scaleX = ((CCDirector.sharedDirector().winSize().width / numberPerPage) - this.paddingX * 2) / WIDTH;
//		float scaleY = (CCDirector.sharedDirector().winSize().height - this.padding * 2) / HEIGHT;
		this.scaleY = scaleX;
		this.width = WIDTH * scaleX;
		this.height = HEIGHT * scaleY;
		
		if (fitInHeight && this.height > referenceHeight) {
			this.scaleY = (referenceHeight - this.paddingY * 2) / HEIGHT;
			this.scaleX = this.scaleY;
			this.width = WIDTH * scaleX;
			this.height = HEIGHT * scaleY;
			float referenceWidth = CCDirector.sharedDirector().winSize().width / numberPerPage;			
			this.paddingX = (referenceWidth - this.width) / 2f;
		}								
		
		this.label = SlimeFactory.getLabel(" ", this.getFontSize());
		this.defineLabelPosition();
		this.label.setPosition(this.labelX, this.labelY);
		this.addChild(this.label);
		this.setIsTouchEnabled(true);
	}

	protected abstract float getFontSize();

	@Override
	public void onEnter() {
		if (this.canvas == null) {
			this.canvas = CCSprite.sprite(this.getBackgroundPath());
			canvas.setAnchorPoint(0, 0);
			canvas.setScaleX(this.scaleX);
			canvas.setScaleY(this.scaleY);			
			canvas.setPosition(paddingX, paddingY);
			
			this.addChild(canvas, Level.zUnder);
		}
		
		this.label.setString(this.getTitle());
		
		super.onEnter();
	}

	protected float getPadding(float paddingBase) {
		return paddingBase * SlimeFactory.SGSDensity;
	}

	protected abstract void defineLabelPosition();

	protected abstract String getTitle();

	protected abstract String getBackgroundPath();

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		float x = event.getX();
		float y = CCDirector.sharedDirector().winSize().getHeight() - event.getY();
		float parentX = this.getParent().getPosition().x;
		float parentY = this.getParent().getPosition().y;
		float realX = this.getPosition().x + parentX + this.width / 2f + this.paddingX;
		float realY = this.getPosition().y + parentY + this.height / 2f + this.paddingY;
		boolean inrectX = x < realX + this.width / 2f && x > realX - this.width / 2f;
		boolean inrectY = y < realY + this.height / 2F && y > realY - this.height / 2f;
		if (inrectX && inrectY) {
			this.select();
			return true;
		} else {
			return false;
		}
	}

	public void select() {
		Sounds.playEffect(R.raw.menuselect);
		CCScene transition = this.getTransition();
		CCDirector.sharedDirector().replaceScene(transition);
	}

	protected abstract CCScene getTransition();
}
