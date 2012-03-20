package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;

/*
 * Generate levels with rectagle block compatibility: use only previous direction and next direction, not block connectivity
 * 
 */
public class LevelGraphGeneratorCorridor2 extends LevelGraphGeneratorCorridor {
	@Override
	protected void initAssets(List<String> assetsList) {
		 assetsList.add(BlocsAssetsBase);
		 assetsList.add(BlocsAssetsBaseRect);
	}

	@Override
	protected LevelGenNode pickEnd(LevelGenNode source) {
		return this.pickEnd(LevelGenNode.getMirror(this.previousDirection));
	}

	private LevelGenNode pickEnd(BlocDirection fromDirection) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndComeFrom(fromDirection)) {
				selection.add(node);
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	@Override
	protected LevelGenNode pickBoss(LevelGenNode source) {
		return this.pickBoss(LevelGenNode.getMirror(this.previousDirection));
	}

	private LevelGenNode pickBoss(BlocDirection fromDirection) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelBossAndComeFrom(fromDirection)) {
				selection.add(node);
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}

	@Override
	protected LevelGenNode pickNextConstrained(LevelGenNode source,
			BlocDirection constrained) {
		return this.pickNextConstrained(LevelGenNode.getMirror(this.previousDirection), this.getRandomDirection(constrained));
	}

	private LevelGenNode pickNextConstrained(BlocDirection fromDirection,
			BlocDirection toDirection) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.NoSpecialComeFromAndGoTo(fromDirection, toDirection)) {				
				selection.add(node);				
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
}
