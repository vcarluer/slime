package gamers.associate.Slime.levels.generator;

import java.util.UUID;

import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.itemdef.BlocInfoDef;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;
import gamers.associate.Slime.levels.itemdef.LevelInfoDef;

public class BlocDefinitionParser extends BlocDefinition {
	
	public BlocDefinitionParser(String resourceName) {
		super(resourceName, true);		
	}
	
	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		this.setOffset(xOffset, yOffset);
		this.setBlocId(UUID.randomUUID());
		this.buildLevel(level);
	}
	
	@Override
	protected void HandleLine(Level level, String line) throws Exception {
		ItemDefinition itemDef = this.getItemDef(line);
		// Todo: plug here probability check?
		if (itemDef != null) {
			itemDef.parseAndCreate(line, level, this.currentXOffset, this.currentYOffset, this.getBlocId().toString());
		}
	}

	@Override
	protected void defineIgnoredItems() {
		super.defineIgnoredItems();
		this.ignoredItems.add(BlocInfoDef.Handled_Info);
		this.ignoredItems.add(LevelInfoDef.Handled_Info);
	}
}
