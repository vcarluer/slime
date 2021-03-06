package gamers.associate.Slime.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gamers.associate.Slime.levels.generator.BlocDirection;
import gamers.associate.Slime.levels.generator.Connector;
import gamers.associate.Slime.levels.generator.LevelGenNode;
import junit.framework.TestCase;

public class LevelGenNodeTest extends TestCase {
	private LevelGenNode node;
	private Set<Integer> list;
	
	@Override
	protected void setUp() throws Exception {
		node = new LevelGenNode();
		list = new HashSet<Integer>();
		super.setUp();
	}

	public void testCreate() {		
		assertNotNull(node);
	}
	
	public void testSetAvailableConnector1() {		
		LevelGenNode.addConnector(list, Connector.TopLeft);
		assertEquals(1, list.size());
	}
	
	public void testSet2Connectors() {
		LevelGenNode.addConnector(list, Connector.TopLeft);
		LevelGenNode.addConnector(list, Connector.TopMid);
		assertEquals(2, list.size());
	}
	
	public void testOnlyOneType() {
		LevelGenNode.addConnector(list, Connector.TopLeft);
		LevelGenNode.addConnector(list, Connector.TopLeft);
		assertEquals(1, list.size());
	}	
	
	public void testBTIsConnected() {
		assertTrue(LevelGenNode.isConnected(Connector.BottomLeft, Connector.TopLeft));
	}
	
	public void testRLIsConnected() {
		assertTrue(LevelGenNode.isConnected(Connector.RightMid, Connector.LeftMid));
	}
	
	public void testTBIsNotConnected() {
		assertFalse(LevelGenNode.isConnected(Connector.TopLeft, Connector.BottomMid));		
	}
	
	public void testLRIsNotConnected() {
		assertFalse(LevelGenNode.isConnected(Connector.LeftMid, Connector.RightTop));		
	}
	
	public void testTLIsNotConnected() {
		assertFalse(LevelGenNode.isConnected(Connector.BottomLeft, Connector.LeftTop));		
	}
	
	public void testHasCorrespondingConnector() {
		LevelGenNode.addConnector(list, Connector.TopLeft);
		assertTrue(LevelGenNode.isConnectedTo(list, Connector.BottomLeft));
	}
	
	public void testHasNoCorrespondingConnector() {
		LevelGenNode.addConnector(list, Connector.TopLeft);
		assertFalse(LevelGenNode.isConnectedTo(list, Connector.TopMid));
	}
	
	public void testMultiSourceHasCorresponding() {
		node.addConnectorEntry(Connector.TopLeft);
		LevelGenNode nodeSource = new LevelGenNode();
		nodeSource.addConnectorExit(Connector.BottomLeft);
		nodeSource.addConnectorExit(Connector.BottomMid);
		assertTrue(node.isEntryConnectedTo(nodeSource));
	}
	
	public void testMultiSourceHasCorresponding2() {
		node.addConnectorEntry(Connector.BottomLeft);
		LevelGenNode nodeSource = new LevelGenNode();
		nodeSource.addConnectorExit(Connector.TopLeft);
		nodeSource.addConnectorExit(Connector.TopMid);
		assertTrue(node.isEntryConnectedTo(nodeSource));
	}
	
	public void testConnectAtLeastOneConnectorPerList() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomLeft);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertTrue(node.isEntryConnectedMultiple(list));		
	}
	
	public void testDisConnectOneConnector() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomRight);		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertFalse(node.isEntryConnectedMultiple(list));		
	}
	
	public void testDisConnectOneConnector2() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomLeft);		
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftTop);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertFalse(node.isEntryConnectedMultiple(list));		
	}
	
	public void testConnectAtLeastOneConnectorPerList2() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(Connector.BottomRight);
		list1.add(Connector.BottomLeft);
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(Connector.LeftTop);
		list2.add(Connector.LeftMid);
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(list1);
		list.add(list2);
		assertTrue(node.isEntryConnectedMultiple(list));		
	}
	
	public void testConnectAtLeastOne() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.RightMid);
		List<Integer> list1 = new ArrayList<Integer>();		
		list1.add(Connector.BottomLeft);		
		assertTrue(node.isEntryConnectedAtLeastOne(list1));		
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
		node.addConnectorExit(Connector.RightMid);		
		assertTrue(node.goTo(BlocDirection.Right));
	}
	
	public void testDontGoTo() {		
		node.addConnectorExit(Connector.LeftMid);		
		assertFalse(node.goTo(BlocDirection.Right));
	}
	
	public void testIsConnectedAndGoTo() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorExit(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomLeft);
		assertTrue(node.connectNoSpecialAndGoTo(list, BlocDirection.Right));
	}
	
	public void testIsDisConnectedAndGoTo() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorExit(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomMid);
		assertFalse(node.connectNoSpecialAndGoTo(list, BlocDirection.Right));
	}
	
	public void testIsConnectedAndDontGoTo() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorExit(Connector.RightMid);
		List<Integer> list = new ArrayList<Integer>();
		list.add(Connector.BottomLeft);
		assertFalse(node.connectNoSpecialAndGoTo(list, BlocDirection.Left));
	}
	
	public void testNodeConnectAndGoTo() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorExit(Connector.RightMid);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnectorExit(Connector.BottomLeft);
		assertTrue(node.connectNoSpecialAndGoTo(sourceNode, BlocDirection.Right));
	}
	
	public void testNodeDisConnectAndGoTo() {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorExit(Connector.RightMid);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnectorExit(Connector.BottomRight);
		assertFalse(node.connectNoSpecialAndGoTo(sourceNode, BlocDirection.Right));
	}
	
	public void testNodeLevelStartAndGoTo() {
		node.addConnectorExit(Connector.RightMid);
		node.setIsLevelStart(true);
		assertTrue(node.isStartAndGoTo(BlocDirection.Right));
	}
	
	public void testNodeNotLevelStartAndGoTo() {
		node.addConnectorExit(Connector.RightMid);
		node.setIsLevelStart(false);
		assertFalse(node.isStartAndGoTo(BlocDirection.Right));
	}
	
	public void testGetComplexity() {
		node.setComplexity(10);
		assertEquals(10, node.getComplexity());
	}
	
	public void testNodeLevelEndComeFrom() {
		node.addConnectorEntry(Connector.LeftMid);
		node.setIsLevelEnd(true);
		LevelGenNode sourceNode = new LevelGenNode();
		sourceNode.addConnectorExit(Connector.RightMid);
		assertTrue(node.isLevelEndAndConnect(sourceNode));
	}
}
