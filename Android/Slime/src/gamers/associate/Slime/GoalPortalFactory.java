package gamers.associate.Slime;


public class GoalPortalFactory extends GameItemPhysicFactory<GoalPortal> {
	@Override
	protected void createAnimList() {
		this.createAnim(GoalPortal.Anim_Goal_Portal, 4);		
	}

	@Override
	protected String getPlist() {		
		return "labo.plist";
	}

	@Override
	protected String getPng() {		
		return "labo.png";
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
