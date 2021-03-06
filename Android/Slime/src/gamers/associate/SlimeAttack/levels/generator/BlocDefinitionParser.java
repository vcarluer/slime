package gamers.associate.SlimeAttack.levels.generator;

import gamers.associate.SlimeAttack.SlimeAttack;
import gamers.associate.SlimeAttack.game.Level;
import gamers.associate.SlimeAttack.game.LevelDifficulty;
import gamers.associate.SlimeAttack.game.SlimeFactory;
import gamers.associate.SlimeAttack.levels.itemdef.BecBunsenDef;
import gamers.associate.SlimeAttack.levels.itemdef.BlocInfoDef;
import gamers.associate.SlimeAttack.levels.itemdef.CircularSawDef;
import gamers.associate.SlimeAttack.levels.itemdef.EnergyBallDef;
import gamers.associate.SlimeAttack.levels.itemdef.EnergyBallGunDef;
import gamers.associate.SlimeAttack.levels.itemdef.ItemDefinition;
import gamers.associate.SlimeAttack.levels.itemdef.LaserGunDef;
import gamers.associate.SlimeAttack.levels.itemdef.LevelInfoDef;
import gamers.associate.SlimeAttack.levels.itemdef.RedDef;
import gamers.associate.SlimeAttack.levels.itemdef.StarDef;
import gamers.associate.SlimeAttack.levels.itemdef.TeslaCoilDef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import android.util.FloatMath;

public class BlocDefinitionParser extends BlocDefinition {
	private static Random rand = new Random();
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
		this.hazardItemsDef.add(BecBunsenDef.Handled_BecBunsen);	
		this.hazardItemsDef.add(CircularSawDef.Handled_Def);
		this.hazardItemsDef.add(LaserGunDef.Handled_Def);		
		this.hazardItemsDef.add(RedDef.Handled_DefMini);
		this.hazardItemsDef.add(TeslaCoilDef.Handled_Def);
		this.hazardItemsDef.add(EnergyBallDef.Handled_Def);
		this.hazardItemsDef.add(EnergyBallGunDef.Handled_Def);
	}

	@Override
	public void buildLevel(Level level, int xOffset, int yOffset) {
		this.setOffset(xOffset, yOffset);
		this.setBlocId(UUID.randomUUID());
		this.buildLevel(level);
	}
	
	@Override
	public boolean buildLevel(Level level) {
		InputStream inputStream;
		boolean constructed = true;
		try {
			SlimeFactory.Log.d(SlimeAttack.TAG, "Loading level from " + this.getResourceName());
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
				this.hazardLines.clear();
				while (( line = buffreader.readLine()) != null) {
					try {
						i++;
						SlimeFactory.Log.d(SlimeAttack.TAG, line);
						String itemType = this.getItemType(line);
						if (itemType == StarDef.Handled_Def) {
							if (this.isStarBlock()) {
								this.HandleLine(level, line);
							} else {
								SlimeFactory.Log.d(SlimeAttack.TAG, "Skipping star line " + String.valueOf(i));
							}
						} else {
							if (this.hazardItemsDef.contains(itemType)) {
								this.storeHazardLine(line);
							} else {
								this.HandleLine(level, line);
							}
						}						
					} catch (Exception e) {
						constructed = false;
						SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
						e.printStackTrace();
					}										
				}
				
				this.pickHazardLines(level);
			} catch (IOException e) {
				constructed = false;
				SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during read of " + this.getResourceName() + " line " + String.valueOf(i));
				e.printStackTrace();
			} finally {
				if (buffreader != null) {
					buffreader.close();
				}
			}
		} catch (IOException e1) {
			constructed = false;
			SlimeFactory.Log.e(SlimeAttack.TAG, "ERROR during opening of " + this.getResourceName());
			e1.printStackTrace();
		}
		
		this.postBuildItem.postBuild();
		
		return constructed;
	}
	
	@Override
	protected void createItemDefinitions() {
		super.createItemDefinitions();
		BlocInfoDef infoDef = new BlocInfoDef();
		this.postBuildItem = infoDef;
	}

	private void pickHazardLines(Level level) {
		if (SlimeFactory.GameInfo.getDifficulty() == LevelDifficulty.Extrem) {
			for(String line : this.hazardLines) {
				try {
					this.HandleLine(level, line);					
				} catch (Exception e) {
					SlimeFactory.Log.e(SlimeAttack.TAG, "Error during hazard pick");
					e.printStackTrace();
				}
			}
			
			return;
		}
		float nbHazard = this.hazardLines.size();
		int nbPick = 0;
		if (SlimeFactory.LevelBuilder.isTutorial()) {
			nbPick = (int) nbHazard;
		} else {
			switch (SlimeFactory.GameInfo.getDifficulty()) {
			default:
			case LevelDifficulty.Easy: nbPick = (int) FloatMath.ceil(nbHazard / 4f); break;
			case LevelDifficulty.Normal: nbPick = (int) FloatMath.ceil(nbHazard / 2f); break;
			case LevelDifficulty.Hard: nbPick = (int) FloatMath.ceil(nbHazard * 3f / 4f); break;
			case LevelDifficulty.Extrem: nbPick = (int) FloatMath.ceil(nbHazard); break;
		}
		}					
		
		if (nbPick > 0) {
			this.pickAndHandle(nbPick, this.hazardLines, level);
		}
	}

	private void pickAndHandle(int nbPick, List<String> lines, Level level) {
		int pickPos = rand.nextInt(lines.size());
		try {
			this.HandleLine(level, lines.get(pickPos));			
			lines.remove(pickPos);
			nbPick--;
			if (nbPick > 0) {
				this.pickAndHandle(nbPick, lines, level);
			}
		} catch (Exception e) {
			SlimeFactory.Log.e(SlimeAttack.TAG, "Error during hazard pick");
			e.printStackTrace();
		}		
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
