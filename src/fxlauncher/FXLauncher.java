package fxlauncher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedHashSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import spellChecker.Paragraph;
import spellChecker.SpellChecker;

/*	File Name: FXLauncher Class
 *	Course Name: Data Structures CST 8130
 *	Lab	Section:CST 8130-301
 *	Student	Name: Amaury Diaz Diaz
 *	Date: Mar-25-2015
 */
/*
 * References:(Rex Woollard (2015) personal communication)
 */
/**
 * It creates a JavaFX application that is going to display the spelling
 * mistakes and the concordance of a text file. When the user hover over
 * one of the words the paragraphs where the word is present appear with
 * the corresponding word highlighted.
 * @author Amaury Diaz Diaz
 *
 */
public class FXLauncher extends Application {
    /**
     * Reference to a SpellChecker object
     */
	private SpellChecker spellCheck;
	/**
	 * Used to display the grammar errors in the JavaFX application.
	 */
	private VBox viewGrammarErrors;
	/**
	 * Used to display the concordance in the JavaFX application.
	 */
	private VBox viewConcordance;
	/**
	 * Used to contain the viewGrammarErrors
	 */
	private ScrollPane scrollGrammarErrors;
	/**
	 * Used to contain the viewConcordance
	 */
    private ScrollPane scrollConcordance;
	
	/**
	 * Used to display the paragraphs were the word selected resides
	 */
	private WebView webView;
	/**
	 * Description and size of the grammar errors.
	 */
	private Text grammarErrorsText;
	/**
	 * Used to display the grammar errors, its description and size
	 */
	private VBox grammarErrorsVBox;
	/**
	 *  Description and size of the concordance.
	 */
	private Text concordanceText;
	/**
	 * Used to display the concordance, its description and size
	 */
	private VBox concordanceVBox;
	/**
	 *  Description of the document.
	 */
	private Text documentText;
	/**
	 * Used to display the paragraphs were the word resides and its description.
	 */
	private VBox documentVBox;
	/**
	 * Used to contain the grammarErrorsVBox, concordanceVBox and documentVBox.
	 */
	private HBox fatherHBox;
	/**
	 * Used to contain the fatherHBox and the application menu
	 */
	private VBox motherVBox;
	
	/**
	 * It creates the stage on which the JavaFX application is going to be launched.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("My Basic Spell checker");

		spellCheck = new SpellChecker();//initialize the spellCheck object
		spellCheck.spellCheckIt();//spell check the document.
		spellCheck.concordance();//find the concordance
		
		//WebView
		webView = new WebView();
		documentText = new Text("Paragraphs");
		documentText.setFont(new Font("Arial",18));
		documentText.setFill(Color.GREEN);
		documentVBox = new VBox(documentText, webView);
		documentVBox.setPrefSize(450.0, 900.0);
		documentVBox.setAlignment(Pos.CENTER);
		
		//Grammar Errors words
		scrollGrammarErrors= new ScrollPane(buildGrammarErrorsVBox());
		grammarErrorsText = new Text("Misspelled Words: "+ spellCheck.getGrammarErrorsSize());
		grammarErrorsText.setFont(new Font("Arial",18));
		grammarErrorsText.setFill(Color.BLUE);
		grammarErrorsVBox = new VBox(grammarErrorsText, scrollGrammarErrors);
		grammarErrorsVBox.setPrefSize(150.0, 900.0);
	
		
		//Concordance
		scrollConcordance= new ScrollPane(buildConcordanceVBox());
		concordanceText = new Text("Concordance Words: " + spellCheck.getConcordanceSize());
		concordanceText.setFont(new Font("Arial",18));
		concordanceText.setFill(Color.RED);
		concordanceVBox = new VBox(concordanceText, scrollConcordance);
		concordanceVBox.setPrefSize(150.0, 900.0);
		
		
		
		//Add the three VBoxes to fatherBOX.
		fatherHBox = new HBox(12.0,grammarErrorsVBox,concordanceVBox, documentVBox);
		fatherHBox.setPadding(new Insets(12.0));
		fatherHBox.setAlignment(Pos.CENTER);
		
		//Initialize menuBar
		MenuBar menuBar = buildMenuBar();
		
		//add the menuBar and the fatherHBox to motherVBox
		motherVBox = new VBox(menuBar, fatherHBox);
	

		Scene scene = new Scene(motherVBox);
		scene.getStylesheets().add("/view/StyleSheet.css");//inport the css file
		primaryStage.getIcons().add(new Image("http://www.keenada.com/students/diaz0064/lab1/img/26356.png"));//set a new icon for the app
		primaryStage.setScene(scene);

		primaryStage.show();
	}//end of start method
    /**
     * This method creates the menu bar that is going to be used in the JavaFX
     * application.
     * @return the menu bar of the JavaFX application
     * @throws FileNotFoundException Exception
     */
	private MenuBar buildMenuBar() throws FileNotFoundException {

		final MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(event -> System.exit(0));// user clicks the item, it exits.

		final MenuItem open = new MenuItem("Open new file");
		open.setMnemonicParsing(true);
		open.setOnAction(event -> {
			try {
				openFile();//called to open the fileChooser
			} catch (Exception e) {
				e.printStackTrace();
			}
			spellCheck.spellCheckIt();//spell check the new document
			spellCheck.concordance();//find the concordance
			
			//Start the new webView
			webView = new WebView();
			documentText = new Text("Paragraphs");
			documentText.setFont(new Font("Arial",18));
			documentText.setFill(Color.GREEN);
			documentVBox = new VBox(documentText, webView);
			documentVBox.setPrefSize(450.0, 900.0);
			documentVBox.setAlignment(Pos.CENTER);
			
			//start the new Grammar Errors
			scrollGrammarErrors= new ScrollPane(buildGrammarErrorsVBox());
			grammarErrorsText = new Text("Misspelled Words: "+ spellCheck.getGrammarErrorsSize());
			grammarErrorsText.setFont(new Font("Arial",18));
			grammarErrorsText.setFill(Color.BLUE);
			grammarErrorsVBox = new VBox(grammarErrorsText, scrollGrammarErrors);
			grammarErrorsVBox.setPrefSize(150.0, 900.0);
			
			//start the new Concordance
			scrollConcordance= new ScrollPane(buildConcordanceVBox());
			concordanceText = new Text("Concordance Words: " + spellCheck.getConcordanceSize());
			concordanceText.setFont(new Font("Arial",18));
			concordanceText.setFill(Color.RED);
			concordanceVBox = new VBox(concordanceText, scrollConcordance);
			concordanceVBox.setPrefSize(150.0, 900.0);

			//Add the three VBoxes to fatherBOX.
			fatherHBox.getChildren().setAll(grammarErrorsVBox,concordanceVBox, documentVBox);
			fatherHBox.setPadding(new Insets(12.0));
			fatherHBox.setAlignment(Pos.CENTER);		
			
		});

		// Creating the File menu
		final Menu fileMenu = new Menu("_File");
		fileMenu.getItems().addAll(open, exit);// add only the MenuItems Exit and Open new File
		fileMenu.setMnemonicParsing(true);

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);// Adding File Menu to the menu bar
		return menuBar;
	}//end of buildMenuBar method
	
    /**
     * It launches a FileChooser to choose a new document to spell check and find its concordance
     * @throws FileNotFoundException Exception
     */
	private void openFile() throws FileNotFoundException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"TXT files (*.txt)", "*.txt");// filter to be used when searching a file. Only.txt allowed
		fileChooser.getExtensionFilters().add(filter);
		File file = fileChooser.showOpenDialog(new Stage());
		if (file != null) {
			try {
				spellCheck = new SpellChecker(file);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}//end of openFile method
	/**
	 * Builds the VBox where the grammar errors appear. The font size of the word is affected by the 
	 * many times it is repeated in the text. If a user hovers over the word he paragraphs where the 
	 * word is present appear with the corresponding word highlighted.
	 * @return A VBox with all the grammar errors.
	 */
	private VBox buildGrammarErrorsVBox() {
		viewGrammarErrors = new VBox();
		for (String word : spellCheck.getGrammarErrorsMap().keySet()) { //for every word in the Misspelled map
			final Text text = new Text(word);
			text.setFont(new Font("Arial", Math.sqrt(spellCheck.getGrammarErrorsMap().get(word).size()) + 12.0));//set font and font size, final: copy of the value is stuffed inside the method for later use.
			viewGrammarErrors.getChildren().add(text);
			String sRegExSearch = "\\W(?i:" + word + ")\\W";
			text.setOnMouseMoved(event -> {
				StringBuilder sbResults = new StringBuilder("<b></b>");
				LinkedHashSet<Paragraph> paragraphs = spellCheck.getGrammarErrorsMap().get(word); //create ref to the value of the word 
				for (Paragraph paragraph : paragraphs) { //for each paragraph in the LinkedHashSet
					String sModified= paragraph.toString().replaceAll(sRegExSearch, "<b style=\" color: blue\">$0</b>");
					sbResults.append(sModified).append("<br><br>");
					webView.getEngine().loadContent(sbResults.toString());
				}
			}); //end setOnMouseEntered()
		}
		return viewGrammarErrors;
	}
	/**
	 * Builds the VBox where the concordance appear. The font size of the word is affected by the 
	 * many times it is repeated in the text. If a user hovers over the word he paragraphs where the 
	 * word is present appear with the corresponding word highlighted.
	 * @return A VBox with all the grammar errors.
	 */
	private VBox buildConcordanceVBox(){
		viewConcordance = new VBox();
		for(String word : spellCheck.getConcordanceMap().keySet()){
			final Text text = new Text(word);
			text.setFont(new Font("Arial", Math.sqrt(spellCheck.getConcordanceMap().get(word).size())+12.0));
			viewConcordance.getChildren().add(text);
			String sRegExSearch = "\\W(?i:"+word+")\\W";
			text.setOnMouseMoved(event -> {
				StringBuilder sbResults = new StringBuilder("<b></b>");
				LinkedHashSet<Paragraph> paragraphs = spellCheck.getConcordanceMap().get(word);	
				for(Paragraph paragraph : paragraphs){
					String sModified= paragraph.toString().replaceAll(sRegExSearch, "<b style=\" color: red\">$0</b>");
					sbResults.append(sModified).append("<br><br>");
					webView.getEngine().loadContent(sbResults.toString());
 				}
			});
		}
		return viewConcordance;
	}
    /**
     * It runs the JavaFX application.
     * 
     */
	public static void main(String[] args) {
		launch(args);
	}//end of main method
}//end of FXLauncher class
