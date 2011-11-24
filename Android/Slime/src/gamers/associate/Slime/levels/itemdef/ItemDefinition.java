package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;

import java.util.ArrayList;

import android.util.Log;

public abstract class ItemDefinition {		
	protected static String None = "None";
	
	protected String itemType;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float angle;
	protected ArrayList<String> typesHandled;
	
	public ItemDefinition() {
		this.typesHandled = new ArrayList<String>();
		this.initTypeHandled();
	}
	
	protected abstract void initTypeHandled();
	
	/**
	 * @return the type
	 */
	public String getType() {
		return itemType;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		itemType = type;
	}
	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void parse(String line) throws Exception {
		try
		{
			String[] infos = line.split(";", -1);
			this.itemType = infos[0];
			this.x = ZeroIfNone(infos[1]);
			this.y = ZeroIfNone(infos[2]);
			this.width = ZeroIfNone(infos[3]);
			this.height = ZeroIfNone(infos[4]);
			this.angle = ZeroIfNone(infos[5]);
			this.parseNext(infos, 6);
		}
		catch (Exception ex) {
			Log.e(Slime.TAG, "BAD FORMAT for item definition " + this.getType());
			throw ex;
		}
	}
	
	protected static float ZeroIfNone(String value) {
		if (value.equals(None)) {
			return 0f;
		}
		else {
			return Float.valueOf(value).floatValue();
		}
	}
	
	protected abstract void parseNext(String[] infos, int start);
	
	public abstract void createItem(Level level);
	
	public void parseAndCreate(String line, Level level) throws Exception {
		this.parse(line);
		this.createItem(level);
	}
	
	protected void registerHandledType(String typeName) {
		this.typesHandled.add(typeName);
	}
	
	public boolean isHandlerFor(String itemType) {
		boolean found = false;
		for (String regType : this.typesHandled) {
			if (itemType.toUpperCase().equals(regType.toUpperCase())) {
				found = true;
				break;
			}
		}
		
		return found;
	}

	/**
	 * @return the typesHandled
	 */
	public ArrayList<String> getTypesHandled() {
		return typesHandled;
	}

	/**
	 * @return the angle
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public void postBuild() {
		// Override to handle postBuild
	}
}
