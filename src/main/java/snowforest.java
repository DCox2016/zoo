public class snowforest extends forest{
	public snowforest() {
		super();
	}
	
	public void showType(String tType) {
		setName("Taiga Forest");
		setType(tType);
		setClimate("43 - 79");
		setTopography("Hills, Frozen Lakes, and Snow");
		setDeveloped("un-developed");
	}
	
	public void showTerrain() {
		setType(getType());
	}
	
	 //Convert Object to String
    public String toString() {
    	return "Terrain type: " + getType() + "\n" + "Terrain name: " + getName() + "\nClimate: " + getClimate() +  " \u00B0" + "F" +
				"\nTopography consist of " + getTopography() + "\n" + getName() + " " + "is a " + getDevSt()+ " " + "terrain.\n";
    }
	
}