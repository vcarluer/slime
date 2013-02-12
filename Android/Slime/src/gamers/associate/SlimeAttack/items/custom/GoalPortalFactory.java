package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;


public class GoalPortalFactory extends GameItemPhysicFactory<GoalPortal> {
	@Override
	protected void createAnimList() {
		this.createAnim(GoalPortal.Anim_Goal_Portal, 8);		
	}

	@Override
	protected String getPlistPng() {
		return "tank";
	}

	@Override
	protected GoalPortal instantiate(float x, float y, float width, float height) {
		return new GoalPortal(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(GoalPortal item) {
		item.createPortal();
	}
}
