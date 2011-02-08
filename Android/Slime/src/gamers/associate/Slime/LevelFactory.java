package gamers.associate.Slime;

import org.cocos2d.nodes.CCNode;

public class LevelFactory {
	public static Level GetLevel(String levelName, CCNode rootNode) {
		return new HardCodedLevel(rootNode);
	}
}
