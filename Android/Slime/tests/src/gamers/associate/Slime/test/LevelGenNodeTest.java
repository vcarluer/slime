package gamers.associate.Slime.test;

import java.util.ArrayList;
import java.util.List;

import gamers.associate.Slime.levels.generator.BlocDirection;
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
		node.addConnector(Connector.TopLeft);
		assertEquals(1, node.getConnectorCount());
	}
	
	public void testSet2Connectors() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.TopMid);
		assertEquals(2, node.getConnectorCount());
	}
	
	public void testOnlyOneType() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.TopLeft);
		assertEquals(1, node.getConnectorCount());
	}	
	
	public void testBTIsConnected() {
		assertTrue(node.isConnected(Connector.BottomLeft, Connector.TopLeft));
	}
	
	public void testRLIsConnected() {
		assertTrue(node.isConnected(Connector.RightMid, Connector.LeftMid));
	}
	
	public void testTBIsNotConnected() {
		assertFalse(node.isConnected(Connector.TopLeft, Connector.BottomMid));		
	}
	
	public void testLRIsNotConnected() {
		assertFalse(node.isConnected(Connector.LeftMid, Connector.RightTop));		
	}
	
	public void testTLIsNotConnected() {
		assertFalse(node.isConnected(Connector.BottomLeft, Connector.LeftTop));		
	}
	
	public void testHasCorrespondingConnector() {
		node.addConnector(Connector.TopLeft);
		assertTrue(node.isConnectedTo(Connector.BottomLeft));
	}
	
	public void testHasNoCorrespondingConnector() {
		node.addConnector(Connector.TopLeft);
		assertFalse(node.isConnectedTo(Connector.TopMid));
	}
	
	public void testMultiSourceHasCorresponding() {
		node.addConnector(Connector.TopLeft);
		LevelGenNode nodeSource = new LevelGenNode();
		nodeSource.addConnector(Connector.BottomLeft);
		nodeSource.addConnector(Connector.BottomMid);
		assertTrue(node.isConnectedTo(nodeSource));
	}
	
	public void testMultiSourceHasCorresponding2() {
		node.addConnector(Connector.BottomLeft);
		LevelGenNode nodeSource = new LevelGenNode();
		nodeSource.addConnector(Connector.TopLeft);
		nodeSource.addConnector(Connector.TopMid);
		assertTrue(node.isConnectedTo(nodeSource));
	}
	
	public void testConnectAtLeastOneConnectorPerList() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomLeft);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertTrue(node.isConnectedMultiple(list));		
	}
	
	public void testDisConnectOneConnector() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomRight);		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertFalse(node.isConnectedMultiple(list));		
	}
	
	public void testDisConnectOneConnector2() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomLeft);		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftTop);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertFalse(node.isConnectedMultiple(list));		
	}
	
	public void testConnectAtLeastOneConnectorPerList2() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomRight);
		list1.add(Connector.BottomLeft);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftTop);
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertTrue(node.isConnectedMultiple(list));		
	}
	
	public void testConnectAtLeastOne() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();		
		list1.add(Connector.BottomLeft);		
		assertTrue(node.isConnectedAtLeastOne(list1));		
	}
	
	public void testGetConnectorListForDirectionT() {
		List<Integer> connectors = node.getConnectorsFor(BlocDirection.Top);
		assertEquals(Connector.TopLeft, (int)connectors.get(0));
		assertEquals(Connector.TopMid, (int)connectors.get(1));
		assertEquals(Connector.TopRight, (int)connectors.get(2));		
	}
	
	public void testGetConnectorListForDirectionR() {
		List<Integer> connectors = node.getConnectorsFor(BlocDirection.Right);
		assertEquals(Connector.RightTop, (int)connectors.get(0));
		assertEquals(Connector.RightMid, (int)connectors.get(1));
		assertEquals(Connector.RightBottom, (int)connectors.get(2));		
	}
	
	public void testGetConnectorListForDirectionB() {
		List<Integer> connectors = node.getConnectorsFor(BlocDirection.Bottom);
		assertEquals(Connector.BottomLeft, (int)connectors.get(0));
		assertEquals(Connector.BottomMid, (int)connectors.get(1));
		assertEquals(Connector.BottomRight, (int)connectors.get(2));
	}
	
	public void testGetConnectorListForDirectionL() {
		List<Integer> connectors = node.getConnectorsFor(BlocDirection.Left);
		assertEquals(Connector.LeftTop, (int)connectors.get(0));
		assertEquals(Connector.LeftMid, (int)connectors.get(1));
		assertEquals(Connector.LeftBottom, (int)connectors.get(2));
	}
	
	public void testGoTo() {		
		node.addConnector(Connector.RightMid);		
		assertTrue(node.goTo(BlocDirection.Right));
	}
	
	public void testDontGoTo() {		
		node.addConnector(Connector.LeftMid);		
		assertFalse(node.goTo(BlocDirection.Right));
	}
	
	public void testIsConnectedAndGoTo() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomLeft);
		assertTrue(node.ConnectAndGoTo(list, BlocDirection.Right));
	}
	
	public void testIsDisConnectedAndGoTo() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomMid);
		assertFalse(node.ConnectAndGoTo(list, BlocDirection.Right));
	}
	
	public void testIsConnectedAndDontGoTo() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomLeft);
		assertFalse(node.ConnectAndGoTo(list, BlocDirection.Left));
	}
	
	public void testNodeConnectAndGoTo() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnector(Connector.BottomLeft);
		assertTrue(node.ConnectAndGoTo(sourceNode, BlocDirection.Right));
	}
	
	public void testNodeDisConnectAndGoTo() {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.RightMid);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnector(Connector.BottomRight);
		assertFalse(node.ConnectAndGoTo(sourceNode, BlocDirection.Right));
	}
	
	public void testNodeLevelStartAndGoTo() {
		node.addConnector(Connector.RightMid);
		node.setIsLevelStart(true);
		assertTrue(node.isStartAndGoTo(BlocDirection.Right));
	}
	
	public void testNodeNotLevelStartAndGoTo() {
		node.addConnector(Connector.RightMid);
		node.setIsLevelStart(false);
		assertFalse(node.isStartAndGoTo(BlocDirection.Right));
	}
	
	public void testNodeLevelEndAndGoTo() {
		node.addConnector(Connector.LeftMid);
		node.setIsLevelEnd(true);
		assertTrue(node.isLevelEndAndGoTo(BlocDirection.Left));
	}
	
	public void testNodeNotLevelEndAndGoTo() {
		node.addConnector(Connector.LeftMid);
		node.setIsLevelEnd(false);
		assertFalse(node.isLevelEndAndGoTo(BlocDirection.Left));
	}
	
	public void testGetComplexity() {
		node.setComplexity(10);
		assertEquals(10, node.getComplexity());
	}
	
	public void testNodeLevelEndComeFrom() {
		node.addConnector(Connector.LeftMid);
		node.setIsLevelEnd(true);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnector(Connector.RightMid);
		assertTrue(node.isLevelEndAndConnect(sourceNode));
	}
}
