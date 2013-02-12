package gamers.associate.SlimeAttack.levels.generator;

import android.annotation.SuppressLint;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.game.SurvivalGame;
import gamers.associate.SlimeAttack.game.TimeAttackGame;
import gamers.associate.SlimeAttack.levels.GamePlay;
import gamers.associate.SlimeAttack.levels.LevelUtil;
import gamers.associate.SlimeAttack.levels.generator.hardcoded.BlocHardInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.cocos2d.types.CGPoint;

@SuppressLint("DefaultLocale") public abstract class LevelGraphGeneratorBase {
	protected static boolean debugBlocOn = SlimeFactory.IsDebugBlocOn;
	protected static String forceBlock = SlimeFactory.ForceBlockPath;
	
	protected static final int timeCalcBase = 20;
	protected static final float timeCalcPerBlock = 8f;
	protected static final int timeCritic = 5;
	
	protected static final int maxWidth = 3; // -1
	protected static final int maxAddHeight = 2; // -1
	
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
	protected int totalCount;
	protected Level currentLevel;
	protected Random randomGenerator;
	
	protected int minX;
	protected int maxX;
	protected int minY;
	protected int maxY;
	
	protected int lvlWidth;
	protected int lvlHeight;
	
	private List<String> assets;
	
	protected ArrayList<CGPoint> blockMap;
	
	public LevelGraphGeneratorBase() {
		this.nodes = new ArrayList<LevelGenNode>();
		this.randomGenerator = new Random();
		this.assets = new ArrayList<String>();
		this.blockMap = new ArrayList<CGPoint>();
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

		 // Interdit de tirer l'inverse de la direction pr�c�dente
		 if (direction == LevelGenNode.getMirror(this.lastDirection)) {
			 direction = this.getRandomDirection(constrained);
		 }
				
		if (direction == constrained) {
			direction = this.getRandomDirection(constrained);
		}
		
		this.lastDirection = direction;
		
		return direction;
	}	
	
	public void generate(int maxComplexity, GamePlay gamePlay) {			
		this.generate(maxComplexity, null, gamePlay);
	}
	
	public void generate(int maxComplexity, BlocDirection constrained, GamePlay gamePlay) {
		this.generate(maxComplexity, constrained, false, gamePlay);
	}
	
	public void generate(int maxComplexity, BlocDirection constrained, boolean isBoss, GamePlay gamePlay) {
		this.initCount();
		this.currentComplexity = 0;
		this.previousPickComplexity = 0;		
		this.currentMaxComplexity = maxComplexity;
		this.blockMap.clear();
		this.lvlWidth = 0;
		this.lvlHeight = 0;
			
		this.computeLevelSize(isBoss);
		this.generateInternal(maxComplexity, constrained, isBoss, gamePlay);
		
		this.postGenerate();		
	}
	
	protected abstract void generateInternal(int maxComplexity, BlocDirection constrained, boolean isBoss, GamePlay gamePlay);

	protected void postGenerate() {
		if (this.currentLevel != null) {
			int xSize = this.maxX - this.minX + 1;
			int ySize = this.maxY - this.minY + 1;
			
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
		int xShift = this.minX * BlocDefinition.BlocWidth;
		int yShift = this.minY * BlocDefinition.BlocHeight;
		
		this.currentLevel.setLevelOrigin(xShift, yShift);		
	}
	
	protected int currentX;
	protected int currentY;
	
	protected void handlePick(LevelGenNode pick, boolean countDirection) {		
		this.handlePick(pick, this.currentX, this.currentY);
		
		if (countDirection) {
			this.count(this.lastDirection);
			this.Forward(this.lastDirection);
		}
	}
	
	protected void handlePick(LevelGenNode pick, int x , int y) {		
		this.blockMap.add(CGPoint.make(x, y));
		
		BlocDefinition bloc = pick.getBlocDefinition();
		if (bloc != null) {
			bloc.buildLevel(this.currentLevel, x, y);
		}
		
		this.currentComplexity += pick.getComplexity();
		this.previousPickComplexity = pick.getComplexity();
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
		
		this.totalCount++;
	}
	
	protected void Forward(BlocDirection direction) {
		switch(direction) {
		case Top:
			this.currentY++;;
			break;
		case Right:
			this.currentX++;
			break;
		case Bottom:
			this.currentY--;
			break;
		case Left:
			this.currentX--;
			break;
		default:
			break;
		}
		
		if (this.currentX < this.minX) this.minX = this.currentX;
		if (this.currentX > this.maxX) this.maxX = this.currentX;
		if (this.currentY < this.minY) this.minY = this.currentY;
		if (this.currentY > this.maxY) this.maxY = this.currentY;
	}
	
	protected void initCount() {
		this.topCount = 0;
		this.rightCount = 0;
		this.bottomCount = 0;
		this.leftCount = 0;
		this.totalCount = 0;
		
		this.minX = 0;
		this.maxX = 0;
		this.minY = 0;
		this.maxY = 0;
		
		this.currentX = 0;
		this.currentY = 0;
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
				selected = pickBlockByName(forceBlock, list);
				if (selected != null) {
					return selected;
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

	protected LevelGenNode pickBlockByName(String resourceName) {	
		return this.pickBlockByName(resourceName, this.nodes);
	}
	protected LevelGenNode pickBlockByName(String resourceName, List<LevelGenNode> list) {
		for(LevelGenNode node : list) {
			if (node.getBlocDefinition().getResourceName().toUpperCase().equals(resourceName.toUpperCase())) {
				return node;
			}
		}
		
		return null;
	}

	public List<String> getAssetsBase() {
		if (this.assets.size() == 0) {
			this.initAssets(this.assets);
		}

		return this.assets;
	}
	
	protected abstract void initAssets(List<String> assetsList);
	
	protected void addGamePlay(int blocCount, GamePlay gamePlay) {
		// Compute total time et critic time here
		this.currentLevel.removeCurrentGamePlay();
		
		if (gamePlay == GamePlay.TimeAttack) {
			TimeAttackGame taGame = TimeAttackGame.NewGame();
			this.currentLevel.addGamePlay(taGame);
					
			int baseTime = timeCalcBase - SlimeFactory.GameInfo.getDifficulty();
			int secPerBloc = Math.round(timeCalcPerBlock / SlimeFactory.GameInfo.getDifficulty());
			int totalTime = baseTime + (blocCount) * secPerBloc; 
			taGame.setStartTime(totalTime);
			// 10% or other or fix?
			int critic = timeCritic;
			taGame.setCriticTime(critic);
		}
		
		if (gamePlay == GamePlay.Survival) {
			SurvivalGame sGame = SurvivalGame.NewGame();
			this.currentLevel.addGamePlay(sGame);
		}
	}
	
	protected abstract void computeLevelSize(boolean isBoss);
	
	protected int getLvlBlockCount() {
		return this.lvlWidth * this.lvlHeight;
	}
	
	protected int getRatioDiff(int val) {
		int ratioVal = 1;
		switch (SlimeFactory.GameInfo.getDifficulty()) {
			default:
			case LevelDifficulty.Easy: ratioVal = Math.round(val / 4f); break;
			case LevelDifficulty.Normal: ratioVal = Math.round(val / 2f); break;
			case LevelDifficulty.Hard: ratioVal = Math.round(val * 3f / 4f); break;
			case LevelDifficulty.Extrem: ratioVal = val; break;
		}
		
		return ratioVal;
	}
	
	protected void computeLevelWidth(int lgMax) {
		float tmp = SlimeFactory.GameInfo.getLevelNum();
		if (this.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.TimeAttack && SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Easy) {
			tmp -=  LevelGraphGeneratorTutorial.tutorialCount;
		}
		
		tmp = tmp * lgMax;
		if (this.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.TimeAttack) {
			tmp = tmp / SlimeFactory.PackageManager.getCurrentPackage().getLevelCount();
		}
		
		if (this.currentLevel.getLevelDefinition().getGamePlay() == GamePlay.Survival) {
			tmp = tmp / SlimeFactory.GameInfo.getLevelMax();
		}
		
		lvlWidth = Math.round(tmp);
	}
}