package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gamers.associate.Slime.game.IGamePlay;
import gamers.associate.Slime.game.LevelDifficulty;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;

public class LevelGraphGeneratorRectangle2 extends LevelGraphGeneratorRectangle {
	private static final int timeCritic = 5;
	private static final float timeCalcPerBlock = 8f;
	private static final int timeCalcBase = 20;
	// tuned with SGS
	private static final int maxWidth = 10;
	private static final int maxAddHeight = 3;
	private static final int minBossPos = 1;
	private static final Random rand = new Random();
	
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
					
		float tmp = SlimeFactory.GameInfo.getLevelNum() * lgMax;
		tmp = tmp / SlimeFactory.GameInfo.getLevelMax();
		int lvlWidth = Math.round(tmp);
		if (lvlWidth == 0) {
			lvlWidth = 1;
		}
		tmp = lvlWidth * maxAddHeight;
		tmp = tmp / maxWidth;
		int lvlHeight = Math.round(tmp);
		if (lvlHeight == 0) {
			lvlHeight = 1;
		}

		// Always at least 2
		lvlWidth++;
		lvlHeight++;
		
		int startPos = rand.nextInt(lvlHeight);
		/*int endPos = rand.nextInt(lvlHeight);
		// try to repick if equal
		if (startPos == endPos) {
			endPos = rand.nextInt(lvlHeight);
		}*/
		int endPos = lvlHeight - 1 - startPos;
		
		// Pick start / end and all other blocks ! Based on complexity? And difficulty?
		// Boss always at right or at bottom?
		// At bottom:
		int bossPos = -1;
		if (isBoss) {
			bossPos = rand.nextInt(lvlWidth - minBossPos) + minBossPos; 
		}
		
		LevelGenNode pick = null;
		this.rightCount = 0;
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
							pick = this.pickBossBottomMiddle();
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
