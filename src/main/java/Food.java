import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
/* Author: Dustin Cox
* Version 2
* Zoo class
*/
public class Food{

	private String[] foodInfo;
	private TextField food;
	private TextField description;
	private TextField vendor;
	private TextField onhand;
	private TextField units;

	public Food(String[] allFood, TextField foodF, TextField descriptionF, TextField vendorF,
			TextField onHandF, TextField unitsF) {
		foodInfo = allFood;
		food = foodF;
		description = descriptionF;
		vendor = vendorF;
		onhand = onHandF;
		units = unitsF;
	}
	
	//getter methods for animal object
	public String[] getInfo() {
		return foodInfo;
	}
	
	public TextField getFood() {
		return food;
	}
	
    public TextField getDescription() {
		return description;
	}
    
    public TextField getVendor() {
		return vendor;
	}
    
    public TextField getOnHand() {
		return onhand;
	}
    
    public TextField getUnits() {
		return units;
	}
		
	//setter method for animal object
	public void setInfo(String[] allInfo) {
		foodInfo = allInfo;
	}
	
	public void setFood(TextField foodF) {
		food = foodF;
	}

	public void setDescription(TextField descriptionF) {
		description = descriptionF;
	}
	
	public void setVendor(TextField vendorF) {
		vendor = vendorF;
	}
	
	public void setOnHand(TextField onHandF) {
		onhand = onHandF;
	}
	
	public void setUnits(TextField unitsF) {
		units = unitsF;
	}
	

	//setter Update for animal object
	public void updateFood(String[] foods) {
		foodInfo = foods;
	}
	
}