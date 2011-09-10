package gamers.associate.Slime.levels;

import java.util.ArrayList;

import gamers.associate.Slime.game.Level;

public interface ILevelBuilder {
	void init();
	String getNext(String levelName);
	void build(Level level, String levelName);
	ArrayList<LevelDefinition> getNormalLevels();
}
