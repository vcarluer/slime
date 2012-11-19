package gamers.associate.Slime.levels.itemdef;

import android.annotation.SuppressLint;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.levels.LevelUtil;

@SuppressLint("DefaultLocale") 
public class LevelInfoDef extends ItemDefinition {
	public static String Handled_Info = "LevelInfo";
	private static String Dimension_Auto = "Auto";
	private static String Dimension_Width = "Width";
	private static String Dimension_Height = "Height";	
	private String maxDimension;

	@Override
	public void createItem(Level level) {
		level.setLevelSize(this.width, this.height);
		
		if (maxDimension.toUpperCase().equals(Dimension_Auto.toUpperCase())) {		
			float heightRatio = this.height / this.width;
			if (LevelUtil.getHeightRatio() >= heightRatio) {
				level.setLevelSize(
						this.width,
						this.width * LevelUtil.getHeightRatio());
			}
			else {
				level.setLevelSize(
						this.height * LevelUtil.getWidthRatio(),
						this.height);
			}
		}
		
		if (maxDimension.toUpperCase().equals(Dimension_Width.toUpperCase())) {
			float heightRatio = this.height / this.width;			
			float height = this.height;
			if (LevelUtil.getHeightRatio() > heightRatio) {				
				height = this.width * LevelUtil.getHeightRatio();
			}
			
			level.setLevelSize(
					this.width,
					height);
		}
		
		if (maxDimension.toUpperCase().equals(Dimension_Height.toUpperCase())) {
			float widthRatio = this.width / this.height;			
			float width = this.width;
			if (LevelUtil.getWidthRatio() > widthRatio) {				
				width = this.height * LevelUtil.getWidthRatio();
			}
			level.setLevelSize(
					width,
					this.height);
		}
		
		level.setLevelOrigin(this.x, this.y);
		level.setMaxDimension(this.maxDimension);
		LevelUtil.createGroundBox(level);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Info);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.maxDimension = infos[start];
	}

	@Override
	protected void initClassHandled() {
		// NONE		
	}

	@Override
	protected String writeNext(String line) {
		line = this.addValue(line, this.maxDimension);
		return line;
	}

	@Override
	protected boolean getIsBL() {
		return false;
	}

	@Override
	protected String getItemType(GameItem item) {
		return Handled_Info;
	}

	@Override
	protected void setValuesNext(GameItem item) {		
	}
	
	public void setValuesSpe(Level level) {		
		this.itemType = this.getItemType(null);
		this.x = level.getLevelOrigin().x;
		this.y = level.getLevelOrigin().y;
		this.width = level.getLevelWidth();
		this.height = level.getLevelHeight();
		this.angle = 0;
		this.maxDimension = level.getMaxDimension();
	}

}
