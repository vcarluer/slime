package gamers.associate.Slime.levels;

import java.util.HashMap;

public class LevelDefinitionParserCache {
	private static HashMap<String, LevelDefinitionParser> cache = new HashMap<String, LevelDefinitionParser>();
	
	public static LevelDefinitionParser get(String fileName) {
		if (cache.containsKey(fileName)) {
			return cache.get(fileName);
		}
		else {
			LevelDefinitionParser levelDef = new LevelDefinitionParser(fileName);
			cache.put(fileName, levelDef);
			return levelDef;
		}
	}
	
	public static void reset() {
		cache.clear();
	}
}
