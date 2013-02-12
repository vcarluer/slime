package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;
import gamers.associate.SlimeAttack.items.base.TextureAnimation;

public class PlatformFactory extends GameItemPhysicFactory<Platform>{
	
	private int currentType = Platform.Sticky;
	private boolean currentMove = false;
	
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Sticky, 1);		
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Bump, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_NoSticky, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Icy, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Wall, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Corner, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_T, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_Cross, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_End, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_NoSticky_Corner, 1);
		TextureAnimation.createFramesFromFiles(Platform.Anim_Base_NoSticky_End, 1);
	}

	@Override
	protected String getPlistPng() {
		return "";
	}

	@Override
	protected Platform instantiate(float x, float y, float width, float height) {
		return new Platform(x, y, width, height, this.world, this.worldRatio, this.currentType, this.currentMove);
	}	

	@Override
	protected void runFirstAnimations(Platform item) {	
		item.animate();
	}
	
	@Override
	public Platform createBL(String name, float x, float y, float width, float height) {		
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	public Platform create(String name, float x, float y, float width, float height, int platformType) {		
		this.currentType = platformType;
		this.currentMove = false;
		Platform platform = this.create(name, x, y, width, height);		
		return platform;
	}
	
	public Platform create(float x, float y, float width, float height, int platformType) {
		return this.create(null, x, y, width, height, platformType);		
	}
		
	public Platform createStickyBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	public Platform createBumpBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Bump);
	}
	
	public Platform createNoStickyBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.NoSticky);
	}
	
	public Platform createIcyBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Icy);
	}
	
	public Platform createWallBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Wall);
	}
	
	public Platform createCornerBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Corner);
	}
	
	public Platform createTBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.T);
	}
	
	public Platform createCrossBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.Cross);
	}
	
	public Platform createEndBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.End);
	}
	
	public Platform createNoStickyCornerBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.NoStickyCorner);
	}
	
	public Platform createNoStickyEndBL(String name, float x, float y, float width, float height) {
		return this.create(name, x + width / 2, y + height / 2, width, height, Platform.NoStickyEnd);
	}
	
	public void setCurrentType(int platformType) {
		this.currentType = platformType;
	}
	
	//add moving parameter
	
	public Platform createMoveBL(float x, float y, float width, float height) {		
		return this.createMove(x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	private Platform createMove(float x, float y, float width, float height, int platformType) {		
		this.currentType = platformType;
		this.currentMove = true;
		Platform platform = this.create(x, y, width, height);		
		return platform;
	}
		
	public Platform createMoveStickyBL(float x, float y, float width, float height) {
		return this.createMove(x + width / 2, y + height / 2, width, height, Platform.Sticky);
	}
	
	public Platform createMoveBumpBL(float x, float y, float width, float height) {
		return this.createMove(x + width / 2, y + height / 2, width, height, Platform.Bump);
	}
	
	public Platform createMoveNoStickyBL(float x, float y, float width, float height) {
		return this.createMove(x + width / 2, y + height / 2, width, height, Platform.NoSticky);
	}
	
	public Platform createMoveIcyBL(float x, float y, float width, float height) {
		return this.createMove(x + width / 2, y + height / 2, width, height, Platform.Icy);
	}
	
}