package gamers.associate.SlimeAttack.levels.pathfinding;

import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;

import java.util.ArrayList;
import java.util.List;

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
	
	public LevelPathFinder() {
		this.wallPlatforms = new ArrayList<GameItem>();
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
		return getNodeFrom(this.startItem);
	}
	
	private Node getGoalNode() {		
		return getNodeFrom(this.goal);
	}
	
	private Node getNodeFrom(GameItem item) {
		int i = getIdxIFrom(item.getPosition().x);
		int j = getIdxJFrom(item.getPosition().y);
		Node node = new Node(i, j);
		
		return node;
		
	}
	
	private int getIdxIFrom(float length) {
		return (int) FloatMath.ceil(length / BlocSizeW);
	}
	
	private int getIdxJFrom(float length) {
		// Level height added to handle negative coordinates... Arg. No way to have minimul y in level for now.
		return (int) FloatMath.ceil((length + this.levelSize.height) / BlocSizeH);
	}
	
	private int getArrayWidth() {
		return getIdxIFrom(this.levelSize.width);
	}
	
	private int getArrayHeight() {
		return getIdxJFrom(this.levelSize.height);
	}
	
	private boolean[][] getWallArray() {
		int sizeI = this.getArrayWidth();
		int sizeJ = this.getArrayHeight();
		boolean[][] walls = new boolean[sizeI][sizeJ];
		
		for(GameItem wall : this.wallPlatforms) {
			int i = this.getIdxIFrom(wall.getPosition().x);
			int j = this.getIdxJFrom(wall.getPosition().y);
			walls[i][j] = true;
		}
		
		return walls;
	}
	
	public List<CGPoint> pathFinding() {
		List<CGPoint> points = new ArrayList<CGPoint>();
		List<Node> nodes = Pathfinder.generate(getStartNode(), getGoalNode(), getWallArray());		
		for(Node node : nodes) {
			CGPoint point = CGPoint.make(node.x * BlocSizeW + BlocSizeW / 2f, node.y * BlocSizeH - this.levelSize.height + BlocSizeH / 2f);
			points.add(point);
		}
		
		return points;
	}
}
