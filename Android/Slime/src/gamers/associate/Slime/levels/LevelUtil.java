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
		float x = level.getLevelOrigin().x;
		float y = level.getLevelOrigin().y;
		
		// up
		SlimeFactory.LevelEnd.create(w2 + x, h + m + y, w, si);
		//right
		SlimeFactory.LevelEnd.create(w + m + x, h2 + y, si, h);
		// bottom
		SlimeFactory.LevelEnd.create(w2 + x, -m + y, w, si);
		// left
		SlimeFactory.LevelEnd.create(-m + x, h2 + y, si, h);			
	}
	
	public static void createGroundBoxGlass(Level level) {					
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		// Creating Level end box which destroys physic objects when then contact it			
		float sw = CCDirector.sharedDirector().displaySize().width;
		float sh = CCDirector.sharedDirector().displaySize().height;
		float siW = CCDirector.sharedDirector().displaySize().width / 2;
		float siH = CCDirector.sharedDirector().displaySize().height / 2;
		float mW = siW / 2;
		float mH = siH / 2;
		float w = s.width;
		float w2 = s.width / 2;
		float h = s.height;
		float h2 = s.height / 2;			
		float x = level.getLevelOrigin().x;
		float y = level.getLevelOrigin().y;
		
		// up
		SlimeFactory.Platform.createWallBL("GBG", -siW + x, h + y, w + sw, siH);
		//right
		SlimeFactory.Platform.createWallBL("GBG", w + x, y, siW, h);
		// bottom
		SlimeFactory.Platform.createWallBL("GBG", -siW + x, -siH + y, w + sw, siH);
		// left
		SlimeFactory.Platform.createWallBL("GBG", -siW + x, y, siW, h);
				
		level.setLevelOrigin(level.getLevelOrigin().x - siW, level.getLevelOrigin().y - siH);
		level.setLevelSize(w + sw, h + sh);
	}
	
	public static void createLand(Level level) {
		CGSize s = CGSize.make(level.getLevelWidth(), level.getLevelHeight());		
		
		SlimeFactory.Platform.create(s.width / 2 + level.getLevelOrigin().x, LAND_HEIGHT / 2 + level.getLevelOrigin().y, s.width, LAND_HEIGHT);
	}
	
	public static float getHeightRatio() {
		return CCDirector.sharedDirector().winSize().height / CCDirector.sharedDirector().winSize().width;
	}
	
	public static float getWidthRatio() {
		return CCDirector.sharedDirector().winSize().width / CCDirector.sharedDirector().winSize().height;
	}
}
