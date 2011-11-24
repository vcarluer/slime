package gamers.associate.Slime.items.custom;

import java.util.HashMap;

import gamers.associate.Slime.items.base.GameItemCocosFactory;


public class MenuNodeFactory extends GameItemCocosFactory<MenuNode> {			
	private HashMap<String, MenuNode> levelNodes;
	private HashMap<String, MenuNode> nodes;
	private String name;
	private String target;
	private String conn1;
	private String conn2;
	private String conn3;
	private String conn4;
	
	private MenuNode currentNode;
	
	public MenuNodeFactory() {
		this.levelNodes = new HashMap<String, MenuNode>();
		this.nodes = new HashMap<String, MenuNode>();
	}
	
	@Override
	protected void createAnimList() {
		this.createAnim(MenuNode.Anim_Node, 4);
	}

	@Override
	protected String getPlistPng() {
		return "labo";
	}

	@Override
	protected MenuNode instantiate(float x, float y, float width,
			float height) {
		return new MenuNode(x, y, width, height);		
	}

	@Override
	protected void runFirstAnimations(MenuNode item) {
		item.createPortal();
	}
	
	public MenuNode create(float x, float y, float width, float height, 
			String name, String target, 
			String connection1, String connection2, String connection3, String connection4) {
		this.name = name;
		this.target = target;
		this.conn1 = connection1;
		this.conn2 = connection2;
		this.conn3 = connection3;
		this.conn4 = connection4;
		MenuNode node = super.create(x, y, width, height);
		return node;
	}
	
	public MenuNode createBL(float x, float y, float width, float height, 
			String name, String target,
			String connection1, String connection2, String connection3, String connection4) {
		return this.create(x + width / 2, y + height / 2, width, height, name, target, connection1, connection2, connection3, connection4);
	}
	
	/* (non-Javadoc)
	 * @see gamers.associate.Slime.items.base.ItemFactoryBase#initItem(gamers.associate.Slime.items.base.GameItemCocos)
	 */
	@Override
	protected void initItem(MenuNode item) {
		item.setNodeId(this.name);	
		item.setTargetLevel(this.target);
		this.addConnection(item, this.conn1);
		this.addConnection(item, this.conn2);
		this.addConnection(item, this.conn3);
		this.addConnection(item, this.conn4);		
		this.levelNodes.put(item.getTargetLevel(), item);
		this.nodes.put(item.getNodeId(), item);
		super.initItem(item);
	}
	
	private void addConnection(MenuNode item, String connection) {
		if (connection != null && connection.length() > 0) {
			item.addConnection(connection);
		}
	}

	public void setCurrentNode(MenuNode currentNode) {
		this.currentNode = currentNode;
	}

	public MenuNode getCurrentNode() {
		return currentNode;
	}
	
	public MenuNode getForLevel(String levelName) {
		if (this.levelNodes.containsKey(levelName)) {
			return this.levelNodes.get(levelName); 
		} 
		else {
			return null;
		}
	}
	
	public MenuNode get(String nodeId) {
		if (this.nodes.containsKey(nodeId)) {
			return this.nodes.get(nodeId); 
		} 
		else {
			return null;
		}
	}
}