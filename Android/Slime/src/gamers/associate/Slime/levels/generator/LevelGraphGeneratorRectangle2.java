package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import android.util.Log;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.IGamePlay;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.levels.LevelHome;

public class LevelGraphGeneratorRectangle2 extends LevelGraphGeneratorRectangle {
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
	private static final int tutorialCount = 7;
	private static final int noBossPos = -1;
	private static final int timeCritic = 5;
	private static final float timeCalcPerBlock = 8f;
	private static final int timeCalcBase = 20;
	// tuned with SGS
	private static final int maxWidth = 6;
	private static final int maxAddHeight = 2;
	private static final int minBossPos = 1;
	private static final Random rand = new Random();
	private HashSet<Integer> starList;
	private List<Integer> allStars;
	
	public LevelGraphGeneratorRectangle2() {
		  this.starList = new HashSet<Integer>();
		  this.allStars = new ArrayList<Integer>();
	}
	
	@Override
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss) {
		
		if (SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Easy && SlimeFactory.GameInfo.getLevelNum() <= tutorialCount) {
			this.generateTutorialLevel(SlimeFactory.GameInfo.getLevelNum());
			return;
		}
		
		int lvlWidth = 0;
		int lvlHeight = 0;
		int endPos = noBossPos;
		int bossPos = noBossPos;
				
		if (!isBoss) {
			int lgMax = 1;
			switch (SlimeFactory.GameInfo.getDifficulty()) {
				default:
				case LevelDifficulty.Easy: lgMax = Math.round(maxWidth / 4f); break;
				case LevelDifficulty.Normal: lgMax = Math.round(maxWidth / 2f); break;
				case LevelDifficulty.Hard: lgMax = Math.round(maxWidth * 3f / 4f); break;
				case LevelDifficulty.Extrem: lgMax = maxWidth; break;
			}
						
			float tmp = SlimeFactory.GameInfo.getLevelNum() * lgMax;
			tmp = tmp / SlimeFactory.GameInfo.getLevelMax();
			lvlWidth = Math.round(tmp);
			if (lvlWidth == 0) {
				lvlWidth = 1;
			}
			tmp = lvlWidth * maxAddHeight;
			tmp = tmp / maxWidth;
			lvlHeight = Math.round(tmp);
			if (lvlHeight == 0) {
				lvlHeight = 1;
			}
			
			// Always at least 2
			lvlWidth++;
			lvlHeight++;						
		} else {
			lvlWidth = 3;
			lvlHeight = 2;			
		}				
		
		int startPos = rand.nextInt(lvlHeight);		
		
		if (!isBoss) {
			// Inverse from start
			endPos = lvlHeight - 1 - startPos;			
		} else {
			bossPos = rand.nextInt(lvlWidth - minBossPos) + minBossPos;
		}					
		
		int startStar = startPos * lvlWidth + lvlWidth - 1;
		int endStar = noBossPos;
		if (!isBoss) {
			endStar = lvlWidth * endPos;			
		}
		 
		float totalBlocs = lvlWidth * lvlHeight;
		int starBlocCountBase = (int) Math.ceil(totalBlocs / 2f);
		int starBlocCount = starBlocCountBase - 1; // 2 opposite corner are always picked
		if (!isBoss) {
			starBlocCount--;
		}
		
		Log.d(Slime.TAG, "Picking stars: " + String.valueOf(starBlocCount) + " in " + String.valueOf(totalBlocs) + "blocks");

		this.starList.clear();
		this.allStars.clear();
		for(int i = 0; i < totalBlocs; i++) {
			if (i != startStar && i != endStar) {
				this.allStars.add(i);
			}			
		}		
		
		if (starBlocCount > 0) {
			this.pickStar(starBlocCount, this.allStars, this.starList);
		}
		
		this.starList.add(startStar);
		Log.d(Slime.TAG, "star inverse of star in " + String.valueOf(startStar));
		if (!isBoss) {
			this.starList.add(endStar);
			Log.d(Slime.TAG, "star inverse of end in " + String.valueOf(endStar));
		}
		
		LevelGenNode pick = null;
		this.rightCount = 0;
		int starIdx = 0;
		for (int col = 0; col < lvlWidth; col++) {
			this.topCount = 0;
			for (int row = 0; row < lvlHeight; row++) {
				// Left col
				if (col == 0) {
					if (row == 0) {
						// can be start
						if (row == startPos) {
							pick = this.pickStartBottomLeft();
						} else {
							pick = this.pickBottomLeft();
						}											
					}
					if (row > 0 && row < lvlHeight - 1) {
						if (row == startPos) {
							pick = this.pickStartMidLeft();
						} else {
							pick = this.pickMiddleLeft();
						}
						
					}
					if (row == lvlHeight - 1) {
						if (row == startPos) {
							pick = this.pickStartTopLeft();
						} else {
							pick = this.pickTopLeft();
						}
						
					}
				}
				
				// Mid col
				if (col > 0 && col < lvlWidth - 1) {
					if (row == 0) {
						// can be boss
						if (col == bossPos) {
							pick = this.pickBossBottomMiddle();
						} else {
							pick = this.pickNextGround();
						}
					}
					
					if (row > 0 && row < lvlHeight - 1) {
						pick = this.pickMiddle();
					}
					
					if (row == lvlHeight - 1) {
						pick = this.pickTopMiddle();
					}
				}
				
				// Right col
				if (col == lvlWidth - 1) {
					if (row == 0) {
						// can be boss
						if (col == bossPos) {
							pick = this.pickBossBottomRightCorner();
						} else {
							// can be exit
							if (row == endPos) {
								pick = this.pickExitGroundRightCorner();
							} else {
								pick = this.pickGroundRightCorner();
							}							
						}
					}
					
					if (row > 0 && row < lvlHeight - 1) {
						if (row == endPos) {
							pick = this.pickExitMiddleRight();
						} else {
							pick = this.pickMiddleRight();
						}						
					}
					
					if (row == lvlHeight - 1) {
						if (row == endPos) {
							pick = this.pickExitTopRight();
						} else {
							pick = this.pickTopRight();
						}
					}
				}
				
				if (pick != null) {					
					// reset always (previous may have change it)
					starIdx = row * lvlWidth + col;
					Log.d(Slime.TAG, "Block star idx: " + String.valueOf(starIdx));
					pick.setStarBlock(this.starList.contains(starIdx));
					if (pick.isStarBlock()) {
						Log.d(Slime.TAG, "Stars in block " + pick.getBlocDefinition().getResourceName());
					}
					this.handlePick(pick, false);
				}
								
				this.topCount++;
			}
			
			this.rightCount++;
		}
		
		this.topCount--;
		this.rightCount--;
		// Compute total time et critic time here		
		this.currentLevel.removeCurrentGamePlay();
		
		TimeAttackGame taGame = TimeAttackGame.NewGame();
		this.currentLevel.addGamePlay(taGame);
				
		int baseTime = timeCalcBase - SlimeFactory.GameInfo.getDifficulty();
		int secPerBloc = Math.round(timeCalcPerBlock / SlimeFactory.GameInfo.getDifficulty());
		int totalTime = baseTime + (lvlWidth * lvlHeight) * secPerBloc; 
		taGame.setStartTime(totalTime);
		// 10% or other or fix?
		int critic = timeCritic;
		taGame.setCriticTime(critic);					
	}	

	private void generateTutorialLevel(int levelNum) {
		int lvlWidth = 0;
		int lvlHeight = 0;
		
		this.rightCount = 0;
		this.topCount = 0;
		LevelGenNode pick = null;
		switch (levelNum) {
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
			this.handleTut(TUT3_2);
			break;
		// 4 - no sticky platform + death threat + star
		case 4: // horizontal level
			this.handleTut(TUT4_1);
			this.rightCount++;
			this.handleTut(TUT4_2);
			break;
		// 5 - icy platform + death threat + star
		case 5: // vertical level down
			this.handleTut(TUT5_1);
			this.bottomCount++;
			this.handleTut(TUT5_2);
			break;
		// 6 - button + laser + star
		case 6: // horizontal level
			this.handleTut(TUT6_1);
			this.rightCount++;
			this.handleTut(TUT6_2);
			break;
		// 7 - Bullet time + death threat + 2 stars
		case 7: // vertical level
			this.handleTut(TUT7_1);
			this.topCount++;
			this.handleTut(TUT7_2);
			break;	
		}			
		
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
		LevelGenNode pick = this.pickBlockByName(this.resource("tut1"));
		if (pick != null) {
			pick.setStarBlock(true);
			this.handlePick(pick, false);
		}
	}
	
	private String resource(String shortName) {
		return BLOCS_TUTORIAL + "/" + shortName + ".slime";
	}	

	private void pickStar(int starBlocCount, List<Integer> allStars,
			HashSet<Integer> starList) {
		int idx = rand.nextInt(allStars.size());
		Log.d(Slime.TAG, "star in " + String.valueOf(allStars.get(idx)));
		starList.add(allStars.get(idx));
		allStars.remove(idx);
		starBlocCount--;
		if (starBlocCount > 0) {
			this.pickStar(starBlocCount, allStars, starList);
		}
	}

	protected LevelGenNode pickStartBottomLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelStartAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	protected LevelGenNode pickStartMidLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelStartAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	protected LevelGenNode pickStartTopLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelStartAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	protected LevelGenNode pickBottomLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	protected LevelGenNode pickBossBottomMiddle() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelBossAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	protected LevelGenNode pickBossBottomRightCorner() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelBossAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
}
