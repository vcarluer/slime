package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.Box;

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
			SlimeFactory.Box.createTubeBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Multitubes.toUpperCase())) {
			SlimeFactory.Box.createMultitubesBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Glassbox.toUpperCase())) {
			SlimeFactory.Box.createGlassboxBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
		}

		if (this.itemType.toUpperCase().equals(Handled_Bottle.toUpperCase())) {
			SlimeFactory.Box.createBottleBL(this.getUName(), this.getX(), this.getY(), this.width, this.height, this.isStatic, this.isStickable).setAngle(this.angle);
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

	@Override
	protected void initClassHandled() {
		this.classHandled.add(Box.class);		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, String.valueOf(this.isStatic));
		line = this.addValue(line, String.valueOf(this.isStickable));
		
		return line;
	}

	@Override
	protected String getItemType(GameItem item) {
		switch (((Box)item).getType()) {
		case Box.bottle:
			return Handled_Bottle;
		case Box.glassbox:
			return Handled_Glassbox;
		case Box.multitubes:
			return Handled_Multitubes;
		case Box.tube:
			return Handled_Tube;
		default:
			return null;
		}
	}

	@Override
	protected void setValuesNext(GameItem item) {
		Box box = (Box)item;
		this.isStatic = box.isStatic();
		this.isStickable = !box.isNoStick();
	}

	@Override
	protected boolean getIsBL() {
		return true;
	}

}
