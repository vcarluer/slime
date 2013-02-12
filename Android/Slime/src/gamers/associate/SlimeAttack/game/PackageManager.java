package gamers.associate.SlimeAttack.game;

import java.util.ArrayList;
import java.util.List;

public class PackageManager {
	private List<WorldPackage> packages;
	private WorldPackage currentPackage;
	
	public PackageManager() {
		this.packages = new ArrayList<WorldPackage>();
		this.initPackageList();
	}

	private void initPackageList() {
		// order is important here for package order field
		this.addPackage(new LaboratoryPackage());
//		this.addPackage(new MexicoPackage());
	}
	
	private void addPackage(WorldPackage wPackage) {
		wPackage.setOrder(this.packages.size() + 1);
		this.packages.add(wPackage);
	}
	
	public List<WorldPackage> getPackages() {
		return this.packages;
	}

	public WorldPackage getPackage(int order) {
		if (this.packages.size() >= order) {
			return this.packages.get(order - 1);
		} else {
			return null;
		}
	}

	public int getPackageCount() {
		return this.packages.size();
	}

	public WorldPackage getCurrentPackage() {
		return currentPackage;
	}

	public void setCurrentPackage(WorldPackage currentPackage) {
		this.currentPackage = currentPackage;
	}
}
