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
	private static final String BlocsAssetsBase = "blocsCorridor";
	private static final String BlocsAssetsBaseRect = "blocsRectangle";
	
	public LevelGenNode pickStart(BlocDirection goToDirection) {
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

	public LevelGenNode pickNext(LevelGenNode source, BlocDirection goToDirection) {
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

	public LevelGenNode pickEnd(LevelGenNode source) {		
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
	
	public LevelGenNode pickBoss(LevelGenNode source) {		
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
	
	public LevelGenNode pickStart() {
		return this.pickStart(this.getRandomDirection());
	}

	public LevelGenNode pickNext(LevelGenNode source) {
		return this.pickNext(source, this.getRandomDirection());
	}
	
	private ArrayList<CGPoint> blockMap;
	
	@Override
	protected void generateInternal(int maxComplexity, BlocDirection constrained, boolean isBoss) {
		this.blockMap = new ArrayList<CGPoint>();
		this.minX = 0;
		this.maxX = 0;
		this.minY = 0;
		this.maxY = 0;
		
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

	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	@Override
	protected void handlePick(LevelGenNode pick, boolean countDirection) {		
		int x = this.rightCount - this.leftCount;
		int y = this.topCount - this.bottomCount;
		if (x < minX) this.minX = x;
		if (x > maxX) this.maxX = x;
		if (y < this.minY) this.minY = y;
		if (y > this.maxY) this.maxY = y;

		this.blockMap.add(CGPoint.make(x, y));

		super.handlePick(pick, countDirection);				
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

	private LevelGenNode pickNextConstrained(LevelGenNode source,
			BlocDirection constrained) {
		return this.pickNext(source, this.getRandomDirection(constrained));
	}

	private LevelGenNode pickStartConstrained(BlocDirection constrained) {
		return this.pickStart(this.getRandomDirection(constrained));
	}

	public void generate(int maxComplexity) {			
		this.generate(maxComplexity, null);
	}

	@Override
	protected void initAssets(List<String> assetsList) {
		 assetsList.add(BlocsAssetsBase);
		 assetsList.add(BlocsAssetsBaseRect);
	}		
}