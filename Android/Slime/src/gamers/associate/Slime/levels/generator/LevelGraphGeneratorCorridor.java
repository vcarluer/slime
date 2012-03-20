package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelUtil;
import gamers.associate.Slime.levels.generator.hardcoded.BlocHardInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.types.CGPoint;

import android.util.Log;

public class LevelGraphGeneratorCorridor extends LevelGraphGeneratorBase {		
	protected static final String BlocsAssetsBase = "blocsCorridor";
	protected static final String BlocsAssetsBaseRect = "blocsRectangle";
	
	protected LevelGenNode pickStart(BlocDirection goToDirection) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.isStartAndGoTo(goToDirection)) {
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickNext(LevelGenNode source, BlocDirection goToDirection) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.connectNoSpecialAndGoTo(source, goToDirection)) {
				// hack for multi entry / exit test blocks				
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	protected LevelGenNode pickEnd(LevelGenNode source) {		
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndConnect(source)) {
				selection.add(node);
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}	
	
	protected LevelGenNode pickBoss(LevelGenNode source) {		
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelBossAndConnect(source)) {
				selection.add(node);
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
	
	@Override
	protected void generateInternal(int maxComplexity, BlocDirection constrained, boolean isBoss) {		
		Log.d(Slime.TAG, "Picking start node with constraint " + String.valueOf(constrained));
		LevelGenNode pick = this.pickStartConstrained(constrained);
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, true);		
		while (this.currentComplexity < this.currentMaxComplexity) {
			Log.d(Slime.TAG, "Picking next node with constraint " + String.valueOf(constrained));
			pick = this.pickNextConstrained(pick, constrained);
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, true);	
		}
		
		Log.d(Slime.TAG, "Picking end node");
		if (isBoss) {
			pick = this.pickBoss(pick);
		} else {
			pick = this.pickEnd(pick);
		}
		
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		
		this.addGamePlay(this.totalCount);
		
		this.fillEmptyBlocks();
	}
	
	private void fillEmptyBlocks() {
		for(int x = this.minX; x <= this.maxX; x++) {
			for(int y = this.minY; y <= this.maxY; y++) {
				if (!blocExist(x, y )) {
					BlocHardInit.BlockFill.buildLevel(this.currentLevel, x, y);
				}
			}
		}
	}
	
	private boolean blocExist(int x, int y) {
		boolean exists = false;
		for(CGPoint blocPos : this.blockMap) {
			if (blocPos.x == x && blocPos.y == y) {
				exists = true;
				break;
			}
		}
		
		return exists;
	}

	protected LevelGenNode pickNextConstrained(LevelGenNode source,
			BlocDirection constrained) {
		return this.pickNext(source, this.getRandomDirection(constrained));
	}

	protected LevelGenNode pickStartConstrained(BlocDirection constrained) {
		return this.pickStart(this.getRandomDirection(constrained));
	}

	public void generate(int maxComplexity) {			
		this.generate(maxComplexity, null);
	}

	@Override
	protected void initAssets(List<String> assetsList) {
		 assetsList.add(BlocsAssetsBase);
	}		
}