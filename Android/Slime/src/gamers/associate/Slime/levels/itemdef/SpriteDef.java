package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class SpriteDef extends ItemDefinition {
	private static String Handled_Def = "Sprite";
	private String plist;
	private String frame;
	private int count;
	
	@Override
	public void createItem(Level level) {
		SlimeFactory.Sprite.createBL(this.x, this.y, this.width, this.height, this.plist, this.frame, this.count).setAngle(this.angle);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Def);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.plist = infos[start];
		this.frame = infos[start+1];
		this.count = Integer.parseInt(infos[start+2]);		
	}
}
