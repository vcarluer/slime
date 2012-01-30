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
	private BlocDirection lastDirection;
	private int topCount;
	private int rightCount;
	private int bottomCount;
	private int leftCount;
	private Level currentLevel;
	
	public LevelGraphGenerator() {
		this.nodes = new ArrayList<LevelGenNode>();
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
		 return this.getRandomDirection(null);
	}
	
	public BlocDirection getRandomDirection(BlocDirection constrained) {
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
		Log.d(Slime.TAG, "Picking start node with constraint " + String.valueOf(constrained));
		LevelGenNode pick = this.pickStartConstrained(constrained);			
		Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
		this.handlePick(pick, true);		
		while (currentComplexity < maxComplexity) {
			LevelGenNode prev = pick;
			Log.d(Slime.TAG, "Picking next node with constraint " + String.valueOf(constrained));
			pick = this.pickNextConstrained(pick, constrained);
			Log.d(Slime.TAG, "picked: " + String.valueOf(pick.getId()));
			this.handlePick(pick, true);	
		}
		
		Log.d(Slime.TAG, "Picking end node");
		pick = this.pickEnd(pick);
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
		
		currentComplexity += pick.getComplexity();
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
}