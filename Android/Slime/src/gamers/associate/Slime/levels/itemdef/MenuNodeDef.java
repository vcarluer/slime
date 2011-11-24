package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.custom.MenuNode;
import gamers.associate.Slime.items.custom.Slimy;

public class MenuNodeDef extends ItemDefinition {
	private static String Handled_Def = "MenuNode";
	private static String rootNode = "n00";
	
	private String id;
	private String targetLevel;
	private String targetN1;
	private String targetN2;
	private String targetN3;
	private String targetN4;
	
	private MenuNode rootItem;
	
	@Override
	public void createItem(Level level) {
		MenuNode node = SlimeFactory.MenuNode.createBL(this.x, this.y, this.width, this.height, 
										id, targetLevel,
										this.targetN1, this.targetN2, this.targetN3, this.targetN4);		
		
		if (this.id.equals(rootNode)) {
			node.setUnlock(true);
			this.rootItem = node;
		}
		
		MenuNode startNode = null;
		if (SlimeFactory.MenuNode.getCurrentNode() == null && this.id.equals(rootNode)) {
			startNode = node;
		}
		else {
			if (SlimeFactory.MenuNode.getCurrentNode() != null && SlimeFactory.MenuNode.getCurrentNode().getNodeId().equals(this.id)) {
				startNode = node;
			}
		}
		
		if (startNode != null) {
			float ratio = 1.0f;
			// start item centered in node
			Slimy slimy = SlimeFactory.Slimy.create(this.x + this.width / 2, this.y + (ratio * Slimy.Default_Height) / 2, ratio);
			slimy.land();
			slimy.disablePhysic();
			level.setStartItem(slimy);
			SlimeFactory.MenuNode.setCurrentNode(node);
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
			this.rootItem.unlockChildConnectionsGraph();
		}
	}
}
