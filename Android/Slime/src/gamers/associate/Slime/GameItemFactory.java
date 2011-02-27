package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSpriteSheet;

public abstract class GameItemFactory<T extends GameItem> extends ItemFactoryBase<T> {
	
	public void attach(CCNode attachNode, CCSpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		this.attach(attachNode);
	}
	
	public void attach(CCNode attachNode) {
		this.rootNode = attachNode;		
		this.initAnimation();
		this.rootNode.addChild(this.spriteSheet);
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			// true here?
			this.rootNode.removeChild(spriteSheet, true);
			this.rootNode = null;			
			this.isAttached = false;
		}
	}
}
