public class desert extends terrain{
	
	public desert() {
		super();
	}
	
	public void showType(String tType) {
		setName("Sarah Desert");
		setType(tType);
		setClimate("80-122");
		setTopography("Dunes, Gravel Plains, and Salt Falts");
	}
	
	public void showTerrain() {
		setName(getName());
	}

	@Override
	public int compareTo(terrain o) {
		return 0;
	}
}