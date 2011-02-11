package gamers.associate.Slime;


/**
 * @uml.dependency   supplier="gamers.associate.Slime.HardCodedLevel"
 */
public class LevelFactory {
	public static Level GetLevel(String levelName) {
		return new HardCodedLevel();
	}
}
