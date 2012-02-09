package gamers.associate.Slime.levels;

import gamers.associate.Slime.Slime;
import gamers.associate.Slime.game.Level;
import gamers.associate.Slime.game.SlimeFactory;
import gamers.associate.Slime.game.TimeAttackGame;
import gamers.associate.Slime.items.base.GameItem;
import gamers.associate.Slime.items.custom.LevelEnd;
import gamers.associate.Slime.levels.itemdef.BecBunsenDef;
import gamers.associate.Slime.levels.itemdef.BoxDef;
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
import gamers.associate.Slime.levels.itemdef.RedDef;
import gamers.associate.Slime.levels.itemdef.SpawnDef;
import gamers.associate.Slime.levels.itemdef.SpriteDef;
import gamers.associate.Slime.levels.itemdef.StarDef;
import gamers.associate.Slime.levels.itemdef.TargetDef;
import gamers.associate.Slime.levels.itemdef.TimeAttackDef;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.Log;

public class LevelDefinitionParser extends LevelDefinition
{
	private static String SpecialLevel = "Special";
	private String resourceName;	
	private ArrayList<ItemDefinition> itemDefinitions;
	private HashMap<String, ItemDefinition> typeHandler;
	private HashMap<Class, ItemDefinition> classHandler;
	private ItemDefinition postBuildItem;
	private boolean isLocalStorage;
	protected Set<Class> ignoredClasses;
	protected Set<String> ignoredItems;
	
	public LevelDefinitionParser() {		
	}
	
	public LevelDefinitionParser(String resourceName, boolean noUserInfoStore) {		
		this.noStore = noUserInfoStore;
		this.gamePlay = GamePlay.ManuallyDefined;
		this.resourceName = resourceName;		
		this.itemDefinitions = new ArrayList<ItemDefinition>();
		this.typeHandler = new HashMap<String, ItemDefinition>();
		this.classHandler = new HashMap<Class, ItemDefinition>();
		this.ignoredClasses = new HashSet<Class>();
		this.ignoredItems = new HashSet<String>();
		this.createItemDefinitions();		
		this.buildItemTypeMap();
		this.defineIgnoredItems();
		this.defineIgnoreClasses();
				
		if (this.resourceName.toUpperCase().contains(SpecialLevel.toUpperCase())) {
			this.isSpecial = true;
		}
		
		int lastPeriodPos = this.resourceName.lastIndexOf('.');
		if (lastPeriodPos != -1) {
			this.setId(this.resourceName.substring(0, lastPeriodPos));
		}
		else {
			this.setId(this.resourceName);
		}
	}
	
	protected void defineIgnoredItems() {
		// TODO Auto-generated method stub
		
	}

	public LevelDefinitionParser(String resourceName) {
		this(resourceName, false);
	}
	
	private void defineIgnoreClasses() {
		this.ignoredClasses.add(LevelEnd.class);
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
		MenuNodeDef menuNode = new MenuNodeDef();
		this.itemDefinitions.add(menuNode);
		this.postBuildItem = menuNode;
		this.itemDefinitions.add(new PolygonDef());
		this.itemDefinitions.add(new LaserGunDef());
		this.itemDefinitions.add(new TargetDef());
		this.itemDefinitions.add(new StarDef());
		this.itemDefinitions.add(new BoxDef());
		this.itemDefinitions.add(new SpriteDef());
		this.itemDefinitions.add(new RedDef());
	}
	
	private void buildItemTypeMap() {
		for (ItemDefinition itemDef : this.itemDefinitions) {
			ArrayList<String> types = itemDef.getTypesHandled();
			for(String typeHandled : types) {
				this.typeHandler.put(typeHandled, itemDef);
			}
			
			ArrayList<Class> classes = itemDef.getClassesHandled();
			for(Class claz : classes) {
				this.classHandler.put(claz, itemDef);
			}
		}
	}
	
	@Override
	public void buildLevel(Level level) {
		InputStream inputStream;
		try {
			Log.d(Slime.TAG, "Loading level from " + this.resourceName);
			if (this.isLocalStorage) {
				inputStream = SlimeFactory.ContextActivity.openFileInput(this.resourceName);
			} else {
				inputStream = SlimeFactory.ContextActivity.getAssets().open(this.resourceName);
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
						this.HandleLine(level, line);
					} catch (Exception e) {
						Log.e(Slime.TAG, "ERROR during read of " + this.resourceName + " line " + String.valueOf(i));
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				Log.e(Slime.TAG, "ERROR during read of " + this.resourceName + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening of " + this.resourceName);
			e1.printStackTrace();
		}
		
		this.postBuildItem.postBuild();
	}
	
	protected void HandleLine(Level level, String line) throws Exception {
		ItemDefinition itemDef = this.getItemDef(line);
		if (itemDef != null) {
			itemDef.parseAndCreate(line, level);
		}
	}
	
	protected ItemDefinition getItemDef(String line) {
		String[] items = line.split(";", -1);		
		String itemType = items[0];
		if (!this.ignoredItems.contains(itemType)) {
			return this.typeHandler.get(itemType);
		} else {
			Log.d(Slime.TAG, "Item " + itemType + " ignored");
			return null;
		}		
	}
	
	public void storeLevel(Level level) {
		BufferedWriter buffWriter = null;
		try {
			Log.d(Slime.TAG, "Storing level in " + this.resourceName);
			FileOutputStream fos = SlimeFactory.ContextActivity.openFileOutput(this.resourceName, Context.MODE_PRIVATE);
			OutputStreamWriter streamWriter = new OutputStreamWriter(fos);
			buffWriter = new BufferedWriter(streamWriter);
			// First line: LevelInfo
			LevelInfoDef infoDef = (LevelInfoDef) this.typeHandler.get(LevelInfoDef.Handled_Info);
			infoDef.setValuesSpe(level);
			infoDef.writeLine(buffWriter);
			// Second line: TimeAttackGame
			if (level.getGamePlay() != null) {
				TimeAttackDef gamePlayDef = (TimeAttackDef) this.typeHandler.get(TimeAttackDef.Handled_TimeAttack);
				TimeAttackGame gamePlay = (TimeAttackGame) level.getGamePlay();
				gamePlayDef.setValues(gamePlay);
				gamePlayDef.writeLine(buffWriter);
			}
						
			// Next lines
			for(GameItem item : level.getItemsToAdd()) {
				if (!this.ignoredClasses.contains(item.getClass())) {
					ItemDefinition itemDef = this.classHandler.get(item.getClass());
					if (itemDef != null) {			
						itemDef.setValues(item);
						itemDef.writeLine(buffWriter);
					}
					else {
						Log.e(Slime.TAG, "ERROR, ItemDefinition not found for " + item.getClass().toString() + ". Can not store item in " + this.resourceName);
					}
				}
			}
		} catch (FileNotFoundException ex) {
			Log.e(Slime.TAG, "ERROR, file not found " + this.resourceName);
			ex.printStackTrace();
        } catch (IOException e1) {
			Log.e(Slime.TAG, "ERROR during opening or write of " + this.resourceName);
			e1.printStackTrace();
        } finally {
        	 //Close the BufferedWriter
            try {
                if (buffWriter != null) {
                	buffWriter.flush();
                	buffWriter.close();
                }
            } catch (IOException ex) {
            	Log.e(Slime.TAG, "ERROR during close of " + this.resourceName);
                ex.printStackTrace();
            }
        }
	}

	public boolean isStored() {
		return SlimeFactory.ContextActivity.getFileStreamPath(this.resourceName).exists();
	}

	public boolean isLocalStorage() {
		return isLocalStorage;
	}

	public void setLocalStorage(boolean isLocalStorage) {
		this.isLocalStorage = isLocalStorage;
	}
	
	public void resetStorage() {
		SlimeFactory.ContextActivity.deleteFile(this.resourceName);
	}
}
