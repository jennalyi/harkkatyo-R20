package harjoitustyo_test;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class AsiakasHallinta {
	
	//Tämän päälle luo lomake eli tekemällä siihen suoraa objekteja tai sitten luomalla siihen uusia paneeleja
	BorderPane paneeliHallinta;
	
	
	Stage window;
	Scene paasivu;
	Label paaotsikko;
	Button Btakaisin; 
	
	public AsiakasHallinta(BorderPane paneeliHallinta, Stage window, Scene paasivu){
		this.paneeliHallinta = paneeliHallinta;
		this.window = window;
		this.paasivu = paasivu;
		
		paaotsikko = new Label("Asiakkaiden hallinta");
		paaotsikko.setFont(new Font("Arial", 25));
		paaotsikko.setStyle("-fx-padding: 10 0 0 30;");
		
		paneeliHallinta.setTop(paaotsikko);
		
		
		
		Btakaisin = new Button("takaisin");
		
		paneeliHallinta.setBottom(Btakaisin);
		
		Btakaisin.setOnAction(e -> window.setScene(paasivu));

}
	}
