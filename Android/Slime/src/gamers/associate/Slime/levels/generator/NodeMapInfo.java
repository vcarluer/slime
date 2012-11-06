package gamers.associate.Slime.levels.generator;

public class NodeMapInfo {
	public NodeMapInfo(int x, int y) {
		this.position = new MapPoint(x, y);
	}
	
	public MapPoint position;
	public boolean isStart;
	public boolean isEnd;
}
