package gamers.associate.Slime.levels.generator;

import java.util.Set;
import java.util.HashSet;

public class LevelGenNode {
	private Set<Integer> connectors;
	
	public LevelGenNode() {
		this.connectors = new HashSet<Integer>();
	}
	
	public void AddConnector(int connector) {
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
		return connectorSource - connectorTarget == 3;
	}

	public boolean isConnectedTo(LevelGenNode nodeSource) {
		boolean isConnected = false;
		for(Integer connector : nodeSource.getConnectorList()) {
			isConnected = this.isConnectedTo(connector);
			if (isConnected) break;
		}
		
		return isConnected;
	}		
}
