package gamers.associate.Slime.levels.generator;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.itemdef.BlocInfoDef;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;
import gamers.associate.Slime.levels.itemdef.LevelInfoDef;

public class BlocDefinitionParser extends BlocDefinition {
	
	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		this.setOffset(xOffset, yOffset);
		this.buildLevel(level);
	}
	
	@Override
	protected void HandleLine(Level level, String line) throws Exception {
		ItemDefinition itemDef = this.getItemDef(line);
		if (itemDef != null) {
			itemDef.parseAndCreate(line, level, this.currentXOffset, this.currentYOffset);
		}
	}

	@Override
	protected void defineIgnoredItems() {
		super.defineIgnoredItems();
		this.ignoredItems.add(BlocInfoDef.Handled_Info);
		this.ignoredItems.add(LevelInfoDef.Handled_Info);
	}
}
