package gamers.associate.SlimeAttack.levels.pathfinding;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;

import java.util.ArrayList;
import java.util.List;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

import android.util.FloatMath;

public class LevelPathFinder {
	private List<GameItem> wallPlatforms;
	private CGSize levelSize;
	private GameItem startItem;
	private GameItem goal;
	private static int BlocSizeW = BlocDefinition.Default_Bloc_Width;
	private static int BlocSizeH = BlocDefinition.Default_Bloc_Height;
	private static float sw;
	private static float sh;
	
	public LevelPathFinder() {
		this.wallPlatforms = new ArrayList<GameItem>();
		Pathfinder.canCutCorners = true;	
	}
	
	public void reset() {
		this.wallPlatforms.clear();
	}
	
	public void addWall(GameItem wall) {
		this.wallPlatforms.add(wall);
	}

	public CGSize getLevelSize() {
		return levelSize;
	}

	public void setLevelSize(CGSize levelSize) {
		this.levelSize = levelSize;
	}

	public void setStartItem(GameItem startItem) {
		this.startItem = startItem;
	}

	public void setGoal(GameItem goal) {
		this.goal = goal;
	}
	
	private Node getStartNode() {
		SlimeFactory.Log.d(SlimeAttack.TAG, "Start node");
		return getNodeFrom(this.startItem);
	}
	
	private Node getGoalNode() {		
		SlimeFactory.Log.d(SlimeAttack.TAG, "Goal node");
		return getNodeFrom(this.goal);
	}
	
	private Node getNodeFrom(GameItem item) {
		int i = getIdxIFrom(item.getPosition().x) + 1;
		int j = getIdxJFrom(item.getPosition().y) + 1;
		SlimeFactory.Log.d(SlimeAttack.TAG, "i, j: " + String.valueOf(i) + ", " + String.valueOf(j));
		Node node = new Node(i, j);
		
		return node;
		
	}
	
	private float levelPathWidth() {
		return this.levelSize.width - sw;
	}
	
	private float levelPathHeight() {
		return this.levelSize.height - sh;
	}
	
	private int getIdxIFrom(float length) {
		return (int) FloatMath.floor(length / BlocSizeW);
	}
	
	private int getIdxJFrom(float length) {
		// Level height added to handle negative coordinates... Arg. No way to have minimum y in level for now.
		return (int) FloatMath.floor((length + levelPathHeight()) / BlocSizeH);
	}
	
	private int getArrayWidth() {
		return getIdxIFrom(levelPathWidth()) + 2;
	}
	
	private int getArrayHeight() {
		return getIdxJFrom(levelPathHeight()) + 2;
	}
	
	private boolean[][] getWallArray() {
		int sizeI = this.getArrayWidth();
		int sizeJ = this.getArrayHeight();
		boolean[][] walls = new boolean[sizeI][sizeJ];
		
		for(int i = 0; i < sizeI; i++) {
			walls[i][0] = true;
			walls[i][sizeJ - 1] = true;
		}
		
		for(int j = 0; j < sizeJ; j++) {
			walls[0][j] = true;
			walls[sizeI - 1][j] = true;
		}
		
		for(GameItem wall : this.wallPlatforms) {
			int i = this.getIdxIFrom(wall.getPosition().x) + 1;
			int j = this.getIdxJFrom(wall.getPosition().y) + 1;
			walls[i][j] = true;
		}
		
		return walls;
	}
	
	public List<CGPoint> pathFinding() {
		sw = CCDirector.sharedDirector().displaySize().width;
		sh = CCDirector.sharedDirector().displaySize().height;
		
		List<CGPoint> points = new ArrayList<CGPoint>();
		Node start = getStartNode();
		Node goal = getGoalNode();
		boolean[][] walls = getWallArray();
		
		if (SlimeFactory.debugPathfinding) {
			this.debugPathfindingBefore(goal, start, walls);
		}
		
		List<Node> nodes = Pathfinder.generate(goal, start, walls);
		
		if (SlimeFactory.debugPathfinding) {
			this.debugPathfindingAfter(goal, start, walls, nodes);
		}
		
		for(Node node : nodes) {
			CGPoint point = CGPoint.make((node.x - 1) * BlocSizeW + BlocSizeW / 2f, (node.y - 1) * BlocSizeH + BlocSizeH / 2f - levelPathHeight());
			points.add(point);
		}
		
		return points;
	}

	private void debugPathfindingBefore(Node start, Node finish, boolean[][] mapWalls) {
		StringBuilder builder = new StringBuilder();
		for(int j = mapWalls[0].length - 1; j >=0 ; j--) {
			for(int i = 0; i < mapWalls.length; i++) {			
				if (mapWalls[i][j]) {
					builder.append("W");
				} else {
					if (start.x == i && start.y == j) {
						builder.append("S");
					} else {
						if (finish.x == i && finish.y == j) {
							builder.append("G");
						} else {
							builder.append("X");
						}
					}
				}
			}
			
			builder.append("\n");
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, builder.toString());
	}
	
	private void debugPathfindingAfter(Node start, Node finish, boolean[][] mapWalls, List<Node> solution) {
		StringBuilder builder = new StringBuilder();
		for(int j = mapWalls[0].length - 1; j >=0 ; j--) {
			for(int i = 0; i < mapWalls.length; i++) {			
				if (mapWalls[i][j]) {
					builder.append("W");
				} else {
					if (start.x == i && start.y == j) {
						builder.append("S");
					} else {
						if (finish.x == i && finish.y == j) {
							builder.append("G");
						} else {
							boolean found = false;
							for(Node node : solution) {
								if (node.x == i && node.y == j) {
									builder.append("T");
									found = true;
									break;
								}
							}
							
							if (!found) {
								builder.append("X");
							}
						}
					}
				}
			}
			
			builder.append("\n");
		}
		
		SlimeFactory.Log.d(SlimeAttack.TAG, builder.toString());
	}
}
