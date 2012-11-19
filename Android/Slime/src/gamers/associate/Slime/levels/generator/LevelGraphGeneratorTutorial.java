package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.levels.GamePlay;

import java.util.List;

public class LevelGraphGeneratorTutorial extends LevelGraphGeneratorRectangle {
	private static final String TUT7_TITLE = "The reveleation of the flying monster";
	private static final String TUT6_TITLE = "The prismatic equation of the mechanician";
	private static final String TUT5_TITLE = "The explorer of the frozen labyrinth";
	private static final String TUT4_TITLE = "The suspicious world which do not stick";
	private static final String TUT3_TITLE = "The amazing bounce of the vigilant creature";
	private static final String TUT2_TITLE = "The golden star of the forbidden doorway";
	private static final String TUT1_TITLE = "The incredible exit tube";
	private static final String BLOCS_TUTORIAL = "blocsTutorial";
	private static final String TUT7_2 = "tut7-2";
	private static final String TUT7_1 = "tut7-1";
	private static final String TUT6_2 = "tut6-2";
	private static final String TUT6_1 = "tut6-1";
	private static final String TUT5_2 = "tut5-2";
	private static final String TUT5_1 = "tut5-1";
	private static final String TUT4_2 = "tut4-2";
	private static final String TUT4_1 = "tut4-1";
	private static final String TUT3_2 = "tut3-2";
	private static final String TUT3_1 = "tut3-1";
	private static final String TUT2_1 = "tut2-1";
	private static final String TUT1_1 = "tut1-1";
	private static final int tutorialTimeMult = 2;
	public static final int tutorialCount = 7;
	private static final int timeCritic = 5;
	
	@Override
	protected void initAssets(List<String> assetsList) {
		assetsList.add(BLOCS_TUTORIAL);
	}

	private static final int timeCalcBase = 20;
	private static final float timeCalcPerBlock = 8f;
		
	@Override
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss, GamePlay gamePlay) {
						
		int lvlWidth = 0;
		int lvlHeight = 0;
		
		this.rightCount = 0;
		this.topCount = 0;
		switch (SlimeFactory.GameInfo.getLevelNum()) {
		// 1 - Shoot / Go to end
		case 1:
			this.handleTut(TUT1_1);
			break;
		// 2 - Take star
		case 2:
			this.handleTut(TUT2_1);			
			break;
		// 3 - bumper platform + star
		case 3: // vertical level
			this.handleTut(TUT3_1);
			this.topCount++;
			this.maxX = 0;
			this.maxY = 1;
			this.currentX = 0;
			this.currentY = 1;
			this.handleTut(TUT3_2);
			break;
		// 4 - no sticky platform + death threat + star
		case 4: // horizontal level
			this.handleTut(TUT4_1);
			this.rightCount++;
			this.maxX = 1;
			this.maxY = 0;
			this.currentX = 1;
			this.currentY = 0;
			this.handleTut(TUT4_2);
			break;
		// 5 - icy platform + death threat + star
		case 5: // vertical level down
			this.handleTut(TUT5_1);
			this.bottomCount++;
			this.maxX = 0;
			this.maxY = 0;
			this.minY = -1;
			this.currentX = 0;
			this.currentY = -1;
			this.handleTut(TUT5_2);
			break;
		// 6 - button + laser + star
		case 6: // horizontal level
			this.handleTut(TUT6_1);
			this.rightCount++;
			this.maxX = 1;
			this.maxY = 0;
			this.minY = 0;
			this.currentX = 1;
			this.currentY = 0;
			this.handleTut(TUT6_2);
			break;
		// 7 - Bullet time + death threat + 2 stars
		case 7: // vertical level
			this.handleTut(TUT7_1);
			this.topCount++;
			this.maxX = 0;
			this.maxY = 1;
			this.currentX = 0;
			this.currentY = 1;
			this.handleTut(TUT7_2);
			break;	
		}			
		
		this.setTitle();
		
		TimeAttackGame taGame = TimeAttackGame.NewGame();
		this.currentLevel.addGamePlay(taGame);
				
		int baseTime = timeCalcBase - SlimeFactory.GameInfo.getDifficulty() * tutorialTimeMult;
		int secPerBloc = Math.round(timeCalcPerBlock / SlimeFactory.GameInfo.getDifficulty());		
		int totalTime = baseTime + (lvlWidth  * lvlHeight ) * secPerBloc; 
		taGame.setStartTime(totalTime);
		int critic = timeCritic;
		taGame.setCriticTime(critic);		
		
	}
	
	private void handleTut(String shortName) {
		LevelGenNode pick = this.pickBlockByName(this.resource(shortName));
		if (pick != null) {
			pick.setStarBlock(true);
			this.handlePick(pick, false);
		}
	}
	
	private String resource(String shortName) {
		return BLOCS_TUTORIAL + "/" + shortName + ".slime";
	}
	
	public void setTitle() {
		switch (SlimeFactory.GameInfo.getLevelNum()) {
		// 1 - Shoot / Go to end
		case 1:
			Level.currentLevel.setTitle(TUT1_TITLE);
			break;
		// 2 - Take star
		case 2:
			Level.currentLevel.setTitle(TUT2_TITLE);		
			break;
		// 3 - bumper platform + star
		case 3: // vertical level
			Level.currentLevel.setTitle(TUT3_TITLE);
			break;
		// 4 - no sticky platform + death threat + star
		case 4: // horizontal level
			Level.currentLevel.setTitle(TUT4_TITLE);
			break;
		// 5 - icy platform + death threat + star
		case 5: // vertical level down
			Level.currentLevel.setTitle(TUT5_TITLE);
			break;
		// 6 - button + laser + star
		case 6: // horizontal level
			Level.currentLevel.setTitle(TUT6_TITLE);
			break;
		// 7 - Bullet time + death threat + 2 stars
		case 7: // vertical level
			Level.currentLevel.setTitle(TUT7_TITLE);
			break;	
		}		
	}
}
