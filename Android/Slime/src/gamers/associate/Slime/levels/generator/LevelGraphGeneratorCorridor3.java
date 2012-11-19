package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.SlimeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Generates corridor in 2 steps */
public class LevelGraphGeneratorCorridor3 extends LevelGraphGeneratorCorridor2 {
	private HashMap<MapPoint, NodeMapInfo> nodeMap;
	
	public LevelGraphGeneratorCorridor3() {
		this.nodeMap = new HashMap<MapPoint, NodeMapInfo>();
	}
	
	@Override
	protected void generateBeforeGP(BlocDirection constrained, boolean isBoss) {
		generateMap(constrained);
		
		generateBlocs(isBoss);
	}

	protected void generateBlocs(boolean isBoss) {
		for(NodeMapInfo nmi : this.nodeMap.values()) {
			boolean goTop = this.nodeMap.containsKey(new MapPoint(nmi.position.x, nmi.position.y + 1));
			boolean goRight = this.nodeMap.containsKey(new MapPoint(nmi.position.x + 1, nmi.position.y));
			boolean goBottom = this.nodeMap.containsKey(new MapPoint(nmi.position.x, nmi.position.y - 1));
			boolean goLeft = this.nodeMap.containsKey(new MapPoint(nmi.position.x - 1, nmi.position.y));
			LevelGenNode pick = this.pick(nmi.isStart, nmi.isEnd && !isBoss, nmi.isEnd && isBoss, goTop, goRight, goBottom, goLeft);
			
			if (pick != null) {
				SlimeFactory.Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
				this.handlePick(pick, nmi.position.x, nmi.position.y);
			}
		}
	}

	protected void generateMap(BlocDirection constrained) {
		this.nodeMap.clear();
		SlimeFactory.Log.d(Slime.TAG, "Generating corridor in 2 steps. Step 1: picking map");
		NodeMapInfo nmi = new NodeMapInfo(0, 0);
		nmi.isStart = true;
		this.nodeMap.put(nmi.position, nmi);
		
		while (this.nodeMap.size() < this.getLvlBlockCount()) {
			BlocDirection direction = this.getRandomDirection(constrained);
			this.handleMap(direction);
			nmi = new NodeMapInfo(this.currentX, this.currentY);
			this.nodeMap.put(nmi.position, nmi);
		}
		
		BlocDirection direction = this.getRandomDirection(constrained);
		this.handleMap(direction);
		nmi = new NodeMapInfo(this.currentX, this.currentY);
		nmi.isEnd = true;
		this.nodeMap.put(nmi.position, nmi);
	}
	
	private void handleMap(BlocDirection direction) {
		this.count(direction);
		this.Forward(direction);
	}
	
	private LevelGenNode pick(boolean isStart, boolean isEnd, boolean isBoss, boolean top, boolean right, boolean bottom, boolean left) {
		LevelGenNode pick = null;
		List<LevelGenNode> selection = new ArrayList<LevelGenNode>();
		for(LevelGenNode node : this.nodes) {
			if (node.atLeast(isStart, isEnd, isBoss, top, right, bottom, left)) {
				selection.add(node);
			}
		}
				
		pick = this.pickFromCompatible(selection);
		return pick;
	}
}
