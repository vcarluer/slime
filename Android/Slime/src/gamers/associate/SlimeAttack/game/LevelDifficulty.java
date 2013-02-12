package gamers.associate.SlimeAttack.game;

import org.cocos2d.nodes.CCSprite;

public class LevelDifficulty {
	public final static int Easy = 1;
	public final static int Normal = 2;
	public final static int Hard = 4;
	public final static int Extrem = 8;
	
	public static int LevelsPerDiff = 10;
	
	public static int getNextDifficulty(int diff) {
		switch (diff) {
			case Easy: return Normal;
			case Normal: return Hard;
			case Hard: return Extrem;
			case Extrem: return Extrem;
			default: return Easy;
		}
	}
	
	public static int getPreviousDifficulty(int diff) {
		switch (diff) {
			case Easy: return Easy;
			case Normal: return Easy;
			case Hard: return Normal;
			case Extrem: return Hard;
			default: return Easy;
		}
	}
	
	public static String getText(int diff) {
		switch (diff) {
		case Easy: return "Easy";
		case Normal: return "Normal";
		case Hard: return "Hard";
		case Extrem: return "Extrem";
		default: return "Easy";
		}
	}
	
	public static CCSprite getSpriteBkg(int diff) {		
		return CCSprite.sprite(getSpriteBkgPath(diff));
		
	}
	
	public static String getSpriteBkgPath(int diff) {
		String fileBase = "";
		switch (diff) {
		case LevelDifficulty.Normal:
			fileBase = "moon-postcard";
			break;
		case LevelDifficulty.Hard:
			fileBase = "disco-postcard";
			break;
		case LevelDifficulty.Extrem:
			fileBase = "hawaii-postcard";
			break;
		case LevelDifficulty.Easy:
		default:
			fileBase = "mexico-postcard";
			break;
		}
		
		String fileBg = "bkg/" + fileBase + ".png";
		return fileBg;
		
	}
}
