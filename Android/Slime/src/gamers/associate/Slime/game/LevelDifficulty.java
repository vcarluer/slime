package gamers.associate.Slime.game;

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
	
	public static String getText(int diff) {
		switch (diff) {
		case Easy: return "Easy";
		case Normal: return "Normal";
		case Hard: return "Hard";
		case Extrem: return "Extrem";
		default: return "Easy";
		}
	}
}
