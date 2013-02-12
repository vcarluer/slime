package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.base.GameItemCocos;

public class Director extends GameItem {
	private String target;
	private SpriteAction action;
	private boolean isInit;
	
	public Director(float x, float y, float width, float height, String target, SpriteAction action) {
		super(x, y, width, height);
		this.setTarget(target);
		this.setAction(action);
		this.isInit = false;
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (!this.isInit) {
			if (this.getTarget() != null && this.getAction() != null) {
				for(GameItem item : Level.currentLevel.getItemsByName(this.getTarget())) {
					if (item instanceof GameItemCocos) {
						GameItemCocos cocos = (GameItemCocos) item;
						this.getAction().apply(cocos.getSprite());
						// At least one is apply for init
						this.isInit = true;
					}
				}
			}
		}
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public SpriteAction getAction() {
		return action;
	}

	public void setAction(SpriteAction action) {
		this.action = action;
	}
}
