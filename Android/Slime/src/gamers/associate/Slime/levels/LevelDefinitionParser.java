package gamers.associate.Slime.levels;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.levels.itemdef.BecBunsenDef;
import gamers.associate.Slime.levels.itemdef.BumperAngleDef;
import gamers.associate.Slime.levels.itemdef.ButtonDef;
import gamers.associate.Slime.levels.itemdef.CircularSawDef;
import gamers.associate.Slime.levels.itemdef.GoalPortalDef;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;
import gamers.associate.Slime.levels.itemdef.LaserGunDef;
import gamers.associate.Slime.levels.itemdef.LevelInfoDef;
import gamers.associate.Slime.levels.itemdef.MenuNodeDef;
import gamers.associate.Slime.levels.itemdef.MetaMenuDef;
import gamers.associate.Slime.levels.itemdef.PlatformDef;
import gamers.associate.Slime.levels.itemdef.PolygonDef;
import gamers.associate.Slime.levels.itemdef.SpawnDef;
import gamers.associate.Slime.levels.itemdef.TargetDef;
import gamers.associate.Slime.levels.itemdef.TimeAttackDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class LevelDefinitionParser extends LevelDefinition
{
	private static String SpecialLevel = "Special";
	private String resourceName;
	private Context context;
	private ArrayList<ItemDefinition> itemDefinitions;
	private HashMap<String, ItemDefinition> typeHandler;
	
	public LevelDefinitionParser(String resourceName, Context context) {
		this.gamePlay = GamePlay.ManuallyDefined;
		this.resourceName = resourceName;
		this.context = context;
		this.itemDefinitions = new ArrayList<ItemDefinition>();
		this.typeHandler = new HashMap<String, ItemDefinition>();
		this.createItemDefinitions();
		this.buildItemTypeMap();
				
		if (this.resourceName.toUpperCase().contains(SpecialLevel.toUpperCase())) {
			this.isSpecial = true;
		}
		
		int lastPeriodPos = this.resourceName.lastIndexOf('.');
		if (lastPeriodPos != -1) {
			this.id = this.resourceName.substring(0, lastPeriodPos);
		}
		else {
			this.id = this.resourceName;
		}
	}
	
	private void createItemDefinitions() {
		this.itemDefinitions.add(new PlatformDef());
		this.itemDefinitions.add(new LevelInfoDef());
		this.itemDefinitions.add(new TimeAttackDef());
		this.itemDefinitions.add(new GoalPortalDef());
		this.itemDefinitions.add(new SpawnDef());
		this.itemDefinitions.add(new BecBunsenDef());
		this.itemDefinitions.add(new BumperAngleDef());
		this.itemDefinitions.add(new ButtonDef());
		this.itemDefinitions.add(new CircularSawDef());
		this.itemDefinitions.add(new MetaMenuDef());
		this.itemDefinitions.add(new MenuNodeDef());
		this.itemDefinitions.add(new PolygonDef());
		this.itemDefinitions.add(new LaserGunDef());
		this.itemDefinitions.add(new TargetDef());
	}
	
	private void buildItemTypeMap() {
		for (ItemDefinition itemDef : this.itemDefinitions) {
			ArrayList<String> types = itemDef.getTypesHandled();
			for(String typeHandled : types) {
				this.typeHandler.put(typeHandled, itemDef);
			}
		}
	}
	
	@Override
	public void buildLevel(Level level) {
		java.io.InputStream inputStream;
		try {
			inputStream = this.context.getAssets().open(this.resourceName);
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;		
			
			int i = 0;
			try {
				while (( line = buffreader.readLine()) != null) {
					try {
						i++;
						this.HandleLine(level, line);
					} catch (Exception e) {
						Log.e(Slime.TAG, "ERROR during read of " + this.resourceName + " line " + String.valueOf(i));
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				Log.e(Slime.TAG, "ERROR during read of " + this.resourceName + " line " + String.valueOf(i));
				e.printStackTrace();
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + this.resourceName);
			e1.printStackTrace();
		}				
	}
	
	private void HandleLine(Level level, String line) throws Exception {
		String[] items = line.split(";", -1);		
		String itemType = items[0];
		ItemDefinition itemDef = this.typeHandler.get(itemType);
		itemDef.parseAndCreate(line, level);		
	}
}
