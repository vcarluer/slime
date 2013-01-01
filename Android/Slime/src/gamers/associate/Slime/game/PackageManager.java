package gamers.associate.Slime.game;

import java.util.ArrayList;
import java.util.List;

public class PackageManager {
	private List<WorldPackage> packages;
	
	public PackageManager() {
		this.packages = new ArrayList<WorldPackage>();
		this.initPackageList();
	}

	private void initPackageList() {
		// order is important here for package order field
		this.addPackage(new LaboratoryPackage());
	}
	
	private void addPackage(WorldPackage wPackage) {
		wPackage.setOrder(this.packages.size() + 1);
		this.packages.add(wPackage);
	}
	
	public List<WorldPackage> getPackages() {
		return this.packages;
	}
}
