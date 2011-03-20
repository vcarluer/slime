package gamers.associate.Slime.items;

import org.cocos2d.types.CGPoint;

public class PhysicPolygonFactory extends GameItemPhysicFactory<PhysicPolygon> {
	// Class Not thread safe due to this fields
	private boolean isDynamic;
	private CGPoint[] bodyPoints;
	private CGPoint[] glVertices;
	
	public PhysicPolygon create(float x, float y, float width, float height, boolean isDynamic, CGPoint[] bodyPoints, CGPoint[] glVertices) {		
		this.isDynamic = isDynamic;
		this.bodyPoints = bodyPoints;
		this.glVertices = glVertices;
		
		PhysicPolygon polygon = super.create(x, y, width, height);
		
		return polygon;
	}
	
	@Override
	protected void preInit(PhysicPolygon item) {
		super.preInit(item);
		item.initPoly(this.isDynamic, this.bodyPoints, this.glVertices);
		this.isDynamic = false;
		this.bodyPoints = null;
		this.glVertices = null;
	}
	
	@Override
	protected void createAnimList() {
		TextureAnimation.createFramesFromFiles(PhysicPolygon.Anim_Base, 1);		
	}

	@Override
	protected String getPlistPng() {
		return ""; 
	}

	@Override
	protected PhysicPolygon instantiate(float x, float y, float width,
			float height) {
		return new PhysicPolygon(x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(PhysicPolygon item) {		
	}

}
