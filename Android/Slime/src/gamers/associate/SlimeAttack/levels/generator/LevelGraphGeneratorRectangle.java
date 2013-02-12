package gamers.associate.SlimeAttack.levels.generator;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.GamePlay;

import java.util.ArrayList;
import java.util.List;


public class LevelGraphGeneratorRectangle extends LevelGraphGeneratorBase {
	protected static final String BlocsAssetsBase = "blocsRectangle";	
	protected static final float GoldenRatio = 1.61803f;
	
	@Override
	// For now only to right
	// Complexity = complexity of first line
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss, GamePlay gamePlay) {
		// todo: remove colCount and rowCount => countRight & countTop enough
		// todo: refactor!
		int colCount = 0;		
		int rowNum = 1;
		this.rightCount = 0;
		this.topCount = 0;
		LevelGenNode pick = null;		
		SlimeFactory.Log.d(SlimeAttack.TAG, "Picking start node");
		pick = this.pickStart();		
		SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colCount++;
		this.rightCount++;
		while (this.currentComplexity < this.currentMaxComplexity) {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking next ground");
			pick = this.pickNextGround();
			SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colCount++;
			this.rightCount++;
		}
		
		// Compute row count before last block to compute exit and eventually take it in first line		
		int rowCount = (int)((colCount + 1) / GoldenRatio);
		int rowExit = this.randomGenerator.nextInt(rowCount) + 1;
		
		if (rowExit == rowNum) {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking EXIT ground line right corner");
			pick = this.pickExitGroundRightCorner();
		} else {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking ground line right corner");
			pick = this.pickGroundRightCorner();
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colCount++;		
		// First line picked
		rowNum++;		
		this.topCount++;
		 		
		while (rowNum < rowCount) {
			
			int colNum = 1;
			this.rightCount = 0;
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking left middle block");
			pick = this.pickMiddleLeft();
			SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colNum++;
			this.rightCount++;
			while (colNum < colCount) {
				SlimeFactory.Log.d(SlimeAttack.TAG, "Picking middle block");
				pick = this.pickMiddle();
				SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
				this.handlePick(pick, false);
				colNum++;
				this.rightCount++;
			}
			
			if (rowNum == rowExit) {
				SlimeFactory.Log.d(SlimeAttack.TAG, "Picking EXIT right middle block");
				pick = this.pickExitMiddleRight();
			} else {
				SlimeFactory.Log.d(SlimeAttack.TAG, "Picking right middle block");
				pick = this.pickMiddleRight();
			}			
			SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			
			rowNum++;
			this.topCount++;
		}
		
		int colNum = 1;
		this.rightCount = 0;
		SlimeFactory.Log.d(SlimeAttack.TAG, "Picking left top block");
		pick = this.pickTopLeft();
		SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colNum++;
		this.rightCount++;
		while (colNum < colCount) {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking middle block");
			pick = this.pickTopMiddle();
			SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colNum++;
			this.rightCount++;
		}
		
		if (rowNum == rowExit) {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking EXIT right middle block");
			pick = this.pickExitTopRight();
		} else {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Picking right middle block");
			pick = this.pickTopRight();
		}			
		SlimeFactory.Log.d(SlimeAttack.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
	}

	protected LevelGenNode pickTopRight() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickExitTopRight() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickTopMiddle() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickTopLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));		
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickMiddleRight() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickExitMiddleRight() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));	
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickMiddle() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickMiddleLeft() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Bottom)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickExitGroundRightCorner() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickGroundRightCorner() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));		
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickNextGround() {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Top)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Right)));
		list.addAll(LevelGenNode.getConnectorsFor(LevelGenNode.getMirror(BlocDirection.Left)));
		for(LevelGenNode node : this.nodes) {
			if (node.isNoSpecialAndExactlyConnectedTo(list)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	@Override
	protected void initAssets(List<String> assetsList) {		
		assetsList.add(BlocsAssetsBase);
	}
	
	protected LevelGenNode pickStart() {
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

	@Override
	protected void computeLevelSize(boolean isBoss) {
		if (!isBoss) {
			int lgMax = this.getRatioDiff(maxWidth);						
			this.computeLevelWidth(lgMax);
			
			if (lvlWidth == 0) {
				lvlWidth = 1;
			}

			float tmp = lvlWidth * maxAddHeight;
			tmp = tmp / maxWidth;
			this.lvlHeight = Math.round(tmp);
			if (lvlHeight == 0) {
				lvlHeight = 1;
			}
			
			// Always at least 2
			lvlWidth++;
			lvlHeight++;						
		} else {
			if (SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Easy) {
				lvlWidth = 2;
				lvlHeight = 2;
			}
			
			if (SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Normal) {
				lvlWidth = 3;
				lvlHeight = 2;
			}
			
			if (SlimeFactory.GameInfo.getDifficulty() >= LevelDifficulty.Hard) {
				lvlWidth = 4;
				lvlHeight = 2;
			}
		}		
	}
}
