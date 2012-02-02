package gamers.associate.Slime.test;

import gamers.associate.Slime.levels.generator.BlocDirection;
import gamers.associate.Slime.levels.generator.Connector;
import gamers.associate.Slime.levels.generator.LevelGenNode;
import gamers.associate.Slime.levels.generator.LevelGraphGeneratorCorridor;
import junit.framework.TestCase;

public class LevelGraphGeneratorTest extends TestCase {
	private LevelGraphGeneratorCorridor generator;
	
	protected void setUp() throws Exception {
		generator = new LevelGraphGeneratorCorridor();
		super.setUp();
	}
	
	public void testCreate() {
		generator = new LevelGraphGeneratorCorridor();
	}
	
	public void testAddNodes() {
		generator.addNode(new LevelGenNode());
		assertEquals(1, generator.getNodeCount());
	}
	
	public void testPickStartNode() {
		LevelGenNode node = new LevelGenNode();
		node.addConnectorExit(Connector.TopLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertEquals(node, pick);
	}	
	
	public void testNoPickStartNode1() {
		LevelGenNode node = new LevelGenNode();
		node.addConnectorExit(Connector.BottomLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertNull(pick);
	}
	
	public void testNoPickStartNode2() {
		LevelGenNode node = new LevelGenNode();
		node.addConnectorExit(Connector.TopLeft);
		node.setIsLevelStart(false);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertNull(pick);
	}
	
	public void testPickNode() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.BottomLeft);
		node.addConnectorExit(Connector.RightMid);
		generator.addNode(node);
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		assertEquals(node, next);
	}
	
	public void testNoPickNode() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.BottomLeft);
		node.addConnectorExit(Connector.LeftMid);
		generator.addNode(node);
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		assertNull(next);
	}	
	
	public void testPickEndNode() {
		this.createGraph1();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.LeftMid);
		node.setIsLevelEnd(true);
		generator.addNode(node);		
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		LevelGenNode end = generator.pickEnd(next);
		assertEquals(node, end);
	}
	
	public void testNoPickEndNode() {
		this.createGraph1();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.LeftBottom);
		node.setIsLevelEnd(true);
		generator.addNode(node);		
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		LevelGenNode end = generator.pickEnd(next);
		assertNull(end);
	}
	
	public void testNoPickStartNodeForNext() {
		// In V1 can not happen: Start node has only one side direction
		// Same for end node
		this.createGraph1();
		assertNull(generator.pickNext(generator.pickStart(BlocDirection.Top), BlocDirection.Top));
	}
	
	public void testRandomizeDirection() {
		BlocDirection direction = generator.getRandomDirection();
		boolean ok = (direction == BlocDirection.Top) || (direction == BlocDirection.Right) || (direction == BlocDirection.Bottom) || (direction == BlocDirection.Left);
		assertTrue(ok);
	}
	
	public void testEndWhenComplexityReach() {
		this.createFullGraph();
		int complexity = 0;
		int maxComplexity = 25;
		int cycle = 0;
		LevelGenNode pick = generator.pickStart();
		complexity += pick.getComplexity();
		cycle++;
		while (complexity < maxComplexity) {
			pick = generator.pickNext(pick);
			complexity += pick.getComplexity();
			cycle++;
		}
		
		pick = generator.pickEnd(pick);
		complexity += pick.getComplexity();
		cycle++;
		
		assertEquals(6, cycle);
		assertEquals(30, complexity);
	}
	
	// Local JUnit test problem due to android log system
	/*public void testGenerate() {
		this.createFullGraph();
		generator.generate(500);
		assertEquals(505, generator.getLastGeneratedComplexity());
		assertTrue(generator.getLeftCount() > 0);
		assertTrue(generator.getTopCount() > 0);
		assertTrue(generator.getRightCount() > 0);
		assertTrue(generator.getBottomCount() > 0);
	}
	
	public void testGenerateConstrained() {
		this.createFullGraph();
		generator.generate(500, BlocDirection.Left);
		assertEquals(505, generator.getLastGeneratedComplexity());
		assertEquals(0, generator.getLeftCount());
		assertTrue(generator.getTopCount() > 0);
		assertTrue(generator.getRightCount() > 0);
		assertTrue(generator.getBottomCount() > 0);
	}*/
		
	private void createGraphStart() {
		LevelGenNode node = new LevelGenNode();
		node.addConnectorExit(Connector.TopLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);		
	}
	
	private void createGraph1() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.BottomLeft);
		node.addConnectorExit(Connector.RightMid);
		generator.addNode(node);
	}
	
	private void createMinGraph() {
		this.createGraph1();
		LevelGenNode node = new LevelGenNode();
		node.addConnectorEntry(Connector.LeftBottom);
		node.setIsLevelEnd(true);
		generator.addNode(node);
	}
	
	private void createFullGraph() {		
		generator.addNode(this.createStartNodeTop());
		generator.addNode(this.createStartNodeRight());
		generator.addNode(this.createStartNodeBottom());
		generator.addNode(this.createStartNodeLeft());
		generator.addNode(this.createEndNodeTop());
		generator.addNode(this.createEndNodeRight());
		generator.addNode(this.createEndNodeBottom());
		generator.addNode(this.createEndNodeLeft());
		generator.addNode(this.addTopEx(this.createNodeLeft()));		
		generator.addNode(this.addBottomEx(this.createNodeLeft()));
		generator.addNode(this.addRightEx(this.createNodeLeft()));
		generator.addNode(this.addRightEx(this.createNodeTop()));
		generator.addNode(this.addBottomEx(this.createNodeTop()));
		generator.addNode(this.addLeftEx(this.createNodeTop()));
		generator.addNode(this.addBottomEx(this.createNodeRight()));
		generator.addNode(this.addLeftEx(this.createNodeRight()));
		generator.addNode(this.addTopEx(this.createNodeRight()));
		generator.addNode(this.addTopEx(this.createNodeBottom()));
		generator.addNode(this.addRightEx(this.createNodeBottom()));
		generator.addNode(this.addLeftEx(this.createNodeBottom()));		
	}		
	
	private LevelGenNode addTopEn(LevelGenNode node) {
		node.addConnectorEntry(Connector.TopLeft);
		node.addConnectorEntry(Connector.TopMid);
		node.addConnectorEntry(Connector.TopRight);
		return node;
	}
	
	private LevelGenNode addRightEn(LevelGenNode node) {
		node.addConnectorEntry(Connector.RightTop);
		node.addConnectorEntry(Connector.RightMid);
		node.addConnectorEntry(Connector.RightBottom);
		return node;
	}
	
	private LevelGenNode addBottomEn(LevelGenNode node) {
		node.addConnectorEntry(Connector.BottomLeft);
		node.addConnectorEntry(Connector.BottomMid);
		node.addConnectorEntry(Connector.BottomRight);
		return node;
	}
	
	private LevelGenNode addLeftEn(LevelGenNode node) {
		node.addConnectorEntry(Connector.LeftTop);
		node.addConnectorEntry(Connector.LeftMid);
		node.addConnectorEntry(Connector.LeftBottom);
		return node;
	}
	
	private LevelGenNode addTopEx(LevelGenNode node) {
		node.addConnectorExit(Connector.TopLeft);
		node.addConnectorExit(Connector.TopMid);
		node.addConnectorExit(Connector.TopRight);
		return node;
	}
	
	private LevelGenNode addRightEx(LevelGenNode node) {
		node.addConnectorExit(Connector.RightTop);
		node.addConnectorExit(Connector.RightMid);
		node.addConnectorExit(Connector.RightBottom);
		return node;
	}
	
	private LevelGenNode addBottomEx(LevelGenNode node) {
		node.addConnectorExit(Connector.BottomLeft);
		node.addConnectorExit(Connector.BottomMid);
		node.addConnectorExit(Connector.BottomRight);
		return node;
	}
	
	private LevelGenNode addLeftEx(LevelGenNode node) {
		node.addConnectorExit(Connector.LeftTop);
		node.addConnectorExit(Connector.LeftMid);
		node.addConnectorExit(Connector.LeftBottom);
		return node;
	}
	
	private LevelGenNode createNode() {
		LevelGenNode node = new LevelGenNode();
		node.setComplexity(5);		
		return node;
	}
	
	private LevelGenNode createStartNodeTop() {
		LevelGenNode node = this.createNode();
		addTopEx(node);
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeRight() {
		LevelGenNode node = this.createNode();
		addRightEx(node);
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeBottom() {
		LevelGenNode node = this.createNode();
		addBottomEx(node);
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeLeft() {
		LevelGenNode node = this.createNode();
		addLeftEx(node);
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createEndNodeTop() {
		LevelGenNode node = this.createNode();
		addTopEn(node);
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeRight() {
		LevelGenNode node = this.createNode();
		addRightEn(node);
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeBottom() {
		LevelGenNode node = this.createNode();
		addBottomEn(node);
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeLeft() {
		LevelGenNode node = this.createNode();
		addLeftEn(node);
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createNodeTop() {
		LevelGenNode node = this.createNode();
		addTopEn(node);		
		return node;
	}
	
	private LevelGenNode createNodeRight() {
		LevelGenNode node = this.createNode();
		addRightEn(node);		
		return node;
	}
	
	private LevelGenNode createNodeBottom() {
		LevelGenNode node = this.createNode();
		addBottomEn(node);		
		return node;
	}
	
	private LevelGenNode createNodeLeft() {
		LevelGenNode node = this.createNode();
		addLeftEn(node);		
		return node;
	}
}
