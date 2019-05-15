package harjoitustyo_test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class LaskujenHallinta {
	BorderPane paneelivaraus;
	Stage window;
	Scene paasivu;
	Label paaotsikko;
	Button Btakaisin; 
	
	public LaskujenHallinta(BorderPane paneelivaraus, Stage window, Scene paasivu){
		this.paneelivaraus = paneelivaraus;
		this.window = window;
		this.paasivu = paasivu;
		
		paaotsikko = new Label("Laskujen hallinta");
		paaotsikko.setFont(new Font("Arial", 25));
		paaotsikko.setStyle("-fx-padding: 10 0 0 30;");
		
		paneelivaraus.setTop(paaotsikko);
		
		
		
		Btakaisin = new Button("takaisin");
		
		paneelivaraus.setBottom(Btakaisin);
		//paneelimokit.setBottom(Bvaraus);
		Btakaisin.setOnAction(e -> window.setScene(paasivu));

}
	}
