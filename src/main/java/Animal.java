import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
/* Author: Dustin Cox
* Version 2
* Zoo class
*/
public class Animal{
	private String[] allAnimals;
	private TextField name;
	private TextField species;
	private TextField gender;
	private LocalDate bod;
	private TextField loan;
	private TextField food1;
	private TextField food2;
	private TextField food3;
	private TextField food4;
	private TextField food5;
	
	

	public Animal(String[] animals, TextField nameA, TextField speciesA, LocalDate bodA,
			TextField loanA, TextField foodA1, TextField foodA2, TextField foodA3, TextField foodA4,
			TextField foodA5, TextField genderA) {
		allAnimals = animals;
		name = nameA;
		species = speciesA;
		bod = bodA;
		loan = loanA;
		food1 = foodA1;
		food2 = foodA2;
		food3 = foodA3;
		food4 = foodA4;
		food5 = foodA5;
		gender = genderA;
	}
	
	//getter methods for animal object
	public String[] getInfo() {
		return allAnimals;
	}
	
	public TextField getName() {
		return name;
	}

    public TextField getSpecies() {
		return species;
	}
    
    public LocalDate getBOD() {
		return bod;
	}
    
    public TextField getLoan() {
		return loan;
	}

    public TextField getFood1() {
    	return food1;
    }

    public TextField getFood2() {
    	return food2;
    }
    
    public TextField getFood3() {
    	return food3;
    }

    public TextField getFood4() {
    	return food4;
    }

    public TextField getFood5() {
    	return food5;
    }
    
    public TextField getGender() {
    	return gender;
    }
		
	//setter method for animal object
	public void setInfo(String[] allInfo) {
		allAnimals = allInfo;
	}
	
	public void setName(TextField aName) {
		name = aName;
	}
	
	public void setSpecies(TextField setSpecies) {
		species = setSpecies;
	}
	
	public void setBOD(LocalDate ld) {
		bod = ld;
	}
	public void setLoan(TextField setLoan) {
		loan = setLoan;
	}
	public void setFood1(TextField foodA1) {
		food1 = foodA1;
	}
	public void setFood2(TextField foodA2) {
		food2 = foodA2;
	}
	public void setFood3(TextField foodA3) {
		food3 = foodA3;
	}
	public void setFood4(TextField foodA4) {
		food4 = foodA4;
	}
	public void setFood5(TextField foodA5) {
		food5 = foodA5;
	}
	public void setGender(TextField GenderA) {
		gender = GenderA;
	}

	//setter Update for animal object
	public void updateAnimals(String[] animalInfo2) {
		allAnimals = animalInfo2;
	}
	

	
}