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
	private static final int HEIGHT = 533;
	private static final int WIDTH = 215;

	public ModeItemLayer() {
		CCSprite canvas = CCSprite.sprite(this.getBackgroundPath());
		float scaleX = (CCDirector.sharedDirector().winSize().width / 2f) / WIDTH;
		float scaleY = CCDirector.sharedDirector().winSize().height / HEIGHT;
		canvas.setAnchorPoint(0, 0);
		canvas.setScaleX(scaleX);
		canvas.setScaleY(scaleY);
		this.addChild(canvas);
		CCLabel label = SlimeFactory.getLabel(this.getTitle());
		label.setPosition(WIDTH * scaleX / 2f, HEIGHT * scaleY / 2f);
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
		float realX = this.getPosition().x + parentX;
		float realY = this.getPosition().y + parentY;
		boolean inrectX = x < realX + WIDTH / 2f && x > realX - HEIGHT / 2f;
		boolean inrectY = y < realY + WIDTH / 2F && y > realY - HEIGHT / 2f;
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
