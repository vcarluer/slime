package gamers.associate.Slime.test;

import gamers.associate.Slime.levels.generator.BlocDirection;
import gamers.associate.Slime.levels.generator.Connector;
import gamers.associate.Slime.levels.generator.LevelGenNode;
import gamers.associate.Slime.levels.generator.LevelGraphGenerator;
import junit.framework.TestCase;

public class LevelGraphGeneratorTest extends TestCase {
	private LevelGraphGenerator generator;
	
	protected void setUp() throws Exception {
		generator = new LevelGraphGenerator();
		super.setUp();
	}
	
	public void testCreate() {
		generator = new LevelGraphGenerator();
	}
	
	public void testAddNodes() {
		generator.addNode(new LevelGenNode());
		assertEquals(1, generator.getNodeCount());
	}
	
	public void testPickStartNode() {
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.TopLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertEquals(node, pick);
	}	
	
	public void testNoPickStartNode1() {
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.BottomLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertNull(pick);
	}
	
	public void testNoPickStartNode2() {
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.TopLeft);
		node.setIsLevelStart(false);
		generator.addNode(node);
		LevelGenNode pick = generator.pickStart(BlocDirection.Top);
		assertNull(pick);
	}
	
	public void testPickNode() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.BottomLeft);
		node.addConnector(Connector.RightMid);
		generator.addNode(node);
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		assertEquals(node, next);
	}
	
	public void testNoPickNode() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.BottomLeft);
		node.addConnector(Connector.LeftMid);
		generator.addNode(node);
		LevelGenNode start = generator.pickStart(BlocDirection.Top);
		LevelGenNode next = generator.pickNext(start, BlocDirection.Right);
		assertNull(next);
	}	
	
	public void testPickEndNode() {
		this.createGraph1();
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.LeftMid);
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
		node.addConnector(Connector.LeftBottom);
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
	
	public void testGenerate() {
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
	}
		
	private void createGraphStart() {
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.TopLeft);
		node.setIsLevelStart(true);
		generator.addNode(node);		
	}
	
	private void createGraph1() {
		this.createGraphStart();
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.BottomLeft);
		node.addConnector(Connector.RightMid);
		generator.addNode(node);
	}
	
	private void createMinGraph() {
		this.createGraph1();
		LevelGenNode node = new LevelGenNode();
		node.addConnector(Connector.LeftBottom);
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
		generator.addNode(this.addTop(this.createNodeLeft()));		
		generator.addNode(this.addBottom(this.createNodeLeft()));
		generator.addNode(this.addRight(this.createNodeLeft()));
		generator.addNode(this.addRight(this.createNodeTop()));
		generator.addNode(this.addBottom(this.createNodeTop()));
		generator.addNode(this.addLeft(this.createNodeTop()));
		generator.addNode(this.addBottom(this.createNodeRight()));
		generator.addNode(this.addLeft(this.createNodeRight()));
		generator.addNode(this.addTop(this.createNodeRight()));
		generator.addNode(this.addTop(this.createNodeBottom()));
		generator.addNode(this.addRight(this.createNodeBottom()));
		generator.addNode(this.addLeft(this.createNodeBottom()));		
	}		
	
	private LevelGenNode addTop(LevelGenNode node) {
		node.addConnector(Connector.TopLeft);
		node.addConnector(Connector.TopMid);
		node.addConnector(Connector.TopRight);
		return node;
	}
	
	private LevelGenNode addRight(LevelGenNode node) {
		node.addConnector(Connector.RightTop);
		node.addConnector(Connector.RightMid);
		node.addConnector(Connector.RightBottom);
		return node;
	}
	
	private LevelGenNode addBottom(LevelGenNode node) {
		node.addConnector(Connector.BottomLeft);
		node.addConnector(Connector.BottomMid);
		node.addConnector(Connector.BottomRight);
		return node;
	}
	
	private LevelGenNode addLeft(LevelGenNode node) {
		node.addConnector(Connector.LeftTop);
		node.addConnector(Connector.LeftMid);
		node.addConnector(Connector.LeftBottom);
		return node;
	}
	
	private LevelGenNode createNodeTop() {
		LevelGenNode node = new LevelGenNode();
		node.setComplexity(5);
		this.addTop(node);
		return node;
	}
	
	private LevelGenNode createNodeRight() {
		LevelGenNode node = new LevelGenNode();
		node.setComplexity(5);
		this.addRight(node);
		return node;
	}
	
	private LevelGenNode createNodeBottom() {
		LevelGenNode node = new LevelGenNode();
		node.setComplexity(5);
		this.addBottom(node);
		return node;
	}
	
	private LevelGenNode createNodeLeft() {
		LevelGenNode node = new LevelGenNode();
		node.setComplexity(5);
		this.addLeft(node);
		return node;
	}
	
	private LevelGenNode createStartNodeTop() {
		LevelGenNode node = this.createNodeTop();
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeRight() {
		LevelGenNode node = this.createNodeRight();
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeBottom() {
		LevelGenNode node = this.createNodeBottom();
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createStartNodeLeft() {
		LevelGenNode node = this.createNodeLeft();
		node.setIsLevelStart(true);
		return node;
	}
	
	private LevelGenNode createEndNodeTop() {
		LevelGenNode node = this.createNodeTop();
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeRight() {
		LevelGenNode node = this.createNodeRight();
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeBottom() {
		LevelGenNode node = this.createNodeBottom();
		node.setIsLevelEnd(true);
		return node;
	}
	
	private LevelGenNode createEndNodeLeft() {
		LevelGenNode node = this.createNodeLeft();
		node.setIsLevelEnd(true);
		return node;
	}
}
