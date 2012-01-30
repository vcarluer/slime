package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.base.GameItemCocos;

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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(GameItemCocos.class);
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.plist);
		line = this.addValue(line, this.frame);
		line = this.addValue(line, String.valueOf(this.count));
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Def;
	}

	@Override
	protected void setValuesNext(GameItem item) {
		GameItemCocos cocos = (GameItemCocos)item;
		this.plist = cocos.getpList();
		this.frame = cocos.getFrameName();
		this.count = cocos.getFrameCount();
	}
}
