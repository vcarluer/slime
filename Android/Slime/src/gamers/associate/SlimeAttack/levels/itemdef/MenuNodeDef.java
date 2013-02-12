package gamers.associate.SlimeAttack.levels.itemdef;

import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.items.custom.MenuNode;
import gamers.associate.SlimeAttack.items.custom.Slimy;

public class MenuNodeDef extends ItemDefinition {
	private static String Handled_Def = "MenuNode";
	private static String rootNode = "n00";
	
	// unique name here for id and target?
	private String id;
	private String targetLevel;
	private String targetN1;
	private String targetN2;
	private String targetN3;
	private String targetN4;
	
	private MenuNode rootItem;
	
	@Override
	public void createItem(Level level) {
		MenuNode node = SlimeFactory.MenuNode.createBL(this.getX(), this.getY(), this.width, this.height, 
										id, targetLevel,
										this.targetN1, this.targetN2, this.targetN3, this.targetN4);		
		
		if (this.id.equals(rootNode)) {
			node.setUnlock(true);
			this.rootItem = node;
		}
		
		if (node.isCurrentSelection()) {
			this.createStartNode(node);
		}						
	}
	
	private void createStartNode(MenuNode startNode) {				
		if (startNode != null) {
			float ratio = 1.0f;
			// start item centered in node
			Slimy slimy = SlimeFactory.Slimy.create("Menu", this.getX() + this.width / 2, this.getY() + (ratio * Slimy.Default_Height) / 2, ratio);
			slimy.land();
			slimy.disablePhysic();
			Level.currentLevel.setStartItem(slimy);
			SlimeFactory.MenuNode.setCurrentNode(startNode);
		}		
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.id = infos[start];
		this.targetLevel = infos[start + 1];
		this.targetN1 = infos[start + 2];
		this.targetN2 = infos[start + 3];
		this.targetN3 = infos[start + 4];
		this.targetN4 = infos[start + 5];		
	}
	
	@Override
	public void postBuild() {
		// Browse node graph to enable or disable new nodes
		if (this.rootItem != null) {
			this.rootItem.unlockChildConnectionsGraph(null);
			
			if (SlimeFactory.MenuNode.getCurrentNode() == null) {
				this.createStartNode(this.rootItem);
			}
		}				
	}

	@Override
	protected void initClassHandled() {
		// NONE for now		
	}

	@Override
	protected String writeNext(String line) {
		return null;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return null;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		
	}
}
