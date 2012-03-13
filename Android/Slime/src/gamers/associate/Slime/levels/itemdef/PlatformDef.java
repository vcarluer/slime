package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Platform;

public class PlatformDef extends ItemDefinition {
	private static String Handled_Platform = "Platform";
	private static String Handled_Bumper = "Platform_Bumper";
	private static String Handled_NoSticky = "Platform_NoSticky";
	private static String Handled_Ice = "Platform_Ice";
	private static String Handled_Wall = "Platform_Wall";
	private static String Handled_Corner = "Platform_Corner";
	private static String Handled_T = "Platform_T";
	private static String Handled_Cross = "Platform_Cross";
	private static String Handled_End = "Platform_End";
	private static String Handled_NoSticky_Corner = "Platform_NoSticky_Corner";
	private static String Handled_NoSticky_End = "Platform_NoSticky_End";
	
	@Override
	public void createItem(Level level) {		
		if (this.itemType.toUpperCase().equals(Handled_Platform.toUpperCase())) {
			SlimeFactory.Platform.createBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Bumper.toUpperCase())) {
			SlimeFactory.Platform.createBumpBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_NoSticky.toUpperCase())) {
			SlimeFactory.Platform.createNoStickyBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Ice.toUpperCase())) {
			SlimeFactory.Platform.createIcyBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Wall.toUpperCase())) {
			SlimeFactory.Platform.createWallBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Corner.toUpperCase())) {
			SlimeFactory.Platform.createCornerBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_T.toUpperCase())) {
			SlimeFactory.Platform.createTBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_Cross.toUpperCase())) {
			SlimeFactory.Platform.createCrossBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_End.toUpperCase())) {
			SlimeFactory.Platform.createEndBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_NoSticky_Corner.toUpperCase())) {
			SlimeFactory.Platform.createNoStickyCornerBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
		}
		
		if (this.itemType.toUpperCase().equals(Handled_NoSticky_End.toUpperCase())) {
			SlimeFactory.Platform.createNoStickyEndBL(this.getX(), this.getY(), this.width, this.height).setAngle(this.angle);
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
		this.typesHandled.add(Handled_End);
		this.typesHandled.add(Handled_NoSticky_Corner);
		this.typesHandled.add(Handled_NoSticky_End);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		// None
	}

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Platform.class);
	}

	@Override
	protected String writeNext(String line) {
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

	@Override
	protected String getItemType(GameItem item) {
		Platform platform = (Platform)item;
		switch(platform.getType()) {
		case Platform.Bump: return Handled_Bumper;
		case Platform.Corner: return Handled_Corner;
		case Platform.Cross: return Handled_Cross;
		case Platform.Icy: return Handled_Ice;
		case Platform.NoSticky: return Handled_NoSticky;
		case Platform.Sticky: return Handled_Platform;
		case Platform.T: return Handled_T;
		case Platform.Wall: return Handled_Wall;
		case Platform.End: return Handled_End;
		case Platform.NoStickyCorner: return Handled_NoSticky_Corner;
		case Platform.NoStickyEnd: return Handled_NoSticky_End;
		default: return null;
		}
	}

	@Override
	protected void setValuesNext(GameItem item) {
		
	}
}
