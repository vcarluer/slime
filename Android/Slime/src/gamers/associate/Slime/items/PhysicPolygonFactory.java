package gamers.associate.Slime.items;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrameCache;
import org.cocos2d.types.CGPoint;

public class PhysicPolygonFactory extends GameItemPhysicFactory<PhysicPolygon> {
	
	public PhysicPolygon create(float x, float y, float width, float height, boolean isDynamic, CGPoint[] bodyPoints, CGPoint[] glVertices) {		
		PhysicPolygon polygon = super.create(x, y, width, height);
		polygon.init(isDynamic, bodyPoints, glVertices);
		
		if (polygon != null) {
			//CCSpritePolygon sprite = CCSpritePolygon.sprite(CCSpriteFrameCache.sharedSpriteFrameCache().getSpriteFrame(PhysicPolygon.texture));
			CCSpritePolygon sprite = CCSpritePolygon.sprite(PhysicPolygon.texture, width, height);
			sprite.setVertices(glVertices);
			polygon.setSprite(sprite);
		}
		
		return polygon;				
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
