/* Author: Dustin Cox
* Version 4
* ZooTester is the component that connects my anim
*/
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ZooTester extends Application
{
	static Zoo choice = new Zoo(null, null, null, null, 0, 0, null, null, null, null, 0, null, null, null,
			null, null, null, null, null, null, 0);
	static Animal allAnimalInfo = new Animal(null, null, null, null, null, null, null, null, null, null, null);
	static Food allFoodInfo = new Food(null, null, null, null, null, null);
	static ArrayList<terrain> showTerrains;
	static JsonObject session;
	GridPane gridpane = new GridPane();
	
	//Starts stage
	public static void main(String [] args) throws IOException {
        showTerrains = new ArrayList<terrain>();
		showTerrains.add(new forest());
		showTerrains.add(new snowforest());
		showTerrains.add(new desert());
		showTerrains.add(new desertoasis());
		showTerrains.add(new mountain());
		showTerrains.add(new snowmountain());
		
		startNewSession();
	
		//Start session collection
	    sessionCollection();
			
		launch(args);
	}
	
	//Reads both txt files into the animal class and food class
	//Create Stage and open welcome screen
	@Override
	public void start(Stage primaryStage) throws IOException {
		//Check if animal exist if not create it, check it animals exist in animals.txt file if not add animals
		int a = 0;
		int f = 0;
		
		File deleteFile = new File("showSessions.json");
	 		
	 	deleteFile.delete();
 		  try {
 		deleteFile.createNewFile();
 		   } catch (IOException e1) {
 	 		       JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
 	 		       System.exit(0);
 	       }
	
		File animalData = new File("animals.txt");
     	Scanner whatAnimal = null;
		try {
			whatAnimal = new Scanner(animalData);
		} catch (FileNotFoundException e1) {
			int input = JOptionPane.showConfirmDialog(null, "Animal.txt does not exist.\n Would you like to create it?");
		    if(input == 0) {
		    	File missingFile = new File("animals.txt");
		    	missingFile.createNewFile();
		    	FileOutputStream addFile = new FileOutputStream(missingFile, false);
		    	whatAnimal = new Scanner(missingFile);
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "The Zoo program needs Animal.txt to run.");
		    }
		}
		
		String[] animals = new String[(int) animalData.length()]; 
		
		if (!whatAnimal.hasNext()) {
			JOptionPane.showMessageDialog(null, "Looks like we have an empty zoo. Let's start off by adding the animals to your zoo.");
			choice.setNewFile(1);
		}
		
		while (whatAnimal.hasNext()) {
			animals[a] = whatAnimal.nextLine();
			allAnimalInfo.updateAnimals(animals);
			a++;
		}
		choice.setHowManyA(a);
		
		File foodData = new File("food.txt");
		Scanner whatFood = null;
		try {
			whatFood = new Scanner(foodData);
		} catch (FileNotFoundException e1) {
			int input = JOptionPane.showConfirmDialog(null, "Food.txt does not exist.\n Would you like to create it?");
		    if(input == 0) {
		    	File missingFile = new File("food.txt");
		    	missingFile.createNewFile();
		    	FileOutputStream addFile = new FileOutputStream(missingFile, false);
		    	whatFood = new Scanner(missingFile);
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "The Zoo program needs Food.txt to run.");
		    }
		}

		String[] foods = new String[(int) foodData.length()];
		
		if (!whatFood.hasNext()) {
			JOptionPane.showMessageDialog(null, "Looks like we have an empty food inventory. Let's start off by adding food to your inventory.");
			choice.setNewFile(2);
		}
		
		while (whatFood.hasNext()) {
			foods[f] = whatFood.nextLine();
			allFoodInfo.updateFood(foods);
			f++;
		}
		choice.setHowManyF(f);
		
		if(choice.getNewFile() == 1) {
			Label example = new Label("Example of Animal Input:");
			Label aCategory = new Label("Animal Category:\n");
			Label eachCategory = new Label("Name: John \nSpecies: Deer \nGender: Male \nDOB: 07/03/1983 \nOn Load: Y \nFoods: "
					+ "Grass, Honeydew, Apples, Bark, Corn");
			
			Button continueAdd =  new Button("Continue");
			continueAdd.setOnAction(new enterAnimal());
			
			gridpane.getChildren().clear();
			gridpane.add(example, 0, 0);
			gridpane.add(aCategory, 0, 1);
			gridpane.add(eachCategory, 0, 2);
			gridpane.add(continueAdd, 0, 3);
			
		}
		else if(choice.getNewFile() == 2) {
			Label example = new Label("Example of Food Input:");
			Label aCategory = new Label("Food Category:\n");
			Label eachCategory = new Label("FoodId: f1 \nFood: Bananas \nDescription: A sweet fruit \nVendor: bananas.com \nOn hand: 250 \nUnits: 150");
			
			Button continueAdd =  new Button("Continue");
			continueAdd.setOnAction(new enterFood());
			
			gridpane.getChildren().clear();
			gridpane.add(example, 0, 0);
			gridpane.add(aCategory, 0, 1);
			gridpane.add(eachCategory, 0, 2);
			gridpane.add(continueAdd, 0, 3);
		}
		else {
		String reviewAction = "Review";
		
		//Label control
		Label welcome = new Label("Welcome to my Zoo!");
		Label action = new Label("What would you like to do? Review, Enter, or Find.");
		Label viewAll = new Label("Or select View All to view all information.");
		
		//Get image object from the link
		Image image = new Image("https://image.shutterstock.com/image-vector/zoo-gate-isolated-object-cartoon-260nw-1076503121.jpg");
		
		//Create an Image object.
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(300);
		imageView.setFitHeight(300);
		    
	
	    //Button
	    Button review = new Button("Review");
	    Button enter = new Button("Enter");
	    Button find = new Button("Find");
	    Button zooMode = new Button("View All");
	    Button exit = new Button("Exit");
	    
	    //Button click event
	    review.setOnAction(new userReview());
	    enter.setOnAction(new userEnter());
	    find.setOnAction(new userFind());
	    zooMode.setOnAction(new zooMode());
	    exit.setOnAction(new exit());
	    
	    //Hbox for buttons
	    HBox buttonsBox = new HBox(6);
	    buttonsBox.getChildren().addAll(review,enter,find,zooMode, exit);
	   
	    		
		//Add image and message to gridpane
		gridpane.add(welcome , 1, 0);
		gridpane.add(imageView, 1, 1);
		gridpane.add(action, 1, 2);
		gridpane.add(viewAll, 1, 3);
		gridpane.add(buttonsBox, 1, 4);
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		gridpane.setPadding(new Insets(5));
		}
		
		//Create a scene with the HBox as root node, set scene size
		Scene scene = new Scene(gridpane, 700, 600);
		
		gridpane.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, null, null)));
		
	
		//Add the Scence to the Stage
		primaryStage.setScene(scene);
		
		//Set the stage title
		primaryStage.setTitle("Dustin's Zoo");
		
		//Show the window
		primaryStage.show();
	}
	
	//Session data method
	public static void sessionCollection() throws IOException {

		session = new JsonObject();
		
		Date date = new Date();
		long time = date.getTime();
		String makeID = Long.toString(time);
		
		session.add("sessionID",new Gson().toJsonTree(makeID));
		
	}
	
	//Start new session collection
	public static void startNewSession() throws IOException {
		
		File sessionFile = new File("sessions.json");
		
		 if(sessionFile.length() != 0) {
			
			 ArrayList<sessionObject> getSessions = new ArrayList<sessionObject>();	
	        	Type type = new TypeToken<List<sessionObject>>() {
	        	}.getType();
	        	File file = new File("sessions.json");
	        	
	        	Gson gson = new Gson();
	  
	        	try {
	    			Scanner scanner = new Scanner(file, "UTF-8");
	    			String json = scanner.useDelimiter("\\z").next();
	    		    getSessions = gson.fromJson(json, type);
	    			scanner.close();
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		} finally {

	    		}
	        	
	        	String listSessions = gson.toJson(getSessions).replaceAll("]",",").replace("[", "");
	   	 			   	 		
	   	 	    File oldSession = new File("oldSessions.json");
	     	    Scanner whatAnimal = null;
			    try {
				whatAnimal = new Scanner(oldSession);
			     } catch (FileNotFoundException e1) {
			    	File missingFile = new File("oldSessions.json");
			    	missingFile.createNewFile();
			    	FileOutputStream addFile = new FileOutputStream(missingFile, false);
			    	whatAnimal = new Scanner(missingFile);
			    }
			    
			    BufferedWriter  writeFile = new BufferedWriter(new FileWriter("oldSessions.json", true));
			    writeFile.write(listSessions);
	   	 		writeFile.close();
	   	 		
	   	 	sessionFile.delete();
			try {
		    	sessionFile.createNewFile();
		     } catch (IOException e1) {
		    	 JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
	 		       System.exit(0);
		    } 
	   	 		 
			}
	   	 	
		 }
	
	//what terrain and what climate
	class getTerrain implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
 
			session.add("newAction",new Gson().toJsonTree("view terrain"));
          
            
			for(int t = 0; t < 100; t++) {
				int a = 0;
				Label currentAssigned = new Label("Current Animals assigned to terrains");
				File getAnimalsTerrain = new File("animalsTerrain.txt");
				Scanner showTerrain = null;
			    try {
			    	showTerrain = new Scanner(getAnimalsTerrain);
			    } catch (FileNotFoundException e1) {
					int input = JOptionPane.showConfirmDialog(null, "Animal.txt does not exist.\n Would you like to create it?");
				    if(input == 0) {
				    	File missingFile = new File("animalsTerrain.txt");
				    	try {
							missingFile.createNewFile();
						} catch (IOException e) {
							 JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
			 	 		     System.exit(0);
						}
				    	try {
							FileOutputStream addFile = new FileOutputStream(missingFile, false);
						} catch (FileNotFoundException e) {
							 JOptionPane.showMessageDialog(null,"Sorry that file could not be made the output file.\nProgram will exit and please try again.");
			 	 		       System.exit(0);
						}
				    	try {
							showTerrain = new Scanner(missingFile);
						} catch (FileNotFoundException e) {
							 JOptionPane.showMessageDialog(null,"Sorry program could not scan that file.\nProgram will exit and please try again.");
			 	 		     System.exit(0);
						}
				    }
				    else {
				    	JOptionPane.showMessageDialog(null, "The Zoo program needs Animal.txt to run.");
				    }
			    }
			    
			    String[] terrains = new String[(int) getAnimalsTerrain.length()];
			   ListView displayTerrain = new ListView();
			    
			    while (showTerrain.hasNext()) {
			    	terrains[a] = showTerrain.nextLine();
			    	displayTerrain.getItems().add(terrains[a]);
					a++;
				}
			    
			   		
				Label getTandC = new Label("Select terrain and terrain's climate from the drop downs");
				
			    ComboBox<String> whichTerrain = new ComboBox<>();
			    ComboBox<String> whichClimate = new ComboBox<>();
			    
			    String terrainsN = "Forest" + " " + "Mountain" + " " + "Desert";
			    String climate = "Cold" + " " + "Hot";
			    
			    //hbox for drop down
			    HBox dropDown = new HBox(2);
			    dropDown.getChildren().addAll(whichTerrain, whichClimate);
			    
			    for(int aN = 0; aN < 3; aN++) {
					String [] lineByLine = terrainsN.split(" ");
					whichTerrain.getItems().add(lineByLine[aN]);
					}
				
				for(int clim = 0; clim < 2; clim++) {
			 		String [] lineByLine1 = climate.split(" ");
			 		whichClimate.getItems().add(lineByLine1[clim]);
			 		}
				
				Button exit = new Button("Exit");
		   	    Button home = new Button("Home");
		   	    Button submit = new Button("Submit");
		   	     
		   	    exit.setOnAction(new exit());
		   	    home.setOnAction(new home());
		   	    submit.setOnAction(EventTC -> {
		   	    	
		   	        
			   	     String userTerrain = whichTerrain.getValue().toString();
			   	     String userClimate = whichClimate.getValue().toString();
			   	     choice.setTerrain(userTerrain);
			   	     choice.setClimate(userClimate);
			   	     
			   	    session.add("enterInfo",new Gson().toJsonTree(userTerrain+ " " + userClimate));
		            
		           
			   	     Label findTypeT = new Label("You are trying to find a " + choice.getTerrain() +
			   	    		 " terrain with a " + choice.getClimate() + " climate");
			   	     Label isCorrect = new Label("If that is correct, hit the continue button.");
			  
				   	 Button continueST = new Button("Continue");
				     continueST.setOnAction(new showTerrain());
			   	     
			   	     VBox verifyInfo = new VBox(findTypeT, isCorrect, continueST);
			   	   
			   	     gridpane.add(verifyInfo, 3, 0);

			   	    }); 
		   	    
		   	    VBox terrainDisplay = new VBox(currentAssigned, displayTerrain, getTandC, dropDown, submit);
		   	   
		    	gridpane.add(terrainDisplay, 2, 0);
			}
		}	
	}
	
	//Display terrain
	class showTerrain implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {	
			//Get normal forest
		     if(choice.getTerrain().equals("Forest") && choice.getClimate().equals("Hot")) {
		    	    showTerrains.get(0).showType("Forest");    
		    	    ListView forestView = new ListView();
		    	    
		    	    //get image of terrain
		    	    Image forestImg = new Image("https://vignette.wikia.nocookie.net/wearewarriors/images/6/61/Dark_Forest.jpg/revision/latest?cb=20180831145259");
		    	    
		    	    //image viewer
		    	    ImageView imageView = new ImageView(forestImg);
		    	    imageView.setFitWidth(300);
		    		imageView.setFitHeight(300);
		    	    
		    	    forestView.getItems().add(showTerrains.get(0));
		   	      		
		    		Button exit = new Button("Exit");
			   	    Button home = new Button("Home");
			   	    Button assign = new Button("Add terrain");
			   	    
			   	    exit.setOnAction(new exit());
			   	    home.setOnAction(new home());
			   	    assign.setOnAction(eventT -> {
			   	    	
			   	    	String name = allAnimalInfo.getName().getText();
			   	    	String species = allAnimalInfo.getSpecies().getText();
			   	    	
			   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
			   	    	
			   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
			   	    	
			   	    	Button submit = new Button("Submit");
			   	    	submit.setOnAction(EventAT -> {
			   	   
			   	    	String terrain = ("The Dark Forest");
			   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
			   	    	
			   	    	File animalData = new File("animalsTerrain.txt");
			   	      	Scanner whatAnimal = null;
			   	 		try {
			   	 			whatAnimal = new Scanner(animalData);
			   	 		} catch (FileNotFoundException e1) {
			   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
			   	 			
			   	 		    if(input == 0) {
			   	 		    	File missingFile = new File("animalsTerrain.txt");
			   	 		    	try {
									missingFile.createNewFile();
								} catch (IOException e) {
									 JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
					 	 		       System.exit(0);
								}
			   	 		    	try {
									FileOutputStream addFile = new FileOutputStream(missingFile, false);
								} catch (FileNotFoundException e) {
									 JOptionPane.showMessageDialog(null,"Sorry that file could not be set as output.\nProgram will exit and please try again.");
					 	 		       System.exit(0);
								}
			   	 		    	try {
									whatAnimal = new Scanner(missingFile);
								} catch (FileNotFoundException e) {
									 JOptionPane.showMessageDialog(null,"Sorry that file could not be scanned at this time.\nProgram will exit and please try again.");
					 	 		       System.exit(0);
								}
			   	 		    }
			   	 		    else {
			   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
			   	 		    }
			   	 		}
			   	 		
			   	 		String assigningTerrain = name + " " + species + " " + terrain;
			   	 		FileWriter writerTerrain = null;
			   	 		animalData.delete();
			   	 		try {
			   	 			animalData.createNewFile();
			   	 		} catch (IOException e1) {
			   	 		   JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
		 	 		       System.exit(0);
						}
			   	 		
			   	 	    try {
			   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
					    } catch (FileNotFoundException e) {
					      JOptionPane.showMessageDialog(null,"Sorry that animalsTerrain.txt could not be found at this time.\nProgram will exit and please try again.");
			 	 		  System.exit(0);
					    } catch (IOException e) {
					    	 JOptionPane.showMessageDialog(null,"Sorry that file could not be written to at this time.\nProgram will exit and please try again.");
			 	 		     System.exit(0);
					    }			  
	   			
	   			        try {
	   			        writerTerrain.write(assigningTerrain + "\n");
	   			        writerTerrain.close();
				        } catch (IOException e) {
				        	 JOptionPane.showMessageDialog(null,"Sorry that file could not be written to at this time.\nProgram will exit and please try again.");
			 	 		     System.exit(0);
				        }
	   			        
	   			        Button addAnimal = new Button("Add animal");
	   			        addAnimal.setOnAction(new addAnimalFile());
	   			        
	   			        Label complete = new Label("Animal assigned to Terrain");
	   			        
	   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
			   	 
	   			        VBox completeButtons = new VBox(complete,addAnimal, home);
	   			        gridpane.add(completeButtons, 6, 0);
			   	    	});
			   	    	
			   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
			   	    	gridpane.add(terrainButtons, 5, 0);
			   	    	
			   	    	
			   	    });
			   	    
			   	    Label hereT = new Label("Here is your requested terrain");
			   	    
			   	    VBox buttons = new VBox(3);
			   	    buttons.getChildren().addAll(home, exit, assign);

			   	    VBox allButtons = new VBox(hereT, forestView, imageView, buttons);
			   	    gridpane.getChildren().clear();
		    		gridpane.add(allButtons, 4, 0);
		    		
                    session.add("terrainFound",new Gson().toJsonTree("The Dark Forest displayed"));
	
			}
			
			//Get SnowForest
			     if(choice.getTerrain().equals("Forest") && choice.getClimate().equals("Cold")) {
			    	 showTerrains.get(1).showType("Forest");
			         ListView forestView = new ListView();
			         
			         //get image of terrain
			    	 Image forestImg = new Image("https://previews.123rf.com/images/pixphoto/pixphoto1611/pixphoto161100096/66935652-view-of-the-winter-taiga-forest.jpg");
			    	    
			    	 //image viewer
			    	 ImageView imageView = new ImageView(forestImg);
			    	 imageView.setFitWidth(300);
			         imageView.setFitHeight(300);
			    	    
			    	 forestView.getItems().add(showTerrains.get(1));
			    	      		
			    	 Button exit = new Button("Exit");
				     Button home = new Button("Home");
				     Button assign = new Button("Assign terrain");
				   	    
			   	    exit.setOnAction(new exit());
			   	    home.setOnAction(new home());
			   	    assign.setOnAction(eventT -> {

			   	 	String name = allAnimalInfo.getName().getText();
		   	    	String species = allAnimalInfo.getSpecies().getText();
		   	    	
		   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
		   	    	
		   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
		   	    	
		   	    	Button submit = new Button("Submit");
		   	    	submit.setOnAction(EventAT -> {
		   	   
		   	    	String terrain = ("The Taiga Forest");
		   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
		   	    	
		   	    	File animalData = new File("animalsTerrain.txt");
		   	      	Scanner whatAnimal = null;
		   	 		try {
		   	 			whatAnimal = new Scanner(animalData);
		   	 		} catch (FileNotFoundException e1) {
		   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
		   	 			
		   	 		    if(input == 0) {
		   	 		    	File missingFile = new File("animalsTerrain.txt");
		   	 		    	try {
								missingFile.createNewFile();
							} catch (IOException e) {
								 JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
				 	 		     System.exit(0);
							}
		   	 		    	try {
								FileOutputStream addFile = new FileOutputStream(missingFile, false);
							} catch (FileNotFoundException e) {
								 JOptionPane.showMessageDialog(null,"Sorry that file can not be terrain output at this time.\nProgram will exit and please try again.");
				 	 		     System.exit(0);
							}
		   	 		    	try {
								whatAnimal = new Scanner(missingFile);
							} catch (FileNotFoundException e) {
								JOptionPane.showMessageDialog(null,"Sorry that file can not be scanned at this time.\nProgram will exit and please try again.");
				 	 		     System.exit(0);
							}
		   	 		    }
		   	 		    else {
		   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
		   	 		    }
		   	 		}
		   	 		
		   	 		String assigningTerrain = name + " " + species + " " + terrain;
		   	 		FileWriter writerTerrain = null;
		   	 		animalData.delete();
		   	 		try {
		   	 			animalData.createNewFile();
		   	 		} catch (IOException e1) {
		   	 		 JOptionPane.showMessageDialog(null,"Sorry that file could not be created at this time.\nProgram will exit and please try again.");
	 	 		     System.exit(0);
					}
		   	 		
		   	 	    try {
		   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
				    } catch (FileNotFoundException e) {
				    	 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be found at this time.\nProgram will exit and please try again.");
		 	 		     System.exit(0);
				    } catch (IOException e) {
				    	 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
		 	 		     System.exit(0);
				    }			  
   			
   			        try {
   			        writerTerrain.write(assigningTerrain + "\n");
   			        writerTerrain.close();
			        } catch (IOException e) {
			        	 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
		 	 		     System.exit(0);
			        }
   			        
   			        Button addAnimal = new Button("Add animal");
   			        addAnimal.setOnAction(new addAnimalFile());
   			        
   			        Label complete = new Label("Animal assigned to Terrain");
   			        
   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
		   	 
   			        VBox completeButtons = new VBox(complete,addAnimal, home);
   			        gridpane.add(completeButtons, 6, 0);
		   	    	});
		   	    	
		   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
		   	    	gridpane.add(terrainButtons, 5, 0);
			   	    	
			   	    });
			
			   	    Label hereT = new Label("Here is your requested terrain");
			   	   
			   	    VBox buttons = new VBox(3);
			   	    buttons.getChildren().addAll(home, exit, assign);
			   	    
			   	    session.add("terrainFound",new Gson().toJsonTree("Taiga displayed"));

		    		gridpane.getChildren().clear();
		    		gridpane.add(buttons, 1, 1);
		    		gridpane.add(hereT, 0,0);
		    		gridpane.add(imageView, 0, 2);
		    		gridpane.add(forestView, 0,1);
				}
			     
			//Get desert   
			     if(choice.getTerrain().equals("Desert") && choice.getClimate().equals("Hot")) {
			    	 showTerrains.get(2).showType("Desert");
			    	 ListView desertView = new ListView();
			    	 
			    	 //get image of terrain
				       Image desertImg = new Image("https://www.worldatlas.com/r/w728-h425-c728x425/upload/ee/99/48/shutterstock-306150275.jpg");
			    	    
				     //image viewer
				       ImageView imageView = new ImageView(desertImg);
				       imageView.setFitWidth(300);
				       imageView.setFitHeight(300);
				       
			    	  desertView.getItems().add(showTerrains.get(2));
				    	      		
				    	 Button exit = new Button("Exit");
					     Button home = new Button("Home");
					     Button assign = new Button("Assign terrain");
					   	    
				   	    exit.setOnAction(new exit());
				   	    home.setOnAction(new home());
					    assign.setOnAction(eventT -> {
					    	
					    	String name = allAnimalInfo.getName().getText();
				   	    	String species = allAnimalInfo.getSpecies().getText();
				   	    	
				   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
				   	    	
				   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
				   	    	
				   	    	Button submit = new Button("Submit");
				   	    	submit.setOnAction(EventAT -> {
				   	   
				   	    	String terrain = ("Sarah Desert");
				   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
				   	    	
				   	    	File animalData = new File("animalsTerrain.txt");
				   	      	Scanner whatAnimal = null;
				   	 		try {
				   	 			whatAnimal = new Scanner(animalData);
				   	 		} catch (FileNotFoundException e1) {
				   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
				   	 			
				   	 		    if(input == 0) {
				   	 		    	File missingFile = new File("animalsTerrain.txt");
				   	 		    	try {
										missingFile.createNewFile();
									} catch (IOException e) {
										 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not found at this time.\nProgram will exit and please try again.");
						 	 		     System.exit(0);
									}
				   	 		    	try {
										FileOutputStream addFile = new FileOutputStream(missingFile, false);
									} catch (FileNotFoundException e) {
										 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not created at this time.\nProgram will exit and please try again.");
						 	 		     System.exit(0);
									}
				   	 		    	try {
										whatAnimal = new Scanner(missingFile);
									} catch (FileNotFoundException e) {
										JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not created at this time.\nProgram will exit and please try again.");
						 	 		     System.exit(0);
									}
				   	 		    }
				   	 		    else {
				   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
				   	 		    }
				   	 		}
				   	 		
				   	 		String assigningTerrain = name + " " + species + " " + terrain;
				   	 		FileWriter writerTerrain = null;
				   	 		animalData.delete();
				   	 		try {
				   	 			animalData.createNewFile();
				   	 		} catch (IOException e1) {
				   	 		 JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not created at this time.\nProgram will exit and please try again.");
			 	 		     System.exit(0);
							}
				   	 		
				   	 	    try {
				   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
						    } catch (FileNotFoundException e) {
						    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not found at this time.\nProgram will exit and please try again.");
				 	 		     System.exit(0);
						    } catch (IOException e) {
						    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not written to at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
						    }			  
		   			
		   			        try {
		   			        writerTerrain.write(assigningTerrain + "\n");
		   			        writerTerrain.close();
					        } catch (IOException e) {
					        	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not written to at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
					        }
		   			        
		   			        Button addAnimal = new Button("Add animal");
		   			        addAnimal.setOnAction(new addAnimalFile());
		   			        
		   			        Label complete = new Label("Animal assigned to Terrain");
		   			        
		   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
				   	 
		   			        VBox completeButtons = new VBox(complete,addAnimal, home);
		   			        gridpane.add(completeButtons, 6, 0);
				   	    	});
				   	    	
				   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
				   	    	gridpane.add(terrainButtons, 5, 0);
				   	    
				   	    });
				   	  
				   	    
				   	    Label hereT = new Label("Here is your requested terrain");
				   	        
				   	    VBox buttons = new VBox(3);
				   	    buttons.getChildren().addAll(home, exit, assign);
				   	    
				   		session.add("terrainFound",new Gson().toJsonTree("Sarah Desert displayed"));
			 
			    		gridpane.getChildren().clear();
			    		gridpane.add(buttons, 1, 1);
			    		gridpane.add(hereT, 0,0);
			    		gridpane.add(imageView, 0, 2);
			    		gridpane.add(desertView, 0,1);
				   	    
				}
			
			//Get Oasis
			     if(choice.getTerrain().equals("Desert") && choice.getClimate().equals("Cold")) {
			    	 showTerrains.get(3).showType("Desert");
			    	
			    	 ListView desertView = new ListView();
			    	    
			    	  desertView.getItems().add(showTerrains.get(3));
				    	      		
				    	 Button exit = new Button("Exit");
					     Button home = new Button("Home");
					     Button assign = new Button("Assign terrain");
					     
					   	    
				   	    exit.setOnAction(new exit());
				   	    home.setOnAction(new home());
				   	    assign.setOnAction(eventT -> {
				   	    	
				   	 	String name = allAnimalInfo.getName().getText();
			   	    	String species = allAnimalInfo.getSpecies().getText();
			   	    	
			   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
			   	    	
			   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
			   	    	
			   	    	Button submit = new Button("Submit");
			   	    	submit.setOnAction(EventAT -> {
			   	   
			   	    	String terrain = ("Wadi Bani Khalid Desert Oasis");
			   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
			   	    	
			   	    	File animalData = new File("animalsTerrain.txt");
			   	      	Scanner whatAnimal = null;
			   	 		try {
			   	 			whatAnimal = new Scanner(animalData);
			   	 		} catch (FileNotFoundException e1) {
			   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
			   	 			
			   	 		    if(input == 0) {
			   	 		    	File missingFile = new File("animalsTerrain.txt");
			   	 		    	try {
									missingFile.createNewFile();
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not created at this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);
								}
			   	 		    	try {
									FileOutputStream addFile = new FileOutputStream(missingFile, false);
								} catch (FileNotFoundException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be made the output file at this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);;
								}
			   	 		    	try {
									whatAnimal = new Scanner(missingFile);
								} catch (FileNotFoundException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be scanned this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);
								}
			   	 		    }
			   	 		    else {
			   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
			   	 		    }
			   	 		}
			   	 		
			   	 		String assigningTerrain = name + " " + species + " " + terrain;
			   	 		FileWriter writerTerrain = null;
			   	 		animalData.delete();
			   	 		try {
			   	 			animalData.createNewFile();
			   	 		} catch (IOException e1) {
			   	 		JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not created to at this time.\nProgram will exit and please try again.");
		 	 		    System.exit(0);
						}
			   	 		
			   	 	    try {
			   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
					    } catch (FileNotFoundException e) {
					    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be found to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
					    } catch (IOException e) {
					    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
					    }			  
	   			
	   			        try {
	   			        writerTerrain.write(assigningTerrain + "\n");
	   			        writerTerrain.close();
				        } catch (IOException e) {
				        	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
				        }
	   			        
	   			        Button addAnimal = new Button("Add animal");
	   			        addAnimal.setOnAction(new addAnimalFile());
	   			        
	   			        Label complete = new Label("Animal assigned to Terrain");
	   			        
	   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
			   	 
	   			        VBox completeButtons = new VBox(complete,addAnimal, home);
	   			        gridpane.add(completeButtons, 6, 0);
			   	    	});
			   	    	
			   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
			   	    	gridpane.add(terrainButtons, 5, 0);
				   	    	
				   	    });
				   	    
				   	    
				   	    Label hereT = new Label("Here is your requested terrain");
				   	    
				   	       //get image of terrain
					       Image desertImg = new Image("https://i.pinimg.com/originals/49/08/c4/4908c4384268d356676fe4a75ec86961.jpg");
					    	    
					       //image viewer
					         ImageView imageView = new ImageView(desertImg);
					    	 imageView.setFitWidth(300);
					         imageView.setFitHeight(300);
					   	    
					   	    VBox buttons = new VBox(3);
					   	    buttons.getChildren().addAll(home, exit, assign);
					   	    
	                        session.add("terrainFound",new Gson().toJsonTree("Wadi Bani Khalid Desert Oasis displayed"));

				    		gridpane.getChildren().clear();
				    		gridpane.add(buttons, 1, 1);
				    		gridpane.add(hereT, 0,0);
				    		gridpane.add(imageView, 0, 2);
				    		gridpane.add(desertView, 0,1);
				}
			     
			//Get mountain
			     if(choice.getTerrain().equals("Mountain") && choice.getClimate().equals("Hot")) {
			    	 showTerrains.get(4).showType("Mountain");
			    	 
			    	 ListView mountainView = new ListView();
			    	    
			    	 mountainView.getItems().add(showTerrains.get(4));
				    	      		
				    	 Button exit = new Button("Exit");
					     Button home = new Button("Home");
					     Button assign = new Button("Assign terrain");
					   	    
				   	    exit.setOnAction(new exit());
				   	    home.setOnAction(new home());
				   	    assign.setOnAction(eventT -> {
				   	    	String name = allAnimalInfo.getName().getText();
				   	    	String species = allAnimalInfo.getSpecies().getText();
				   	    	
				   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
				   	    	
				   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
				   	    	
				   	    	Button submit = new Button("Submit");
				   	    	submit.setOnAction(EventAT -> {
				   	   
				   	    	String terrain = ("Mount Rushmore");
				   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
				   	    	
				   	    	File animalData = new File("animalsTerrain.txt");
				   	      	Scanner whatAnimal = null;
				   	 		try {
				   	 			whatAnimal = new Scanner(animalData);
				   	 		} catch (FileNotFoundException e1) {
				   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
				   	 			
				   	 		    if(input == 0) {
				   	 		    	File missingFile = new File("animalsTerrain.txt");
				   	 		    	try {
										missingFile.createNewFile();
									} catch (IOException e) {
										JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be created at this time.\nProgram will exit and please try again.");
						 	 		    System.exit(0);
									}
				   	 		    	try {
										FileOutputStream addFile = new FileOutputStream(missingFile, false);
									} catch (FileNotFoundException e) {
										JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be output at this time.\nProgram will exit and please try again.");
						 	 		    System.exit(0);
									}
				   	 		    	try {
										whatAnimal = new Scanner(missingFile);
									} catch (FileNotFoundException e) {
										JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be scanned to at this time.\nProgram will exit and please try again.");
						 	 		    System.exit(0);
									}
				   	 		    }
				   	 		    else {
				   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
				   	 		    }
				   	 		}
				   	 		
				   	 		String assigningTerrain = name + " " + species + " " + terrain;
				   	 		FileWriter writerTerrain = null;
				   	 		animalData.delete();
				   	 		try {
				   	 			animalData.createNewFile();
				   	 		} catch (IOException e1) {
				   	 		JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be created at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
							}
				   	 		
				   	 	    try {
				   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
						    } catch (FileNotFoundException e) {
						    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be found at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
						    } catch (IOException e) {
						    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
						    }			  
		   			
		   			        try {
		   			        writerTerrain.write(assigningTerrain + "\n");
		   			        writerTerrain.close();
					        } catch (IOException e) {
					        	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
					        }
		   			        
		   			        Button addAnimal = new Button("Add animal");
		   			        addAnimal.setOnAction(new addAnimalFile());
		   			        
		   			        Label complete = new Label("Animal assigned to Terrain");
		   			        
		   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
				   	 
		   			        VBox completeButtons = new VBox(complete,addAnimal, home);
		   			        gridpane.add(completeButtons, 6, 0);
				   	    	});
				   	    	
				   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
				   	    	gridpane.add(terrainButtons, 5, 0);
				   	    	
				   	    });
				   	   
				   	    
				   	    Label hereT = new Label("Here is your requested terrain");
				   	    
				   	 //get image of terrain
					       Image mountainImg = new Image("https://media.nationalgeographic.org/assets/photos/000/202/20238.jpg");
					    	    
					       //image viewer
					         ImageView imageView = new ImageView(mountainImg);
					    	 imageView.setFitWidth(300);
					         imageView.setFitHeight(300);
					   	    
					   	    VBox buttons = new VBox(3);
					   	    buttons.getChildren().addAll(home, exit, assign);
					   	    
	                        session.add("terrainFound",new Gson().toJsonTree("Mount Rushmore displayed"));
				           
				    		gridpane.getChildren().clear();
				    		gridpane.add(buttons, 1, 1);
				    		gridpane.add(hereT, 0,0);
				    		gridpane.add(imageView, 0, 2);
				    		gridpane.add(mountainView, 0,1);
				    	
				}
			     
			//Get Snowmountain
			     if(choice.getTerrain().equals("Mountain") && choice.getClimate().equals("Cold")) {
			    	 showTerrains.get(5).showType("Mountain");
			    	
			    	 ListView mountainView = new ListView();
			    	    
			    	 mountainView.getItems().add(showTerrains.get(5));
				    	      		
				    	 Button exit = new Button("Exit");
					     Button home = new Button("Home");
					     Button assign = new Button("Assign terrain");
					   	    
				   	    exit.setOnAction(new exit());
				   	    home.setOnAction(new home());
				   	    assign.setOnAction(eventT -> {
				   	 	String name = allAnimalInfo.getName().getText();
			   	    	String species = allAnimalInfo.getSpecies().getText();
			   	    	
			   	    	session.add("newAction",new Gson().toJsonTree("assign terrain"));
			   	    	
			   	    	Label whichAnimal =  new Label("Assign " + allAnimalInfo.getName().getText() + " " + allAnimalInfo.getSpecies().getText() + " this terrain");
			   	    	
			   	    	Button submit = new Button("Submit");
			   	    	submit.setOnAction(EventAT -> {
			   	   
			   	    	String terrain = ("Mount Everest");
			   	    	session.add("animalName",new Gson().toJsonTree(whichAnimal.toString()));
			   	    	
			   	    	File animalData = new File("animalsTerrain.txt");
			   	      	Scanner whatAnimal = null;
			   	 		try {
			   	 			whatAnimal = new Scanner(animalData);
			   	 		} catch (FileNotFoundException e1) {
			   	 			int input = JOptionPane.showConfirmDialog(null, "animalsTerrain.txt does not exist.\n Would you like to create it?");
			   	 			
			   	 		    if(input == 0) {
			   	 		    	File missingFile = new File("animalsTerrain.txt");
			   	 		    	try {
									missingFile.createNewFile();
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be created at this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);
								}
			   	 		    	try {
									FileOutputStream addFile = new FileOutputStream(missingFile, false);
								} catch (FileNotFoundException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be output at this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);
								}
			   	 		    	try {
									whatAnimal = new Scanner(missingFile);
								} catch (FileNotFoundException e) {
									JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be scanned to at this time.\nProgram will exit and please try again.");
					 	 		    System.exit(0);
								}
			   	 		    }
			   	 		    else {
			   	 		    	JOptionPane.showMessageDialog(null, "The Zoo program needs animalsTerrain.txt to run.");
			   	 		    }
			   	 		}
			   	 		
			   	 		String assigningTerrain = name + " " + species + " " + terrain;
			   	 		FileWriter writerTerrain = null;
			   	 		animalData.delete();
			   	 		try {
			   	 			animalData.createNewFile();
			   	 		} catch (IOException e1) {
			   	 		JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be created at this time.\nProgram will exit and please try again.");
		 	 		    System.exit(0);
						}
			   	 		
			   	 	    try {
			   	 	    writerTerrain = new FileWriter("animalsTerrain.txt", true);
					    } catch (FileNotFoundException e) {
					    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be found at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
					    } catch (IOException e) {
					    	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
					    }			  
	   			
	   			        try {
	   			        writerTerrain.write(assigningTerrain + "\n");
	   			        writerTerrain.close();
				        } catch (IOException e) {
				        	JOptionPane.showMessageDialog(null,"animalsTerrain.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
				        }
	   			        
	   			        Button addAnimal = new Button("Add animal");
	   			        addAnimal.setOnAction(new addAnimalFile());
	   			        
	   			        Label complete = new Label("Animal assigned to Terrain");
	   			        
	   			 	    session.add("sessionAction",new Gson().toJsonTree("Animal assigned to Terrain"));
			   	 
	   			        VBox completeButtons = new VBox(complete,addAnimal, home);
	   			        gridpane.add(completeButtons, 6, 0);
			   	    	});
			   	    	
			   	    	VBox terrainButtons = new VBox(whichAnimal, exit, submit);
			   	    	gridpane.add(terrainButtons, 5, 0);
				   	    	
				   	    });
				   	   
				   	 
				   	    
				   	    Label hereT = new Label("Here is your requested terrain");
				   	    
				       	//get image of terrain
					       Image mountainImg = new Image("https://ichef.bbci.co.uk/news/660/cpsprodpb/1392A/production/_106107108_gettyimages-513452022.jpg");
					    	    
					       //image viewer
					         ImageView imageView = new ImageView(mountainImg);
					    	 imageView.setFitWidth(300);
					         imageView.setFitHeight(300);
					   	    
					   	    VBox buttons = new VBox(3);
					   	    buttons.getChildren().addAll(home, exit, assign);
					   	    
					   	    session.add("terrainFound",new Gson().toJsonTree("Mount Everest displayed"));
			
				    		gridpane.getChildren().clear();
				    		gridpane.add(buttons, 1, 1);
				    		gridpane.add(hereT, 0,0);
				    		gridpane.add(imageView, 0, 2);
				    		gridpane.add(mountainView, 0,1);
				}
			     	     
		}
		
	}
	
    //Add new scene to stage and request what the user wants to review Food or Animal	
    class userReview implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event) {
    		
    	session.add("review",new Gson().toJsonTree("review"));
    	
    	//Label control	
        Label animalOrFood = new Label("What would you like to review?");
        
        //image of food and animal
        Image animal = new Image("https://media.wired.com/photos/5bfde7b13ee8d605f3dd0edf/4:3/w_1040,c_limit/fallshow-01.jpg");
        Image food = new Image("https://thumbs.dreamstime.com/z/food-bag-full-cartoon-58802756.jpg");
        
         //Create an Image object.
      	ImageView imageAnimal = new ImageView(animal);
      	imageAnimal.setFitWidth(200);
      	imageAnimal.setFitHeight(200);
   		
   		ImageView imageFood = new ImageView(food);
   		imageFood.setFitWidth(200);
   		imageFood.setFitHeight(200);
   		
   		//button
   	    Button animalB = new Button("Animal");
   	    Button foodB = new Button("Food"); 
   	    Button exit = new Button("Exit");
   	    Button home = new Button("Home");
   	    
   	    //Eventhandle for buttons
   	    animalB.setOnAction(new reviewAnimal());
   	    foodB.setOnAction(new reviewFood());
   	    exit.setOnAction(new exit());
   	    home.setOnAction(new home());
   	    
   	    HBox buttonsBox = new HBox(2);
	    buttonsBox.getChildren().addAll(home, exit);
   	     	     
        gridpane.getChildren().clear();
		gridpane.add(animalOrFood , 0, 0);
		gridpane.add(imageAnimal, 0, 1);
		gridpane.add(animalB, 0, 2 );
		gridpane.add(imageFood, 1, 1);
		gridpane.add(foodB, 1, 2);
		gridpane.add(buttonsBox, 0, 3);
	 }
    }
   
    //Add new scene to stage and request what the user wants to enter Food or Animal	
    class userEnter implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event) {
    	new File("images").mkdir();
    		
        //Label control	
        Label animalOrFood = new Label("What would you like to enter?");
            
        //image of food and animal
        Image animal = new Image("https://media.wired.com/photos/5bfde7b13ee8d605f3dd0edf/4:3/w_1040,c_limit/fallshow-01.jpg");
        Image food = new Image("https://thumbs.dreamstime.com/z/food-bag-full-cartoon-58802756.jpg");
        
        //Create an Image object.
      	ImageView imageAnimal = new ImageView(animal);
      	imageAnimal.setFitWidth(200);
      	imageAnimal.setFitHeight(200);
   		
   		ImageView imageFood = new ImageView(food);
   		imageFood.setFitWidth(200);
   		imageFood.setFitHeight(200);
   		
   		//button
   	    Button animalB = new Button("Animal");
   	    Button foodB = new Button("Food"); 
   	    Button exit = new Button("Exit");
	    Button home = new Button("Home");
	    
	    //button box
	    HBox buttonsBox = new HBox(2);
	    buttonsBox.getChildren().addAll(home, exit);

   	    //Button event
   	    animalB.setOnAction(new enterAnimal());
   	    foodB.setOnAction(new enterFood());
   	    exit.setOnAction(new exit());
   	    home.setOnAction(new home());
   	    
        gridpane.getChildren().clear();
		gridpane.add(animalOrFood , 1, 0);
		gridpane.add(imageAnimal, 1, 1);
		gridpane.add(imageFood, 2, 1);
		gridpane.add(animalB, 1, 2 );
		gridpane.add(foodB, 2, 2);
		gridpane.add(buttonsBox, 1, 3);
	 }
    }
    
    //Add new scene to stage and request what the user wants to find Food or Animal	
    class userFind implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event) {
    	//Label control	
        Label animalOrFood = new Label("What would you like to review?");
            
        //image of food and animal
        Image animal = new Image("https://media.wired.com/photos/5bfde7b13ee8d605f3dd0edf/4:3/w_1040,c_limit/fallshow-01.jpg");
        Image food = new Image("https://thumbs.dreamstime.com/z/food-bag-full-cartoon-58802756.jpg");
        
        //Create an Image object.
      	ImageView imageAnimal = new ImageView(animal);
      	imageAnimal.setFitWidth(200);
      	imageAnimal.setFitHeight(200);
   		
   		ImageView imageFood = new ImageView(food);
   		imageFood.setFitWidth(200);
   		imageFood.setFitHeight(200);
   		
   		//button
   	    Button animalB = new Button("Animal");
   	    Button foodB = new Button("Food"); 
   	    Button home = new Button("Home");
   	    Button exit = new Button("Exit");
   	    
   	    HBox buttonsBox = new HBox(2);
	    buttonsBox.getChildren().addAll(home, exit);
   	    
   	    //Button event
   	    animalB.setOnAction(new findAnimal());
   	    foodB.setOnAction(new findFood());
   	    home.setOnAction(new home());
   	    exit.setOnAction(new exit());
   	          
        gridpane.getChildren().clear();
		gridpane.add(animalOrFood , 1, 0);
		gridpane.add(imageAnimal, 1, 1);
		gridpane.add(imageFood, 2, 1);
		gridpane.add(animalB, 1, 2 );
		gridpane.add(foodB, 2, 2);
		gridpane.add(buttonsBox, 1, 3);
	 }
    }
    
    //Displays all animal and food information
    class zooMode implements EventHandler<ActionEvent>{
    	@Override
    	public void handle(ActionEvent event)  {
    	gridpane.getChildren().clear();
    	
    	session.add("sessionAction",new Gson().toJsonTree("Zoo Mode"));
     	  	
    	Button viewSession = new Button("View sessions");
    	Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
   	    
   	    int t = 0;
   	    File getAnimalsTerrain = new File("animalsTerrain.txt");
		Scanner showTerrain = null;
	    try {
	    	showTerrain = new Scanner(getAnimalsTerrain);
	    } catch (FileNotFoundException e1) {
			int input = JOptionPane.showConfirmDialog(null, "Animal.txt does not exist.\n Would you like to create it?");
		    if(input == 0) {
		    	File missingFile = new File("animalsTerrain.txt");
		    	try {
					missingFile.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
	 	 		    System.exit(0);
				}
		    	try {
					FileOutputStream addFile = new FileOutputStream(missingFile, false);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,"Animal.txt could not be made as output at this time.\nProgram will exit and please try again.");
	 	 		    System.exit(0);
				}
		    	try {
					showTerrain = new Scanner(missingFile);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,"Animal.txt could not be scanned at this time.\nProgram will exit and please try again.");
	 	 		    System.exit(0);
				}
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "The Zoo program needs Animal.txt to run.");
		    }
	    }
	    
	    String[] terrains = new String[(int) getAnimalsTerrain.length()];
	    ListView displayTerrain = new ListView();
	    
	    while (showTerrain.hasNext()) {
	    	terrains[t] = showTerrain.nextLine();
	    	displayTerrain.getItems().add(terrains[t]);
			t++;
		}
    	
    	ObservableList<String> animals = FXCollections.observableArrayList();
    	
        for(int a = 0; a < choice.getHowManyA(); a++) {
    		animals.add(allAnimalInfo.getInfo()[a]);
    	}
        
    	ListView animalsList = new ListView(animals);
   
    	
    	ObservableList<String> foods = FXCollections.observableArrayList();
    	
    	for(int f = 0; f < choice.getHowManyF(); f++) {
    		foods.add(allFoodInfo.getInfo()[f]);
    	}
    	
    	//BuildSession
    	ArrayList<sessionObject> getSessions = new ArrayList<sessionObject>();	
    	Type type = new TypeToken<List<sessionObject>>() {
    	}.getType();
    	
    	 File showSession = new File("showSessions.json");
     	    Scanner whatAnimal = null;
		    try {
			whatAnimal = new Scanner(showSession);
		     } catch (FileNotFoundException e1) {
		    	File missingFile = new File("showSessions.json");
		    	try {
					missingFile.createNewFile();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"Unable collect all sessions and display them");
				}
		    	try {
					FileOutputStream addFile = new FileOutputStream(missingFile, false);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,"Unable to set showSessions.json as storage for all sessions");
				}
		    	try {
					whatAnimal = new Scanner(missingFile);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null,"Not able to write all sessions to showSessions.json");
				}
		    }
		    
   	 		
         File getAll = new File("oldSessions.json");
         Scanner showAll = null;
         try {
			showAll = new Scanner(getAll);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
    	String addBrackets = showAll.nextLine();
    	String remove = addBrackets.substring(0, addBrackets.length()-1);
    
    	try {
			FileWriter showAllS = new FileWriter("showSessions.json");
			PrintWriter outputAll = new PrintWriter(showAllS);
			outputAll.print("["+remove+"]");
			outputAll.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	viewSession.setOnAction(eventShow -> {
    
    		ObservableList sessions = FXCollections.observableArrayList();
    		String[] prettySession = remove.split(",");
    		
    		for(int i = 0; i < prettySession.length;i++) {
    		sessions.addAll(prettySession[i]);
    		}
			  
        	ListView showSesisons = new ListView(sessions);
        	showSesisons.setPrefWidth(900);
        	
        	gridpane.getChildren().clear();
            gridpane.add(showSesisons, 0, 0);
        	gridpane.add(buttonsBox, 0, 3);
        	
    	});
    	
    	ListView foodsList = new ListView(foods);
    	
    	Label animalInfo = new Label("Animal Info");
    	Label foodInfo = new Label("Food Info");
    	Label terrainInf = new Label("Terrain Info");
    	
    	gridpane.add(animalInfo, 0, 0);
    	gridpane.add(animalsList, 0, 1); 
    	
    	gridpane.add(foodInfo, 1, 0);
    	gridpane.add(foodsList, 1, 1);
    	
    	gridpane.add(terrainInf, 2, 0);
    	gridpane.add(displayTerrain , 2, 1);
    
    	
    	gridpane.add(buttonsBox, 0, 3);
    	gridpane.add(viewSession, 0, 4);
    	
    	session.add("action",new Gson().toJsonTree("All zoo information displayed."));
     	}
    	
    }
    	
    //request what animal the user wants to review
    class reviewAnimal implements EventHandler<ActionEvent>{
        	
	    public  void handle(ActionEvent event) {
		session.add("action",new Gson().toJsonTree("review animal"));
	  
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll( home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
		
		ComboBox<String> animalName = new ComboBox<>();		
		ComboBox<String> animalSpecies = new ComboBox<>();
	
		int i = 0;
	
		while(i != choice.getHowManyA()) {
			for(int a = 0; a < choice.getHowManyA(); a++) {
				String allInfo = allAnimalInfo.getInfo()[a];
				String [] lineByLine = allInfo.split(" ");
				animalName.getItems().add(lineByLine[0]);
				animalSpecies.getItems().add(lineByLine[0 + 1]);
			}
			i++;
		}
		
		//Search by name or species
		RadioButton aName = new RadioButton("Animal's name");
		RadioButton aSpecies = new RadioButton("Animal's species");
		Label searchBy = new Label("Search for animal by name or speces");
	
		gridpane.getChildren().clear();
		gridpane.add(searchBy, 5, 4);
		gridpane.add(aName, 5, 5);
		gridpane.add(aSpecies, 6, 5);
		gridpane.add(buttonsBox, 5, 6);
				
	    aName.setOnAction(Event -> {
	    
	    	session.add("action",new Gson().toJsonTree("Find by Name"));
	    
	    	Button searchA = new Button("Search");
	    	Label aNames = new Label("Click the drop down to view all the avaiable animal names");
	
	   
	    	gridpane.getChildren().clear();
	    	gridpane.add(aNames, 0, 0);
            gridpane.add(animalName, 0, 1);
            gridpane.add(searchA, 0, 10);
            
            searchA.setOnAction(EventSearch -> {
            	choice.setAnimal(animalName.getValue().toString());
            	Label userChoice = new Label("You want review ");
            	Label name = new Label(choice.getAnimal());
            	Label continueSearch = new Label("Hit the continue button to finish search");
            	
            	 
            	session.add("animalName",new Gson().toJsonTree(choice.getAnimal()));
         	    
            	Button continueS = new Button("Continue");
            	continueS.setOnAction(new searchResults());
            	
            	gridpane.getChildren().clear();
                
            	gridpane.add(userChoice, 0, 0);
            	gridpane.add(name, 0, 1);
            	gridpane.add(continueSearch, 0, 2);
            	gridpane.add(continueS, 1, 0);
            	gridpane.add(exit, 1, 1);
            	gridpane.add(home, 1, 2);
            	
            	gridpane.add(aNames, 4, 0);
            	gridpane.add(animalName, 4, 1);
                gridpane.add(searchA, 4, 2);
            });   
	    });
	    
	    aSpecies.setOnAction(Event -> {
	    	Button searchS = new Button("Search");
	    	Label species = new Label("Click the drop down to view all the avaiable animal species");
	    	
	    	gridpane.getChildren().clear();
	    	gridpane.add(species, 0, 0);
	    	gridpane.add(animalSpecies, 0, 1);
	    	gridpane.add(searchS, 0, 10);
	    
	        session.add("animalName",new Gson().toJsonTree("Find by Species"));
	    		
	    	searchS.setOnAction(EventSearch -> {
	
	            	choice.setAnimal(animalSpecies.getValue().toString());
	            	
	            	Label userChoice = new Label("You want review ");
	            	Label name = new Label(choice.getAnimal());
	            	Label continueSearch = new Label("Hit the continue button to finish search");
	            	
	           	
	            	session.add("animalSpecies",new Gson().toJsonTree(choice.getAnimal()));
	           	    
	            	Button continueS = new Button("Continue");
	            	continueS.setOnAction(new searchResults());
	            	
	            	gridpane.getChildren().clear();
	            
	            	gridpane.add(userChoice, 0, 0);
	            	gridpane.add(name, 0, 1);
	            	gridpane.add(continueSearch, 0, 2);
	            	gridpane.add(continueS, 1, 0);
	            	gridpane.add(exit, 1, 1);
	            	gridpane.add(home, 1, 2);
	            	
	            	gridpane.add(species, 4, 0);
	            	gridpane.add(animalSpecies, 4, 1);
	                gridpane.add(searchS, 4, 2);
	            });
	    });
	    }	   
	}

	//request what food the user wants to review
    class reviewFood implements EventHandler<ActionEvent>{
	public void handle(ActionEvent event) {
		
        session.add("action",new Gson().toJsonTree("view food"));
       
		Label enterFind = new Label("Enter food item name: ");
		
		TextField findFood = new TextField();
	    choice.setFindf(findFood);
		
		Button search = new Button("Search");
		
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(search, home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
		
				
		ObservableList<String> foods = FXCollections.observableArrayList();
		
		ComboBox<String> food = new ComboBox<>();	
		int i = 0;
		
		while(i != choice.getHowManyF()) {
			for(int a = 0; a < choice.getHowManyF(); a++) {
				String allInfo = allFoodInfo.getInfo()[a];
				String [] lineByLine = allInfo.split(" ");
				food.getItems().add(lineByLine[0]);
			}
			i++;
		}
		
		search.setOnAction(Event -> {
				choice.setFood(food.getValue());
				Label message = new Label("Click the drop down to view the aviable foods.");
				
			
				Label userChoice = new Label("You want review ");
            	Label name = new Label(choice.getFood());
            	Label continueSearch = new Label("Hit the continue button to finish search");
            	
            	Button continueS = new Button("Continue");
            	continueS.setOnAction(new foodSearch());
            	
            	gridpane.getChildren().clear();
                
            	gridpane.add(userChoice, 0, 0);
            	gridpane.add(name, 0, 1);
            	gridpane.add(continueSearch, 0, 2);
            	gridpane.add(continueS, 1, 0);
            	gridpane.add(exit, 1, 1);
            	gridpane.add(home, 1, 2);
            	
            	gridpane.add(message, 4, 0);
            	gridpane.add(food, 4, 1);
                gridpane.add(search, 4, 2);
		});
		
		Label message = new Label("Click the drop down to view the aviable foods.");
	
		gridpane.getChildren().clear();
		gridpane.add(message, 0, 0);
		gridpane.add(food, 0, 1);
		gridpane.add(buttonsBox, 0, 4);
	  }
   }  

    //request what animal the user wants to add
    class enterAnimal implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
    		
    	Label enterName = new Label("Enter animal's name: ");
    	TextField name = new TextField();
    	allAnimalInfo.setName(name);
    	
    	Label enterSpecies = new Label("Enter animal's species: ");
    	TextField species = new TextField();
    	allAnimalInfo.setSpecies(species);
    	
    	Label enterGender = new Label("Enter animal's gender: ");
    	TextField gender = new TextField();
    	allAnimalInfo.setGender(gender);

    	Label enterBOD = new Label("Enter animal's BOD: ");
   
    	Label onLoan = new Label("Is animal on loan: ");
    	TextField loan = new TextField();
    	allAnimalInfo.setLoan(loan);
   
    	Label enterFoods = new Label("Enter animal's foods: ");
    	TextField food1 = new TextField();
    	allAnimalInfo.setFood1(food1);
    	TextField food2 = new TextField();
    	allAnimalInfo.setFood2(food2);
    	TextField food3 = new TextField();
    	allAnimalInfo.setFood3(food3);
    	TextField food4 = new TextField();
    	allAnimalInfo.setFood4(food4);
    	TextField food5 = new TextField();
    	allAnimalInfo.setFood5(food5);
    	
    	DatePicker DOB = new DatePicker();
    	DOB.setOnAction(e -> {
    		LocalDate ld = DOB.getValue();
    		allAnimalInfo.setBOD(ld);
    	});
    	
    
		Button enter = new Button("Enter");
		 //Eventhandle for buttons
		enter.setOnAction(new addNewAnimal());
		
		
		Button addImage = new Button("Add Image");
		addImage.setOnAction(eventImage -> {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select an image");
			jfc.setAcceptAllFileFilterUsed(false);
			//Will only allow the user to select png gif and jpg
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG, JPG, and GIF images", "png", "gif", "jpg");
			jfc.addChoosableFileFilter(filter);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = new File(jfc.getSelectedFile().getPath().replace("\\","\\\\"));
				BufferedImage image = null;
				try
		        {
		            image = ImageIO.read(file);
		            ImageIO.write(image, "jpg", new File("images/newImage.jpg"));
		            ImageIO.write(image, "png", new File("images/newImage.png"));
		            ImageIO.write(image, "gif", new File("images/newImage.gif"));
		        } 
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		         
		       
		    		
			}
			
		});
		
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
   	    
   	    VBox enterTitle = new VBox(enterName, name, enterSpecies, species, enterBOD, DOB, onLoan, loan,
   	    		enterGender, gender, enterFoods,food1, food2, food3, food4, food5, addImage, enter, buttonsBox);
   	   
    	gridpane.getChildren().clear();
	    gridpane.add(enterTitle, 0, 0);
	  
    	}
    }
    
    //request what food the user wants to add
    class enterFood implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
    	
        session.add("newAction",new Gson().toJsonTree(""));	
    	Label enterFood = new Label("Enter Food: ");
    	TextField food = new TextField();
    	allFoodInfo.setFood(food);
    	
        Label enterDescription = new Label("Enter a small description: ");
        TextField description = new TextField();
        allFoodInfo.setDescription(description);
        
        Label enterVendor = new Label("Enter vendors information: ");
        TextField vendor = new TextField();
        allFoodInfo.setVendor(vendor);
        
        Label onHand = new Label("How many on hand: ");
        TextField hand = new TextField();
        allFoodInfo.setOnHand(hand);
        
        Label enterUnits = new Label("How many units on order: ");
        TextField units = new TextField();
        allFoodInfo.setUnits(units);
        
        Button enter = new Button("Enter");
        
        //Eventhandle for buttons
		enter.setOnAction(new addNewFood());
		
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
        	
        gridpane.getChildren().clear();
    	gridpane.add(enterFood, 0, 0);
    	gridpane.add(food, 0, 1);
    	gridpane.add(enterDescription, 0, 2);
    	gridpane.add(description, 0, 3);
    	gridpane.add(enterVendor, 0, 4);
    	gridpane.add(vendor, 0, 5);
    	gridpane.add(onHand, 0, 6);
    	gridpane.add(hand, 0, 7);
    	gridpane.add(enterUnits, 0, 8);
    	gridpane.add(units, 0, 9);
    	gridpane.add(enter, 0, 10);
    	gridpane.add(buttonsBox, 0, 11);
    	}
    }
    
    //displays all animal information that user searched for
    class findAnimal implements EventHandler<ActionEvent>{
	public  void handle(ActionEvent event) {
		Label findAnimal = new Label("Enter species type or name of animal: ");
	
		session.add("action",new Gson().toJsonTree("Find Animal"));
	
		TextField nameOrSpecies = new TextField();
		choice.setFindA(nameOrSpecies);
		
		Button search = new Button("Search");
		
		//Eventhandle for buttons
		search.setOnAction(new findAResults());
		
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
		
		gridpane.getChildren().clear();
		gridpane.add(findAnimal, 0, 0);
		gridpane.add(nameOrSpecies, 0, 1);
		gridpane.add(search, 0, 2);
		gridpane.add(buttonsBox, 0, 3);

		}
    }
    
    //displays all animal information that user searched for
    class findFood implements EventHandler<ActionEvent>{
	public  void handle(ActionEvent event) {
		Label findFood = new Label("Enter the food you want find: ");
		
		session.add("action",new Gson().toJsonTree("Find Food"));
		
		TextField food = new TextField();
		choice.setFindf(food);
		
		Button search = new Button("Search");
		
		//Eventhandle for buttons
		search.setOnAction(new findFSearch());
		
		Button home = new Button("Home");
    	Button exit = new Button("Exit");
    	
    	HBox buttonsBox = new HBox(2);
 	    buttonsBox.getChildren().addAll(home, exit);
    	
    	home.setOnAction(new home());
   	    exit.setOnAction(new exit());
	
		gridpane.getChildren().clear();
		gridpane.add(findFood, 0, 0);
		gridpane.add(food, 0, 1);
		gridpane.add(search, 0, 2);
		gridpane.add(buttonsBox, 0, 3);
		}
    }

    //display information for the animal that was searched
    class searchResults implements EventHandler<ActionEvent>{
    	public  void handle(ActionEvent event) {
    		int i = 0;
    	
            session.add("sessionAction",new Gson().toJsonTree("Animal displayed"));
          
    		while(i != choice.getHowManyA()) {
    			for(int each = 0; each < 1; each++) {
    			String allInfo = allAnimalInfo.getInfo()[i];
				String [] lineByLine = allInfo.split(" ");
				
				if(choice.getAnimal().equals(lineByLine[each]) || choice.getAnimal().equals(lineByLine[each+1]) ) {
				Label name = new Label("Name:");
		   	    Label aName = new Label(lineByLine[0]);
		   	    
		   	//Get image object from the link
		   	    String getAnimalImg ="file:images/"+aName.getText()+".jpg";
		   	    
	    		Image image = new Image(getAnimalImg);
	    		
	    		//Create an Image object.
	    		ImageView imageView = new ImageView(image);
	    		imageView.setFitWidth(300);
	    		imageView.setFitHeight(300);
			   	    
		   	    Label species = new Label("Species:");
		   	    Label aSpecies = new Label(lineByLine[1]);
			   	    
		   	    Label gender = new Label("Gender:");
			    Label aGender = new Label(lineByLine[2]); 
			   	    
		   	    Label dob = new Label("DOB:");
		   	    Label aDob = new Label(lineByLine[3]);
			   	    
		   	    Label onLoan = new Label("On Loan?");
		   	    Label aLoan = new Label(lineByLine[4]);
			   	    
		   	    Label food = new Label("Food: ");
		   	    Label food1 = new Label(lineByLine[5]);
		   	    Label food2 = new Label(lineByLine[6]);
		   	    Label food3 = new Label(lineByLine[7]);
		   	    Label food4 = new Label(lineByLine[8]);
		     	Label food5 = new Label(lineByLine[9]);
		     	
		     	choice.setAnimal(lineByLine[0]);
			     	
		     	Button home = new Button("Home");
		     	Button exit = new Button("Exit");
		     	Button edit = new Button("Edit");
			     	
			     	//Eventhandle for buttons
		     	home.setOnAction(new home());
		     	exit.setOnAction(new exit());
		     	edit.setOnAction(new editAnimal());
		     	
		     	VBox vTitle = new VBox(name, species, gender);
		     	VBox vTInfo = new VBox(aName, aSpecies, aGender);
		     	VBox vTitle2 = new VBox(dob, food);
		     	VBox vTInfo2 = new VBox(aDob, food1, food2, food3, food4, food5);
			   	        	    
				gridpane.getChildren().clear();
				gridpane.add(imageView, 0, 0);
				
				gridpane.add(vTitle, 1, 0);
				gridpane.add(vTInfo, 2, 0);
				gridpane.add(vTitle2, 3, 0);
				gridpane.add(vTInfo2, 4, 0);
      			
				
				gridpane.add(home, 0, 5);
				gridpane.add(edit, 0, 6);
				gridpane.add(exit, 0, 7);
				}
           };
           i++;
          }
         }
    	}
 
    //display information for the food that was searched
    class foodSearch implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event) {	
    		int i = 0;	
    	
    		while(i != choice.getHowManyF()+1) {
    			for(int each = 0; each < 1; each++) {
    			String allInfo = allFoodInfo.getInfo()[i];
				String [] lineByLine = allInfo.split(" ");
				session.add("animalFood1",new Gson().toJsonTree(choice.getFood()));
					
				if(lineByLine[each].contains(choice.getFood())) {
			   	    Label foods = new Label("Food:");
			   	    Label food = new Label(lineByLine[0]);
			   	    
			   	    Label description = new Label("Description:");
			   	    Label d1 = new Label(lineByLine[1]);
			   	    Label d2 = new Label(lineByLine[2]);
			     	Label d3 = new Label(lineByLine[3]);
			   	    
			   	    Label vendor = new Label("Vendor:");
			   	    Label fvendor = new Label(lineByLine[4]); 
			   	    
			   	    Label unit = new Label("Unit:");
			   	    Label fUnit = new Label(lineByLine[5]);
			   	    
			   	    Label onHand = new Label("On hand:");
			   	    Label fHand = new Label(lineByLine[6]);
			   	    
			   	    Label id = new Label("ID");
			   	    Label fId = new Label(lineByLine[7]);
			   	    
			     	Button exit = new Button("Exit");
			     	Button home = new Button("Home");
			     	
			     	//Eventhandle for buttons
			     	exit.setOnAction(new exit());
			     	home.setOnAction(new home());
			  
					gridpane.getChildren().clear();
					gridpane.add(id, 0, 0);
					gridpane.add(fId, 1, 0);
					gridpane.add(foods, 0, 1);
					gridpane.add(food, 0, 2);
					
					gridpane.add(vendor, 0, 3);
					gridpane.add(fvendor, 0, 4);
					gridpane.add(unit, 1, 1);
					gridpane.add(fUnit, 1, 2);
					gridpane.add(onHand, 1, 3);
					gridpane.add(fHand, 1, 4);
					
					gridpane.add(description, 3, 1);
					gridpane.add(d1, 4, 1);
					gridpane.add(d2, 5,1);
					gridpane.add(d3, 6, 1);
		
					gridpane.add(home, 0, 6);
					gridpane.add(exit, 1, 6);
					
					
				}
    	i++;
    };
        }
    }
   }
     
    class findAResults implements EventHandler<ActionEvent>{
    	public  void handle(ActionEvent event) {
    		String findAnimal = choice.getReviewA().getText();
    		    		
    		int i = 0;	
    		int found = 0;
    		String capitalAnimal = findAnimal;
    		String[] arr = capitalAnimal.split(" ");
    		String animalCapital = " ";
    		
    		for (int a = 0; a < arr.length; a++) {
    			if (arr[a].matches(".*\\d.*") || arr[a].isEmpty()){
    	  		       JOptionPane.showMessageDialog(null,"Animal name or species can only be letters.\nNo numbers or blank entries will be accepted.\nTry again");
    	  		       System.exit(0);
    	             } 
    			
    			char capLetter = Character.toUpperCase(arr[a].charAt(0));
    			animalCapital += " " + capLetter + arr[a].substring(1);
    		}
    		animalCapital = animalCapital.trim();
    		
    		while(i != choice.getHowManyA()) {
    			for(int each = 0; each < 1; each++) {
    			String allInfo = allAnimalInfo.getInfo()[i];
				String [] lineByLine = allInfo.split(" ");

						
				if(animalCapital.equals(lineByLine[each])) {
					found = 1;
			   	    Label name = new Label("Name:");
			   	    Label aName = new Label(lineByLine[0]);
			   	    
			   	    Label species = new Label("Species:");
			   	    Label aSpecies = new Label(lineByLine[1]);
			   	    
			   	    Label gender = new Label("Gender:");
			   	    Label aGender = new Label(lineByLine[2]); 
			   	    
			   	    Label dob = new Label("DOB:");
			   	    Label aDOB = new Label(lineByLine[3]);
			   	  	    
			   	    Label onLoan = new Label("On Loan?");
			   	    Label aLoan = new Label(lineByLine[4]);
			   	    
			   	    Label food = new Label("Food: ");
			   	    Label food1 = new Label(lineByLine[5]);
			   	    Label food2 = new Label(lineByLine[6]);
			   	    Label food3 = new Label(lineByLine[7]);
			   	    Label food4 = new Label(lineByLine[8]);
			     	Label food5 = new Label(lineByLine[9]);
			     	
			     	//Get image object from the link
			   	    String getAnimalImg ="file:images/"+aName.getText()+".jpg";
			   	    
		    		Image image = new Image(getAnimalImg);
		    		
		    		//Create an Image object.
		    		ImageView imageView = new ImageView(image);
		    		imageView.setFitWidth(300);
		    		imageView.setFitHeight(300);
			   	    
			    	Button exit = new Button("Exit");
			     	Button home = new Button("Home");
			     	
			     	//Eventhandle for buttons
			     	exit.setOnAction(new exit());
			     	home.setOnAction(new home());
			  
			   	      	    
					gridpane.getChildren().clear();
					gridpane.add(imageView, 8, 6);
					gridpane.add(name, 0, 1);
					gridpane.add(aName, 1, 1);
					
					gridpane.add(species, 0, 2);
					gridpane.add(aSpecies, 1, 2);
					
					gridpane.add(gender, 0,3);
					gridpane.add(home, 0, 4);
					gridpane.add(exit, 0, 5);
					
					gridpane.add(aGender, 1, 3);
					gridpane.add(dob, 3, 1);
					gridpane.add(aDOB, 4, 1);
					gridpane.add(onLoan, 5, 1);
					gridpane.add(aLoan, 6, 1);
					gridpane.add(food, 7, 1);
					gridpane.add(food1, 8, 1);
					gridpane.add(food2, 8, 2);
					gridpane.add(food3, 8, 3);
					gridpane.add(food4, 8, 4);
					gridpane.add(food5, 8, 5);
					
		
					session.add("animalName",new Gson().toJsonTree(lineByLine[each]));
			       
	              
				}
				if(animalCapital.equals(lineByLine[each+1])) {
					found = 1;
			   	    Label name = new Label("Name:");
			   	    Label aName = new Label(lineByLine[0]);
			   	    
			   	    Label species = new Label("Species:");
			   	    Label aSpecies = new Label(lineByLine[1]);
			   	    
			   	    Label gender = new Label("Gender:");
			   	    Label aGender = new Label(lineByLine[2]); 
			   	    
			   	    Label dob = new Label("DOB:");
			   	    Label aDOB = new Label(lineByLine[3]);
			   	  	    
			   	    Label onLoan = new Label("On Loan?");
			   	    Label aLoan = new Label(lineByLine[4]);
			   	    
			   	    Label food = new Label("Food: ");
			   	    Label food1 = new Label(lineByLine[5]);
			   	    Label food2 = new Label(lineByLine[6]);
			   	    Label food3 = new Label(lineByLine[7]);
			   	    Label food4 = new Label(lineByLine[8]);
			     	Label food5 = new Label(lineByLine[9]);
			   	    
			    	Button exit = new Button("Exit");
			     	Button home = new Button("Home");
			     	
			     	//Eventhandle for buttons
			     	exit.setOnAction(new exit());
			     	home.setOnAction(new home());
			  
			   	      	    
					gridpane.getChildren().clear();
					gridpane.add(name, 0, 1);
					gridpane.add(aName, 1, 1);
					
					gridpane.add(species, 0, 2);
					gridpane.add(aSpecies, 1, 2);
					
					gridpane.add(gender, 0,3);
					gridpane.add(home, 0, 4);
					gridpane.add(exit, 0, 5);
					
					gridpane.add(aGender, 1, 3);
					gridpane.add(dob, 3, 1);
					gridpane.add(aDOB, 4, 1);
					gridpane.add(onLoan, 5, 1);
					gridpane.add(aLoan, 6, 1);
					gridpane.add(food, 7, 1);
					gridpane.add(food1, 8, 1);
					gridpane.add(food2, 8, 2);
					gridpane.add(food3, 8, 3);
					gridpane.add(food4, 8, 4);
					gridpane.add(food5, 8, 5);
					
					session.add("animalSpecies",new Gson().toJsonTree(lineByLine[each+1]));
					
				}
				if(found == 0){	
				
					Label noAnimal = new Label("No animal found.");	
					gridpane.add(noAnimal, 0, 6);
					
				 }	
				i++;
    }; 
   } 
  }
 }
    
    //display information for the food that was searched
    class findFSearch implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event) {
        String findFood = choice.getReviewF().getText();
    		
    		int i = 0;	
    		int found = 0;
    		String capitalAnimal = findFood;
    		String[] arr = capitalAnimal.split(" ");
    		String foodCapital = " ";
    		
    		for (int a = 0; a < arr.length; a++) {
    			if (arr[a].matches(".*\\d.*") || arr[a].isEmpty()){
    	  		       JOptionPane.showMessageDialog(null,"Food name can only be letters.\nNo numbers or blank entries will be accepted.\nTry again");
    	  		       System.exit(0);
    	             } 
    			
    			char capLetter = Character.toUpperCase(arr[a].charAt(0));
    			foodCapital += " " + capLetter + arr[a].substring(1);
    		}
    		foodCapital = foodCapital.trim();
    		
    		session.add("review",new Gson().toJsonTree(findFood));

    		
    		while(i != choice.getHowManyA()+1) {
    			for(int each = 0; each < 1; each++) {
    			String allInfo = allFoodInfo.getInfo()[i];
				String [] lineByLine = allInfo.split(" ");

				if(foodCapital.equals(lineByLine[each])) {
					found = 1;
			   	    Label foods = new Label("Food:");
			   	    Label food = new Label(lineByLine[0]);
			   	    
			   	    Label description = new Label("Description:");
			   	    Label d1 = new Label(lineByLine[1]);
			   	    Label d2 = new Label(lineByLine[2]);
			     	Label d3 = new Label(lineByLine[3]);
			   	    
			   	    Label vendor = new Label("Vendor:");
			   	    Label fvendor = new Label(lineByLine[4]); 
			   	    
			   	    Label unit = new Label("Unit:");
			   	    Label fUnit = new Label(lineByLine[5]);
			   	    
			   	    Label onHand = new Label("On hand:");
			   	    Label fHand = new Label(lineByLine[6]);
			   	    
			   	    Label id = new Label("ID");
			   	    Label fId = new Label(lineByLine[7]);
			   	    
			    	Button exit = new Button("Exit");
			     	Button home = new Button("Home");
			     	
			     	//Eventhandle for buttons
			     	exit.setOnAction(new exit());
			     	home.setOnAction(new home());
			  
			   	      	    
					gridpane.getChildren().clear();
					gridpane.add(id, 0, 0);
					gridpane.add(fId, 1, 0);
					gridpane.add(foods, 0, 1);
					gridpane.add(food, 0, 2);
					gridpane.add(home, 0, 5);
					gridpane.add(exit, 0, 6);
					
					gridpane.add(vendor, 0, 3);
					gridpane.add(fvendor, 0, 4);
					gridpane.add(unit, 1, 1);
					gridpane.add(fUnit, 1, 2);
					gridpane.add(onHand, 1, 3);
					gridpane.add(fHand, 1, 4);
					
					gridpane.add(description, 3, 1);
					gridpane.add(d1, 4, 1);
					gridpane.add(d2, 5,1);
					gridpane.add(d3, 6, 1);
				
					
				}
				if(found == 0){	
					
					Label noAnimal = new Label("No food found.");	
					gridpane.add(noAnimal, 0, 7);
					
				 }	
    	i++;
    };
        }
    }
        
}
    
    //edit selected animal
    class editAnimal implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
            session.add("action",new Gson().toJsonTree("edit animal"));
          
    		int ea = 0;
    		File animalData = new File("animals.txt");
    		Scanner whatAnimal = null;
			try {
				whatAnimal = new Scanner(animalData);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
 	 		    System.exit(0);
			}
    
    		String[] animals = new String[(int) animalData.length()]; 
    		
    		while (whatAnimal.hasNext()) {
    			animals[ea] = whatAnimal.nextLine();
    		    ea++;
    		}
    		
    		
    		String newASpecies = null;
    		
            ObservableList<String> newInfoSpecies = FXCollections.observableArrayList();
	   		
    		for(int s = 0; s < 30; s++) {
    			newASpecies = animals[s];
    			newInfoSpecies.add(newASpecies);
    			String addNewSpecies = newInfoSpecies.toString();
    			String[] splitUp = addNewSpecies.replaceAll("\\p{P}","").split(" ");
    			
    			if(choice.getAnimal().contentEquals(splitUp[s])) {
    				String getSpecies = splitUp[s+1];
    				String getGender = splitUp[s+2];
    				String getDOB = splitUp[s+3];
    				String getLoan = splitUp[s+4];
    				String getFoods = splitUp[s+5] + " " + splitUp[s+6]+ " " + splitUp[s+7] + " " + splitUp[s+8] + " " + splitUp[s+9];
    				
    				choice.setCurrentSpecies(getSpecies);
    				choice.setCurrentGender(getGender);
    				choice.setCurrentDOB(getDOB);
    				choice.setCurrentLoan(getLoan);
    				choice.setFoods(getFoods);
    			}
    		}
    		
    		ComboBox<String> animalAtt = new ComboBox<>();
    	    animalAtt.getItems().addAll("Name","Species","Gender","DOB","On Loan","Food");
    	    
    	    Label whatAtt = new Label("What attribute would you like to edit?");
    	    Label selected = new Label(" ");
    	    whatAnimal.close();
    	   
    	    Button edit = new Button("Edit");
    	    edit.setOnAction(Event -> {
    	    	    	    	
    	    	selected.setText(animalAtt.getValue());
    	    	
    	    	if(animalAtt.getValue() == "Name") {
    	    		gridpane.getChildren().clear();
    
        	    	session.add("newAction",new Gson().toJsonTree("Name"));
                
    	    		Label currentName = new Label(choice.getAnimal());
    	    		Label current = new Label("Current Animal's name");
    	    		
    	    		Label name = new Label("Enter new name: ");
    	    		TextField newName = new TextField();
    	    		
    	    		Button submit = new Button("Submit");
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    		     	submit.setOnAction(EventName -> {
    		     	
    		     	String capitalAnimal = newName.getText().toString();
        	    	String[] arr = capitalAnimal.split(" ");
        	   		String animalCapital = " ";
        	   		
        	   		session.add("animalName",new Gson().toJsonTree(capitalAnimal));
                 
        	   		for (int a = 0; a < arr.length; a++) {
        	   			char capLetter = Character.toUpperCase(arr[a].charAt(0));
        	   			animalCapital += " " + capLetter + arr[a].substring(1);
                 		}
        	   		animalCapital = animalCapital.trim();
        	   			
        	   		String newAName = null;
        	   		
        	   		ObservableList<String> newInfo = FXCollections.observableArrayList();
        	   			
        	   		for(int n = 0; n < choice.getHowManyA(); n++) {
        	   			String replace = choice.getAnimal();
        	   			newAName = animals[n].replaceAll(replace, animalCapital);
        	   			newInfo.add(newAName);
        	   			
        	   			String addNewName = newInfo.toString();
        	   			String[] splitUp = addNewName.split(", ");
        	   			String eachAnimal = " ";
        	   			FileWriter writeAInfo = null;
        	   			animalData.delete();
        	   			try {
							animalData.createNewFile();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}
        	   			
        	   			for (int add = 0; add < splitUp.length;add++) {
        	   				eachAnimal = splitUp[add].replaceAll("\\p{P}","");
        	   					
        	   				try {
								writeAInfo = new FileWriter("animals.txt", true);
							} catch (FileNotFoundException e) {
								JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
				 	 		    System.exit(0);
							}			  
        	   			
        	   			try {
							writeAInfo.write(eachAnimal + "\n");
							writeAInfo.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}
        	   		 }
        	   		}
        	   			
    		     	Label updated = new Label("Name was updated");
    		     		
    		     	gridpane.getChildren().clear();
    		   		gridpane.add(updated, 0, 0);
    		   		gridpane.add(home, 0, 3);
        	   		gridpane.add(exit, 0, 4);
        	   		
        	   		session.add("newAction",new Gson().toJsonTree("Name was updated"));
                    
    		     		
    		     	});
    	    		
    	    		gridpane.add(name, 0, 0);
    	    		gridpane.add(newName, 0, 1);
    	    		gridpane.add(current, 1, 0);
    	    		gridpane.add(currentName, 1, 1);
    	    		gridpane.add(submit, 0, 2);
    	    		gridpane.add(home, 0, 3);
    	    		gridpane.add(exit, 0, 4);
    	    		
    	    	}
    	    	
    	    	else if(animalAtt.getValue() == "Species") {
    	    		gridpane.getChildren().clear();
    	    	
    	    		session.add("newAction",new Gson().toJsonTree("Species"));
                   		
    	    		Label currentSpecies = new Label(choice.getCurrentSpecies());
    	    		Label current = new Label("Current Animal's species");
    	    		
    	    		Label name = new Label("Enter new species: ");
    	    		TextField newSpecies = new TextField();
    	    		
    	    		Button submit = new Button("Submit");
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    		     	submit.setOnAction(EventName -> {
    		     	
    		     	String capitalSpecies = newSpecies.getText().toString();
        	    	String[] arr = capitalSpecies.split(" ");
        	   		String animalCapital = " ";
        	    		
        	   		for (int a = 0; a < arr.length; a++) {
        	   			char capLetter = Character.toUpperCase(arr[a].charAt(0));
        	   			animalCapital += " " + capLetter + arr[a].substring(1);
                 		}
        	   		animalCapital = animalCapital.trim();
        	   		
        	   		session.add("animalSpecies",new Gson().toJsonTree(animalCapital));
                    
                   
        	   		String updateSpecies = null;
        	   		
        	   		ObservableList<String> newInfo = FXCollections.observableArrayList();	
        	   		
        	   		for(int n = 0; n < choice.getHowManyA(); n++) {
        	   			String replace = choice.getCurrentSpecies();
        	   			updateSpecies = animals[n].replaceAll(replace, animalCapital);
        	   			newInfo.add(updateSpecies);
        	   			
        	   			String addNSpecies = newInfo.toString();
        	   			String[] splitUp2 = addNSpecies.split(", ");
        	   			String eachAnimal = " ";
        	   			FileWriter writeAInfo = null;
        	   			animalData.delete();
        	   			try {
							animalData.createNewFile();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}
        	   			
        	   			for (int add = 0; add < splitUp2.length;add++) {
        	   				eachAnimal = splitUp2[add].replaceAll("\\p{P}","");	
        	   			
        	   			try {
							writeAInfo = new FileWriter("animals.txt", true);
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be found to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}			  
    	   			
    	   			try {
						writeAInfo.write(eachAnimal + "\n");
						writeAInfo.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
		 	 		    System.exit(0);
					}
        	   			}
        	   			
        	   		}
        	   		
        	   		Label updated = new Label("Species was updated");
        	  
        	   		session.add("sessionAction",new Gson().toJsonTree("Species was updated"));
                  
    		     	gridpane.getChildren().clear();
    		   		gridpane.add(updated, 0, 0);
    		   		gridpane.add(home, 0, 3);
        	   		gridpane.add(exit, 0, 4);
        	   		});
    		     	
    		     	gridpane.add(name, 0, 0);
    	    		gridpane.add(newSpecies, 0, 1);
    	    		gridpane.add(current, 1, 0);
    	    		gridpane.add(currentSpecies, 1, 1);
    	    		gridpane.add(submit, 0, 2);
    	    		gridpane.add(home, 0, 3);
    	    		gridpane.add(exit, 0, 4);
    	    		 }
    		     	
    	    	else if(animalAtt.getValue() == "Gender") {
                    gridpane.getChildren().clear();
                  
                    session.add("sessionAction",new Gson().toJsonTree("Gender"));
                  
    	    		Label currentGender = new Label(choice.getGender());
    	    		Label current = new Label("Current Animal's gender");
    	    		
    	    		Label gender = new Label("Enter new gender: ");
    	    		TextField newGender = new TextField();
    	    		
    	    		Button submit = new Button("Submit");
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    		     	submit.setOnAction(EventName -> {
    		     	
    		     	String capitalGender = newGender.getText().toString();
        	    	String[] arr = capitalGender.split(" ");
        	   		String animalCapital = " ";
        	    		
        	   		for (int a = 0; a < arr.length; a++) {
        	   			char capLetter = Character.toUpperCase(arr[a].charAt(0));
        	   			animalCapital += " " + capLetter + arr[a].substring(1);
                 		}
        	   		animalCapital = animalCapital.trim();
        	   	
        	       session.add("newAction",new Gson().toJsonTree(animalCapital));
                 
        	   		String updateGender = null;
        	   		
        	   		ObservableList<String> newInfo = FXCollections.observableArrayList();	
        	   		
        	   		for(int n = 0; n < choice.getHowManyA(); n++) {
        	   			String replace = choice.getGender();
        	           
        	   			if(animals[n].contains(choice.getAnimal())) {
        	   			updateGender = animals[n].replaceAll(replace, animalCapital);
        	   			}
        	   			else {
        	   				updateGender = animals[n];
        	   			}
        	   			newInfo.add(updateGender);
        	   			
        	   			String addNSpecies = newInfo.toString();
        	   			String[] splitUp3 = addNSpecies.split(", ");
        	   			String eachAnimal = " ";
        	   			FileWriter writeAInfo = null;
        	   			animalData.delete();
        	   			try {
							animalData.createNewFile();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}
        	   			
        	   			for (int add = 0; add < splitUp3.length;add++) {
        	   				eachAnimal = splitUp3[add].replaceAll("\\p{P}","");	
        	   			
        	   			try {
							writeAInfo = new FileWriter("animals.txt", true);
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}			  
    	   			
    	   			try {
						writeAInfo.write(eachAnimal + "\n");
						writeAInfo.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
		 	 		    System.exit(0);
					}
        	   			}
        	   			
        	   		}
        	   		
        	   		Label updated = new Label("Gender was updated");
        	   		
        	       session.add("newAction",new Gson().toJsonTree("Gender was updated"));
                  
		     		
    		     	gridpane.getChildren().clear();
    		   		gridpane.add(updated, 0, 0);
    		   		gridpane.add(home, 0, 3);
        	   		gridpane.add(exit, 0, 4);
        	   		});
    		     	
    		     	gridpane.add(gender, 0, 0);
    	    		gridpane.add(newGender, 0, 1);
    	    		gridpane.add(current, 1, 0);
    	    		gridpane.add(currentGender, 1, 1);
    	    		gridpane.add(submit, 0, 2);
    	    		gridpane.add(home, 0, 3);
    	    		gridpane.add(exit, 0, 4);
    	    		
    	    	}
    	    	
    	    	else if(animalAtt.getValue() == "DOB") {
    	    		gridpane.getChildren().clear();
    	    		;
    	    		session.add("sessionAction",new Gson().toJsonTree("edit DOB"));
                     
                  
    	    		Label currentDOB = new Label(choice.getDOB());
    	    		Label current = new Label("Current Animal's DOB");
    	    		
    	    	
    	    		DatePicker DOB = new DatePicker();
    	        	DOB.setOnAction(e -> {
    	        		LocalDate ld = DOB.getValue();
    	        		allAnimalInfo.setBOD(ld);
    	        		
    	          	 });
    	    		
    	    		Label getDOB = new Label("Enter new DOB: ");
    	    		
    	    		
    	    		Button submit = new Button("Submit");
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    		     	submit.setOnAction(EventName -> {
    		     	
        	   		String updateGender = null;
        	   		
        	   		ObservableList<String> newInfo = FXCollections.observableArrayList();	
        	   		
        	   		session.add("newAction",new Gson().toJsonTree(choice.getDOB()));
                    
        	   		
        	   		for(int n = 0; n < choice.getHowManyA(); n++) {
        	   			String replace = choice.getDOB();
        	   			
        	   			if(animals[n].contains(choice.getAnimal())) {
        	   			updateGender = animals[n].replaceAll(replace, allAnimalInfo.getBOD().toString());
        	   			}
        	   			else {
        	   				updateGender = animals[n];
        	   			}
        	   			newInfo.add(updateGender);
        	   			
        	   			String addNSpecies = newInfo.toString();
        	   			String[] splitUp3 = addNSpecies.split(", ");
        	   			String eachAnimal = " ";
        	   			FileWriter writeAInfo = null;
        	   			animalData.delete();
        	   			try {
							animalData.createNewFile();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}
        	   			
        	   			for (int add = 0; add < splitUp3.length;add++) {
        	   				eachAnimal = splitUp3[add].replaceAll("\\p{P}","");	
        	   			
        	   			try {
							writeAInfo = new FileWriter("animals.txt", true);
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be found to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
						}			  
    	   			
    	   			try {
						writeAInfo.write(eachAnimal + "\n");
						writeAInfo.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
		 	 		    System.exit(0);
					}
        	   			}
        	   			
        	   		}
        	   		
        	   	
        	   		Label updated = new Label("DOB was updated");
        	   		
    	    		session.add("newAction",new Gson().toJsonTree("DOB was updated"));
		     		
    		     	gridpane.getChildren().clear();
    		   		gridpane.add(updated, 0, 0);
    		   		gridpane.add(home, 0, 3);
        	   		gridpane.add(exit, 0, 4);
        	   		});
    		     	
    		     	gridpane.add(getDOB, 0, 0);
    	    		gridpane.add(DOB, 0, 1);
    	    		gridpane.add(current, 1, 0);
    	    		gridpane.add(currentDOB, 1, 1);
    	    		gridpane.add(submit, 0, 2);
    	    		gridpane.add(home, 0, 3);
    	    		gridpane.add(exit, 0, 4);
    	    		}
    	    		 	
    	    	else if(animalAtt.getValue() == "On Loan") {
    	    		gridpane.getChildren().clear();
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    	    		
    	    		Label currentLoan = new Label(choice.getLoan());
    	    		Label currentStatus = new Label("Current Loan status");
    	    		
    	    		RadioButton onLoan = new RadioButton("Yes");
    	    		RadioButton notLoan = new RadioButton("No");
    	    		Label updateLoan = new Label("Enter animal's new loan status");
    	    		
    	    		session.add("newAction",new Gson().toJsonTree("edit Loan status"));
                    
                     
    	    		onLoan.setOnAction(Event1 -> {
    	    			String updateLoanSt = null;
    	    			
        	   			session.add("newAction",new Gson().toJsonTree("Yes"));
                         
            	   		ObservableList<String> newInfo = FXCollections.observableArrayList();	
            	   		
            	   		for(int n = 0; n < choice.getHowManyA(); n++) {
            	   			String replace = choice.getLoan();
            	           
            	   			if(animals[n].contains(choice.getAnimal())) {
            	   				updateLoanSt = animals[n].replaceAll(replace, "Yes");
            	   			}
            	   			else {
            	   				updateLoanSt = animals[n];
            	   			}
            	   			newInfo.add(updateLoanSt);
            	   			
            	   			String addNSpecies = newInfo.toString();
            	   			String[] splitUp4 = addNSpecies.split(", ");
            	   			String eachAnimal = " ";
            	   			FileWriter writeAInfo = null;
            	   			animalData.delete();
            	   			try {
    							animalData.createNewFile();
    						} catch (IOException e1) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);;
    						}
            	   			
            	   			for (int add = 0; add < splitUp4.length;add++) {
            	   				eachAnimal = splitUp4[add].replaceAll("\\p{P}","");	
            	   			
            	   			try {
    							writeAInfo = new FileWriter("animals.txt", true);
    						} catch (FileNotFoundException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						} catch (IOException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be found to at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						}			  
        	   			
        	   			try {
    						writeAInfo.write(eachAnimal + "\n");
    						writeAInfo.close();
    					} catch (IOException e) {
    						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
    					}
            	   			}
            	   			
            	   		}
            	
            	   		
            	   		Label updated = new Label("Loan status was updated");
            	   		
            	   		session.add("newAction",new Gson().toJsonTree("Loan status was updated"));
                         
                       
        		     	gridpane.getChildren().clear();
        		   		gridpane.add(updated, 0, 0);
        		   		gridpane.add(home, 0, 3);
            	   		gridpane.add(exit, 0, 4);
    	    		});
    	    		
    	    		notLoan.setOnAction(Event2 -> {
                        String updateLoanSt = null;
                        
        	   			session.add("newAction",new Gson().toJsonTree("No"));
                       
            	   		
            	   		ObservableList<String> newInfo = FXCollections.observableArrayList();	
            	   		
            	   		for(int n = 0; n < choice.getHowManyA(); n++) {
            	   			String replace = choice.getLoan();
            	           
            	   			if(animals[n].contains(choice.getAnimal())) {
            	   				updateLoanSt = animals[n].replaceAll(replace, "No");
            	   			}
            	   			else {
            	   				updateLoanSt = animals[n];
            	   			}
            	   			newInfo.add(updateLoanSt);
            	   			
            	   			String addNSpecies = newInfo.toString();
            	   			String[] splitUp4 = addNSpecies.split(", ");
            	   			String eachAnimal = " ";
            	   			FileWriter writeAInfo = null;
            	   			animalData.delete();
            	   			try {
    							animalData.createNewFile();
    						} catch (IOException e1) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						}
            	   			
            	   			for (int add = 0; add < splitUp4.length;add++) {
            	   				eachAnimal = splitUp4[add].replaceAll("\\p{P}","");	
            	   			
            	   			try {
    							writeAInfo = new FileWriter("animals.txt", true);
    						} catch (FileNotFoundException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						} catch (IOException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						}			  
        	   			
        	   			try {
    						writeAInfo.write(eachAnimal + "\n");
    						writeAInfo.close();
    					} catch (IOException e) {
    						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
    					}
            	   			}
            	   			
            	   		}
                        Label updated = new Label("Loan status was updated");
                       
            	   		session.add("newAction",new Gson().toJsonTree("Loan status was updated"));
                        
        		     	gridpane.getChildren().clear();
        		   		gridpane.add(updated, 0, 0);
        		   		gridpane.add(home, 0, 3);
            	   		gridpane.add(exit, 0, 4);
    	    		});
    	    		
    	    		gridpane.add(currentStatus, 0, 0);
    	    		gridpane.add(currentLoan, 0, 1);
    	    		gridpane.add(updateLoan, 0, 2);
    	    		gridpane.add(onLoan, 0, 3);
    	    		gridpane.add(notLoan, 0, 4);
    	    		gridpane.add(home, 1, 0);
    	    		gridpane.add(exit, 1, 1);
    
    	    	}
    	    	
    	    	else if(animalAtt.getValue() == "Food") {
    	    		gridpane.getChildren().clear();
    	    		
    	    		session.add("newAction",new Gson().toJsonTree("edit food"));
                    
    	    		
    	    		Label currentFoods = new Label("The animal's current foods are");
    	    		Label animalsFoods = new Label(choice.getCurrentFood());
    	    		Label updateFood = new Label("Which food do you want to update");
    	    		Label enterFood = new Label("Enter replacement food");
    	    		TextField newFood = new TextField();
    	    		
    	    		ComboBox<String> foods = new ComboBox<>();
    	    	    for(int f = 0; f < 5; f++) {
    	    				String allFoods = choice.getCurrentFood();
    	    				String [] lineByLine = allFoods.split(" ");
    	    				foods.getItems().add(lineByLine[f]);
    	    			}
    	    	    
    
    	    		Button home = new Button("Home");
    		     	Button exit = new Button("Exit");
    		     	Button replace = new Button("Replace food");
    
    		    	home.setOnAction(new home());
    		     	exit.setOnAction(new exit());
    		     	replace.setOnAction(EventReplace -> {
    		     		String updatedFood = null;
            	   		
            	   		ObservableList<String> newInfo = FXCollections.observableArrayList();
            	   
            	   		session.add("newAction",new Gson().toJsonTree( newFood.getText().toString()));
                        
            	   		
            	   		for(int n = 0; n < choice.getHowManyA(); n++) {
            	   			String replaceFood = foods.getValue().toString();
            	   			String capFood = replaceFood.substring(0, 1).toUpperCase() + replaceFood.substring(1);
            	           
            	   			if(animals[n].contains(choice.getAnimal())) {
            	   				
            	   				updatedFood = animals[n].replaceAll(capFood, newFood.getText().toString());
            	   				
            	   			}
            	   			else {
            	   				updatedFood = animals[n];
            	   			}
            	   			newInfo.add(updatedFood);
            	   			
            	   			String addNSpecies = newInfo.toString();
            	   			String[] splitUp5 = addNSpecies.split(", ");
            	   			String eachAnimal = " ";
            	   			FileWriter writeAInfo = null;
            	   			animalData.delete();
            	   			try {
    							animalData.createNewFile();
    						} catch (IOException e1) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be created at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						}
            	   			
            	   			for (int add = 0; add < splitUp5.length;add++) {
            	   				eachAnimal = splitUp5[add].replaceAll("\\p{P}","");	
            	   			
            	   			try {
    							writeAInfo = new FileWriter("animals.txt", true);
    						} catch (FileNotFoundException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be found at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						} catch (IOException e) {
    							JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
    			 	 		    System.exit(0);
    						}			  
        	   			
        	   			try {
    						writeAInfo.write(eachAnimal + "\n");
    						writeAInfo.close();
    					} catch (IOException e) {
    						JOptionPane.showMessageDialog(null,"Animal.txt could not be written to at this time.\nProgram will exit and please try again.");
			 	 		    System.exit(0);
    					}
            	   			}
            	   			
            	   		}
            	   		
            	   		Label updated = new Label("Food was updated");
            	   		
            	   		session.add("newAction",new Gson().toJsonTree("Food was updated"));
                               		     	gridpane.getChildren().clear();
        		   		gridpane.add(updated, 0, 0);
        		   		gridpane.add(home, 0, 3);
            	   		gridpane.add(exit, 0, 4);
    		     		
    		     	});
    	    		
    	    		
    	    		gridpane.add(currentFoods, 0, 0);
    	    		gridpane.add(animalsFoods, 0, 1);
    	    		gridpane.add(enterFood, 1, 0);
    	    		gridpane.add(newFood, 1, 1);
    	    		gridpane.add(updateFood, 1, 2);
    	    		gridpane.add(foods, 1, 3);
    	    		gridpane.add(replace, 1, 4);
    	    		gridpane.add(home, 2, 0);
    	    		gridpane.add(exit, 2, 1);
    	    		
    	    	}
    	    	
    	    });
    		
    		Button home = new Button("Home");
	     	Button exit = new Button("Exit");
	    
	     	
	    	home.setOnAction(new home());
	     	exit.setOnAction(new exit());
	     	
    		gridpane.getChildren().clear();
    		gridpane.add(whatAtt, 0, 0);
    		gridpane.add(edit, 1, 1);
    		gridpane.add(animalAtt, 1, 0);
    		gridpane.add(home, 0, 1);
    		gridpane.add(exit, 0, 2);
    		gridpane.add(selected, 0, 4);
    		
    		
    	}
    }

    //Request user input of animal
    class addNewAnimal implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
            session.add("action",new Gson().toJsonTree("Add animal"));
           
    		String capitalName = allAnimalInfo.getName().getText();	
    		String[] arr = capitalName.split(" ");
    		String animalName = " ";
    		
    		for (int a = 0; a < arr.length; a++) {
    			 if (arr[a].matches(".*\\d.*") || arr[a].isEmpty()){
    	  		       JOptionPane.showMessageDialog(null,"Animal name can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
    	  		       System.exit(0);
    	             } 
    			
    			char capLetter = Character.toUpperCase(arr[a].charAt(0));
    			animalName += " " + capLetter + arr[a].substring(1);
    		}
    		animalName = animalName.trim();
    		
    		File getOldGif = new File("images/newImage.gif");
    		File getOldJpg = new File("images/newImage.jpg");
    		File getOldPng = new File("images/newImage.png");
    		
    		File getNewGif = new File("images/"+animalName+".gif");
    		File getNewJpg = new File("images/"+animalName+".jpg");
    		File getNewPng = new File("images/"+animalName+".png");
    		
    		session.add("animalName",new Gson().toJsonTree(animalName));
    		 	
    		String capitalSpecies = allAnimalInfo.getSpecies().getText();
    		String[] arr1 = capitalSpecies.split(" ");
    		String animalSpecies = " ";
    		
    		for (int a = 0; a < arr1.length; a++) {
    			if (arr1[a].matches(".*\\d.*") || arr1[a].isEmpty()){
 	  		       JOptionPane.showMessageDialog(null,"Animal Species can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
 	  		       System.exit(0);
 	             } 
    			char capLetter = Character.toUpperCase(arr1[a].charAt(0));
    			animalSpecies += " " + capLetter + arr1[a].substring(1);
    		}
    		
    		animalSpecies = animalSpecies.trim();
    		
    		session.add("animalSpecies",new Gson().toJsonTree(animalSpecies));	
            
    		String capitalLoan = allAnimalInfo.getLoan().getText();
    		String[] arr2 = capitalLoan.split(" ");
    		String animalLoan = " ";
    		
    		for (int a = 0; a < arr2.length; a++) {
    			if (arr2[a].matches(".*\\d.*") || arr2[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Loan stats can only contain letters and must be Yes or No.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			char capLetter = Character.toUpperCase(arr2[a].charAt(0));
    			animalLoan += " " + capLetter + arr2[a].substring(1);
    		}
    		animalLoan = animalLoan.trim();
    		
    		session.add("animalALoan",new Gson().toJsonTree(animalLoan));
           
    		String capitalFood1 = allAnimalInfo.getFood1().getText();
    		String[] arr3 = capitalFood1.split(" ");
    		String animalFood1 = " ";
    		
    		for (int a = 0; a < arr3.length; a++) {
    			if (arr3[a].matches(".*\\d.*") || arr3[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Food1 can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			
    			char capLetter = Character.toUpperCase(arr3[a].charAt(0));
    			animalFood1 += " " + capLetter + arr3[a].substring(1);
    		}
    		animalFood1 = animalFood1.trim();
    	
    		session.add("animalFood1",new Gson().toJsonTree(animalFood1));
          
    		String capitalFood2 = allAnimalInfo.getFood2().getText();
    		String[] arr4 = capitalFood2.split(" ");
    		String animalFood2 = " ";
    		
    		for (int a = 0; a < arr4.length; a++) {
    			if (arr4[a].matches(".*\\d.*") || arr4[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Food2 can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			
    			char capLetter = Character.toUpperCase(arr4[a].charAt(0));
    			animalFood2 += " " + capLetter + arr4[a].substring(1);
    		}
    		animalFood2 = animalFood2.trim();

    		session.add("animalFood2",new Gson().toJsonTree(animalFood2));
    		
    		String capitalFood3 = allAnimalInfo.getFood3().getText();
    		String[] arr5 = capitalFood3.split(" ");
    		String animalFood3 = " ";
    		
    		for (int a = 0; a < arr5.length; a++) {
    			char capLetter = Character.toUpperCase(arr5[a].charAt(0));
    			animalFood3 += " " + capLetter + arr5[a].substring(1);
    		}
    		animalFood3 = animalFood3.trim();
    		
    		session.add("animalFood3",new Gson().toJsonTree(animalFood3));
            
    		String capitalFood4 = allAnimalInfo.getFood4().getText();
    		String[] arr6 = capitalFood4.split(" ");
    		String animalFood4 = " ";
    		
    		for (int a = 0; a < arr6.length; a++) {
    			if (arr6[a].matches(".*\\d.*") || arr6[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Food3 can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			
    			char capLetter = Character.toUpperCase(arr6[a].charAt(0));
    			animalFood4 += " " + capLetter + arr6[a].substring(1);
    		}
    		animalFood4 = animalFood4.trim();
    		

    		session.add("animalFood4",new Gson().toJsonTree(animalFood4));
            
    		String capitalFood5 = allAnimalInfo.getFood5().getText();
    		String[] arr7 = capitalFood5.split(" ");
    		String animalFood5 = " ";
    		
    		for (int a = 0; a < arr7.length; a++) {
    			if (arr7[a].matches(".*\\d.*") || arr7[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Food4 can only contain letters.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			
    			char capLetter = Character.toUpperCase(arr7[a].charAt(0));
    			animalFood5 += " " + capLetter + arr7[a].substring(1);
    		}
    		animalFood5 = animalFood5.trim();

    	    session.add("animalFood5",new Gson().toJsonTree(animalFood5));
      
    		String capitalGender = allAnimalInfo.getGender().getText();
    		String[] arr8 = capitalGender.split(" ");
    		String animalGender = " ";
    		
    		for (int a = 0; a < arr8.length; a++) {
    			if (arr8[a].matches(".*\\d.*") || arr8[a].isEmpty()){
  	  		       JOptionPane.showMessageDialog(null,"Animal Gender can only contain letters and must be Male or Female.\nNo numbers or blank entries will be accepted.\nTry again");
  	  		       System.exit(0);
  	             } 
    			
    			char capLetter = Character.toUpperCase(arr8[a].charAt(0));
    			animalGender += " " + capLetter + arr8[a].substring(1);
    		}
    		animalGender = animalGender.trim();

    		session.add("animalGender",new Gson().toJsonTree(animalGender));
    		
    		Button terrain = new Button("Add Terrain");
     	    terrain.setOnAction(new getTerrain());
            
    		
    		 for(int n = 0; n < choice.getHowManyA(); n++) {
    		  if(allAnimalInfo.getInfo()[n].contains(animalName)) {
     				if(allAnimalInfo.getInfo()[n].contains(animalSpecies)) {
     					Label animalExist = new Label(allAnimalInfo.getInfo()[n]);
     					Label existMessage = new Label("Animal Already exist.");
     					
     					gridpane.getChildren().clear();
     					gridpane.add(existMessage, 0, 0);
     					gridpane.add(animalExist, 0, 1);
     				}
        		}
     			else {
     				String newAnimal = animalName + " " + animalSpecies + " " + animalGender + " " + allAnimalInfo.getBOD()
     						+ " " + animalLoan + " " + animalFood1 + " "  + animalFood2 + " " + animalFood3 + " " +
     						animalFood4 + " " + animalFood5;
     				choice.setNewAnimal(newAnimal);
     			   
     			   Label reviewEntredInfo = new Label("Please review your entry and when done hit the add button.");
     			   Label newAnimalInfo = new Label(newAnimal);
     			   Button add = new Button("Add");
     			  //Eventhandle for buttons
     			   add.setOnAction(new addAnimalFile());
     			   
     			  //Get image object from the link
    	    	  Image image = new Image("file:images/newImage.jpg");
    	    		
    	    		//Create an Image object.
    	    		ImageView imageView = new ImageView(image);
    	    		imageView.setFitWidth(300);
    	    		imageView.setFitHeight(300);
    	    		
     			   
     			    Button home = new Button("Home");
     		    	Button exit = new Button("Exit");
     		    	
     		    	VBox addInfo = new VBox(reviewEntredInfo, newAnimalInfo, imageView);
     		    	
     		    	HBox buttonsBox = new HBox(2);
     		 	    buttonsBox.getChildren().addAll(terrain, home, exit);
     		    	
     		    	home.setOnAction(new home());
     		   	    exit.setOnAction(new exit());
     			    
     			   gridpane.getChildren().clear();
				   gridpane.add(buttonsBox, 0, 1);
				   gridpane.add(addInfo, 0, 0);
     			}
     		}
    
    	}
    }
    
    //Request user input of food
    class addNewFood implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
    	
            session.add("sessionAction",new Gson().toJsonTree("Add Food"));
            
    		String capitalFood = allFoodInfo.getFood().getText();
    		String[] arr = capitalFood.split(" ");
    		String newFood = " ";
    		
    		for (int a = 0; a < arr.length; a++) {
    			if (arr[a].matches(".*\\d.*") || arr[a].isEmpty()){
   	  		       JOptionPane.showMessageDialog(null,"Food name can only be letters.\nNo numbers or blank entries will be accepted.\nTry again");
   	  		       System.exit(0);
   	             } 
    			
    			char capLetter = Character.toUpperCase(arr[a].charAt(0));
    			newFood += " " + capLetter + arr[a].substring(1);
    		}
    		newFood = newFood.trim();
    		
    		session.add("newFood",new Gson().toJsonTree(newFood));
            
    		String capitalDescription = allFoodInfo.getDescription().getText();
    		String[] arr1 = capitalDescription.split(" ");
    		String description = " ";
    		
    		for (int a = 0; a < arr1.length; a++) {
    			if (arr1[a].matches(".*\\d.*") || arr1[a].isEmpty()){
    	  		       JOptionPane.showMessageDialog(null,"Food description can only be letters.\nNo numbers or blank entries will be accepted.\nTry again");
    	  		       System.exit(0);
    	             } 
    			
    			char capLetter = Character.toUpperCase(arr1[a].charAt(0));
    			description += " " + capLetter + arr1[a].substring(1);
    		}
    		
    		description = description.trim();
    		
    		session.add("newDescription",new Gson().toJsonTree(description));

    		String capitalVendor = allFoodInfo.getVendor().getText();
    		String[] arr2 = capitalVendor.split(" ");
    		String vendor = " ";
    		
    		for (int a = 0; a < arr2.length; a++) {
    			if (arr2[a].matches(".*\\d.*") || arr2[a].isEmpty()){
    	  		       JOptionPane.showMessageDialog(null,"Food name can only be letters.\nNo numbers or blank entries will be accepted.\nTry again");
    	  		       System.exit(0);
    	             } 
    			
    			char capLetter = Character.toUpperCase(arr2[a].charAt(0));
    			vendor += " " + capLetter + arr2[a].substring(1);
    		}
    		vendor = vendor.trim();
    		
    		session.add("vendorIndo",new Gson().toJsonTree(vendor));
           
    		 for(int n = 0; n < choice.getHowManyA(); n++) {
    		  if(allFoodInfo.getInfo()[n].contains(newFood)) {
     					Label foodExist = new Label(allFoodInfo.getInfo()[n]);
     					Label existMessage = new Label("Food Already exist.");
     					
     					gridpane.getChildren().clear();
     					gridpane.add(existMessage, 0, 0);
     					gridpane.add(foodExist, 0, 1);
     				}
     			else {
     				String addNewFood = newFood + " " + description + " " + vendor + " " + allFoodInfo.getOnHand().getText() + " " + allFoodInfo.getUnits().getText();
     				choice.setNewFood(addNewFood);
     				
     				Label reviewEntredInfo = new Label("Please review your entry and when done hit the add button.");
     				Label newFoodInfo = new Label(addNewFood);
     				
     				Button add = new Button("Add");
       			  //Eventhandle for buttons
       			   add.setOnAction(new addFoodFile());
       			   
       			   Button home = new Button("Home");
       			   Button exit = new Button("Exit");
       	    	
       			   HBox buttonsBox = new HBox(2);
       			   buttonsBox.getChildren().addAll(home, exit);
       	    	
       			   home.setOnAction(new home());
       			   exit.setOnAction(new exit());
       			    
       			   gridpane.getChildren().clear();
       			   gridpane.add(reviewEntredInfo, 0, 0);
  				   gridpane.add(newFoodInfo, 0, 1);
  				   gridpane.add(add, 0, 3);
  				   gridpane.add(buttonsBox, 0, 4);
     			}
     		}
    
    	}
    }
    
     //If animal does not exist, add to animal.txt file
    class addAnimalFile implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
    	
    		session.add("sessionAction",new Gson().toJsonTree("Animal added"));
            
    		FileWriter fwriter = null;
			try {
				fwriter = new FileWriter("animals.txt", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter outputfile = new PrintWriter(fwriter);
			outputfile.print(choice.getnewA()+"\n");
			outputfile.close();
			
		   Label animalAdd = new Label("Animal has been added");	
		   Button home = new Button("Home");
		 //Eventhandle for buttons
		   home.setOnAction(new home());
			
		   gridpane.getChildren().clear();
		   gridpane.add(animalAdd, 0, 0);
		   gridpane.add(home, 0, 1);
    	}
    }
    
    //If food does not exist, add to food.txt file
    class addFoodFile implements EventHandler<ActionEvent>{
    	public void handle(ActionEvent event) {
    		FileWriter fwriter = null;
			try {
				fwriter = new FileWriter("food.txt", true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			PrintWriter outputfile = new PrintWriter(fwriter);
			outputfile.print(choice.getNewFood()+"\n");
			outputfile.close();
			
		   Label foodAdd = new Label("Food has been added");	
		   Button home = new Button("Home");
		   //Eventhandle for buttons
		   home.setOnAction(new home());
			
		   gridpane.getChildren().clear();
		   gridpane.add(foodAdd, 0, 0);
		   gridpane.add(home, 0, 1);
    	}
    }
    
    //exit program
    class exit implements EventHandler<ActionEvent>{
    	 public void handle(ActionEvent event) {
    	 
    		 session.add("sessionAction",new Gson().toJsonTree("exit"));
    		 
    		 File sessionFile = new File("sessions.json");
    		 
    		 if(sessionFile.length() == 0) {
    			 try(FileWriter collectedSD = new FileWriter("sessions.json", true)){
     				collectedSD.write("["+session.toString()+",");
     			} catch (IOException e) {
     				JOptionPane.showMessageDialog(null,"Under able to write session data");
	 	 		    System.exit(0);
 				}
    		 }
    		 
    		 if(sessionFile.length() != 0) {
    			 try(FileWriter collectedSD = new FileWriter("sessions.json", true)){
      				collectedSD.write(session.toString()+"]");
      			} catch (IOException e) {
      				JOptionPane.showMessageDialog(null,"Under able to write session data");
	 	 		    System.exit(0);
  				}
    		 }
    		 
    				
    		 gridpane.getChildren().clear();
    		 System.exit(0);
    	 }
     }
    
    //Go home
    class home implements EventHandler<ActionEvent> {
    	@Override
    	public void handle(ActionEvent event) {
    		session.add("sessionAction",new Gson().toJsonTree("home"));
    		
             File sessionFile = new File("sessions.json");
    		 
    		 if(sessionFile.length() == 0) {
    			 try(FileWriter collectedSD = new FileWriter("sessions.json", true)){
      				collectedSD.write("["+session.toString()+",");
      			} catch (IOException e) {
      				JOptionPane.showMessageDialog(null,"Under able to write session data");
	 	 		    System.exit(0);
  				}
    		 }
    		 
    		 if(sessionFile.length() != 0) {
    			 try(FileWriter collectedSD = new FileWriter("sessions.json", true)){
      				collectedSD.write(session.toString()+",");

      			} catch (IOException e) {
      				JOptionPane.showMessageDialog(null,"Under able to write session data");
	 	 		    System.exit(0);
  				}
    		 }
    		 
    	 
    		gridpane.getChildren().clear();
    		try {
				start(null);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Could not go home. Please exit app and try again.");
 	 		    System.exit(0);
			}
    	}
    }
  }
