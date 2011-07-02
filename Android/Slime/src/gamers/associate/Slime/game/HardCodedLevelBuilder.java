package gamers.associate.Slime.game;

import gamers.associate.Slime.levels.LevelBeta;
import gamers.associate.Slime.levels.LevelDefinition;
import gamers.associate.Slime.levels.LevelHome;

import java.util.ArrayList;
import java.util.HashMap;


public class HardCodedLevelBuilder {
	private static HashMap<String, LevelDefinition> levels;
	
	public static void init() {
		levels = new HashMap<String, LevelDefinition>();
		
		add(new LevelHome());
		add(new LevelBeta());
	}
	
	private static void add(LevelDefinition levelDef) {
		if (levelDef != null) {
			levels.put(levelDef.getId(), levelDef);
		}
	}
	
	public static void build(Level level, String levelName) {
		LevelDefinition levelDef = levels.get(levelName);
		if (levelDef != null) {
			levelDef.buildLevel(level);
		}
	}
	
	public static ArrayList<LevelDefinition> getNormalLevels() {
		ArrayList<LevelDefinition> gameLevels = new ArrayList<LevelDefinition>();
		for(LevelDefinition levelDef : levels.values()) {
			if (!levelDef.isSpecial()) {
				gameLevels.add(levelDef);
			}
		}
		
		return gameLevels;
	}
}
