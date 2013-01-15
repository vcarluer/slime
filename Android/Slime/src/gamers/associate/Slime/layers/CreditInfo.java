package gamers.associate.Slime.layers;


public class CreditInfo {
	private String category;
	private String[] names;
	
	public CreditInfo(String category, String[] names) {
		this.category = category;
		this.names = names;
	}

	public String getCategory() {
		return category;
	}

	public String[] getNames() {
		return names;
	}
}
