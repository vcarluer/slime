package gamers.associate.Slime.levels.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import android.util.Log;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.levels.itemdef.BlocInfoDef;
import gamers.associate.Slime.levels.itemdef.ItemDefinition;
import gamers.associate.Slime.levels.itemdef.LevelInfoDef;

public class BlocDefinitionParser extends BlocDefinition {
	private HashSet<String> hazardItemsDef;
	private List<String> hazardLines;
	
	public BlocDefinitionParser(String resourceName) {
		super(resourceName, true);		
		
		this.hazardItemsDef = new HashSet<String>();
		this.registerHazardItems();
		
		this.hazardLines = new ArrayList<String>();
	}
	
	private void registerHazardItems() {
		// Todo: loop on itemdef and call a new isDeadly property?
		this.hazardItemsDef.add("BecBunsen");	
		this.hazardItemsDef.add("CircularSaw");
		this.hazardItemsDef.add("LaserGun");		
	}

	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		this.setOffset(xOffset, yOffset);
		this.setBlocId(UUID.randomUUID());
		this.buildLevel(level);
	}
	
	@Override
	public void buildLevel(Level level) {
		InputStream inputStream;
		try {
			Log.d(Slime.TAG, "Loading level from " + this.getResourceName());
			if (this.isLocalStorage) {
				inputStream = SlimeFactory.ContextActivity.openFileInput(this.getResourceName());
			} else {
				inputStream = SlimeFactory.ContextActivity.getAssets().open(this.getResourceName());
			}			
			InputStreamReader inputreader = new InputStreamReader(inputStream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;		
			
			int i = 0;
			try {
				
				while (( line = buffreader.readLine()) != null) {
					try {
						i++;
						Log.d(Slime.TAG, line);
						String itemType = this.getItemType(line);
						if (this.hazardItemsDef.contains(itemType)) {
							this.storeHazardLine(line);
						} else {
							this.HandleLine(level, line);
						}
						
					} catch (Exception e) {
						Log.e(Slime.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
						e.printStackTrace();
					}
					
					this.pickHazardLines();
				}
			} catch (IOException e) {
				Log.e(Slime.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + this.getResourceName());
			e1.printStackTrace();
		}
		
		this.postBuildItem.postBuild();
	}
	
	private void pickHazardLines() {
		// TODO Auto-generated method stub
		
	}

	private void storeHazardLine(String line) {
		this.hazardLines.add(line);
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
