package gamers.associate.Slime.items;



/**
 * @uml.dependency   supplier="gamers.associate.Slime.Slimy"
 */
public class SlimyFactory extends GameItemPhysicFactory<Slimy> {	
		
	@Override
	protected void createAnimList() {
		this.createAnim(Slimy.Anim_Burned_Wait, 2);
		this.createAnim(Slimy.Anim_Burning, 5);
		this.createAnim(Slimy.Anim_Falling, 3);
		this.createAnim(Slimy.Anim_Landing_H, 3);
		this.createAnim(Slimy.Anim_Landing_V, 3);		
		this.createAnim(Slimy.Anim_Wait_H, 5);
		this.createAnim(Slimy.Anim_Wait_V, 5);
	}
	
	@Override
	protected void runFirstAnimations(Slimy item) {
		item.waitAnim();
		item.fadeIn();
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}	

	@Override
	protected Slimy instantiate(float x, float y, float width, float height) {		
		return new Slimy(this.spriteSheet, x, y, width, height, this.world, this.worldRatio);
	}
	
	public Slimy create(float x, float y, float ratio) {		
		Slimy slimy = this.create(x, y, Slimy.Default_Width * ratio, Slimy.Default_Height * ratio);		
		return slimy;
	}
}
