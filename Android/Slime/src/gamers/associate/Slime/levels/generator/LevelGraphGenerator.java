package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.types.CGPoint;

import android.util.Log;

public class LevelGraphGenerator {
	private List<LevelGenNode> nodes;
	private int lastGeneratedComplexity;
	private int currentComplexity;
	private int currentMaxComplexity;
	private int previousPickComplexity;
	private BlocDirection lastDirection;
	private int topCount;
	private int rightCount;
	private int bottomCount;
	private int leftCount;
	private Level currentLevel;
	private int bossComplexity;
	private Random randomGenerator;
	
	public LevelGraphGenerator() {
		this.nodes = new ArrayList<LevelGenNode>();
		this.randomGenerator = new Random();
	}
	
	public void attach(Level level) {
		this.currentLevel = level;
		
		BlocHardInit.InitHardCoded();
	}
	
	public void detach() {
		this.currentLevel = null;
	}
	
	public void destroy() {
		this.currentLevel = null;
		this.nodes.clear();
	}
	
	public void addNode(LevelGenNode levelGenNode) {
		this.nodes.add(levelGenNode);
	}

	public int getNodeCount() {
		return this.nodes.size();
	}
	
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
			if (node.ConnectAndGoTo(source, goToDirection)) {
				// hack for multi entry / exit test blocks
				if (!node.isLevelStart() && !node.isLevelEnd() && !node.isBoss()) {
					selection.add(node);
				}
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
	
	public BlocDirection getRandomDirection() {
		 return this.getRandomDirection(null);
	}
	
	public BlocDirection getRandomDirection(BlocDirection constrained) {		
		 int dir = randomGenerator.nextInt(4);		 
		 BlocDirection direction = BlocDirection.Top;
		 switch(dir) {
		 	case 0:
		 		direction = BlocDirection.Top;
		 		break;
		 	case 1:
		 		direction = BlocDirection.Right;
		 		break;
		 	case 2:
		 		direction = BlocDirection.Bottom;
		 		break;
		 	case 3:
		 		direction = BlocDirection.Left;
		 		break;
		 	default:
		 		direction = this.getRandomDirection(constrained);
		 		break;
		 }
		 
		 // Interdit de tirer l'inverse de la direction précédente
		 if (direction == LevelGenNode.getMirror(this.lastDirection)) {
			 direction = this.getRandomDirection(constrained);
		 }
				
		if (direction == constrained) {
			direction = this.getRandomDirection(constrained);
		}
		
		this.lastDirection = direction;
		
		return direction;
	}

	public LevelGenNode pickStart() {
		return this.pickStart(this.getRandomDirection());
	}

	public LevelGenNode pickNext(LevelGenNode source) {
		return this.pickNext(source, this.getRandomDirection());
	}

	public void generate(int maxComplexity, BlocDirection constrained) {
		this.initCount();
		this.currentComplexity = 0;
		this.previousPickComplexity = 0;		
		this.currentMaxComplexity = maxComplexity;
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
		if (this.currentMaxComplexity >= this.getBossComplexity()) {
			pick = this.pickBoss(pick);
		} else {
			pick = this.pickEnd(pick);
		}
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, false);
		
		if (this.currentLevel != null) {
			int xSize = this.rightCount + this.leftCount + 1;
			int ySize = this.topCount + this.bottomCount + 1;
			
			this.currentLevel.setLevelSize(
					BlocDefinition.BlocWidth * xSize,
					BlocDefinition.BlocHeight * ySize);
			
			this.changeReferenceToZero();
			
			LevelUtil.createGroundBox(this.currentLevel);
		}
		
		this.lastGeneratedComplexity = currentComplexity;
	}
	
	private void changeReferenceToZero() {
		int xShift = 0;
		int yShift = 0;
		if (this.getXOffset() < 0 ) {
			xShift = this.getXOffset() * BlocDefinition.BlocWidth; 
		}
		
		if (this.getYOffset() < 0 ) {
			yShift = this.getYOffset() * BlocDefinition.BlocHeight; 
		}
		
		this.currentLevel.setLevelOrigin(CGPoint.make(xShift, yShift));		
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
	
	private void handlePick(LevelGenNode pick, boolean countDirection) {
		BlocDefinition bloc = pick.getBlocDefinition();
		if (bloc != null) {
			int xOffSet = this.getXOffset();
			int yOffSet = this.getYOffset();
			bloc.buildLevel(this.currentLevel, xOffSet, yOffSet);
		}
		
		this.currentComplexity += pick.getComplexity();
		this.previousPickComplexity = pick.getComplexity();
		if (countDirection) {
			this.count(this.lastDirection);
		}
	}
	
	private int getXOffset() {
		return this.rightCount - this.leftCount;		
	}
	
	private int getYOffset() {
		return this.topCount - this.bottomCount;
	}

	public int getLastGeneratedComplexity() {
		return this.lastGeneratedComplexity;
	}
	
	private void count(BlocDirection direction) {
		switch(direction) {
		case Top:
			this.topCount++;
			break;
		case Right:
			this.rightCount++;
			break;
		case Bottom:
			this.bottomCount++;
			break;
		case Left:
			this.leftCount++;
			break;
		default:
			break;
		}
	}
	
	private void initCount() {
		this.topCount = 0;
		this.rightCount = 0;
		this.bottomCount = 0;
		this.leftCount = 0;
	}
	
	public int getTopCount() {
		return this.topCount;
	}
	
	public int getRightCount() {
		return this.rightCount;
	}
	
	public int getBottomCount() {
		return this.bottomCount;
	}
	
	public int getLeftCount() {
		return this.leftCount;
	}

	public int getBossComplexity() {
		return bossComplexity;
	}

	public void setBossComplexity(int bossComplexity) {
		this.bossComplexity = bossComplexity;
	}
	
	public LevelGenNode pickFromCompatible(List<LevelGenNode> list) {
		LevelGenNode selected = null;		
		
		if (list != null && list.size() > 0) {
			int idx = 0;
			int min = this.previousPickComplexity;
			int max = this.currentMaxComplexity - this.currentComplexity;
			List<LevelGenNode> listGen = new ArrayList<LevelGenNode>();
			
			List<LevelGenNode> listGenOpt = null;
			List<LevelGenNode> listGenOpt1 = new ArrayList<LevelGenNode>();
			List<LevelGenNode> listGenOpt2 = new ArrayList<LevelGenNode>();
			
			// Option 1: complexity grows and complexity does not exceed left complexity
			// But complexity must always grow if possible
			if (max > min) {
				listGenOpt = listGenOpt1;
				for(LevelGenNode node : list) {
					if (node.getComplexity() > min && node.getComplexity() <= max) {
						listGenOpt1.add(node);
					}										
				}
			}else {
				// Option 2: Complexity grows
				listGenOpt = listGenOpt2;
				for(LevelGenNode node : list) {
					if (node.getComplexity() > min) {
						listGenOpt2.add(node);
					}
				}								
			}
				
			if (listGenOpt.size() > 0) {
				listGen = listGenOpt;
			} else {
				// No draw with rules possible, use full list
				// todo: pick item in inverse order if complexity to limit complexity drop?
				listGen = list;
			}
			
			idx = this.randomGenerator.nextInt(listGen.size());						
			selected = listGen.get(idx);
		}
		
		return selected;
	}
}