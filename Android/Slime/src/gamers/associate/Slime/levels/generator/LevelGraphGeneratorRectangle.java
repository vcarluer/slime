package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public class LevelGraphGeneratorRectangle extends LevelGraphGeneratorBase {
	private static final String BlocsAssetsBase = "blocsRectangle";	
	private static final float GoldenRatio = 1.61803f;		
	
	@Override
	// For now only to right
	// Complexity = complexity of first line
	protected void generateInternal(int maxComplexity,
			BlocDirection constrained, boolean isBoss) {
		// todo: remove colCount and rowCount => countRight & countTop enough
		// todo: refactor!
		int colCount = 0;		
		int rowNum = 1;
		this.rightCount = 0;
		this.topCount = 0;
		LevelGenNode pick = null;		
		Log.d(Slime.TAG, "Picking start node");
		pick = this.pickStart();		
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colCount++;
		this.rightCount++;
		while (this.currentComplexity < this.currentMaxComplexity) {
			Log.d(Slime.TAG, "Picking next ground");
			pick = this.pickNextGround();
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colCount++;
			this.rightCount++;
		}
		
		// Compute row count before last block to compute exit and eventually take it in first line		
		int rowCount = (int)((colCount + 1) / GoldenRatio);
		int rowExit = this.randomGenerator.nextInt(rowCount) + 1;
		
		if (rowExit == rowNum) {
			Log.d(Slime.TAG, "Picking EXIT ground line right corner");
			pick = this.pickExitGroundRightCorner();
		} else {
			Log.d(Slime.TAG, "Picking ground line right corner");
			pick = this.pickGroundRightCorner();
		}
		
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colCount++;		
		// First line picked
		rowNum++;		
		this.topCount++;
		 		
		while (rowNum < rowCount) {
			
			int colNum = 1;
			this.rightCount = 0;
			Log.d(Slime.TAG, "Picking left middle block");
			pick = this.pickMiddleLeft();
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colNum++;
			this.rightCount++;
			while (colNum < colCount) {
				Log.d(Slime.TAG, "Picking middle block");
				pick = this.pickMiddle();
				Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
				this.handlePick(pick, false);
				colNum++;
				this.rightCount++;
			}
			
			if (rowNum == rowExit) {
				Log.d(Slime.TAG, "Picking EXIT right middle block");
				pick = this.pickExitMiddleRight();
			} else {
				Log.d(Slime.TAG, "Picking right middle block");
				pick = this.pickMiddleRight();
			}			
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			
			rowNum++;
			this.topCount++;
		}
		
		int colNum = 1;
		this.rightCount = 0;
		Log.d(Slime.TAG, "Picking left top block");
		pick = this.pickTopLeft();
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		colNum++;
		this.rightCount++;
		while (colNum < colCount) {
			Log.d(Slime.TAG, "Picking middle block");
			pick = this.pickTopMiddle();
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, false);
			colNum++;
			this.rightCount++;
		}
		
		if (rowNum == rowExit) {
			Log.d(Slime.TAG, "Picking EXIT right middle block");
			pick = this.pickExitTopRight();
		} else {
			Log.d(Slime.TAG, "Picking right middle block");
			pick = this.pickTopRight();
		}			
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
	}

	private LevelGenNode pickTopRight() {
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

	private LevelGenNode pickExitTopRight() {
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

	private LevelGenNode pickTopMiddle() {
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

	private LevelGenNode pickTopLeft() {
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

	private LevelGenNode pickMiddleRight() {
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

	private LevelGenNode pickExitMiddleRight() {
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

	private LevelGenNode pickMiddle() {
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

	private LevelGenNode pickMiddleLeft() {
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

	private LevelGenNode pickExitGroundRightCorner() {
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

	private LevelGenNode pickGroundRightCorner() {
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

	private LevelGenNode pickNextGround() {
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
	public String getAssetsBase() {
		return BlocsAssetsBase;
	}
	
	private LevelGenNode pickStart() {
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
}
