package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;

public class BoxDef extends ItemDefinition {
	private static String Handled_Tube = "Box_Tube";
	private static String Handled_Multitubes = "Box_Multitubes";
	private static String Handled_Glassbox = "Box_GlassBox";
	private static String Handled_Bottle = "Box_Bottle";
	
	private boolean isStatic;
	private boolean isStickable;

	@Override
	public void createItem(Level level) {
		if (this.itemType.toUpperCase().equals(Handled_Tube.toUpperCase())) {
			SlimeFactory.Box.createTubeBL(this.x, this.y, this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Multitubes.toUpperCase())) {
			SlimeFactory.Box.createMultitubesBL(this.x, this.y, this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Glassbox.toUpperCase())) {
			SlimeFactory.Box.createGlassboxBL(this.x, this.y, this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Bottle.toUpperCase())) {
			SlimeFactory.Box.createBottleBL(this.x, this.y, this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Tube);
		this.typesHandled.add(Handled_Multitubes);
		this.typesHandled.add(Handled_Glassbox);
		this.typesHandled.add(Handled_Bottle);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.isStatic = Boolean.parseBoolean(infos[start]);
		this.isStickable = Boolean.parseBoolean(infos[start + 1]);
	}

}
