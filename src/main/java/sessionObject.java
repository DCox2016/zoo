//Session Objects
//Will contain the getter and setter methods for the session data

public class sessionObject {
	private String sessionID;
	private String sessionAction;
	private String newAction;
	private String enterInfo;
	private String animalName;
	private String animalSpecies;
	private String animalGender;
	private String animalLoan;
	private String animalFood1;
	private String animalFood2;
	private String animalFood3;
	private String animalFood4;
	private String animalFood5;
	private String newFood;
	private String newDescription;
	private String vendorIndo;
	private int onHand;
	private int units;
	private String foodId;
	private String terrainFound;
	private String terrainDisplayed;
	private String review;
	private String action;
	
	public sessionObject() {
		sessionID = " ";
		sessionAction = " ";
		newAction = " ";
		enterInfo = " ";
		animalName = " ";
		animalSpecies = " ";
		animalLoan = " ";
		animalFood1 = " ";
		animalFood2 = " ";
		animalFood3 = " ";
		animalFood4 = " ";
		animalFood5 = " ";
		animalGender = " ";
		terrainFound = " ";
		terrainDisplayed = " ";
		review = " ";
		action = " ";
		
	}
	
	public void setUAction(String uA) {
		action = uA;
	}
	
	public void setAReview(String r) {
		review = r;
	}
	
	public void setTFound(String f) {
		terrainFound = f;
	}
	
	public void setTDisplayed(String d) {
		terrainDisplayed = d;
	}
	
	public void setID(String id) {
		sessionID = id;
	}
	
	public void setAction(String action) {
		sessionAction = action;
	}
	
	public void setAction1(String action1) {
		newAction = action1;
	}

	
	public void setEnterInfo(String enteredInfo) {
		enterInfo = enteredInfo;
	}
	
	public void setFindName(String findName) {
		animalName = findName;
	}
	
	public void setFindSpecies(String findSpecies) {
		animalSpecies = findSpecies;
	}
	
	public void setFindLoan(String loanStatus) {
		animalLoan = loanStatus;
	}
	
	public void setFood1(String f1) {
		animalFood1 = f1;
	}
	
	public void setFood2(String f2) {
		animalFood2 = f2;
	}
	
	public void setFood3(String f3) {
		animalFood3 = f3;
	}
	
	public void setFood4(String f4) {
		animalFood4 = f4;
	}
	
	public void setFood5(String f5) {
		animalFood5 = f5;
	}
	
	public void setGender(String g) {
		animalGender = g;
	}
	
	public void setFood(String f) {
	   newFood = f;
	}
	
	public void setDescription(String d) {
       newDescription = d;
	}
	
	public void setVendor(String v) {
	   vendorIndo = v;
	}
	
	public void setOnHand(int oH) {
	   onHand = oH;
	}
	
	public void setUnit(int u) {
	   units = u;
	}
	
	public void setFId(String fId) {
	   foodId = fId;
	}
	
	public String getSessionId() {
		return sessionID;
	}
	
	public String getTerrain() {
		return terrainFound;
	}
	
	public String displayTerrain() {
		return terrainDisplayed;
	}
	
	public String getAction() {
		return sessionAction;
	}
	
	public String getAction1() {
		return newAction;
	}
	
	public String getEnteredInfo() {
		return enterInfo;
	}
	
	public String getName() {
		return animalName;
	}
	
	public String getSpecies() {
		return animalSpecies;
	}
	
	public String getLoanS() {
		return animalLoan;
	}
	
	public String getFood1() {
		return animalFood1;
	}
	
	public String getFood2() {
		return animalFood2;
	}
	
	public String getFood3() {
		return animalFood3;
	}
	
	public String getFood4() {
		return animalFood4;
	}
	
	public String getFood5() {
		return animalFood5;
	}
	
	public String getGender() {
		return animalGender;
	}
	
	public String getFood() {
	   return newFood;
	}
	
	public String getDescriptio() {
	   return newDescription;
	}
	
	public String getVendor() {
	   return vendorIndo;
	}
	
	public int getHand() {
	   return onHand;
	}
	
	public int getUnits() {
	   return units;
	}
	
	public String getFID() {
	   return foodId;
	}
	
	public String getAReview() {
		return review;
	}
	
	public String getUReview() {
		return action;
	}
}