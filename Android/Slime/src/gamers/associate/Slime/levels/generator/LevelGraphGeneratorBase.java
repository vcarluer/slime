package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelUtil;
import gamers.associate.Slime.levels.generator.hardcoded.BlocHardInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.types.CGPoint;

public abstract class LevelGraphGeneratorBase {
	protected static boolean debugBlocOn = true;
	protected static String forceBlock = "blocsRectangle/br_2.slime";
	
	protected List<LevelGenNode> nodes;
	protected int lastGeneratedComplexity;
	protected int currentComplexity;
	protected int currentMaxComplexity;
	protected int previousPickComplexity;
	protected BlocDirection lastDirection;
	protected int topCount;
	protected int rightCount;
	protected int bottomCount;
	protected int leftCount;
	protected Level currentLevel;
	protected Random randomGenerator;
	
	public LevelGraphGeneratorBase() {
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
	
	public void generate(int maxComplexity) {			
		this.generate(maxComplexity, null);
	}
	
	public void generate(int maxComplexity, BlocDirection constrained) {
		this.generate(maxComplexity, constrained, false);
	}
	
	public void generate(int maxComplexity, BlocDirection constrained, boolean isBoss) {
		this.initCount();
		this.currentComplexity = 0;
		this.previousPickComplexity = 0;		
		this.currentMaxComplexity = maxComplexity;
			
		this.generateInternal(maxComplexity, constrained, isBoss);
		
		this.postGenerate();		
	}
	
	protected abstract void generateInternal(int maxComplexity, BlocDirection constrained, boolean isBoss);

	protected void postGenerate() {
		if (this.currentLevel != null) {
			int xSize = Math.max(this.rightCount, this.leftCount) + 1;
			int ySize = Math.max(this.topCount, this.bottomCount) + 1;
			
			this.currentLevel.setLevelSize(
					BlocDefinition.BlocWidth * xSize,
					BlocDefinition.BlocHeight * ySize);
			
			this.changeReferenceToZero();
						
			LevelUtil.createGroundBoxGlass(this.currentLevel);
			LevelUtil.createGroundBox(this.currentLevel);
		}
		
		this.lastGeneratedComplexity = currentComplexity;
	}
	
	protected void changeReferenceToZero() {
		int xShift = 0;
		int yShift = 0;
		if (this.getXOffset() < 0 ) {
			xShift = this.getXOffset() * BlocDefinition.BlocWidth; 
		}
		
		if (this.getYOffset() < 0 ) {
			yShift = this.getYOffset() * BlocDefinition.BlocHeight; 
		}
		
		this.currentLevel.setLevelOrigin(xShift, yShift);		
	}
	
	protected void handlePick(LevelGenNode pick, boolean countDirection) {
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
	
	protected int getXOffset() {
		return this.rightCount - this.leftCount;		
	}
	
	protected int getYOffset() {
		return this.topCount - this.bottomCount;
	}

	public int getLastGeneratedComplexity() {
		return this.lastGeneratedComplexity;
	}
	
	protected void count(BlocDirection direction) {
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
	
	protected void initCount() {
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
	
	protected LevelGenNode pickFromCompatible(List<LevelGenNode> list) {
		LevelGenNode selected = null;		
		
		if (list != null && list.size() > 0) {
			
			if (debugBlocOn) {
				for(LevelGenNode node : list) {
					if (node.getBlocDefinition().getResourceName().toUpperCase().equals(forceBlock.toUpperCase())) {
						return node;
					}
				}
			}
			
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

	public abstract String getAssetsBase();
}