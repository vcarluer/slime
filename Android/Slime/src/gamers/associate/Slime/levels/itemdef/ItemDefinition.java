package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

public abstract class ItemDefinition {		
	protected static String None = "None";
	protected static String infoSep = ";";
	
	protected String itemType;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float angle;
	protected ArrayList<String> typesHandled;
	protected ArrayList<Class> classHandled;	
	
	protected float xOffset;
	protected float yOffset;
	
	public ItemDefinition() {
		this.typesHandled = new ArrayList<String>();
		this.classHandled = new ArrayList<Class>();
		this.initTypeHandled();
		this.initClassHandled();
	}
	
	protected abstract void initTypeHandled();
	
	protected abstract void initClassHandled();
	
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
		return this.getOffX(this.x);
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return this.getOffY(this.y);
	}
	
	protected float getOffX(float xVal) {
		return xVal + this.xOffset * this.width;
	}
	
	protected float getOffY(float yVal) {
		return yVal + this.yOffset * this.height;
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
			String[] infos = line.split(infoSep, -1);
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
		this.parseAndCreate(line, level, 0, 0);
	}
	
	public void parseAndCreate(String line, Level level, float xOffset, float yOffset) throws Exception {				
		this.parse(line);
		this.setOffset(xOffset, yOffset);
		this.createItem(level);
	}
	
	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
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
	
	public ArrayList<Class> getClassesHandled() {
		return this.classHandled;
	}
	
	public void writeLine(BufferedWriter writer) throws IOException {
		try {
			String line = "";		
			line = this.addValue(line, this.itemType);
			line = this.addValue(line, String.valueOf(this.x));
			line = this.addValue(line, String.valueOf(this.y));
			line = this.addValue(line, String.valueOf(this.width));
			line = this.addValue(line, String.valueOf(this.height));
			line = this.addValue(line, String.valueOf(this.angle));
			
			line = this.writeNext(line);

			writer.write(line);
			Log.d(Slime.TAG, line);
			writer.newLine();
		} catch (IOException e) {
			Log.e(Slime.TAG, "ERROR during write of line");
			throw e;
		}
			
	}
	
	protected abstract String writeNext(String line);
	
	protected String addValue(String line, String value) {
		if (line != "") {
			return line + infoSep +  value;
		} else {
			return value;
		}
	}

	public final void setValues(GameItem item) {		
		this.itemType = this.getItemType(item);
		this.x = item.getPosition().x;
		this.y = item.getPosition().y;
		this.width = item.getWidth();
		this.height = item.getHeight();
		this.angle = item.getAngle();
		
		// x and y may need to be be shift on BL referential depending on item definition
		if (this.getIsBL()) {
			this.transformBL();
		}
		
		this.setValuesNext(item);
	}

	protected abstract boolean getIsBL();

	protected abstract String getItemType(GameItem item);

	protected abstract void setValuesNext(GameItem item);
	
	protected void transformBL() {
		this.x = this.x - this.width / 2;
		this.y = this.y - this.height / 2;
	}
}
