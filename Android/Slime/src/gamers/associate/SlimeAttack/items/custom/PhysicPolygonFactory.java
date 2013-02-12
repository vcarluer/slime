package gamers.associate.SlimeAttack.items.custom;

import gamers.associate.SlimeAttack.items.base.GameItemPhysicFactory;
import gamers.associate.SlimeAttack.items.base.TextureAnimation;

import org.cocos2d.types.CGPoint;

public class PhysicPolygonFactory extends GameItemPhysicFactory<PhysicPolygon> {
	// Class Not thread safe due to this fields
	private boolean isDynamic;
	private CGPoint[] bodyPoints;
	private int currentType;
	private boolean isStickable;
	
	public PhysicPolygon create(float x, float y, float width, float height, boolean isDynamic, CGPoint[] bodyPoints, int type, boolean isStickable) {
		return this.create(null, x, y, width, height, isDynamic, bodyPoints, type, isStickable);
	}
	
	public PhysicPolygon create(String name, float x, float y, float width, float height, boolean isDynamic, CGPoint[] bodyPoints, int type, boolean isStickable) {		
		this.isDynamic = isDynamic;		
		this.bodyPoints = bodyPoints;
		this.currentType = type;
		this.isStickable = isStickable;
		
		PhysicPolygon polygon = super.create(name, x, y, width, height);
		
		return polygon;
	}
	
	@Override
	protected void preInit(PhysicPolygon item) {
		super.preInit(item);
		item.initPoly(this.isDynamic, this.bodyPoints);
		this.isDynamic = false;
		this.bodyPoints = null;
	}
	
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(PhysicPolygon.Anim_Base_Fill, 1);
		this.createAnim(PhysicPolygon.Anim_Base_Fill, 1);
		TextureAnimation.createFramesFromFiles(PhysicPolygon.Anim_Base_Empty, 1);
	}

	@Override
	protected String getPlistPng() {
		return ""; 
	}

	@Override
	protected PhysicPolygon instantiate(float x, float y, float width,
			float height) {
		return new PhysicPolygon(x, y, width, height, this.world, this.worldRatio, this.currentType, this.isStickable);
	}

	@Override
	protected void runFirstAnimations(PhysicPolygon item) {		
	}

}
