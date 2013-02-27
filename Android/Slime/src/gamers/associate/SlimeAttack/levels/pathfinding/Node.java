package gamers.associate.SlimeAttack.levels.pathfinding;

public class Node {
	public int x, y;
	
	public Node(int i, int j) {
		x = i;
		y = j;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Node) {
			Node n = (Node) obj;
			return (x == n.x && y == n.y);
		}
		else return false;
	}
}
