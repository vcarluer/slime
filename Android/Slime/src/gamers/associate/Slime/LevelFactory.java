package gamers.associate.Slime;


/**
 * @uml.dependency   supplier="gamers.associate.Slime.HardCodedLevel"
 */
public class LevelFactory {
	private static Level currentLevel;
	
	public static Level GetLevel(String levelName) {
		if (currentLevel == null) {
			currentLevel = new HardCodedLevel();
		}
		
		return currentLevel;
	}
}
