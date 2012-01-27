package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelGraphGenerator {
	private List<LevelGenNode> nodes;
	private int lastGeneratedComplexity;
	private int currentComplexity;
	private BlocDirection lastDirection;
	private int topCount;
	private int rightCount;
	private int bottomCount;
	private int leftCount;
	
	public LevelGraphGenerator() {
		this.nodes = new ArrayList<LevelGenNode>();
	}
	
	public void addNode(LevelGenNode levelGenNode) {
		this.nodes.add(levelGenNode);
	}

	public int getNodeCount() {
		return this.nodes.size();
	}
	
	public LevelGenNode pickStart(BlocDirection goToDirection) {
		LevelGenNode pick = null;
		for(LevelGenNode node : this.nodes) {
			if (node.isStartAndGoTo(goToDirection)) {
				pick = node;
				break;
			}
		}
				
		return pick;
	}

	public LevelGenNode pickNext(LevelGenNode source, BlocDirection goToDirection) {
		LevelGenNode pick = null;
		for(LevelGenNode node : this.nodes) {
			if (node.ConnectAndGoTo(source, goToDirection)) {
				pick = node;
				break;
			}
		}
				
		return pick;
	}

	public LevelGenNode pickEnd(LevelGenNode source) {		
		LevelGenNode pick = null;
		for(LevelGenNode node : this.nodes) {
			if (node.isLevelEndAndConnect(source)) {
				pick = node;
				break;
			}
		}
				
		return pick;
	}	
	
	public BlocDirection getRandomDirection() {
		 Random randomGenerator = new Random();
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
		 		direction = this.getRandomDirection();
		 		break;
		 }
		 
		 // Interdit de tirer l'inverse de la direction précédente
		 if (direction == LevelGenNode.getMirror(this.lastDirection)) {
			 direction = this.getRandomDirection();
		 }
		 
		 this.lastDirection = direction;
		 
		 return direction;
	}
	
	public BlocDirection getRandomDirection(BlocDirection constrained) {
		BlocDirection direction = this.getRandomDirection();
		if (direction == constrained) {
			direction = this.getRandomDirection(constrained);
		}
		
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
		LevelGenNode pick = this.pickStartConstrained(constrained);			
		this.handlePick(pick, true);		
		while (currentComplexity < maxComplexity) {
			pick = this.pickNextConstrained(pick, constrained);
			this.handlePick(pick, true);	
		}
				
		pick = this.pickEnd(pick);
		this.handlePick(pick, false);
		this.lastGeneratedComplexity = currentComplexity;
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
		// Handle level creation here
		// Offset is directionCount
		
		currentComplexity += pick.getComplexity();
		if (countDirection) {
			this.count(this.lastDirection);
		}
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
}