package gamers.associate.Slime.levels.generator;

import java.util.Random;

import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;

public class LevelGraphGeneratorRectangle2 extends LevelGraphGeneratorRectangle {
	private static int maxWidth = 24;
	private static int maxAddHeight = 3;
	private static int minBossPos = 1;
	private static Random rand = new Random();
	
	@Override
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss) {
		
		int lgMax = 1;
		switch (SlimeFactory.GameInfo.getDifficulty()) {
		default:
		case LevelDifficulty.Easy: lgMax = maxWidth / 4; break;
		case LevelDifficulty.Normal: lgMax = maxWidth / 2; break;
		case LevelDifficulty.Hard: lgMax = maxWidth * 3 / 4; break;
		case LevelDifficulty.Extrem: lgMax = maxWidth; break;
		}
		
		int lvlWidth = (int) Math.ceil(SlimeFactory.GameInfo.getLevelNum() * lgMax / SlimeFactory.GameInfo.getLevelMax());
		int lvlHeight = (int) Math.ceil(lvlWidth * maxAddHeight / maxWidth);
		// Always at least 2
		lvlWidth++;
		lvlHeight++;
		
		int startPos = rand.nextInt(lvlHeight);
		int endPos = rand.nextInt(lvlHeight);
		// try to repick if equal
		if (startPos == endPos) {
			endPos = rand.nextInt(lvlHeight);
		}
		
		// Pick start / end and all other blocks ! Based on complexity? And difficulty?
		// Boss always at right or at bottom?
		// At bottom:
		int bossPos = rand.nextInt(lvlWidth - minBossPos) + minBossPos; 
	}

}
