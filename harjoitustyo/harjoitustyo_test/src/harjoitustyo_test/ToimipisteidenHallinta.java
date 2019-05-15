package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class ToimipisteidenHallinta {
	BorderPane paneelitoimi;
	GridPane kentat, napit;
	Stage window;
	Scene paasivu;
	Label paaotsikko;
	Button Btakaisin; 
	
	Label Ltoimipiste, LtoiID, Ltoinimi, Ltoilah, Ltoipostipaikka, Ltoipostnum, Ltoiemail, Ltoipuh;
	TextField TtoiID, Ttoinimi, Ttoilah, Ttoipostipaikka, Ttoipostnum, Ttoiemail, Ttoipuh;
	Button toiHae, toiLisaa, toiPoista, toiMuokkaa, toiTyhjenna;
	
	Label Lmokki, LtoimiMokki, LmokID, Lmoknimi, LmokHinta, Lvirhe, Lvirhe2, Lvali, LmokVirhe, LmokVirhe2;
	TextField TtoimiMokki, TmokID, Tmoknimi, TmokHinta;
	Button mokHae, mokLisaa, mokPoista, mokMuokkaa, mokTyhjenna;
	
	Connection conn;
	ToimiMokkiKanta paikkaolio;
	
	int toimipisteID;
	int mokkiID;
	public ToimipisteidenHallinta(BorderPane paneelitoimi, Stage window, Scene paasivu, Connection conn){
		this.paneelitoimi = paneelitoimi;
		this.window = window;
		this.paasivu = paasivu;
		this.conn = conn;
		
		//Luodaan olio k�sittelem��n tietokanta operaatiota
		paikkaolio = new ToimiMokkiKanta(conn);
		
		//Luodaan otsikko teksit
		paaotsikko = new Label("Toimipisteiden ja m�kkien hallinta");
		paaotsikko.setFont(new Font("Arial", 20));
		paaotsikko.setStyle("-fx-padding: 10 0 0 30;");
		
		//Luodaan kentille gridpane
		kentat = new GridPane();
		kentat.setPadding(new Insets(10,30,10,50));
		
		//luodaan napeille gridpane
		napit = new GridPane();
		napit.setPrefWidth(125);
		napit.setPadding(new Insets(70,0,0,0));
		napit.setVgap(5.0);
		
		 //asetaan borderpane yl�tasolle
		paneelitoimi.setTop(paaotsikko);
		//Asetetaan luodut paneelit borderpaneen
		paneelitoimi.setLeft(kentat);
		paneelitoimi.setCenter(napit);
		
		//Luo kent�t
		tekstikenttienluonti();
		//Luo napit
		nappienLuonti();
		//Asetetaan luodut kent�t ja napit paneeliin
		kenttienNappienAsettaminen();
		
		//Luodaan takaisin nappi ja asetetaan se borderpaneen
		Btakaisin = new Button("Takaisin");
		paneelitoimi.setBottom(Btakaisin);
		paneelitoimi.setPadding(new Insets(0,0,10,10));
		
		//Asetetaan napeille toiminnallisuudet
		Btakaisin.setOnAction(e -> window.setScene(paasivu));
		toiLisaa.setOnAction(e -> lisaaToimi());
		toiMuokkaa.setOnAction(e -> muokkaaToimi());
		toiPoista.setOnAction(e -> poistaToimi());
		toiHae.setOnAction(e -> haeToimi());
		toiTyhjenna.setOnAction(e -> tyhjennaToimi());		
		mokLisaa.setOnAction(e -> lisaaMokki());
		mokMuokkaa.setOnAction(e -> muokkaaMokki());
		mokTyhjenna.setOnAction(e -> tyhjennaMokki());
		mokPoista.setOnAction(e -> poistaMokki());
		mokHae.setOnAction(e -> haeMokki());
		

}
	//Luodaan tekstit 
	public void tekstikenttienluonti(){
		//Toimipisteen tekstit luodaan
		Ltoimipiste = new Label("Toimipisteet ");
		Ltoimipiste.setStyle("-fx-padding: 10 0 10 30;");
		Ltoimipiste.setFont(new Font("Arial", 16));
		LtoiID = new Label("Tunnus");
		Ltoinimi = new Label("Nimi");
		Ltoilah = new Label("L�hiosoite");
		Ltoipostipaikka = new Label("Postitoimipaikka");
		Ltoipostnum = new Label("Postinumero");
		Ltoiemail = new Label("S�hk�postiosoite");
		Ltoipuh = new Label("Puhelinnumero");
		
		//M�kkien tekstit luodaan
		Lmokki = new Label("M�kit");
		Lmokki.setStyle("-fx-padding: 10 0 10 30;");
		Lmokki.setFont(new Font("Arial", 16));
		LtoimiMokki = new Label("Toimipisteen tunnus");
		LmokID = new Label("M�kin tunnus");
		Lmoknimi = new Label("Nimi");
		LmokHinta = new Label("Hinta");
		
		//Toimipisteiden haku ilmoitukset
		Lvali = new Label("");
		Lvirhe = new Label("");
		Lvirhe2 = new Label("");
		Lvali.setStyle("-fx-padding: 105 0 0 0;");
		
		//M�kkien haku ilmoitukset
		LmokVirhe = new Label("");
		LmokVirhe2 = new Label("");
		
	}
	//Luodaan napit
	public void nappienLuonti(){
		//Toimipisteen nappien luonti
		toiHae = new Button("Hae");
		toiLisaa = new Button("Lis��");
		toiPoista = new Button("Poista");
		toiMuokkaa = new Button("Muokkaa");
		toiTyhjenna = new Button("Hae tai lis�� uusi");
		//Toimipisteen nappien koon asettaminen
		toiHae.setMinWidth(napit.getPrefWidth());
		toiLisaa.setMinWidth(napit.getPrefWidth());
		toiPoista.setMinWidth(napit.getPrefWidth());
		toiMuokkaa.setMinWidth(napit.getPrefWidth());
		toiTyhjenna.setMinWidth(napit.getPrefWidth());
		//M�kkien nappien luonti
		mokHae = new Button("Hae");
		mokLisaa = new Button("Lis��");
		mokPoista = new Button("Poista");
		mokMuokkaa = new Button("Muokkaa");
		mokTyhjenna = new Button("Hae tai lis�� uusi");
		//M�kkien nappien koon asettaminen
		mokHae.setMinWidth(napit.getPrefWidth());
		mokLisaa.setMinWidth(napit.getPrefWidth());
		mokPoista.setMinWidth(napit.getPrefWidth());
		mokMuokkaa.setMinWidth(napit.getPrefWidth());
		mokTyhjenna.setMinWidth(napit.getPrefWidth());
	}

	//Asetetaan tekstit, tekstikent�t ja napit paneeliin
	public void kenttienNappienAsettaminen(){
		//Asetetaan toimipiste tekstit ja kent�t paneeliin
		kentat.add(Ltoimipiste, 0, 0);
		kentat.add(LtoiID, 0, 1);
		kentat.add(TtoiID = new TextField(), 0, 2);
		kentat.add(Ltoinimi, 0, 3);
		kentat.add(Ttoinimi = new TextField(), 0, 4);
		kentat.add(Ltoilah, 0, 5);
		kentat.add(Ttoilah = new TextField(), 0, 6);
		kentat.add(Ltoipostipaikka, 0, 7);
		kentat.add(Ttoipostipaikka = new TextField(), 0, 8);
		kentat.add(Ltoipostnum, 0, 9);
		kentat.add(Ttoipostnum  = new TextField(), 0, 10);
		kentat.add(Ltoiemail, 0, 11);
		kentat.add(Ttoiemail= new TextField(), 0, 12);
		kentat.add(Ltoipuh, 0, 13);
		kentat.add(Ttoipuh = new TextField(), 0, 14);
		//Asetetaan m�kkien tekstit ja kent�t paneeliin
		kentat.add(Lmokki, 0, 15);
		kentat.add(LtoimiMokki, 0, 16);
		kentat.add(TtoimiMokki = new TextField(), 0, 17);
		kentat.add(LmokID, 0, 18);
		kentat.add(TmokID = new TextField(), 0, 19);
		kentat.add(Lmoknimi, 0, 20);
		kentat.add(Tmoknimi = new TextField(), 0, 21);
		kentat.add(LmokHinta, 0, 22);
		kentat.add(TmokHinta = new TextField(), 0, 23);
		//Asetetaan toimipiste napit paneeliin
		napit.add(toiHae, 0, 0);
		napit.add(toiLisaa,0,1);
		napit.add(toiPoista, 0, 4);
		napit.add(toiMuokkaa, 0, 5);
		napit.add(toiTyhjenna, 0, 2);
		//Asetetaan toimipiste haku ilmoitukset paneeliin
		napit.add(Lvirhe, 0, 6);
		napit.add(Lvirhe2, 0, 7);
		napit.add(Lvali, 0, 8);
		
		//Asetetaan m�kki napit paneeliin
		napit.add(mokHae, 0, 9);
		napit.add(mokLisaa,0,10);
		napit.add(mokPoista, 0, 12);
		napit.add(mokMuokkaa, 0, 13);
		napit.add(mokTyhjenna, 0, 11);
		//Asetetaan m�kki haku ilmoitukset paneeliin
		napit.add(LmokVirhe, 0, 14);
		napit.add(LmokVirhe2, 0, 15);
		
		//Poista ja muokkaa nappit n�kym�tt�m�ksi aluksi
		toiMuokkaa.setVisible(false);
		toiPoista.setVisible(false);
		mokMuokkaa.setVisible(false);
		mokPoista.setVisible(false);
		
	}
	
	//Lis�t��n toimipiste
	public void lisaaToimi(){
		
		toimipisteID = 0;
		
		try{
			//Tarkastetaan ett� nimi ja tunnus kent�t t�ytetty
			if(TtoiID.getText().isEmpty() || Ttoinimi.getText().isEmpty()){
				Lvirhe.setText("Tunnus ja nimi pakollisia");
			}
			//Tarkastetaan ettei postinumero liian pitk�
			else if (Ttoipostnum.getText().length()>5){
				Lvirhe.setText("Virheellinen postinumero");
			}
			//tarkastetaan ettei id ole 0
			else if (Integer.parseInt(TtoiID.getText())==0){
				Lvirhe.setText("Id ei voi olla 0");
			//suoritetaan lis�ys	
			}else{		

				toimipisteID = Integer.parseInt(TtoiID.getText());
				int tulos = paikkaolio.lisaaToimi(toimipisteID, Ttoinimi.getText(), Ttoilah.getText(), Ttoipostipaikka.getText()
						, Ttoipostnum.getText(),Ttoiemail.getText(),Ttoipuh.getText());
				
				//jos lis�ys onnistui
				if(tulos ==1){
					Lvirhe.setText("Lis�ys onnistui");
					toiMuokkaa.setVisible(true);
					toiPoista.setVisible(true);
					TtoiID.setDisable(true);
					toiLisaa.setVisible(false);
					toiHae.setVisible(false);
				}
				//tuli jokin tietokanta virhe, uskoisin samasta id st�
				else if(tulos==3){
					Lvirhe.setText("Id on k�yt�ss�");
				}
				//puuttuvia arvoja
				else{
					Lvirhe.setText("T�yt� tarvittavat kent�t");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			Lvirhe.setText("Id t�ytyy olla numero");
		}

	}
	
	//Muokataan toimipistett�
	public void muokkaaToimi(){		
		try{
			//Tarkastetaan ett� nimi kentt� t�ytetty
			if(Ttoinimi.getText().isEmpty()){
				Lvirhe.setText("Nimi pakollinen");
			}
			//Tarkastetaan ettei postinumero liian pitk�
			else if (Ttoipostnum.getText().length()>5){
				Lvirhe.setText("Virheellinen postinumero");
			//suoritetaan muokkaus	
			}else{		

				
				int tulos = paikkaolio.muokkaaToimi(toimipisteID, Ttoinimi.getText(), Ttoilah.getText(), Ttoipostipaikka.getText()
						, Ttoipostnum.getText(),Ttoiemail.getText(),Ttoipuh.getText());
				System.out.print(tulos);
				//jos Muokkaus onnistui
				if(tulos ==1){
					Lvirhe.setText("Muokkaus onnistui");

				}
				//tuli jokin tietokanta virhe, uskoisin samasta id st�
				else if(tulos==3){
					Lvirhe.setText("Tietokanta virhe");
				}
				//puuttuvia arvoja
				else{
					Lvirhe.setText("T�yt� tarvittavat kent�t");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			Lvirhe.setText("Id t�ytyy olla numero");
		}

	}
	
	//Poistetaan toimipiste
	public void poistaToimi(){

			//Tarkastetaan ett� nimi kentt� t�ytetty
			if(toimipisteID ==0){
				Lvirhe.setText("ID ei voi olla 0");
			}else{						
				int tulos = paikkaolio.poistaToimi(toimipisteID);
				//jos Poisto onnistui
				if(tulos ==1){
					Lvirhe.setText("Poisto onnistui");
					tyhjennaToimi();
					tyhjennaMokki();
				}
				//tuli jokin tietokanta virhe
				else if(tulos==3){
					Lvirhe.setText("Tietokanta virhe");
				}
				//puuttuvia arvoja
				else{
					Lvirhe.setText("Poisto ei onnistunut");
				}
			}
		//Jos id muu kuin numero


	}
	
	//haetaan toimipaikka
	public void haeToimi(){		
		ArrayList<String> tulokset = new ArrayList<String>();
		try{
			//Tarkastetaan ett� nimi tai tunnus kentt� t�ytetty
			if(TtoiID.getText().isEmpty() && Ttoinimi.getText().isEmpty()){
				Lvirhe.setText("Tunnus tai nimi pakollinen");
			}
			else{
				//Haetaan nimen perusteella
				if(TtoiID.getText().isEmpty()){
					toimipisteID = 0;
					tulokset = paikkaolio.haeToimi(toimipisteID, Ttoinimi.getText());						
				//Haetaan nimen tai toimipiste_id tai kummankin perusteella	
				}else{
				toimipisteID = Integer.parseInt(TtoiID.getText());
				tulokset = paikkaolio.haeToimi(toimipisteID, Ttoinimi.getText());
				}
				//jos Haku onnistui
				if(tulokset.get(0).equals("1")){					
					Lvirhe.setText("Haku onnistui");
					
					//Asetetaan tulokset kenttiin
					TtoiID.setText(tulokset.get(1));
					Ttoinimi.setText(tulokset.get(2));
					Ttoilah.setText(tulokset.get(3));
					Ttoipostipaikka.setText(tulokset.get(4));
					Ttoipostnum.setText(tulokset.get(5));
					Ttoiemail.setText(tulokset.get(6));
					Ttoipuh.setText(tulokset.get(7));
					//Disabloidaan id ettei sit� voi muuttaa
					TtoiID.setDisable(true);
					//lis�� ja hae napit n�kym�tt�m�ksi
					toiLisaa.setVisible(false);
					toiHae.setVisible(false);

					toiMuokkaa.setVisible(true);
					toiPoista.setVisible(true);
				}
				//Jos kannasta l�ytyi monta rivi� haulla
				else if(tulokset.get(0).equals("4")){
					Lvirhe.setText("L�ytyi monta tulosta");
					Lvirhe2.setText("Sy�t� my�s tunnus");
				}
				//tuli jokin tietokanta virhe
				else if(tulokset.get(0).equals("3")){
					Lvirhe.setText("Tietokanta virhe");
				}
				//Haulla ei l�ydy tuloksia
				else if(tulokset.get(0).equals("5")){
					Lvirhe.setText("Toimipistett� ei ole olemassa");
				}
				//puuttuvia arvoja
				else{
					Lvirhe.setText("Haku ei onnistu");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			Lvirhe.setText("Id t�ytyy olla numero");
		}

	}
	
	//Lis�t��n m�kki
	public void lisaaMokki(){
		LmokVirhe2.setText("");
		mokkiID = 0;
		
		try{
			//Tarkastetaan ett� nimi ja tunnus kent�t t�ytetty
			if(TtoimiMokki.getText().isEmpty() || Tmoknimi.getText().isEmpty() || TmokID.getText().isEmpty() || TmokHinta.getText().isEmpty()){
				LmokVirhe.setText("Kaikki kent�t ovat pakollisia");
			}
			//tarkastetaan ettei id ole 0
			else if (Integer.parseInt(TmokID.getText())==0){
				LmokVirhe.setText("Id ei voi olla 0");
			//suoritetaan lis�ys	
			}else{		
				int toiimiID = Integer.parseInt(TtoimiMokki.getText());
				int hintaa = Integer.parseInt(TmokHinta.getText());
				mokkiID = Integer.parseInt(TmokID.getText());
				int tulos = paikkaolio.lisaaMokki(toiimiID,mokkiID, Tmoknimi.getText(), hintaa);
				System.out.print(tulos);
				//jos lis�ys onnistui
				if(tulos ==1){
					LmokVirhe.setText("Lis�ys onnistui");
					mokMuokkaa.setVisible(true);
					mokPoista.setVisible(true);
					mokHae.setVisible(false);
					mokLisaa.setVisible(false);
					//mokki_id disableksi ettei sit� voi muokata
					TmokID.setDisable(true);

				}
				//tuli jokin tietokanta virhe,  samasta mokki_id st� tai toimipistett� ei l�ydy
				else if(tulos==3){
					LmokVirhe.setText("M�kki id on k�yt�ss� tai");
					LmokVirhe2.setText("toimipistett� ei l�ydy");
				}
				//puuttuvia arvoja
				else{
					LmokVirhe.setText("T�yt� tarvittavat kent�t");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LmokVirhe.setText("Id ja hinta t�ytyy olla numeroita");
		}

	}
	
	
	//Muokataan m�kki�
	public void muokkaaMokki(){		
		try{
			//Tarkastetaan ett� kaikki kentt�t on t�ytetty
			if(TtoimiMokki.getText().isEmpty() || Tmoknimi.getText().isEmpty() || TmokID.getText().isEmpty() || TmokHinta.getText().isEmpty()){
				LmokVirhe.setText("Kaikki kent�t on pakollisia");
			}else{		

				int toiimiID = Integer.parseInt(TtoimiMokki.getText());
				int hintaa = Integer.parseInt(TmokHinta.getText());	
				int tulos = paikkaolio.muokkaaMokki(toiimiID,mokkiID, Tmoknimi.getText(), hintaa);
				
				//jos Muokkaus onnistui
				if(tulos ==1){
					LmokVirhe.setText("Muokkaus onnistui");

				}
				//tuli jokin tietokanta virhe, toimipistett� ei l�ydy
				else if(tulos==3){
					LmokVirhe.setText("toimipistett� ei l�ydy");
				}
				//puuttuvia arvoja
				else{
					LmokVirhe.setText("T�yt� tarvittavat kent�t");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LmokVirhe.setText("Tunnukset ja hinta t�ytyy");
			LmokVirhe.setText("olla numeroita");
		}

	}
	
	//Poista m�kki
	public void poistaMokki(){

		//Tarkastetaan ett� nimi kentt� t�ytetty
		if(mokkiID ==0){
			Lvirhe.setText("ID ei voi olla 0");
		}else{						
			int tulos = paikkaolio.poistaMokki(mokkiID);
			//jos Poisto onnistui
			if(tulos ==1){
				tyhjennaMokki();
				LmokVirhe.setText("Poisto onnistui");
				
			}
			//tuli jokin tietokanta virhe
			else if(tulos==3){
				LmokVirhe.setText("Tietokanta virhe");
			}
			//puuttuvia arvoja
			else{
				LmokVirhe.setText("Poisto ei onnistunut");
			}
		}
	//Jos id muu kuin numero


}

	//haetaan M�kki
	public void haeMokki(){		
		ArrayList<String> tulokset = new ArrayList<String>();
		try{
			//Tarkastetaan ett� nimi tai tunnus kentt� t�ytetty
			if(TmokID.getText().isEmpty() && Tmoknimi.getText().isEmpty()){
				LmokVirhe.setText("Tunnus tai nimi pakollinen");
			}
			else{
				//Haetaan nimen perusteella
				if(TmokID.getText().isEmpty()){
					mokkiID = 0;
					tulokset = paikkaolio.haeMokki(mokkiID, Tmoknimi.getText());						
				//Haetaan nimen tai mokki_id perusteella tai kummankin
				}else{
				mokkiID = Integer.parseInt(TmokID.getText());
				tulokset = paikkaolio.haeMokki(mokkiID, Tmoknimi.getText());
				}
				//jos Haku onnistui
				if(tulokset.get(0).equals("1")){	
					//Asetetaan kenttiin tekstit
					LmokVirhe.setText("Haku onnistui");
					TtoimiMokki.setText(tulokset.get(1));
					TmokID.setText(tulokset.get(2));
					Tmoknimi.setText(tulokset.get(3));
					TmokHinta.setText(tulokset.get(4));					
					//Napeille n�kyvyydet
					mokMuokkaa.setVisible(true);
					mokPoista.setVisible(true);
					TmokID.setDisable(true);
					mokHae.setVisible(false);
					mokLisaa.setVisible(false);
				}
				//Jos haku palautti useita rivej�, ohjeistetaan k�ytt�j�� miten toimia
				else if(tulokset.get(0).equals("4")){
					LmokVirhe.setText("L�ytyi monta tulosta");
					LmokVirhe2.setText("Sy�t� my�s tunnus");
				}
				//tuli jokin tietokanta virhe
				else if(tulokset.get(0).equals("3")){
					LmokVirhe.setText("Tietokanta virhe");
				}
				//Haulla ei l�ytynyt m�kki�
				else if(tulokset.get(0).equals("5")){
					LmokVirhe.setText("M�kki� ei ole olemassa");
				}
				//puuttuvia arvoja
				else{
					LmokVirhe.setText("Haku ei onnistu");
				}
			}
		//Jos id muu kuin numero
		}catch (NumberFormatException exception){
			LmokVirhe.setText("Id t�ytyy olla numero");
		}

	}
	//Tyhjennet��n toimipisteen haku tulos tekstit ja tekstikent�t sek� toimipiste id disable pois p��lt�
	//Lis�ksi napeille n�kyvyydet
	public void tyhjennaToimi(){
		TtoiID.setText("");
		Ttoinimi.setText("");
		Ttoilah.setText("");
		Ttoipostipaikka.setText("");
		Ttoipostnum.setText("");
		Ttoiemail.setText("");
		Ttoipuh.setText("");
		toiMuokkaa.setVisible(false);
		toiPoista.setVisible(false);
		
		TtoiID.setDisable(false);
		
		toiLisaa.setVisible(true);
		toiHae.setVisible(true);
		
		Lvirhe.setText("");
		Lvirhe2.setText("");
		
	}
	//Tyhjennet��n mokkien haku tulos tekstit ja tekstikent�t sek� toimipiste id disable pois p��lt�
	//Lis�ksi napeille n�kyvyydet
	public void tyhjennaMokki(){
		TtoimiMokki.setText("");
		TmokHinta.setText("");
		TmokID.setText("");
		Tmoknimi.setText("");

		mokMuokkaa.setVisible(false);
		mokPoista.setVisible(false);
		TmokID.setDisable(false);
		
		mokLisaa.setVisible(true);
		mokHae.setVisible(true);
		
		LmokVirhe.setText("");
		LmokVirhe2.setText("");
	}
	}
