package harjoitustyo_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUI extends Application{
	Button Bmokit, Bvaraus, Bpalveluthal, Basiakkaiden,Blaskut,Braportit;
	VBox paneeliMokit;
	Scene scene;
	Scene scenemokit;
	BorderPane ekapaneeli,paneelivaraus, laskupaneeli, asiakaspaneeli, raportitpaneeli, toimipaneeli, palvelutpaneeli;
	VaraaMokki varaus;
	Scene scenevaraus, scenelasku, sceneasiakas, sceneraportit, scenetoimi, scenepalvelut;
	Stage window;
	ToimipisteidenHallinta toimiolio;
	Raportit raportitolio;
	LaskujenHallinta laskut;
	AsiakasHallinta asiakas;
	PalveluidenHallinta palveluolio;
	Connection conn;
	@Override 
	public void start(Stage stage) {                     

		window = stage;
		kantaanyhdistys();
		//Paneelien luonti
		VBox keskipaneeli = new VBox(10); 
		
		//k�ytt�liittym�n nappuloiden koko
		keskipaneeli.setPrefWidth(190);
		VBox otsikkopaneeli = new VBox(15);
		otsikkopaneeli.setPrefHeight(80);
		ekapaneeli = new BorderPane();
		Label otsikko = new Label("M�kkien varausj�rjestelm�");
		otsikko.setFont(new Font("Arial", 25));
		
		scene = new Scene(ekapaneeli, 400,400);  


		Bmokit = new Button("Toimipisteiden ja m�kkien hallinta");
		Bmokit.setOnAction(e -> toimipisteidenHallinta());
		Bmokit.setMinWidth(keskipaneeli.getPrefWidth());
		Bvaraus = new Button("Majoitusten varaaminen");
		Bvaraus.setOnAction(e -> mokkivaraus());
		Bvaraus.setMinWidth(keskipaneeli.getPrefWidth());
		Bpalveluthal = new Button("Palveluiden hallinta");
		Bpalveluthal.setMinWidth(keskipaneeli.getPrefWidth());
		Bpalveluthal.setOnAction(e -> palvelujenHallinta());
		Basiakkaiden = new Button("Asiakkaiden hallinta");
		Basiakkaiden.setMinWidth(keskipaneeli.getPrefWidth());
		Basiakkaiden.setOnAction(e -> asiakkaathallinta());
		Blaskut = new Button("Laskujen hallinta");
		Blaskut.setMinWidth(keskipaneeli.getPrefWidth());
		Blaskut.setOnAction(e -> laskuhallinta());
		Braportit = new Button("Raportit");
		Braportit.setMinWidth(keskipaneeli.getPrefWidth());
		Braportit.setOnAction(e -> raportit());
		
		//Tietojen lis�ys paneeleihin
		otsikkopaneeli.getChildren().add(otsikko);
		otsikkopaneeli.setAlignment(Pos.CENTER);
		ekapaneeli.setTop(otsikkopaneeli);
		ekapaneeli.setCenter(keskipaneeli);
		keskipaneeli.getChildren().addAll(Bmokit,Bvaraus,Bpalveluthal, Basiakkaiden,Blaskut,Braportit);
		keskipaneeli.setAlignment(Pos.TOP_CENTER);
		


		
		stage.setTitle("M�kki varausj�rjestelm�"); 	  
		stage.setScene(scene); 
		stage.show();  
	}
	public void mokkialoita(){
		paneeliMokit = new VBox(5);
		scenemokit = new Scene(paneeliMokit, 400,400);

		//Mokit olioMok = new Mokit(paneeliMokit, window, scene, scenemokit);
		window.setScene(scenemokit);
	}

	public void mokkivaraus(){
		paneelivaraus = new BorderPane();
		scenevaraus = new Scene(paneelivaraus, 500, 645);
		varaus = new VaraaMokki(paneelivaraus, window, scene, conn);
		window.setScene(scenevaraus);

	}
	public void palvelujenHallinta(){
		palvelutpaneeli = new BorderPane();
		scenepalvelut = new Scene(palvelutpaneeli, 425, 500);
		palveluolio = new PalveluidenHallinta(palvelutpaneeli, window, scene, conn);
		window.setScene(scenepalvelut);
	}
	
	public void laskuhallinta(){
		laskupaneeli = new BorderPane();
		scenelasku = new Scene(laskupaneeli, 500, 500);
		laskut = new LaskujenHallinta(laskupaneeli, window, scene);
		window.setScene(scenelasku);

	}
	public void asiakkaathallinta(){
		asiakaspaneeli = new BorderPane();
		sceneasiakas = new Scene(asiakaspaneeli, 500, 500);
		asiakas = new AsiakasHallinta(asiakaspaneeli, window, scene);
		window.setScene(sceneasiakas);

	}
	
	public void toimipisteidenHallinta(){
		toimipaneeli = new BorderPane();
		scenetoimi = new Scene(toimipaneeli, 425, 645);
		toimiolio = new ToimipisteidenHallinta(toimipaneeli, window, scene, conn);
		window.setScene(scenetoimi);

	}
	public void raportit(){
		raportitpaneeli = new BorderPane();
		sceneraportit= new Scene(raportitpaneeli, 500, 500);
		raportitolio = new Raportit(raportitpaneeli, window, scene);
		window.setScene(sceneraportit);

	}
	
	
	public void kantaanyhdistys(){
		try {
			//STEP 2: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(
					
			///!!!!!!!!!!!!!!!!		
			//MUUTTAKAA SALASANA MIK� TEILL� ON MARIADB SS� SANAN XXXX TILALLE
					"jdbc:mariadb://127.0.0.1/vp", "root", "");
			System.out.println("Connected database successfully...");
			
			
			
			

		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			//finally block used to close resources

		}//end try

	}
	

	public static void main(String args[]){ 
		launch(args); 
	} 

}
