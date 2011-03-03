package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.badlogic.gdx.physics.box2d.World;

public class SpawnCannon extends GameItemPhysicFx {
	public static String texture = "metal.png";
	public static float Default_Width = 24f;
	public static float Default_Height = 26f;
	
	public SpawnCannon(CCNode node, float x, float y, float width, float height,
			World world, float worldRatio) {
		super(node, x, y, width, height, world, worldRatio);
		
		if (width == 0 && this.height == 0) {
			this.width = Default_Width;
			this.height = Default_Height;
			this.transformTexture();
		}
	}
	
	public void placeLeftBottom(float x, float y) {
		float targetX = x + this.width / 2;
		float targetY = y + this.height / 2;
		this.sprite.setPosition(targetX, targetY);
	}
	
	public void spawnSlime(CGPoint target) {
		Slimy slimy = SlimeFactory.Slimy.create(this.position.x, this.position.y, 1.0f);
	}
}
