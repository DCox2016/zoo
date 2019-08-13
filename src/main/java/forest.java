public class forest extends terrain{
	private String isDeveloped;
	
	public forest() {
		super();
		isDeveloped = "none";
	}
	
	public void showType(String tType) {
		setName("The Dark Forest");
		setType(tType);
		setClimate("68 - 77");
		setTopography("Hills, Rivers, and Lakes");
	}
	
	public void showTerrain() {
		setType(getType());
	}
	
	//settler method
    public void setDeveloped(String devT) {
    	isDeveloped = devT;
	}
    
    //getter method
    public String getDevSt() {
    	return isDeveloped;
    }


	@Override
	public int compareTo(terrain o) {
		return 0;
	}
	

	
}