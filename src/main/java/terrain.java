/* Author: Dustin Cox
* Version 1
* terrain class
*/

//Abstract class Terrain
public abstract class terrain implements Comparable<terrain>{
	
	private String tType;
	private String nameT;
	private String climateT;
	private String topographyT;

	public terrain() {
		tType = "none";
		nameT = "none";
		climateT = "none";
		topographyT = "none";
	}
	
	public abstract void showType(String tType);
	
	//settler methods terrain object
	public void setName(String tName) {
		nameT = tName;
	}
	
	public void setClimate(String climate) {
		climateT = climate;
	}
	
	public void setTopography(String topography) {
		topographyT = topography;
	}
	
	public void setType(String type) {
		tType = type;
	}
	
	//getter methods for terrain object
	public String getName() {
		return nameT;
	}
	
	public String getClimate() {
		return climateT;
	}
	
	public String getTopography() {
		return topographyT;
	}
	
	public String getType() {
		return tType;
	}
	
	//Convert Object to string
	public String toString() {
		return "Terrain type: " + getType() + "\n" + "Terrain name: " + getName() + "\nClimate: " + getClimate() +  " \u00B0" + "F" +
				"\nTopography consist of " + getTopography() + "\n";
	}
			
}


