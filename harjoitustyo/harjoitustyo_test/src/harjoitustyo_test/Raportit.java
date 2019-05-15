package harjoitustyo_test;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Raportit {
	BorderPane paneeliraportit;
	Stage window;
	Scene paasivu;
	Label paaotsikko;
	Button Btakaisin; 
	
	public Raportit(BorderPane paneeliraportit, Stage window, Scene paasivu){
		this.paneeliraportit = paneeliraportit;
		this.window = window;
		this.paasivu = paasivu;
		
		paaotsikko = new Label("Toimipisteidenhallinta");
		paaotsikko.setFont(new Font("Arial", 25));
		paaotsikko.setStyle("-fx-padding: 10 0 0 30;");
		
		paneeliraportit.setTop(paaotsikko);
		
		
		
		Btakaisin = new Button("takaisin");
		
		paneeliraportit.setBottom(Btakaisin);
		
		Btakaisin.setOnAction(e -> window.setScene(paasivu));

}
	}
