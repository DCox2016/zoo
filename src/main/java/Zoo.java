import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

/* Author: Dustin Cox
* Version 2
* Zoo class
*/

public class Zoo{
	private String whatAction;
	private String whatFood;
	private String foodOrAnimal;
	private int howManyAnimals;
	private int howManyFoods;
	private String whatAnimal;
	private TextField findAnimal;
	private TextField findFood;
	private String newAnimal;
	private String newFood;
	private int makeNewFile;
	private String whatAtt;
	private String whatSpecies;
	private String whatGender;
	private String whatDOB;
	private String whatLoan;
	private String whatFoods;
	private String whatTerrain;
	private String whatClimate;
	private int session;
		
	public Zoo(String action, String animal, String[] food, String fOA, int animals, int foods,
			String newA, TextField findA, TextField findF, String newFoodf, int newFile, String att, 
			String wFood, String gSpecies,String gGender, String gDOB, String gLoan, String gFood,
			String terrain, String climate, int currentS) {
		whatAction = action;
		whatAnimal = animal;
		foodOrAnimal = fOA;
		howManyAnimals = animals;
		howManyFoods = foods;
		newAnimal = newA;
		findAnimal = findA;
		findFood = findF;
		newFood = newFoodf;
		makeNewFile = newFile;
		whatAtt = att;
		whatFood = wFood;
		whatSpecies = gSpecies;
		whatGender = gGender;
		whatDOB = gDOB;
		whatLoan = gLoan;
		whatFoods = gFood;
		whatTerrain = terrain;
		whatClimate = climate;
		session = currentS;
	}
	
	//getter methods for zoo object
	public String getAction(){
		return whatAction;
	}
	
	public String getAnimal() {
		return whatAnimal;
	}

	public String getFOA() {
		return foodOrAnimal;
	}
	
	public int getHowManyA() {
		return howManyAnimals;
	}
	
	public int getHowManyF() {
		return howManyFoods;
	}
	
	public String getnewA() {
		return newAnimal;
	}
	
	public TextField getReviewA() {
		return findAnimal;
	}
	
	public TextField getReviewF() {
		return findFood;
	}
	
	public String getNewFood() {
		return newFood;
	}
	
	public int getNewFile() {
		return makeNewFile;
	}
	
	public String getAtt() {
		return whatAtt;
	}
	
	public String getFood() {
		return whatFood;
	}
	
	public String getGender() {
		return whatGender;
	}
	
	public String getDOB() {
		return whatDOB;
	}
	
	public String getLoan() {
		return whatLoan;
	}
	
	public String getCurrentSpecies() {
		return whatSpecies;
	}
	
	public String getCurrentFood() {
		return whatFoods;
	}
	
	public String getTerrain() {
		return whatTerrain;
	}
	
	public String getClimate() {
		return whatClimate;
	}
	
	public int getSession() {
		return session;
	}
	
				
    //setter method for zoo object
	public void makeAction(String usersAction) {
		whatAction = usersAction;
	}
	
	public void setAnimal(String usersAnimal) {
		whatAnimal = usersAnimal;
	}
	
	
	public void setFOA(String fOA) {
		foodOrAnimal = fOA;
	}
	
	
	public void setHowManyA(int aHowM) {
		howManyAnimals = aHowM;
	}
	
	public void setHowManyF(int fHowM) {
		howManyFoods = fHowM;
	}
		
	public void setNewAnimal(String newAnimal1) {
		newAnimal = newAnimal1;
	}
	
	public void setFindA(TextField newFindA) {
		findAnimal = newFindA;
	}
	
	public void setFindf(TextField newFindF) {
		findFood = newFindF;
	}
	
	public void setNewFood(String newFoodf) {
		newFood = newFoodf;
	}
	
	public void setNewFile(int nFile) {
		makeNewFile = nFile;
	}
	
	public void setAtt(String aAtt) {
		whatAtt = aAtt;
	}
	
	public void setFood(String f) {
		whatFood = f;
	}
	
	public void setCurrentSpecies(String s) {
		whatSpecies = s;
	}
	
	public void setCurrentGender(String g) {
		whatGender = g;
	}
	
	public void setCurrentDOB(String dob) {
		whatDOB = dob;
	}
	
	public void setCurrentLoan(String l) {
		whatLoan = l;
	}
	
	public void setFoods(String f) {
		whatFoods = f;
	}
	
	public void setTerrain(String sT) {
		whatTerrain = sT;
	}
	
	public void setClimate(String cT) {
		whatClimate = cT;
	}
	
	public void setSession(int cS) {
		session = cS;
	}
		
	//setter update method for zoo object
	public void updateAction(String updateAction) {
		whatAction = updateAction;
	}
	
	public void updateAnimal(String string) {
		whatAnimal = string;
	}
	
	
	public void updateFOA(String updateFOA) {
		foodOrAnimal = updateFOA;
	}
	
	
}

