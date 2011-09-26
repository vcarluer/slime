package gamers.associate.Slime.items.custom;

import java.util.ArrayList;
import java.util.Arrays;

import gamers.associate.Slime.game.Triangulate;
import gamers.associate.Slime.items.base.GameItemPhysicFactory;
import gamers.associate.Slime.items.base.TextureAnimation;

import org.cocos2d.types.CGPoint;

public class PhysicPolygonFactory extends GameItemPhysicFactory<PhysicPolygon> {
	// Class Not thread safe due to this fields
	private boolean isDynamic;
	private CGPoint[] bodyPoints;
	
	public PhysicPolygon create(float x, float y, float width, float height, boolean isDynamic, CGPoint[] bodyPoints) {		
		this.isDynamic = isDynamic;
		this.bodyPoints = bodyPoints;				
		
		PhysicPolygon polygon = super.create(x, y, width, height);
		
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
		TextureAnimation.createFramesFromFiles(PhysicPolygon.Anim_Base, 1);
		this.createAnim(PhysicPolygon.Anim_Base, 1);
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
