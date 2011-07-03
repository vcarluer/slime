package gamers.associate.Slime.layers;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;

public class EndLevelLayer extends CCLayer {
	private CCLabel textLabel;
	
	public EndLevelLayer() {
		this.textLabel = CCLabel.makeLabel("EMPTY", "fonts/Slime.ttf", 60.0f);
		this.textLabel.setPosition(CCDirector.sharedDirector().winSize().width / 2, CCDirector.sharedDirector().winSize().height / 2);
		this.addChild(this.textLabel);
	}
	
	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onEnter()
	 */
	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		super.onEnter();
	}

	/* (non-Javadoc)
	 * @see org.cocos2d.layers.CCLayer#onExit()
	 */
	@Override
	public void onExit() {
		// TODO Auto-generated method stub
		super.onExit();
	}
	
	public void setText(String text) {
		this.textLabel.setString(text.toUpperCase());
	}
	
	public CCLabel getLabel() {
		return this.textLabel;
	}
}
