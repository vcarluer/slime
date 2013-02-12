package gamers.associate.SlimeAttack.levels.generator;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.GamePlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import android.util.FloatMath;

public class LevelGraphGeneratorRectangle2 extends LevelGraphGeneratorRectangle {
	private static final int noBossPos = -1;	
	
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
			BlocDirection constrained, boolean isBoss, GamePlay gamePlay) {
		
		int endPos = noBossPos;
		int bossPos = noBossPos;
		
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
		int starBlocCountBase = (int) FloatMath.ceil(totalBlocs / 2f);
		int starBlocCount = starBlocCountBase - 1; // 2 opposite corner are always picked
		if (!isBoss) {
			starBlocCount--;
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "Picking stars: " + String.valueOf(starBlocCount) + " in " + String.valueOf(totalBlocs) + "blocks");

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
		SlimeFactory.Log.d(SlimeAttack.TAG, "star inverse of star in " + String.valueOf(startStar));
		if (!isBoss) {
			this.starList.add(endStar);
			SlimeFactory.Log.d(SlimeAttack.TAG, "star inverse of end in " + String.valueOf(endStar));
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
					SlimeFactory.Log.d(SlimeAttack.TAG, "Block star idx: " + String.valueOf(starIdx));
					pick.setStarBlock(this.starList.contains(starIdx));
					if (pick.isStarBlock()) {
						SlimeFactory.Log.d(SlimeAttack.TAG, "Stars in block " + pick.getBlocDefinition().getResourceName());
					}
					this.handlePick(pick, false);
				}
								
				this.topCount++;
			}
			
			this.rightCount++;
		}
		
		this.topCount--;
		this.rightCount--;
		this.addGamePlay(lvlWidth * lvlHeight, gamePlay);					
	}	
	
	private void pickStar(int starBlocCount, List<Integer> allStars,
			HashSet<Integer> starList) {
		int idx = rand.nextInt(allStars.size());
		SlimeFactory.Log.d(SlimeAttack.TAG, "star in " + String.valueOf(allStars.get(idx)));
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
