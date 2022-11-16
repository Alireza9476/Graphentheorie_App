package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

import model.*;

public class RootGridPane extends BorderPane {

	private Label textMatrix;
	private Label textKomponente;
	private Label textBerechnungen;
	private GridPane gpCenter, gpRight;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem menuItem;
	private ScrollPane scrollPane;
	
	private static FileChooser fileChooser = new FileChooser();
	private static File file;
	private String defaultPath;
	
	static int [][] matrix;
	int n;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public RootGridPane() throws IOException{
		defaultPath = "D://Spenglergasse//4.Semester//POS_Chwatal//Graphentheorie//Programmieraufgabe//CSV";
		initComponent();
		eventHandler();
		addComponent();
	}

	private void initComponent() throws IOException{
		
		menuBar = new MenuBar();
		menu = new Menu("Datei");
		menuItem = new MenuItem("Open");
		
		gpCenter = new GridPane();
			gpCenter.setPadding(new Insets(10));
			gpCenter.setVgap(10);
			
		gpRight = new GridPane();
			gpRight.setVgap(10);
			gpRight.maxWidth(10);
		
		textMatrix = new Label();
		textKomponente = new Label();
		textBerechnungen = new Label();
			textBerechnungen.isDisable();
		
		scrollPane = new ScrollPane();
			scrollPane.setContent(gpRight);
			scrollPane.setVisible(false);
	}
	
	private void addComponent() {
		menuBar.getMenus().addAll(menu);
		menu.getItems().add(menuItem);
		
		gpCenter.add(textMatrix,0,1);
		gpCenter.add(textKomponente,0,2);
		
		gpRight.add(textBerechnungen, 0, 0);
		
		setCenter(gpCenter);
		setTop(menuBar);
		setRight(scrollPane);
	}
	
	private void eventHandler() {
		menuItem.setOnAction(event -> eventOpen());		
	}
	
	public void eventOpen() {					
		configureFileChooser(fileChooser);
        file = fileChooser.showOpenDialog(MainFx.primaryStage);
        System.out.println(file.toString());
        if (file != null) {
        	printMatrix();
        	try {
				printEigenschaften();
			} catch (IOException e) {
				e.printStackTrace();
			}	
        }
	}
	
    private void configureFileChooser(final FileChooser fileChooser) {     	
        fileChooser.setTitle("View Pictures");            
        fileChooser.setInitialDirectory(new File(defaultPath));
    }
	
	private void printEigenschaften() throws IOException{	
		String str = "";
		
		Matrix.setPath(file.toString());
		DistanzMatrixBerechnung.DistanzBerechnung();
		WegMatrix wegMatrix = new WegMatrix(DistanzMatrixBerechnung.n);
		wegMatrix.wegMatrix(false);
		
		if(!DistanzMatrixBerechnung.exzentrizitaet.isEmpty()){
			str += "Exzentrizität: " + DistanzMatrixBerechnung.exzentrizitaet;
			str += "\n";
			str += "Radius: " + DistanzMatrixBerechnung.radius;
			str += "\n";
			str += "Zentrum: " + DistanzMatrixBerechnung.zentrum;
			str += "\n";
			str += "Durchmesser: " + DistanzMatrixBerechnung.durchmesser;
			str += "\n\n";
		}
		else {
			str += "Graph nicht zusammenhängend\n\n";
		}
		str += "Komponente: " + "\n" + WegMatrix.komponente + "\n";
		str += "Artikulationen: " + "\n" + WegMatrix.artikulationen + "\n";
		str += "Bruecken:" + "\n" + WegMatrix.bruecken;
		
		textKomponente.setText(str);
		scrollPane.setVisible(true);
		textBerechnungen.setText(DistanzMatrixBerechnung.allResults.concat(WegMatrix.allResults));
	}
    
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void printMatrix(){	
		int lines = matrixLength();
		matrix = new int[lines][lines];
			Scanner sc = null;
			try {
				 sc = new Scanner(new BufferedReader(new FileReader(file)));
				 while(sc.hasNextLine()) {
					for (int i = 0; i < matrix.length; i++) {
						String[] line = sc.nextLine().trim().split(";");
					    for (int j = 0; j < line.length; j++) {
					        matrix[i][j] = Integer.parseInt(line[j]);
					           }
					       }
					}
			} catch (FileNotFoundException e) {
				e.getMessage();
			}
			
			String str = "";
			for(int i = 0; i < lines; i++) {
				for(int j = 0;  j < lines; j++) {
					str += matrix[i][j];
				}
				str += "\n";
			}
			textMatrix.setText(str);
		}
	
	
	public static int getN() {
		return matrixLength();
	}
	
	
	private static int matrixLength() {
		int lines = 0;
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			while (reader.readLine() != null)
				lines++;
		}
		catch(FileNotFoundException e) {
			e.getMessage();
		}
		catch(IOException e) {
			e.getMessage();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
		}
		return lines;
	}
	
}