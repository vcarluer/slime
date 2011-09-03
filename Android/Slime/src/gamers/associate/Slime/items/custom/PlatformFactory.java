package gamers.associate.Slime.items.custom;

import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
	
	private int currentType = Platform.Sticky;
	
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Sticky, 1);		
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Bump, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_NoSticky, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Icy, 1);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Platform instantiate(float x, float y, float width, float height) {
		return new Platform(x, y, width, height, this.world, this.worldRatio, this.currentType);
	}	

	@Override
	protected void runFirstAnimations(Platform item) {	
	}
	
	@Override
	public Platform createBL(float x, float y, float width, float height) {		
		return this.create(x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	private Platform create(float x, float y, float width, float height, int platformType) {		
		this.currentType = platformType;
		Platform platform = this.create(x, y, width, height);		
		return platform;
	}
		
	public Platform createStickyBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	public Platform createBumpBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height, Platform.Bump);
	}
	
	public Platform createNoStickyBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height, Platform.NoSticky);
	}
	
	public Platform createIcyBL(float x, float y, float width, float height) {
		return this.create(x + width / 2, y + height / 2, width, height, Platform.Icy);
	}
	
	public void setCurrentType(int platformType) {
		this.currentType = platformType;
	}
}