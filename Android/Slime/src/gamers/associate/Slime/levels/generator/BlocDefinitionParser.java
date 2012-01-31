package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;

public class BlocDefinitionParser extends BlocDefinition {
	
	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		this.setOffset(xOffset, yOffset);
		this.buildLevel(level);
	}
	
	@Override
	protected void HandleLine(Level level, String line) throws Exception {
		ItemDefinition itemDef = this.getItemDef(line);
		itemDef.parseAndCreate(line, level, this.currentXOffset, this.currentYOffset);		
	}
}
