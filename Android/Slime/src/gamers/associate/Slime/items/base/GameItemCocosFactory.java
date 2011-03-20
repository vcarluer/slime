package gamers.associate.Slime.items.base;

import gamers.associate.Slime.game.Level;

import org.cocos2d.nodes.CCNode;

public abstract class GameItemCocosFactory<T extends GameItemCocos> extends ItemFactoryBase<T> {
		
	public void attach(Level level, CCNode attachNode) {
		this.level = level;
		this.rootNode = attachNode;		
		this.initAnimation();		
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			this.level = null;			
			this.rootNode = null;			
			this.isAttached = false;
		}
	}		
}
