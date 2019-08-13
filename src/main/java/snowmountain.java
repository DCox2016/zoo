public class snowmountain extends mountain{
	public snowmountain() {
		super();
	}
	
	public void showType(String tType) {
		setName("Mount Everest");
		setType(tType);
		setClimate("-32 - 25");
		setTopography("Windy, Rocky, and Cold");
		setProtected("protected");
	}
	
	public void showTerrain() {
		setType(getType());
	}
	
	 //Convert Object to String
    public String toString() {
    	return "Terrain type: " + getType() + "\n" + "Terrain name: " + getName() + "\nClimate: " + getClimate() +  " \u00B0" + "F" +
				"\nTopography consist of " + getTopography() + "\n" + getName() + " " + "is a " + getProSt()+ " " + "terrain.\n";
    }
	
}