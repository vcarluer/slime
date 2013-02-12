package gamers.associate.SlimeAttack.items.base;

import gamers.associate.SlimeAttack.game.IGameItemHandler;

import org.cocos2d.nodes.CCNode;

public abstract class GameItemCocosFactory<T extends GameItemCocos> extends ItemFactoryBase<T> {
		
	public void attach(IGameItemHandler itemHandler, CCNode attachNode) {
		this.itemHandler = itemHandler;
		this.rootNode = attachNode;		
		this.initAnimation();		
		this.isAttached = true;
	}
	
	public void detach() {
		if (this.isAttached && this.spriteSheet != null && this.rootNode != null) {
			this.itemHandler = null;			
			this.rootNode = null;			
			this.isAttached = false;
		}
	}		
}
