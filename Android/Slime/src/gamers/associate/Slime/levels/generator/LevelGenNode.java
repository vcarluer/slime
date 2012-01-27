package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

// For future, multiple connector should exclude unwanted direction by grouping connectors in direction)
public class LevelGenNode {
	private Set<Integer> connectors;
	private boolean isLevelStart;
	private boolean isLevelEnd;
	
	public LevelGenNode() {
		this.connectors = new HashSet<Integer>();
	}
	
	public void addConnector(int connector) {
		this.connectors.add(connector);
	}

	public int getConnectorCount() {
		return connectors.size();
	}
	
	public Set<Integer> getConnectorList() {
		return connectors;
	}

	public boolean isConnectedTo(int connector) {
		boolean connected = false;
		for(Integer connection : this.connectors) {
			// Source here is connector
			connected = this.isConnected(connector, connection);
			if (connected) break;
		}
		
		return connected;
	}

	public boolean isConnected(int connectorSource, int connectorTarget) {
		return Math.abs(connectorSource - connectorTarget) == 3;
	}

	public boolean isConnectedTo(LevelGenNode nodeSource) {
		boolean isConnected = false;
		for(Integer connector : nodeSource.getConnectorList()) {
			isConnected = this.isConnectedTo(connector);
			if (isConnected) break;
		}
		
		return isConnected;
	}

	public boolean isConnectedMultiple(List<List<Integer>> list) {
		boolean isConnected = false;
		
		if (list != null) {
			for(List<Integer> connectorList : list) {
				for(Integer connector : connectorList) {
					isConnected = this.isConnectedTo(connector);
					if (isConnected) break;
				}
				
				if (!isConnected) break;
			}
		}
		
		return isConnected;
	}

	public boolean isConnectedAtLeastOne(List<Integer> list) {
		boolean connected = false;
		if (list != null) {
			for(Integer connector : list) {
				connected = this.isConnectedTo(connector);
				if (connected) break;
			}
		}
		
		return connected;
	}

	public List<Integer> getConnectorsFor(BlocDirection direction) {
		List<Integer> list = new ArrayList<Integer>();
		switch (direction) {
		case Top: 
			list.add(Connector.TopLeft);
			list.add(Connector.TopMid);
			list.add(Connector.TopRight);
			break;			
		case Right: 			
			list.add(Connector.RightTop);
			list.add(Connector.RightMid);
			list.add(Connector.RightBottom);
			break;
		case Bottom:
			list.add(Connector.BottomLeft);
			list.add(Connector.BottomMid);
			list.add(Connector.BottomRight);			
			break;
		case Left: 
			list.add(Connector.LeftTop);
			list.add(Connector.LeftMid);
			list.add(Connector.LeftBottom);			
			break;			
		default:
			break;
		}
		
		return list;
	}
	
	public BlocDirection getMirror(BlocDirection direction) {
		switch (direction) {
		case Top: 
			return BlocDirection.Bottom;
		case Right: 			
			return BlocDirection.Left;
		case Bottom:
			return BlocDirection.Top;
		case Left: 
			return BlocDirection.Right;
		default:
			return null;
		}
	}

	public boolean ConnectAndGoTo(List<Integer> list, BlocDirection goToDirection) {
		List<Integer> directionConnectors = this.getConnectorsFor(this.getMirror(goToDirection));		
		return this.isConnectedMultiple(this.getMatchList(list, directionConnectors));
	}
	
	public List<List<Integer>> getMatchList(List<Integer> list1, List<Integer> list2) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		return list;
	}

	public boolean goTo(BlocDirection goToDirection) {
		return this.isConnectedAtLeastOne(this.getConnectorsFor(this.getMirror(goToDirection)));
	}

	public boolean ConnectAndGoTo(LevelGenNode sourceNode, BlocDirection goToDirection) {
		return this.ConnectAndGoTo(new ArrayList<Integer>(sourceNode.connectors), goToDirection);
	}

	public void setIsLevelStart(boolean value) {
		this.isLevelStart = value;
	}

	public boolean isStartAndGoTo(BlocDirection goToDirection) {		
		return this.isLevelStart && this.goTo(goToDirection);
	}

	public void setIsLevelEnd(boolean value) {
		this.isLevelEnd = value;
	}

	public boolean isEndLevelAndGoTo(BlocDirection goToDirection) {
		return this.isLevelEnd && this.goTo(goToDirection);
	}
}
