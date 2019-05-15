package harjoitustyo_test;

import java.sql.Connection;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PalveluidenHallinta {
	BorderPane paneeliPalvelut;
	GridPane kentat, napit;
	Stage window;
	Scene paasivu;
	Label paaotsikko;
	Connection conn;
	PalveluidenHallintaKanta palveluolio;

	Button Btakaisin;
	Label LtoiID , LpalID, Lpalnimi, LpalTyyppi, LpalKuvaus,LpalHinta, LpalAlv, LpalVirhe, LpalVirhe2;
	TextField TtoiID , TpalID, Tpalnimi, TpalTyyppi,TpalHinta, TpalAlv;
	TextArea TpalKuvaus;
	Button palHae, palLisaa, palPoista, palMuokkaa, palTyhjenna;

	int palveluID = 0;

	public PalveluidenHallinta(BorderPane paneeliPalvelut, Stage window, Scene paasivu, Connection conn){
		this.paneeliPalvelut = paneeliPalvelut;
		this.window = window;
		this.paasivu = paasivu;
		this.conn = conn;

		palveluolio = new PalveluidenHallintaKanta(conn);

		paaotsikko = new Label("Palveluiden hallinta");
		paaotsikko.setFont(new Font("Arial", 20));
		paaotsikko.setStyle("-fx-padding: 10 0 0 30;");

		kentat = new GridPane();
		kentat.setPadding(new Insets(20,30,10,50));

		napit = new GridPane();
		napit.setPrefWidth(125);
		napit.setPadding(new Insets(30,0,0,0));
		napit.setVgap(5.0);
		paneeliPalvelut.setTop(paaotsikko);
		paneeliPalvelut.setLeft(kentat);
		paneeliPalvelut.setCenter(napit);

		tekstikenttienluonti();
		nappienLuonti();
		kenttienNappienAsettaminen();

		Btakaisin = new Button("Takaisin");

		paneeliPalvelut.setBottom(Btakaisin);
		paneeliPalvelut.setPadding(new Insets(0,0,10,10));

		Btakaisin.setOnAction(e -> window.setScene(paasivu));
		palLisaa.setOnAction(e -> lisaaPalvelu());
		palMuokkaa.setOnAction(e -> muokkaaPalvelua());
		palHae.setOnAction(e -> haePalvelu());
		palTyhjenna.setOnAction(e -> tyhjennaPalvelu());
		palPoista.setOnAction(e -> poistaPalvelu());
	}

	public void tekstikenttienluonti(){	
		LpalID = new Label("Palvelun tunnus *");
		LtoiID = new Label("Toimipisteen tunnus *");
		Lpalnimi = new Label("Nimi *");
		LpalTyyppi = new Label("Tyyppi *");
		LpalKuvaus = new Label("Kuvaus");
		LpalHinta = new Label("Hinta *");
		LpalAlv = new Label("Alv *");

		LpalVirhe = new Label("");
		LpalVirhe2 = new Label("");

	}
	public void nappienLuonti(){
		palHae = new Button("Hae");
		palLisaa = new Button("Lisää");
		palPoista = new Button("Poista");
		palMuokkaa = new Button("Muokkaa");
		palTyhjenna = new Button("Hae tai lisää uusi");

		palHae.setMinWidth(napit.getPrefWidth());
		palLisaa.setMinWidth(napit.getPrefWidth());
		palPoista.setMinWidth(napit.getPrefWidth());
		palMuokkaa.setMinWidth(napit.getPrefWidth());
		palTyhjenna.setMinWidth(napit.getPrefWidth());
	}

	public void kenttienNappienAsettaminen(){

		kentat.add(LpalID, 0, 1);
		kentat.add(TpalID = new TextField(), 0, 2);
		kentat.add(LtoiID, 0, 3);
		kentat.add(TtoiID = new TextField(), 0, 4);
		kentat.add(Lpalnimi, 0, 5);
		kentat.add(Tpalnimi = new TextField(), 0, 6);
		kentat.add(LpalTyyppi, 0, 7);
		kentat.add(TpalTyyppi = new TextField(), 0, 8);
		kentat.add(LpalKuvaus, 0, 9);
		kentat.add(TpalKuvaus = new TextArea(), 0, 10);
		kentat.add(LpalHinta, 0, 11);
		kentat.add(TpalHinta = new TextField(), 0, 12);
		kentat.add(LpalAlv, 0, 13);
		kentat.add(TpalAlv = new TextField(), 0, 14);		

		TpalKuvaus.setPrefWidth(175);
		TpalKuvaus.setWrapText(true);
		
		napit.add(palHae, 0, 1);
		napit.add(palLisaa,0,2);
		napit.add(palTyhjenna, 0, 3);
		napit.add(palMuokkaa, 0, 4);
		napit.add(palPoista, 0, 5);

		napit.add(LpalVirhe, 0, 6);
		napit.add(LpalVirhe2, 0, 7);

		palMuokkaa.setVisible(false);
		palPoista.setVisible(false);

	}
	public void lisaaPalvelu(){

		palveluID = 0;
		LpalVirhe2.setText("");


		try{
			//Tarkastetaan että nimi ja tunnus kentät täytetty
			if(TtoiID.getText().isEmpty() || Tpalnimi.getText().isEmpty() || TpalID.getText().isEmpty() 
					|| TpalHinta.getText().isEmpty() || TpalTyyppi.getText().isEmpty() || TpalAlv.getText().isEmpty()){
				LpalVirhe.setText("Tähdellä merkatut kentät ");
				LpalVirhe2.setText("ovat pakollisia");
			}
			//tarkastetaan ettei id ole 0
			else if (Integer.parseInt(TpalID.getText())==0){
				LpalVirhe.setText("Id ei voi olla 0");
				//suoritetaan lisäys	
			}else{		
				
				palveluID = Integer.parseInt(TpalID.getText());
				int toiimiID = Integer.parseInt(TtoiID.getText());
				double hintaa = Double.parseDouble(TpalHinta.getText());
				
				
				int tulos = palveluolio.lisaaPalvelu(palveluID,toiimiID, Tpalnimi.getText(),Integer.parseInt(TpalTyyppi.getText())
						, TpalKuvaus.getText(),hintaa, Double.parseDouble(TpalAlv.getText()));
				System.out.print(tulos);
				//jos lisäys onnistui
				if(tulos ==1){
					LpalVirhe.setText("Lisäys onnistui");
					palMuokkaa.setVisible(true);
					palPoista.setVisible(true);
					palHae.setVisible(false);
					palLisaa.setVisible(false);
					TpalID.setDisable(true);

				}
				//tuli jokin tietokanta virhe, uskoisin samasta id stä
				else if(tulos==3){
					LpalVirhe.setText("Palvelu id on käytössä tai");
					LpalVirhe2.setText("toimipistettä ei löydy");
				}
				//puuttuvia arvoja
				else{
					LpalVirhe.setText("Täytä tarvittavat kentät");
				}
			}
			//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LpalVirhe.setText("Tunnukset,tyyppi ja");
			LpalVirhe2.setText("hinta täytyy olla numeroita");
		}


	}//lisää palvelu
	
	public void muokkaaPalvelua(){		
		try{
			//Tarkastetaan että kaikki kenttät on täytetty
			if(TtoiID.getText().isEmpty() || Tpalnimi.getText().isEmpty() || TpalID.getText().isEmpty() 
					|| TpalHinta.getText().isEmpty() || TpalTyyppi.getText().isEmpty() || TpalAlv.getText().isEmpty()){
				LpalVirhe.setText("Kaikki kentät on pakollisia");
			}else{		

				int toiimiID = Integer.parseInt(TtoiID.getText());
				double hintaa = Double.parseDouble(TpalHinta.getText());
				int palIDD = Integer.parseInt(TpalID.getText());
				int tulos = palveluolio.muokkaaPalvelua(palIDD,toiimiID, Tpalnimi.getText(),Integer.parseInt(TpalTyyppi.getText())
						, TpalKuvaus.getText(),hintaa, Double.parseDouble(TpalAlv.getText()));
				
				//jos Muokkaus onnistui
				if(tulos ==1){
					LpalVirhe.setText("Muokkaus onnistui");

				}
				//tuli jokin tietokanta virhe, uskoisin samasta id stä
				else if(tulos==3){
					LpalVirhe.setText("toimipistettä ei löydy");
				}
				//puuttuvia arvoja
				else{
					LpalVirhe.setText("Täytä tarvittavat kentät");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LpalVirhe.setText("Tunnukset ja hinta täytyy");
			LpalVirhe2.setText("olla numeroita");
		}

	}
	
	public void poistaPalvelu(){
		try{
		int palIDD = Integer.parseInt(TpalID.getText());
		//Tarkastetaan että nimi kenttä täytetty
		if(palIDD ==0){
			LpalVirhe.setText("ID ei voi olla 0");
		}else{						
			int tulos = palveluolio.poistaPalvelu(palIDD);
			//jos Poisto onnistui
			if(tulos ==1){
				tyhjennaPalvelu();
				LpalVirhe.setText("Poisto onnistui");
				
			}
			//tuli jokin tietokanta virhe
			else if(tulos==3){
				LpalVirhe.setText("Tietokanta virhe");
			}
			//puuttuvia arvoja
			else{
				LpalVirhe.setText("Poisto ei onnistunut");
			}
		}
	}catch (NumberFormatException exception){
		LpalVirhe.setText("Tunnukset ja hinta täytyy");
		LpalVirhe.setText("olla numeroita");
	}
	//Jos id muu kuin numero


}
	
	
	//haetaan palvelu
	public void haePalvelu(){		
		ArrayList<String> tulokset = new ArrayList<String>();
		try{
			//Tarkastetaan että nimi tai tunnus kenttä täytetty
			if(TpalID.getText().isEmpty() && Tpalnimi.getText().isEmpty()){
				LpalVirhe.setText("Tunnus tai nimi pakollinen");
			}
			else{
				if(TpalID.getText().isEmpty()){
					palveluID = 0;
					tulokset = palveluolio.haePalvelu(palveluID, Tpalnimi.getText());						
					
				}else{
				palveluID = Integer.parseInt(TpalID.getText());
				tulokset = palveluolio.haePalvelu(palveluID, Tpalnimi.getText());
				}
				//jos Haku onnistui
				if(tulokset.get(0).equals("1")){					
					LpalVirhe.setText("Haku onnistui");
					
					TpalID.setText(tulokset.get(1));
					TtoiID.setText(tulokset.get(2));
					Tpalnimi.setText(tulokset.get(3));
					TpalTyyppi.setText(tulokset.get(4));
					TpalKuvaus.setText(tulokset.get(5));
					TpalHinta.setText(tulokset.get(6));
					TpalAlv.setText(tulokset.get(7));
					
					palMuokkaa.setVisible(true);
					palPoista.setVisible(true);
					TpalID.setDisable(true);
					palHae.setVisible(false);
					palLisaa.setVisible(false);
				}
				else if(tulokset.get(0).equals("4")){
					LpalVirhe.setText("Löytyi monta tulosta");
					LpalVirhe2.setText("Syötä myös tunnus");
				}
				//tuli jokin tietokanta virhe
				else if(tulokset.get(0).equals("3")){
					LpalVirhe.setText("Tietokanta virhe");
				}
				else if(tulokset.get(0).equals("5")){
					LpalVirhe.setText("Palvelua ei ole olemassa");
				}
				//puuttuvia arvoja
				else{
					LpalVirhe.setText("Haku ei onnistu");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LpalVirhe.setText("Id täytyy olla numero");
		}

	}//hae palvelu
	
	
	public void tyhjennaPalvelu(){
		TpalID.setText("");
		TtoiID.setText("");
		Tpalnimi.setText("");
		TpalTyyppi.setText("");
		TpalKuvaus.setText("");
		TpalHinta.setText("");
		TpalAlv.setText("");
		
		palMuokkaa.setVisible(false);
		palPoista.setVisible(false);
		
		TpalID.setDisable(false);
		
		palLisaa.setVisible(true);
		palHae.setVisible(true);
		
		LpalVirhe.setText("");
		LpalVirhe2.setText("");
		
	}
}
