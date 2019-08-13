public class desertoasis extends desert{
	public desertoasis() {
		super();
	}
	
	public void showType(String tType) {
		setName("Wadi Bani Khalid Desert Oasis");
		setType(tType);
		setClimate("80 - 105");
		setTopography("Dunes, Lakes, and Trees");
	}
	
	public void showTerrain() {
		setType(getType());
	}
	
	@Override
	public int compareTo(terrain o) {
		return 0;
	}
	
}