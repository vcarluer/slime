package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class PlatformDef extends ItemDefinition {
	private static String Handled_Platform = "Platform";
	private static String Handled_Bumper = "Platform_Bumper";
	private static String Handled_NoSticky = "Platform_NoSticky";
	private static String Handled_Ice = "Platform_Ice";
	
	@Override
	public void createItem(Level level) {
		if (this.itemType.toUpperCase().equals(Handled_Platform.toUpperCase())) {
			SlimeFactory.Platform.createBL(this.x, this.y, this.width, this.height);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Bumper.toUpperCase())) {
			SlimeFactory.Platform.createBumpBL(this.x, this.y, this.width, this.height);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_NoSticky.toUpperCase())) {
			SlimeFactory.Platform.createNoStickyBL(this.x, this.y, this.width, this.height);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Ice.toUpperCase())) {
			SlimeFactory.Platform.createIcyBL(this.x, this.y, this.width, this.height);
		}
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Platform);
		this.typesHandled.add(Handled_Bumper);
		this.typesHandled.add(Handled_NoSticky);
		this.typesHandled.add(Handled_Ice);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// None
	}
}
