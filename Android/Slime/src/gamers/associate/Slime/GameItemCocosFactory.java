package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

public abstract class GameItemCocosFactory<T extends GameItemCocos> extends ItemFactoryBase<T> {
		
	public void attach(Level level, CCNode attachNode) {
		this.level = level;
		this.rootNode = attachNode;		
		this.initAnimation();		
		this.rootNode.addChild(this.spriteSheet);
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			this.level = null;			
			this.rootNode.removeChild(spriteSheet, true);
			this.rootNode = null;			
			this.isAttached = false;
		}
	}
}
