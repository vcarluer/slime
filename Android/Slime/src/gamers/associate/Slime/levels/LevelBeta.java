package gamers.associate.Slime.levels;

import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.GoalPortal;

public class LevelBeta extends LevelTimeAttack {
	@Override
	public void buildLevel(Level level) {
		// float width = 950;
		float width = 2950;
		level.setLevelSize(
				width,
				width * LevelUtil.getHeightRatio());
		
		LevelUtil.createGroundBox(level);
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());
				
		// Platform
		/*float goalPlatW = 100f;
		float goalPlatH = 100f;		
		SlimeFactory.Platform.create(goalPlatW / 2, goalPlatH / 2, goalPlatW, goalPlatH);*/
		
		// Lava
		/*SlimeFactory.Lava.create(200, 25, 200, 50);
		SlimeFactory.Platform.create(325, 50, 50, 100);
		SlimeFactory.Lava.create(400, 25, 100, 50);
		SlimeFactory.Bumper.create(375, 75, 50, 50);*/
		
		float cX = 0;
		float cY = 0;
		// Line 0
		SlimeFactory.Platform.createBL(cX, cY, level.getLevelWidth(), 200);
		cY+=200;
		
		cX += 100;
		
		// Line 1
		
		SlimeFactory.Platform.createBL(cX, cY, 200, 150);
		// SpawnCannon spawnCannon = SlimeFactory.Cannon.create(200 - SpawnCannon.Default_Width / 2, 150 + SpawnCannon.Default_Height / 2);
		level.setStartItem(SlimeFactory.Slimy.createJump(cX + 150, cY + 500, 1.0f));
		SlimeFactory.Slimy.createJump(cX + 200, cY + 550, 1.0f);
		cX += 200;
		// SlimeFactory.Lava.createBL(cX, cY, 200, 50);
		SlimeFactory.Platform.createBL(cX, cY, 100, 50);		
		cX += 100;
		SlimeFactory.Platform.createBL(cX, cY, 50, 100);
		cX += 50;		
		SlimeFactory.Platform.createBL(cX, cY, 50, 50);
		SlimeFactory.Bumper.createBL(cX, cY + 50, 50, 50);
		cX += 50;
		SlimeFactory.Lava.createBL(cX, cY, 50, 50);
		cX += 50;
		SlimeFactory.Platform.createBL(cX, cY, 200, 80);
		cX += 70;
		SlimeFactory.Box.createBL(cX, cY + 80, 40, 40);		
		SlimeFactory.Box.createBL(cX, cY + 120, 40, 40);
		cX += 50;
		/*CGPoint[] vertices = new CGPoint[4];
		vertices[0] = CGPoint.make(0, 50);
		vertices[1] = CGPoint.make(0, 0);
		vertices[2] = CGPoint.make(50, 50);
		vertices[3] = CGPoint.make(50, 0);
		
		CGPoint[] body = new CGPoint[4];
		body[0] = CGPoint.make(0, 50);
		body[1] = CGPoint.make(0, 0);
		body[2] = CGPoint.make(50, 0);
		body[3] = CGPoint.make(50, 50);*/
		
		CGPoint[] vertices = new CGPoint[9];
		vertices[0] = CGPoint.make(0, 0);
		vertices[1] = CGPoint.make(20, 40);
		vertices[2] = CGPoint.make(20, 0);
		vertices[3] = CGPoint.make(50, 50);
		vertices[4] = CGPoint.make(50, 0);
		vertices[5] = CGPoint.make(70, 35);
		vertices[6] = CGPoint.make(70, 0);
		vertices[7] = CGPoint.make(77, 20);
		vertices[8] = CGPoint.make(80, 0);		
		
		CGPoint[] body = new CGPoint[6];
		body[0] = CGPoint.make(0, 0);
		body[1] = CGPoint.make(80, 0);
		body[2] = CGPoint.make(77, 20);
		body[3] = CGPoint.make(70, 35);
		body[4] = CGPoint.make(50, 50);
		body[5] = CGPoint.make(20, 40);
		
		SlimeFactory.Polygon.create(cX, cY + 80, 0, 0, true, body, vertices);
		cX += 80;
		SlimeFactory.Lava.createBL(cX, cY, 200, 50);
		cX += 200;
		SlimeFactory.Platform.createBL(cX, cY, 100, 50);
		cX += 40;
		SlimeFactory.Bumper.createBL(cX, cY + 50, 50, 50, 0.8f).setAngle(-90);
		//Bumper bumper = SlimeFactory.Bumper.createBL(cX, 50, 100, 100);		
		cX += 50;
		SlimeFactory.Platform.createBL(cX, cY + 50, 10, 128);
		
		// Line 2
		cX = 400;
		cY += 250;
		SlimeFactory.Platform.createBL(cX, cY, 256, 20);
		SlimeFactory.Box.createBL(cX, cY + 20, 10, 80);
		cX += 28;
		GoalPortal goalPortal = SlimeFactory.GoalPortal.create(cX + 40, cY + 90);
		cX += 100;				
		SlimeFactory.Platform.createBL(cX, cY + 20, 20, 128);
		cX += 64;
		// Goal
		/*GoalPortal goalPortal = SlimeFactory.GoalPortal.create(cX, cY + 40);
		level.setGoalPortal(goalPortal);*/
		cX += 108;
		cY += 150;
		//Line 3
		/*cX = 0;
		cY = 398;
		SlimeFactory.Platform.createBL(cX, cY, 256, 20);
		cX += 256;		
		SlimeFactory.Box.createBL(cX, cY + 20, 60, 60);
		SlimeFactory.Platform.createBL(cX, cY, 128, 20);*/
				
		// Chain
		/*Platform platform = SlimeFactory.Platform.create(cX, cY - 5, 30, 10);
		cY -= 10;
		Box.setChainMode(true);
		CGSize segSize = CGSize.make(3, 15);
		RevoluteJointDef joint = new RevoluteJointDef();
		Body link = platform.getBody();
		float count = 10;
		for(int i = 0; i < count; i++) {
			Box box = SlimeFactory.Box.create(cX, cY - (i + 1) * (segSize.height) + segSize.height / 2, segSize.width, segSize.height);			
			Vector2 linkPoint = new Vector2();
			linkPoint.x = box.getBody().getPosition().x;
			linkPoint.y = box.getBody().getPosition().y + (segSize.height / 2 / level.getWorlRatio());
			joint.initialize(link, box.getBody(), linkPoint);
			level.getWorld().createJoint(joint);
			link = box.getBody();
		}
		
		Box.setChainMode(false);
		
		float boxSize = 20f;
		Box box = SlimeFactory.Box.create(cX, cY - (count * segSize.height) - boxSize / 2, boxSize, boxSize);
		Vector2 linkPoint = new Vector2();
		linkPoint.x = box.getBody().getPosition().x;
		linkPoint.y = box.getBody().getPosition().y + (boxSize / 2 / level.getWorlRatio());
		joint.initialize(link, box.getBody(), linkPoint);
		level.getWorld().createJoint(joint);
		
		box.getBody().applyLinearImpulse(new Vector2(5.0f, 0f), box.getBody().getPosition());
		
		cY += 10;		
		// end chain
		*/
		cX += 128;				
		SlimeFactory.Platform.createBL(cX, cY, 256, 128);		
		cX += 256;
		SlimeFactory.Platform.createBL(cX - 20, cY + 128, 20, 128);				
		
		// Spawn cannon
		// level.setSpawnCannon(spawnCannon);
	}

	@Override
	protected void initLevel() {
		this.setId("Beta");		
	}

	@Override
	public int getLevelCriticTime() {
		return 10;
	}

	@Override
	public int getLevelTime() {
		return 20;
	}

}