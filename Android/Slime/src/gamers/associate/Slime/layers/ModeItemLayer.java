package gamers.associate.Slime.layers;

import gamers.associate.Slime.R;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.Sounds;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;

import android.view.MotionEvent;

public abstract class ModeItemLayer extends CCLayer {
	protected static final int HEIGHT = 533;
	protected static final int WIDTH = 215;
	
	private float width;
	private float height;
	protected float screenScaleX;
	protected float screenScaleY;
	
	private float padding;
	protected float labelY;
	protected float labelX;

	public ModeItemLayer() {
		CCSprite canvas = CCSprite.sprite(this.getBackgroundPath());
		this.padding = 50f * SlimeFactory.SGSDensity;
		this.screenScaleX = (CCDirector.sharedDirector().winSize().width / 2f) / WIDTH;
		this.screenScaleY = (CCDirector.sharedDirector().winSize().height) / HEIGHT;
		float scaleX = ((CCDirector.sharedDirector().winSize().width / 2f) - this.padding * 2) / WIDTH;
//		float scaleY = (CCDirector.sharedDirector().winSize().height - this.padding * 2) / HEIGHT;
		float scaleY = scaleX;
		canvas.setAnchorPoint(0, 0);
		canvas.setScaleX(scaleX);
		canvas.setScaleY(scaleY);
		this.width = WIDTH * scaleX;
		this.height = HEIGHT * scaleY;
		canvas.setPosition(padding, padding);
		
		this.addChild(canvas);
		CCLabel label = SlimeFactory.getLabel(this.getTitle());
		this.labelY = HEIGHT * this.screenScaleY / 2f;
		this.labelX = WIDTH * this.screenScaleX / 2f;
		label.setPosition(this.labelX, this.labelY);
		this.addChild(label);
		this.setIsTouchEnabled(true);
	}

	protected abstract String getTitle();

	protected abstract String getBackgroundPath();

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		float x = event.getX();
		float y = CCDirector.sharedDirector().winSize().getHeight() - event.getY();
		float parentX = this.getParent().getPosition().x;
		float parentY = this.getParent().getPosition().y;
		float realX = this.getPosition().x + parentX + this.width / 2f + this.padding;
		float realY = this.getPosition().y + parentY + this.height / 2f + this.padding;
		boolean inrectX = x < realX + this.width / 2f && x > realX - this.height / 2f;
		boolean inrectY = y < realY + this.width / 2F && y > realY - this.height / 2f;
		if (inrectX && inrectY) {
			this.select();
			return true;
		} else {
			return false;
		}
	}

	private void select() {
		Sounds.playEffect(R.raw.menuselect);
		CCScene transition = this.getTransition();
		CCDirector.sharedDirector().replaceScene(transition);
	}

	protected abstract CCScene getTransition();
}
