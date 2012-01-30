package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

// For future, multiple connector should exclude unwanted direction by grouping connectors in direction)
public class LevelGenNode {
	private Set<Integer> connectorsEntry;
	private Set<Integer> connectorsExit;
	private boolean isLevelStart;
	private boolean isLevelEnd;
	private int complexity;
	private BlocDefinition blocDefinition;
	
	public LevelGenNode() {
		this.connectorsEntry = new HashSet<Integer>();
		this.connectorsExit = new HashSet<Integer>();
	}
	
	public void addConnectorEntry(int connector) {
		addConnector(this.connectorsEntry, connector);
	}
	
	public void addConnectorExit(int connector) {
		addConnector(this.connectorsExit, connector);
	}
	
	public static void addConnector(Set<Integer> connectors, int connector) {
		connectors.add(connector);
	}
	
	public void addConnectorsEntry(List<Integer> connectorsToAdd) {
		addConnectors(this.connectorsEntry, connectorsToAdd);
	}
	
	public void addConnectorsExit(List<Integer> connectorsToAdd) {
		addConnectors(this.connectorsExit, connectorsToAdd);
	}
	
	public static void addConnectors(Set<Integer> connectors, List<Integer> connectorsToAdd) {
		for(Integer connector : connectorsToAdd) {
			connectors.add(connector);
		}
	}

	public int getConnectorEntryCount() {
		return getConnectorCount(this.connectorsEntry);
	}
	
	public int getConnectorExitCount() {
		return getConnectorCount(this.connectorsExit);
	}
	
	public static int getConnectorCount(Set<Integer> connectors) {
		return connectors.size();
	}
	
	public Set<Integer> getConnectorEntryList() {
		return this.connectorsEntry;
	}
	
	public Set<Integer> getConnectorExitList() {
		return this.connectorsExit;
	}

	public boolean isEntryConnectedTo(int connector) {
		return isConnectedTo(this.connectorsEntry, connector);
	}
	
	public boolean isExitConnectedTo(int connector) {
		return isConnectedTo(this.connectorsExit, connector);
	}
	
	public static boolean isConnectedTo(Set<Integer> connectors, int connector) {
		boolean connected = false;
		for(Integer connection : connectors) {
			// Source here is connector
			connected = isConnected(connector, connection);
			if (connected) break;
		}
		
		return connected;
	}

	public static boolean isConnected(int connectorSource, int connectorTarget) {
		return Math.abs(connectorSource - connectorTarget) == 3;
	}

	public boolean isEntryConnectedTo(LevelGenNode nodeSource) {
		boolean isConnected = false;
		for(Integer connector : nodeSource.getConnectorExitList()) {
			isConnected = this.isEntryConnectedTo(connector);
			if (isConnected) break;
		}
		
		return isConnected;
	}

	public boolean isEntryConnectedMultiple(List<List<Integer>> list) {
		boolean isConnected = false;
		
		if (list != null) {
			for(List<Integer> connectorList : list) {
				for(Integer connector : connectorList) {
					isConnected = this.isEntryConnectedTo(connector);
					if (isConnected) break;
				}
				
				if (!isConnected) break;
			}
		}
		
		return isConnected;
	}

	public boolean isEntryConnectedAtLeastOne(List<Integer> list) {
		boolean connected = false;
		if (list != null) {
			for(Integer connector : list) {
				connected = this.isEntryConnectedTo(connector);
				if (connected) break;
			}
		}
		
		return connected;
	}
	
	public boolean isExitConnectedAtLeastOne(List<Integer> list) {
		boolean connected = false;
		if (list != null) {
			for(Integer connector : list) {
				connected = this.isExitConnectedTo(connector);
				if (connected) break;
			}
		}
		
		return connected;
	}
	
	public boolean isExitConnectedMultiple(List<List<Integer>> list) {
		boolean isConnected = false;
		
		if (list != null) {
			for(List<Integer> connectorList : list) {
				for(Integer connector : connectorList) {
					isConnected = this.isExitConnectedTo(connector);
					if (isConnected) break;
				}
				
				if (!isConnected) break;
			}
		}
		
		return isConnected;
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
	
	public static BlocDirection getMirror(BlocDirection direction) {
		if (direction != null) {
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
		else {
			return null;
		}
	}

	public boolean ConnectAndGoTo(List<Integer> list, BlocDirection goToDirection) {
		List<Integer> directionConnectors = this.getConnectorsFor(this.getMirror(goToDirection));		
		return this.isEntryConnectedAtLeastOne(list) && this.isExitConnectedAtLeastOne(directionConnectors);
	}
	
	public List<List<Integer>> getMatchList(List<Integer> list1, List<Integer> list2) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		return list;
	}

	public boolean goTo(BlocDirection goToDirection) {
		return this.isExitConnectedAtLeastOne(this.getConnectorsFor(this.getMirror(goToDirection)));
	}

	public boolean ConnectAndGoTo(LevelGenNode sourceNode, BlocDirection goToDirection) {
		return this.ConnectAndGoTo(new ArrayList<Integer>(sourceNode.getConnectorExitList()), goToDirection);
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

	public void setComplexity(int value) {
		this.complexity = value;		
	}

	public int getComplexity() {
		return this.complexity;
	}

	public boolean isLevelEndAndConnect(LevelGenNode sourceNode) {
		return this.isLevelEnd && this.isEntryConnectedTo(sourceNode);
	}

	public void setBlocDefinition(BlocDefinition blocDefinition) {
		this.blocDefinition = blocDefinition;		
	}
	
	public BlocDefinition getBlocDefinition() {
		return this.blocDefinition;
	}
}
