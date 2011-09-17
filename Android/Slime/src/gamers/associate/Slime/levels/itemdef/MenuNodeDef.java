package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class MenuNodeDef extends ItemDefinition {
	private static String Handled_Def = "MenuNode";
	
	private String id;
	private String targetLevel;
	private String targetN1;
	private String targetN2;
	private String targetN3;
	private String targetN4;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.MenuNode.createBL(this.x, this.y, 0, 0);
		if (this.id.equals("n00")) {
			SlimeFactory.Slimy.createBL(this.x, this.y, this.width, this.height);
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

}
