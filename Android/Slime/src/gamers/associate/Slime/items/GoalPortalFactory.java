package gamers.associate.Slime.items;


public class GoalPortalFactory extends GameItemPhysicFactory<GoalPortal> {
	@Override
	protected void createAnimList() {
		this.createAnim(GoalPortal.Anim_Goal_Portal, 4);		
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected GoalPortal instantiate(float x, float y, float width, float height) {
		return new GoalPortal(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(GoalPortal item) {
		item.createPortal();
	}
}
