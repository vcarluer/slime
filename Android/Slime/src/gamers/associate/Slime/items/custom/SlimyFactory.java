package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;

/**
 * @uml.dependency   supplier="gamers.associate.Slime.Slimy"
 */
public class SlimyFactory extends GameItemPhysicFactory<Slimy> {	
		
	public static final int Type_Simple = 0;
	public static final int Type_Grow = 1;
	public static final int Type_Jump = 2;	
	
	private int currentType;
			
	@Override
	protected void createAnimList() {
		this.createAnim(Slimy.Anim_Burned_Wait, 2);
		this.createAnim(Slimy.Anim_Burning, 5);
		this.createAnim(Slimy.Anim_Falling, 3);
		this.createAnim(Slimy.Anim_Landing_H, 3);
		this.createAnim(Slimy.Anim_Landing_V, 3);		
		this.createAnim(Slimy.Anim_Wait_H, 5);
		this.createAnim(Slimy.Anim_Wait_V, 5);
		this.createAnim(Slimy.Anim_Splash, 5);
		this.createAnim(Slimy.Anim_Success, 7);
		this.createAnim(Slimy.Anim_Spawn, 8);
		this.createAnim(Slimy.Anim_Spawn_Falling, 3);
	}
	
	@Override
	protected void runFirstAnimations(Slimy item) {
		/*item.waitAnim();
		item.fadeIn();*/
	}

	@Override
	protected String getPlistPng() {
		return "slime";
	}	

	@Override
	protected Slimy instantiate(float x, float y, float width, float height) {		
		Slimy slimy = null;
		switch(this.currentType) {
			case Type_Simple:
				slimy = new Slimy(x, y, width, height, this.world, this.worldRatio);
				break;
			case Type_Grow:
				slimy = new SlimyGrow(x, y, width, height, this.world, this.worldRatio);
				break;
			case Type_Jump:
				slimy = new SlimyJump(x, y, width, height, this.world, this.worldRatio);
				break;
		}
		
		return slimy;
	}
	
	public Slimy create(float x, float y, float ratio) {
		return this.create(x, y, ratio, Type_Simple);
	}
	
	public SlimyGrow createGrow(float x, float y, float ratio) {
		return (SlimyGrow)this.create(x, y, ratio, Type_Grow);
	}
	
	public SlimyJump createJump(float x, float y, float ratio) {
		return (SlimyJump)this.create(x, y, ratio, Type_Jump);
	}
	
	public Slimy create(float x, float y, float ratio, int slimyType) {		
		this.currentType = slimyType;
		Slimy slimy = this.create(x, y, Slimy.Default_Width * ratio, Slimy.Default_Height * ratio);		
		return slimy;
	}
}
