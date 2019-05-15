package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;



public class Raportit {
	BorderPane paneeliraportit;
	GridPane kaikki;
	ChoiceBox raportti;
	Stage window;
	Scene paasivu;
	Label paaotsikko, Lchoiceselite, Ltulosta, Lonnistuiko, kalenterivihje, kalenterivihje2;
	Button Btakaisin, Btulosta;
	TextField Ttulosta;
	Connection conn;
	DatePicker aloituspaiva, lopetuspaiva;
	int toimipiste_id = 0;
	
	public Raportit(BorderPane paneeliraportit, Stage window, Scene paasivu, Connection conn){
		this.paneeliraportit = paneeliraportit;
		this.window = window;
		this.paasivu = paasivu;
		this.conn = conn;
		
		paaotsikko = new Label("Raportit");
		paaotsikko.setFont(new Font("Arial", 25));
		paaotsikko.setStyle("-fx-padding: 10 0 0 75;");
		
		paneeliraportit.setTop(paaotsikko);
		kaikki = new GridPane();
		kaikki.setPadding(new Insets(10,30,10,75));
		kaikki.setVgap(10.0);
		kaikki.setHgap(10);
		paneeliraportit.setCenter(kaikki);
		kaleterinluonti();
		grafiikka();
		
	
		Btakaisin = new Button("takaisin");
		
		paneeliraportit.setBottom(Btakaisin);
		paneeliraportit.setPadding(new Insets(0,0,10,10));
		Btakaisin.setOnAction(e -> window.setScene(paasivu));

		

		
}
	
	public void grafiikka(){
		Lchoiceselite = new Label("Valitse raportti");
		raportti = new ChoiceBox(FXCollections.observableArrayList(
				"Majoitukset", "Lisäpalvelut"));
		Ltulosta = new Label("Toimipisteen tunnus");
		Ttulosta = new TextField();
		Btulosta = new Button("Tulosta raportti");
		
		
		kaikki.add(Lchoiceselite, 0, 0);
		kaikki.add(raportti, 0, 1);
		kaikki.add(Ltulosta, 0, 2);
		kaikki.add(Ttulosta, 0, 3);
		kaikki.add(Btulosta, 1, 7);
		kaikki.add(kalenterivihje, 0, 4);
		kaikki.add(aloituspaiva, 0, 5);
		kaikki.add(kalenterivihje2, 0, 6);
		kaikki.add(lopetuspaiva, 0, 7);
		kaikki.add(Lonnistuiko, 0, 8);
	}
	
	public void kaleterinluonti(){
		kalenterivihje = new Label("Valitse ajankohdan aloituspäivä");
		//kalenterivihje.setFont(new Font("Arial", 12));
		//kalenterivihje.setStyle("-fx-padding: 10 0 0 0;");
		
		kalenterivihje2 = new Label("Valitse ajankohdan lopetuspäivä");
		//kalenterivihje2.setFont(new Font("Arial", 12));
		//kalenterivihje2.setStyle("-fx-padding: 10 0 0 0;");
		//kalenterivihje.setStyle("-fx-font-weight: bold;");
		
		//Luodaan datepickerit aloitus ja lopetuspäivälle
		aloituspaiva = new DatePicker();
		lopetuspaiva = new DatePicker();
		aloituspaiva.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

					}
				};
			}
		};
		aloituspaiva.setDayCellFactory(dayCellFactory);
		lopetuspaiva.setDayCellFactory(dayCellFactory);
		//Asetetaan lopetus päiväksi huominen päivä
		lopetuspaiva.setValue(aloituspaiva.getValue().plusDays(1));

		//paiva = lopetuspaiva.getValue();


		//keski.getChildren().addAll(kalenterivihje,new Label("Valitse aloituspäivä:"), aloituspaiva, new Label("Valitse lopetuspäivä:"),lopetuspaiva);
	}
	public void luoRaportti(){
		toimipiste_id = 0;
		try{
			toimipiste_id = Integer.parseInt(Ttulosta.getText());
		}catch (NumberFormatException exception){
			Lonnistuiko.setText("Id täytyy olla numero");
		}
		Date alotus = Date.valueOf(aloituspaiva.getValue());
		Date lopetus = Date.valueOf(lopetuspaiva.getValue());
		
		if(raportti.getValue().equals("Majoitukset")){
			majoituksetRaportinLuonti();
		}else{
			palvelutRaportinLuonti();
		}
	}
	
	
	public void majoituksetRaportinLuonti(){
		
	}

	public void palvelutRaportinLuonti(){
		
	}
	
	}
