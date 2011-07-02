package gamers.associate.Slime.levels;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class LevelUtil {
	public static float LAND_HEIGHT = 64f;	
	
	public static void createGroundBox(Level level) {					
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		// Creating Level end box which destroys physic objects when then contact it			
		float si = 10;
		float m = level.getWorlRatio() * 2;
		float w = s.width;
		float w2 = s.width / 2;
		float h = s.height;
		float h2 = s.height / 2;					
		// up
		SlimeFactory.LevelEnd.create(w2, h + m, w, si);
		//right
		SlimeFactory.LevelEnd.create(w + m, h2, si, h);
		// bottom
		SlimeFactory.LevelEnd.create(w2, -m, w, si);
		// left
		SlimeFactory.LevelEnd.create(-m, h2, si, h);			
	}
	
	public static void createLand(Level level) {
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		SlimeFactory.Platform.create(s.width / 2, LAND_HEIGHT / 2, s.width, LAND_HEIGHT);
	}
	
	public static float getHeightRatio() {
		return CCDirector.sharedDirector().winSize().height / CCDirector.sharedDirector().winSize().width;
	}
}
