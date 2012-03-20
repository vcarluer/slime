package gamers.associate.Slime.levels.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

// For future, multiple connector should exclude unwanted direction by grouping connectors in direction)
public class LevelGenNode {
	private Set<Integer> connectorsEntry;
	private Set<Integer> connectorsExit;
	private Set<Integer> connectors;
	private boolean isLevelStart;
	private boolean isLevelEnd;
	private boolean isBoss;
	private int complexity;
	private BlocDefinition blocDefinition;
	private String id;
	private boolean isStarBlock;
	
	public LevelGenNode() {
		this.connectorsEntry = new HashSet<Integer>();
		this.connectorsExit = new HashSet<Integer>();
		this.connectors = new HashSet<Integer>();
	}
	
	public void addConnectorEntry(int connector) {
		addConnector(this.connectorsEntry, connector);
		addConnector(this.connectors, connector);
	}
	
	public void addConnectorExit(int connector) {
		addConnector(this.connectorsExit, connector);
		addConnector(this.connectors, connector);
	}
	
	public void addConnector(int connector) {
		addConnector(this.connectors, connector);
	}
	
	public static void addConnector(Set<Integer> connectors, int connector) {
		connectors.add(connector);
	}
	
	public void addConnectorsEntry(List<Integer> connectorsToAdd) {
		addConnectors(this.connectorsEntry, connectorsToAdd);
		addConnectors(this.connectors, connectorsToAdd);
	}
	
	public void addConnectorsExit(List<Integer> connectorsToAdd) {
		addConnectors(this.connectorsExit, connectorsToAdd);
		addConnectors(this.connectors, connectorsToAdd);
	}
	
	public void addConnectors(List<Integer> connectorsToAdd) {
		addConnectors(this.connectors, connectorsToAdd);
		addConnectors(this.connectorsEntry, connectorsToAdd);
		addConnectors(this.connectorsExit, connectorsToAdd);
	}
	
	private static void addConnectors(Set<Integer> connectors, List<Integer> connectorsToAdd) {
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
	
	public int getConnectorCount() {
		return getConnectorCount(this.connectors);
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
	
	public Set<Integer> getConnectorList() {
		return this.connectors;
	}

	public boolean isConnectedTo(int connector) {
		return isConnectedTo(this.connectors, connector);
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
	
	public boolean isEntryConnectedTo(List<Integer>	list) {
		boolean isConnected = false;
		for(Integer connector : list) {
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

	public static List<Integer> getConnectorsFor(BlocDirection direction) {
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

	public boolean connectNoSpecialAndGoTo(List<Integer> list, BlocDirection goToDirection) {
		List<Integer> directionConnectors = getConnectorsFor(LevelGenNode.getMirror(goToDirection));		
		return this.isEntryConnectedAtLeastOne(list) && this.isExitConnectedAtLeastOne(directionConnectors) && this.isNoSpecial();
	}
	
	public List<List<Integer>> getMatchList(List<Integer> list1, List<Integer> list2) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		return list;
	}

	public boolean goTo(BlocDirection goToDirection) {
		return this.isExitConnectedAtLeastOne(getConnectorsFor(LevelGenNode.getMirror(goToDirection)));
	}

	public boolean connectNoSpecialAndGoTo(LevelGenNode sourceNode, BlocDirection goToDirection) {
		return this.connectNoSpecialAndGoTo(new ArrayList<Integer>(sourceNode.getConnectorExitList()), goToDirection);
	}
	
	public boolean isNoSpecial() {
		return !this.isLevelStart() && !this.isLevelEnd() && !this.isBoss();
	}

	public void setIsLevelStart(boolean value) {
		this.setLevelStart(value);
	}

	public boolean isStartAndGoTo(BlocDirection goToDirection) {		
		return this.isLevelStart() && this.goTo(goToDirection);
	}

	public void setIsLevelEnd(boolean value) {
		this.setLevelEnd(value);
	}

	public void setComplexity(int value) {
		this.complexity = value;		
	}

	public int getComplexity() {
		return this.complexity;
	}

	public boolean isLevelEndAndConnect(LevelGenNode sourceNode) {
		return this.isLevelEnd() && this.isEntryConnectedTo(sourceNode);
	}
	
	public boolean isLevelBossAndConnect(LevelGenNode sourceNode) {
		return this.isBoss && this.isEntryConnectedTo(sourceNode);
	}

	public void setBlocDefinition(BlocDefinition blocDefinition) {
		this.blocDefinition = blocDefinition;		
	}
	
	public BlocDefinition getBlocDefinition() {
		return this.blocDefinition;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	public boolean isLevelStart() {
		return isLevelStart;
	}

	public void setLevelStart(boolean isLevelStart) {
		this.isLevelStart = isLevelStart;
	}

	public boolean isLevelEnd() {
		return isLevelEnd;
	}

	public void setLevelEnd(boolean isLevelEnd) {
		this.isLevelEnd = isLevelEnd;
	}
	
	public boolean isExactlyConnectedTo(List<Integer> list) {
		boolean connected = false;
		if (list != null) {
			// Exactly the same amount of connections
			int connectionCount = list.size();
			if (connectionCount == this.connectors.size()) {		
				for(Integer connector : list) {
					connected = this.isConnectedTo(connector);
					if (!connected) break;
				}
			}
		}
		
		return connected;
	}
	
	public boolean isLevelStartAndExactlyConnectedTo(List<Integer> list) {
		return this.isLevelStart && this.isExactlyConnectedTo(list);
	}
	
	public boolean isLevelEndAndExactlyConnectedTo(List<Integer> list) {
		return this.isLevelEnd && this.isExactlyConnectedTo(list);
	}
	
	public boolean isLevelBossAndExactlyConnectedTo(List<Integer> list) {
		return this.isBoss && this.isExactlyConnectedTo(list);
	}
	
	public boolean isNoSpecialAndExactlyConnectedTo(List<Integer> list) {
		return this.isNoSpecial() && this.isExactlyConnectedTo(list);
	}

	public boolean isStarBlock() {
		return isStarBlock;
	}

	public void setStarBlock(boolean isStarBlock) {
		this.isStarBlock = isStarBlock;		
		this.blocDefinition.setStarBlock(isStarBlock);
	}

	public boolean NoSpecialComeFromAndGoTo(BlocDirection fromDirection,
			BlocDirection toDirection) {
		List<Integer> fromConnectors = getConnectorsFor(LevelGenNode.getMirror(fromDirection));
		List<Integer> toConnectors = getConnectorsFor(LevelGenNode.getMirror(toDirection));
		
		return this.isEntryConnectedAtLeastOne(fromConnectors) && this.isExitConnectedAtLeastOne(toConnectors) && this.isNoSpecial();
	}

	public boolean isLevelEndAndComeFrom(BlocDirection fromDirection) {
		List<Integer> fromConnectors = getConnectorsFor(LevelGenNode.getMirror(fromDirection));
		return this.isLevelEnd() && this.isEntryConnectedTo(fromConnectors);
	}

	public boolean isLevelBossAndComeFrom(BlocDirection fromDirection) {
		List<Integer> fromConnectors = getConnectorsFor(LevelGenNode.getMirror(fromDirection));
		return this.isBoss && this.isEntryConnectedTo(fromConnectors);
	}
}
