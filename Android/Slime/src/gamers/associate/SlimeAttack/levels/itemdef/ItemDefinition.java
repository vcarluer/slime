package gamers.associate.SlimeAttack.levels.itemdef;

import android.annotation.SuppressLint;
import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.items.base.GameItem;
import gamers.associate.SlimeAttack.levels.generator.BlocDefinition;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

@SuppressLint("DefaultLocale") public abstract class ItemDefinition {		
	protected static String None = "None";
	protected static String infoSep = ";";
	
	protected String itemType;

	protected float x;
	protected float y;
	protected float width;
	protected float height;
	protected float angle;
	protected String name;
	
	protected ArrayList<String> typesHandled;
	@SuppressWarnings("rawtypes")
	protected ArrayList<Class> classHandled;	
	
	protected float xOffset;
	protected float yOffset;
	private String idPre;
	
	@SuppressWarnings("rawtypes")
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
		return xVal + this.xOffset * BlocDefinition.BlocWidth;
	}
	
	protected float getOffY(float yVal) {
		return yVal + this.yOffset * BlocDefinition.BlocHeight;
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
			int cpt = 1;
			this.name = infos[cpt];
			cpt++;
			this.x = ZeroIfNone(infos[cpt]);
			cpt++;
			this.y = ZeroIfNone(infos[cpt]);
			cpt++;
			this.width = ZeroIfNone(infos[cpt]);
			cpt++;
			this.height = ZeroIfNone(infos[cpt]);
			cpt++;
			this.angle = ZeroIfNone(infos[cpt]);
			cpt++;
			this.parseNext(infos, cpt);
		}
		catch (Exception ex) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "BAD FORMAT for item definition " + this.getType());
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
		this.parseAndCreate(line, level, 0, 0, "");
	}
	
	public void parseAndCreate(String line, Level level, float xOffset, float yOffset, String idPre) throws Exception {						
		this.parse(line);
		this.setOffset(xOffset, yOffset);
		this.setIdPre(idPre);
		this.createItem(level);
	}
	
	private void setIdPre(String idPre) {
		this.idPre = idPre;
	}
	
	protected String getIdPre() {
		return this.idPre;
	}
	
	protected String getUName() {
		return this.getUString(this.name);
	}
	
	protected String getUString(String original) {
		return this.idPre + original;
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
	
	@SuppressWarnings("rawtypes")
	public ArrayList<Class> getClassesHandled() {
		return this.classHandled;
	}
	
	public void writeLine(BufferedWriter writer) throws IOException {
		try {
			String line = "";		
			line = this.addValue(line, this.itemType);
			line = this.addValue(line, this.name);
			line = this.addValue(line, String.valueOf(this.x));
			line = this.addValue(line, String.valueOf(this.y));
			line = this.addValue(line, String.valueOf(this.width));
			line = this.addValue(line, String.valueOf(this.height));
			line = this.addValue(line, String.valueOf(this.angle));
			
			line = this.writeNext(line);

			writer.write(line);
			SlimeFactory.Log.d(SlimeAttack.TAG, line);
			writer.newLine();
		} catch (IOException e) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during write of line");
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
		this.name = item.getName();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
