package gamers.associate.Slime.test;

import gamers.associate.Slime.levels.generator.Connector;
import gamers.associate.Slime.levels.generator.LevelGenNode;
import junit.framework.TestCase;

public class LevelGenNodeTest extends TestCase {
	private LevelGenNode node;
	
	@Override
	protected void setUp() throws Exception {
		node = new LevelGenNode();
		super.setUp();
	}

	public void testCreate() {		
		assertNotNull(node);
	}
	
	public void testSetAvailableConnector1() {		
		node.AddConnector(Connector.TopLeft);
		assertEquals(1, node.getConnectorCount());
	}
	
	public void testSet2Connectors() {
		node.AddConnector(Connector.TopLeft);
		node.AddConnector(Connector.TopMid);
		assertEquals(2, node.getConnectorCount());
	}
	
	public void testOnlyOneType() {
		node.AddConnector(Connector.TopLeft);
		node.AddConnector(Connector.TopLeft);
		assertEquals(1, node.getConnectorCount());
	}	
	
	public void testBTIsConnected() {
		assertTrue(node.isConnected(Connector.BottomLeft, Connector.TopLeft));
	}
	
	public void testRLIsConnected() {
		assertTrue(node.isConnected(Connector.RightMid, Connector.LeftMid));
	}
	
	public void testTBIsNotConnected() {
		assertFalse(node.isConnected(Connector.TopLeft, Connector.BottomLeft));		
	}
	
	public void testLRIsNotConnected() {
		assertFalse(node.isConnected(Connector.LeftMid, Connector.RightMid));		
	}
	
	public void testHasCorrespondingConnector() {
		node.AddConnector(Connector.TopLeft);
		assertTrue(node.isConnectedTo(Connector.BottomLeft));
	}
	
	public void testHasNoCorrespondingConnector() {
		node.AddConnector(Connector.TopLeft);
		assertFalse(node.isConnectedTo(Connector.TopMid));
	}
	
	public void testMultiSourceHasCorresponding() {
		node.AddConnector(Connector.TopLeft);
		LevelGenNode nodeSource = new LevelGenNode();
		nodeSource.AddConnector(Connector.BottomLeft);
		nodeSource.AddConnector(Connector.BottomMid);
		assertTrue(node.isConnectedTo(nodeSource));
	}
}
