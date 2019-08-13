public class mountain extends terrain{
	private String isProtected;
		
	public mountain() {
		super();
		isProtected = "none";
	}
	
	public void showType(String tType) {
		setName("Mount Rushmore");
		setType(tType);
		setClimate("43 - 79");
		setTopography("Windy, Rocky, and Steams");
	}
	
	public void showTerrain() {
		setType(getType());
	}
	
	//settler method
    public void setProtected(String protectedT) {
		isProtected = protectedT;
	}
    
    //getter method
    public String getProSt() {
    	return isProtected;
    }
    
	@Override
	public int compareTo(terrain o) {
		return 0;
	}
	
}