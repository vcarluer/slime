package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class PlatformDef extends ItemDefinition {
	private static String Handled_Platform = "Platform";
	private static String Handled_Bumper = "Platform_Bumper";
	private static String Handled_NoSticky = "Platform_NoSticky";
	private static String Handled_Ice = "Platform_Ice";
	private static String Handled_Wall = "Platform_Wall";
	private static String Handled_Corner = "Platform_Corner";
	private static String Handled_T = "Platform_T";
	private static String Handled_Cross = "Platform_Cross";
	
	@Override
	public void createItem(Level level) {
		if (this.itemType.toUpperCase().equals(Handled_Platform.toUpperCase())) {
			SlimeFactory.Platform.createBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Bumper.toUpperCase())) {
			SlimeFactory.Platform.createBumpBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_NoSticky.toUpperCase())) {
			SlimeFactory.Platform.createNoStickyBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Ice.toUpperCase())) {
			SlimeFactory.Platform.createIcyBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Wall.toUpperCase())) {
			SlimeFactory.Platform.createWallBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Corner.toUpperCase())) {
			SlimeFactory.Platform.createCornerBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_T.toUpperCase())) {
			SlimeFactory.Platform.createTBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Cross.toUpperCase())) {
			SlimeFactory.Platform.createCrossBL(this.x, this.y, this.width, this.height).setAngle(this.angle);
		}
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Platform);
		this.typesHandled.add(Handled_Bumper);
		this.typesHandled.add(Handled_NoSticky);
		this.typesHandled.add(Handled_Ice);
		this.typesHandled.add(Handled_Wall);
		this.typesHandled.add(Handled_Corner);
		this.typesHandled.add(Handled_T);
		this.typesHandled.add(Handled_Cross);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// None
	}
}
