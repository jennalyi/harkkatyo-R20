package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class VaraaMokki {
	BorderPane paneelivaraus, alaikkuna;
	VBox oikea, oik;
	GridPane keski;
	Stage window;
	Button Btakaisin, Bhaepaikka, Basiakas, Bvaraa, Bvaraus, Bpoista, Bmuuta, Bhaeuus,
	BpalvHae, BuusPalv, BpoistPalv;
	Scene paasivu;
	TextField paikka, Tasiakas, Tmokki, Tvaraus,TpalvHae, TuusPalv;

	Label Lpaikanimi, Lhinta, Lotsikko,
	Lasiakasot, Lasiakashaettu, Lnumero, paaotsikko,Lpaikka, Lasiakas, kalenterivihje
	, hintayhteensa, hintaselite, Lmokki, Lvaraus, LpalvHae, LuusPalv, LpalvIlmoitus,
	Lvirhevaraus;
	Kanta kantaolio;
	DatePicker aloituspaiva, lopetuspaiva;
	Boolean test;
	Connection conn;
	int varausID = 0;
	int idasiakas = 0;
	int idmokki = 0;
	int idtoimi = 0;
	
	boolean haettuvaraus = false;
	//Määritetään ettei virheellisellä arvolla voi varata eli jos on hakenut onnistuneen
	//haun ja sitten hakee väärin niin ei pysty varaamaan vaan vaatii että haku on onnistunut
	boolean lippupaikka = false;
	boolean lippuasiakas = false;
	
	LocalDate paiva;

	public VaraaMokki(BorderPane paneelivaraus, Stage window, Scene paasivu, Connection conn){
		this.paneelivaraus = paneelivaraus;
		this.window = window;
		this.paasivu = paasivu;
		this.conn = conn;
		
		
		//Luodaan olio jolla käsitellään kantaa
		kantaolio = new Kanta(conn);
		
		//Lomakkeen otsikon luonti
		paaotsikko = new Label("Majoitusten varaus");
		paaotsikko.setFont(new Font("Arial", 25));
		paaotsikko.setStyle("-fx-padding: 10 0 10 140;");
		//paaotsikko.setAlignment(Pos.BASELINE_CENTER);
		//paaotsikko.setAlignment(Pos.TOP_LEFT);
		paneelivaraus.setTop(paaotsikko);

		//oik = new VBox(10);
		//paneelivaraus.setRight(oik);

		//oikea = new VBox();
		//oikea.setPadding(new Insets(30,10,0,5));
		//oikea.setPrefWidth(150);
		//paneelivaraus.setLeft(oikea);
		
		//Luodaan gridpane johon kaikki grafiikka
		keski = new GridPane();
		keski.setPadding(new Insets(20,10,10,10));
		keski.setPrefWidth(185);
		keski.setHgap(10.0);
		keski.setVgap(5.0);
		paneelivaraus.setCenter(keski);
		
		//alapaneeliin laitetaan takaisin nappi
		alaikkuna = new BorderPane();
		alaikkuna.setPadding(new Insets(10,30,10,30));
		paneelivaraus.setBottom(alaikkuna);

		//luodaan asiakas ja paikka hakuun liittyvä objectit
		asiakasjapaikkahaku();

		//luodaan paikka ja asiakas tietoja varten kentät
		asiakaspaikkaselite();

		//Varaus kalenterin luonti
		varauskaleterinluonti();

		//kalenteri ja palveluje varaus
		kalenteripalv();

		//Luodaan alapaneeli
		alapaneeli();

		//asetetaan toiminnallisuudet nappeihin
		Btakaisin.setOnAction(e -> window.setScene(paasivu));
		Bhaepaikka.setOnAction(e -> haepaikkaa());
		Basiakas.setOnAction(e -> haeasiakas());
		Bvaraa.setOnAction(e -> suoritavaraus());
		Bvaraus.setOnAction(e -> haeOlemassa());
		Bhaeuus.setOnAction(e -> haeUus());
		BuusPalv.setOnAction(e -> uusiPalv());
		BpoistPalv.setOnAction(e -> poistaPalvelu());
		Bmuuta.setOnAction(e -> paivitavaraus());
		Bpoista.setOnAction(e -> poistaOlemassa());
	}
	
	//hakee toimipaikan ja mökin tiedot
	public void haepaikkaa(){
		try{
			//Tarkistetaan ettei toimipaikka kenttä tyhjä
			if(paikka.getText().isEmpty()){
				Lotsikko.setText("Syötä id");
				kantaolio.toimilippu();
				lippupaikka = false;
			}
			//Tarkistetaan ettei mökki kenttä tyhjä
			if(Tmokki.getText().isEmpty()){
				kantaolio.mokkilippu();
				Lhinta.setText("Syötä id");
				lippupaikka = false;
			
			}else{
				//Otetaan teksti kentistä tiedot
				idtoimi = Integer.parseInt(paikka.getText());
				idmokki = Integer.parseInt(Tmokki.getText());
				
				//Tulokset kantahauista
				String tulos = kantaolio.haetaanPaikka(idtoimi);
				String tulosmokki = kantaolio.haetaanMokki(idmokki, idtoimi);
				
				//Tarkistetaan onko haku onnistunut
				if(tulos.equals("Toimipaikkaa ei löydy") || tulosmokki.equals("Mökkiä ei löydy")){
					if(tulos.equals("Toimipaikkaa ei löydy")){
						Lotsikko.setText(tulos);
						lippupaikka = false; 
					}
					if(tulosmokki.equals("Mökkiä ei löydy")){
						Lhinta.setText(tulosmokki);
						lippupaikka = false;
					}
					
				}else{
					//Asetetaan tiedot
					Lotsikko.setText(tulos);
					Lhinta.setText(tulosmokki);
					
					//jos ei ole menty haun kautta asetetaan napeille alla olevat näkyvyydet
					if(haettuvaraus ==false){
						Bvaraa.setVisible(true);
						Bvaraus.setVisible(false);
						Bhaeuus.setVisible(true);
						}
					lippupaikka = true;
				}
			}
			//Jos tekstin muuttamisessa numeroksi tulee ongelmia
		}catch (NumberFormatException exception){
			lippupaikka = false;
			Lotsikko.setText("Id täytyy olla numero");
			Lhinta.setText("Id täytyy olla numero");
		}


		//Lotsikko.setText("Paikan perustiedot:"+"\n"+"Mökkivaara");		
		//Lpaikanimi.setText("Mökkivaara");
		//Lhinta.setText("250"+"per päivä");

	}
	public void haeasiakas(){
		try{
			if(Tasiakas.getText().isEmpty()){
				Lasiakashaettu.setText("Syötä id");
				kantaolio.asiakaslippu();
				lippuasiakas = false;
			}else{
				idasiakas = Integer.parseInt(Tasiakas.getText());

				//kantaolio.haetaanAsiakas(id);

				Lasiakashaettu.setText(kantaolio.haetaanAsiakas(idasiakas));		
				//Lasiakashaettu.setText("Kari Möttönen");
				//Lnumero.setText("05324977234");
				if(haettuvaraus ==false){
				Bvaraa.setVisible(true);
				Bvaraus.setVisible(false);
				}
				lippuasiakas = true;
			}
		}catch (NumberFormatException exception){
			Lasiakashaettu.setText("Id täytyy olla numero");
			lippuasiakas = false;
		}

	}
	public void suoritavaraus(){
		boolean lippuvarattu = false;
		LocalDate varattuL = LocalDate.now();
		LocalDate vahvistusL = LocalDate.now();

		Date varattu = Date.valueOf(varattuL);
		Date vahvistus = Date.valueOf(vahvistusL);
		Date alotus = Date.valueOf(aloituspaiva.getValue());
		Date lopetus = Date.valueOf(lopetuspaiva.getValue());
		if(lippuasiakas && lippupaikka){
		
		//Tarkastetaan ettei ole varattu päivä
		ArrayList<Pari> paivat1 = new ArrayList<Pari>();
			paivat1 = kantaolio.haetaanPaivat(idmokki);
			if(paivat1 != null){
			for(int i = 0; i<paivat1.size(); i++){
				
				if(aloituspaiva.getValue().isBefore(paivat1.get(i).alkupaiv) && lopetuspaiva.getValue().isAfter(paivat1.get(i).loppupaiv)){
					lippuvarattu = true;
				}
				// Disable Monday, Tueday, Wednesday.
				//if (item.isAfter(paivat.get(i).alkupaiv)&&item.isBefore(paivat.get(i).loppupaiv)){

			}
			}
			
		try{
			if(Tvaraus.getText().isEmpty()){
				hintaselite.setText("Syötä varaus id");
			}
			else if(lippuvarattu){
				hintaselite.setText("Päivät sisältävät \n jo varattuja päiviä");
			}
			else{		

				int varausID = Integer.parseInt(Tvaraus.getText());
				int tulos = kantaolio.varaaMokki(varausID, varattu, vahvistus, alotus, lopetus);
				System.out.print(tulos);
				if(tulos ==1){
					//hintaselite.setText("Hinta yhteensä: \n"+ kantaolio.haetaanMokkiHinta());
					hintaselite.setText("Varaus onnistui");
					Bvaraa.setVisible(false);
					//Bhaepaikka.setVisible(false);
					//Basiakas.setVisible(false);
					Bmuuta.setVisible(true);
					LuusPalv.setVisible(true);
					TuusPalv.setVisible(true);
					BuusPalv.setVisible(true);
					BpoistPalv.setVisible(true);
					Tvaraus.setDisable(true);
				}
				else if(tulos==3){
					hintaselite.setText("Id on jo käytössä");
				}
				else{
					hintaselite.setText("Täytä tarvittavat kentät");
				}
			}
		}catch (NumberFormatException exception){
			hintaselite.setText("Id täytyy olla numero");
		}
		}else{
			hintaselite.setText("Tietoja puuttuu");
		}

	}//suoritavaraus
	
	
	//Haeolemassa oleva
	public void haeOlemassa(){
		
		try{
			if(Tvaraus.getText().isEmpty()){
				Lvirhevaraus.setText("Syötä id");
			}else{		

				varausID = Integer.parseInt(Tvaraus.getText());
				VarausTiedot tulos1 = new VarausTiedot();
				tulos1 = kantaolio.haetaanVaraus(varausID);
						
				System.out.print(tulos1);
				if(tulos1.lippu){
					Lvirhevaraus.setText("Haku onnistui");
					Tvaraus.setText(Integer.toString(varausID));
					Tasiakas.setText(Integer.toString(tulos1.asiakasID));
					paikka.setText(Integer.toString(tulos1.toimiID));
					Tmokki.setText(Integer.toString(tulos1.mokkiID));
					idmokki = tulos1.mokkiID;
					
					aloituspaiva.setValue(tulos1.alkupaiv.toLocalDate());
					lopetuspaiva.setValue(tulos1.loppupaiv.toLocalDate()); 
					
					Bvaraus.setVisible(false);
					nakyvyysNapeille(true);
					Bvaraa.setVisible(false);
					//hakunapitnakyvyys(false);
					palveluNakyvyys(true);
					haettuvaraus = true;
					
					lippupaikka = true;
					lippuasiakas = true;
					Tvaraus.setDisable(true);

				}else{
					Lvirhevaraus.setText("Varausta ei löytynyt");
				}
			}
		}catch (NumberFormatException exception){
			Lvirhevaraus.setText("Id täytyy olla numero");
		}
		
		



	}//Haeolemassa oleva
	
	
	
	public void haeUus(){


		kantaolio = new Kanta(conn);
		idasiakas = 0;
		idmokki = 0;
		idtoimi = 0;
		Lvirhevaraus.setText("");
		LpalvIlmoitus.setText("");
		TuusPalv.setText("");
		Lasiakashaettu.setText("");
		Lhinta.setText("");
		Lotsikko.setText("");
		hintaselite.setText("");
		Tmokki.setText("");
		Tasiakas.setText("");
		paikka.setText("");
		Tvaraus.setText("");
		
		//Lasiakashaettu.setText("");

		nakyvyysNapeille(false);
		hakunapitnakyvyys(true);
		Bvaraus.setVisible(true);
		Bvaraa.setVisible(false);
		palveluNakyvyys(false);
		haettuvaraus = false;
		
		aloituspaiva.setValue(LocalDate.now());
		lopetuspaiva.setValue(aloituspaiva.getValue().plusDays(1));
		Tvaraus.setDisable(false);
	}
	public void uusiPalv(){
		try{
			if(TuusPalv.getText().isEmpty()){
				LpalvIlmoitus.setText("Syötä id");
			}else{
				int palveluid = Integer.parseInt(TuusPalv.getText());
				String tulosK = kantaolio.lisaaPalv(palveluid);
				if(tulosK != null){
				LpalvIlmoitus.setText("Lisätty palvelu: \n"+tulosK);		
				//hintaselite.setText("Hinta yhteensä: \n"+kantaolio.gethinta());
				palveluNakyvyys(true);}
				else{
					LpalvIlmoitus.setText("Palvelua ei löydy tai\n sinulla on jo se");
				}
			}
		}catch (NumberFormatException exception){
			Lasiakashaettu.setText("Id täytyy olla numero");
		}	
	}
public void poistaPalvelu(){
	try{
		if(TuusPalv.getText().isEmpty()){
			LpalvIlmoitus.setText("Syötä id");
		}else{
			int palveluid = Integer.parseInt(TuusPalv.getText());
			String tulosK = kantaolio.poistaPalvelu(palveluid);
			if(tulosK != null){
			LpalvIlmoitus.setText("Poisto "+tulosK);		
			//hintaselite.setText("Hinta yhteensä: \n"+kantaolio.gethinta());
			palveluNakyvyys(true);}
			else{
				LpalvIlmoitus.setText("Poisto ei onnistunut");
			}
		}
	}catch (NumberFormatException exception){
		Lasiakashaettu.setText("Id täytyy olla numero");
	}	
}


//Poistaolemassa oleva KESKEN!!!!!!!!
public void poistaOlemassa(){
	
	
		boolean tulos = kantaolio.poistaVaraus(varausID);
		System.out.print(tulos);
		if(tulos){
			
/*				Bvaraa.setVisible(false);
			LuusPalv.setVisible(true);
			TuusPalv.setVisible(true);
			BuusPalv.setVisible(true);
			BpoistPalv.setVisible(true);*/
			haeUus();
			hintaselite.setText("Poisto onnistui");
		}
		else{
			hintaselite.setText("Poisto ei onnistunut");
		}

	

}//Poistaolemassa oleva


public void paivitavaraus(){
	
	if(lippuasiakas && lippupaikka){
		
		boolean lippuvarattu = false;
	Date alotus = Date.valueOf(aloituspaiva.getValue());
	Date lopetus = Date.valueOf(lopetuspaiva.getValue());
	ArrayList<Pari> paivat1 = new ArrayList<Pari>();
	paivat1 = kantaolio.haetaanPaivat(idmokki);
	if(paivat1 != null){
	for(int i = 0; i<paivat1.size(); i++){
		
		if(aloituspaiva.getValue().isBefore(paivat1.get(i).alkupaiv) && lopetuspaiva.getValue().isAfter(paivat1.get(i).loppupaiv)){
			lippuvarattu = true;
		}
		// Disable Monday, Tueday, Wednesday.
		//if (item.isAfter(paivat.get(i).alkupaiv)&&item.isBefore(paivat.get(i).loppupaiv)){

	}}
	try{	
			int tulos = kantaolio.paivitaVaraus(varausID, alotus, lopetus);
			System.out.print(tulos);

			if(lippuvarattu){
				hintaselite.setText("Päivät sisältävät \n jo varattuja päiviä");
			}
			else if(tulos ==1){
				hintaselite.setText("Muutos onnistui");
/*				Bvaraa.setVisible(false);
				LuusPalv.setVisible(true);
				TuusPalv.setVisible(true);
				BuusPalv.setVisible(true);
				BpoistPalv.setVisible(true);*/
			}
			else if(tulos==3){
				hintaselite.setText("Id on jo käytössä");
			}
			else{
				hintaselite.setText("Täytä tarvittavat kentät");
			}
		
	}catch (NumberFormatException exception){
		hintaselite.setText("Id täytyy olla numero");
	}}
	else{
		hintaselite.setText("Tietoja puuttuu");
	}

}


	public void asiakasjapaikkahaku(){
		Lvaraus = new Label("Hae varaus tai syötä id");
		Tvaraus = new TextField();
		Bvaraus = new Button("Hae varaus");

		Lpaikka = new Label("Syötä haettava toimipaikka");
		paikka = new TextField();
		paikka.setMinWidth(keski.getPrefWidth());
		paikka.setPrefWidth(150);
		paikka.setMaxWidth(150);
		Bhaepaikka = new Button("Hae paikka");
		//Bhaepaikka.setMinWidth(keski.getPrefWidth());

		Lmokki = new Label("Syötä varattava mökki");
		Tmokki = new TextField();

		Lasiakas = new Label("Syötä haettava asiakas");
		//Lasiakas.setStyle("-fx-padding: 10 0 0 0;");
		Tasiakas = new TextField();
		Tasiakas.setMinWidth(keski.getPrefWidth());
		Tasiakas.setPrefWidth(150);
		Tasiakas.setMaxWidth(150);
		Basiakas = new Button("Hae asiakasta");
		//Basiakas.setMinWidth(keski.getPrefWidth());

		Btakaisin = new Button("takaisin");
		//Btakaisin.setMinWidth(keski.getPrefWidth());

		//keski.getChildren().addAll(Lpaikka,paikka, 				Lasiakas, Tasiakas);

		keski.add(Lvaraus, 1, 0);
		keski.add(Tvaraus, 1, 1);
		Lpaikka.setStyle("-fx-padding: 20 0 0 0;");
		keski.add(Lpaikka, 1, 2);
		keski.add(paikka, 1, 3);
		keski.add(Lmokki, 1, 4);
		keski.add(Tmokki, 1, 5);
		Lasiakas.setStyle("-fx-padding: 20 0 0 0;");
		keski.add(Lasiakas, 1, 6);
		keski.add(Tasiakas, 1, 7);
		//oik.getChildren().addAll(Bhaepaikka,Basiakas);
		keski.add(Bvaraus, 2, 1);
		keski.add(Bhaepaikka, 2, 5);
		keski.add(Basiakas, 2, 7);
		keski.getColumnConstraints().add(new ColumnConstraints(150));
	}
	//selitteet hauista
	public void asiakaspaikkaselite(){
		Lotsikko = new Label();
		//Lotsikko.setAlignment(Pos.BASELINE_LEFT);

		Lpaikanimi = new Label();
		Lhinta = new Label();
		//oikea.getChildren().addAll(Lotsikko,Lpaikanimi, Lhinta);
		Lasiakasot = new Label();
		//Lasiakasot.setAlignment(Pos.BASELINE_LEFT);
		Lasiakashaettu = new Label();
		Lnumero = new Label();		
		//Lasiakasot.setStyle("-fx-padding: 40 0 0 0;");
		hintaselite = new Label("");
		Lvirhevaraus = new Label();
		//Laskee yhteen lasketun hinnan
		//hintayhteensa = new Label("");
		//hintaselite.setStyle("-fx-padding: 70 0 0 10;");
		//oikea.getChildren().addAll(Lasiakasot,Lasiakashaettu, Lnumero, hintaselite, hintayhteensa);
		keski.add(Lvirhevaraus, 0, 1);
		keski.add(Lotsikko, 0, 3);
		keski.add(Lpaikanimi, 0, 4);
		keski.add(Lhinta, 0, 5);
		keski.add(Lasiakasot, 0, 6);
		keski.add(Lasiakashaettu, 0, 7);
		keski.add(Lnumero, 0, 8);
		keski.add(hintaselite, 0, 12);

	}
//lisää palvelu ja kalenteri gui
	public void kalenteripalv(){

		keski.add(kalenterivihje, 1, 8);
		keski.add(new Label("Valitse aloituspäivä:"), 1, 9);
		keski.add(aloituspaiva, 1, 10);
		keski.add(new Label("Valitse lopetuspäivä:"), 1, 11);
		keski.add(lopetuspaiva, 1, 12);

		Bpoista = new Button("Poista varaus");
		Bmuuta = new Button("Muokkaa varausta");
		Bhaeuus = new Button("Hae tai varaa uusi");
		Bvaraa = new Button("Varaa mökki");

		keski.add(Bpoista, 2, 10);
		keski.add(Bmuuta, 2, 11);
		keski.add(Bvaraa, 2, 12);
		keski.add(Bhaeuus, 2, 13);

		LpalvHae = new Label("Hae varattu palvelu");
		TpalvHae = new TextField();
		BpalvHae = new Button("Hae ");
		BpoistPalv = new Button("Poista palvelu ");

		LuusPalv = new Label("Syötä palvelu id");
		TuusPalv = new TextField();
		BuusPalv = new Button("Lisää");
		LpalvIlmoitus = new Label("");
		
		//keski.add(LpalvHae, 1, 16);
		//keski.add(TpalvHae, 1, 17);
		//keski.add(BpalvHae, 2, 17);
		keski.add(LuusPalv, 1, 14);
		keski.add(TuusPalv, 1, 15);
		keski.add(LpalvIlmoitus, 0, 15);
		keski.add(BuusPalv, 2, 15);
		keski.add(BpoistPalv, 2, 16);

		palveluNakyvyys(false);
		nakyvyysNapeille(false);
		Bvaraa.setVisible(false);
	}

	public void varauskaleterinluonti(){
		kalenterivihje = new Label("Majoituksen ajankohdan valinta");
		kalenterivihje.setFont(new Font("Arial", 12));
		kalenterivihje.setStyle("-fx-padding: 10 0 0 0;-fx-font-weight: bold;");
		//kalenterivihje.setStyle("-fx-font-weight: bold;");

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

						/*						if (item.isBefore(aloituspaiva.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #ff4444;");
						}*/
						ArrayList<Pari> paivat = new ArrayList<Pari>();
			
						if(idmokki!=0){
							
							paivat = kantaolio.haetaanPaivat(idmokki);
							if(paivat!=null){
								//paivat.get(i).alkupaiv+"  Loppupäivä"+paivat.get(i).loppupaiv
								for(int i = 0; i<paivat.size(); i++){
									// Disable Monday, Tueday, Wednesday.
									if (item.isAfter(paivat.get(i).alkupaiv.minusDays(1))&&item.isBefore(paivat.get(i).loppupaiv.plusDays(1))){
										setDisable(true);

										setStyle("-fx-background-color: #ffc0cb;");
									}
								}}

						}

					}
				};
			}
		};
		aloituspaiva.setDayCellFactory(dayCellFactory);
		lopetuspaiva.setDayCellFactory(dayCellFactory);
		lopetuspaiva.setValue(aloituspaiva.getValue().plusDays(1));

		//paiva = lopetuspaiva.getValue();


		//keski.getChildren().addAll(kalenterivihje,new Label("Valitse aloituspäivä:"), aloituspaiva, new Label("Valitse lopetuspäivä:"),lopetuspaiva);
	}
	public void nakyvyysNapeille(boolean arvo){
		Bpoista.setVisible(arvo);
		Bmuuta.setVisible(arvo);
		Bhaeuus.setVisible(arvo);
		Bvaraa.setVisible(arvo);
	}
	public void hakunapitnakyvyys(boolean arvo){
		Bvaraa.setVisible(arvo);
		Bhaepaikka.setVisible(arvo);
		Basiakas.setVisible(arvo);
	}
	public void palveluNakyvyys(boolean arvo){
		LpalvHae.setVisible(arvo);
		TpalvHae.setVisible(arvo);
		BpalvHae.setVisible(arvo);
		BpoistPalv.setVisible(arvo);

		LuusPalv.setVisible(arvo);
		TuusPalv.setVisible(arvo);
		BuusPalv.setVisible(arvo);
	}
	public void alapaneeli(){

		//varaa.setAlignment(Pos.TOP_CENTER);
		alaikkuna.setLeft(Btakaisin);
		//alaikkuna.setLeft(Bvaraa);

	}
}
