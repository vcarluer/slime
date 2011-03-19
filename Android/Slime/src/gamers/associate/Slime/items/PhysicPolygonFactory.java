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
		
		if (polygon != null) {
			//CCSpritePolygon sprite = CCSpritePolygon.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(PhysicPolygon.texture));
			CCSpritePolygon sprite = CCSpritePolygon.sprite(PhysicPolygon.texture, width, height);
			sprite.setVertices(glVertices);
			polygon.setSprite(sprite);
		}
		
		return polygon;				
	}
	
	@Override
	protected void preInit(PhysicPolygon item) {
		item.initPoly(this.isDynamic, this.bodyPoints, this.glVertices);
		this.isDynamic = false;
		this.bodyPoints = null;
		this.glVertices = null;
	}
	
	@Override
	protected void createAnimList() {
	}

	@Override
	protected String getPlistPng() {
		return "labo"; 
	}

	@Override
	protected PhysicPolygon instantiate(float x, float y, float width,
			float height) {
		return new PhysicPolygon(this.rootNode, x, y, width, height, this.world, this.worldRatio);
	}

	@Override
	protected void runFirstAnimations(PhysicPolygon item) {		
	}

}
