package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

/**
 * @uml.dependency   supplier="gamers.associate.Slime.HardCodedLevel"
 */
public class LevelFactory {
	public static Level GetLevel(String levelName, CCNode rootNode) {
		return new HardCodedLevel(rootNode);
	}
}
