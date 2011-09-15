package gamers.associate.Slime.levels.itemdef;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.LevelUtil;

public class LevelInfoDef extends ItemDefinition {
	private static String Handled_Size = "LevelInfo";
	private static String Dimension_Auto = "Auto";
	private static String Dimension_Width = "Width";
	private static String Dimension_Height = "Height";	
	
	private String maxDimension;

	@Override
	public void createItem(Level level) {
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
		
		LevelUtil.createGroundBox(level);
	}

	@Override
	protected void initTypeHandled() {
		this.typesHandled.add(Handled_Size);
	}

	@Override
	protected void parseNext(String[] infos, int start) {
		this.maxDimension = infos[start];
	}
}
