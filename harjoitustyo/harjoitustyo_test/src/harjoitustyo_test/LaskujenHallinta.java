package harjoitustyo_test;
//package harjoitustyo_test;

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

import javafx.geometry.Insets;

import javafx.scene.control.TextField; 

import javafx.scene.layout.HBox;

import java.sql.*;

import java.awt.*;

import java.awt.event.*;

//import javax.swing.*;

import javax.swing.JOptionPane;







public class LaskujenHallinta{






    BorderPane paneelivaraus;

    Button Btakaisin,btnRight,btnRight2,btnRight3,btnRight4,btnRight5;

    Label paaotsikko,joku;

    Stage window;

    VBox paneelit,vbox,vbox2;

    Scene paasivu;

    TextField textfield,textfield2,textfield3,textfield4,textfield5,textfield6,textfield7,textfield8,textfield9;

    StackPane stackPane;

	HBox hbox;

    Lasku m_lasku = new Lasku();

    Connection m_conn;

  



    

    public LaskujenHallinta(BorderPane paneelivaraus, Stage window, Scene paasivu,VBox vbox){

		this.paneelivaraus = paneelivaraus;

		this.window = window;

    this.paasivu = paasivu;

    this.vbox = vbox;  

  //      VBox vbox = new VBox();

    //    vbox.setPrefWidth(90);





      //  window.setTitle("Laskujen Hallinta");

      



      //  paasivu = new Scene(paneelivaraus,400,400);





		btnRight = new Button("Hae");

        btnRight.setOnAction(e -> hae_tiedot());

        btnRight.setPadding(new Insets(25, 25, 25, 25));

        btnRight.setMaxWidth(100);

        BorderPane.setMargin(btnRight, new Insets(100, 100, 100, 100));



        

        btnRight2 = new Button("Muuta");

        btnRight2.setOnAction(e -> muuta_tiedot());

        btnRight2.setPadding(new Insets(25, 25, 25, 25));

        btnRight2.setMaxWidth(100);

        BorderPane.setMargin(btnRight2, new Insets (100,100, 100, 100));

        



       btnRight3 = new Button("Lisaa");

        btnRight3.setOnAction(e -> lisaa_tiedot());

        btnRight3.setPadding(new Insets(25, 25, 25, 25));

        btnRight3.setMaxWidth(100);

        BorderPane.setMargin(btnRight3, new Insets(100, 100, 100, 100));



         btnRight4 = new Button("Poista");

        btnRight4.setOnAction(e -> poista_tiedot());

        btnRight4.setPadding(new Insets(25,25, 25,25));

        btnRight4.setMaxWidth(100);

        BorderPane.setMargin(btnRight4, new Insets(100, 100, 100, 100));



         btnRight5 = new Button("Laheta");

        btnRight5.setPadding(new Insets(25, 25, 25, 25));

        btnRight5.setMaxWidth(100);

        BorderPane.setMargin(btnRight5, new Insets(100, 100, 100, 100));



        vbox.getChildren().addAll(btnRight,btnRight2,btnRight3,btnRight4,btnRight5);





        paneelivaraus.setRight(vbox);



        



        

        Btakaisin = new Button("takaisin");
        
		

        paneelivaraus.setBottom(Btakaisin);
        Btakaisin.setOnAction(e -> window.setScene(paasivu));






        

         textfield = new TextField();

        //textfield.setPadding(new Insets(25, 25, 25,0));

        //textfield.setMaxWidth(150);

        //textfield.setMaxHeight(5);

        //BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));



         textfield2 = new TextField();

        /*textfield2.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield2, new Insets(10, 10, 10, 0));*/



         textfield3 = new TextField();

       /* textfield3.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield4 = new TextField();

       /* textfield4.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield5 = new TextField();

       /* textfield5.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield6 = new TextField();

        /*textfield6.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield7 = new TextField();

       /* textfield7.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield8 = new TextField();

        /*textfield8.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield9 = new TextField();

       /* textfield9.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



        Label label1 = new Label("Lasku_ID");



        Label label2 = new Label("Varaus_ID");

        Label label3 = new Label("Asiakas_ID");

        Label label4 = new Label("Nimi:");

        Label label5 = new Label("Lahiosoite:");

        Label label6 = new Label("Postitoimipaikka:");



        Label label7 = new Label("Postinumero:");



        Label label8 = new Label("Summa:");



        Label label9 = new Label("ALV:");

        

        VBox vbox2 = new VBox();

        vbox2.setMaxHeight(5);





        vbox2.getChildren().addAll(label1,textfield,label2,textfield2,label3,textfield3,label4,textfield4,label5,textfield5,

        label6,textfield6,label7,

        textfield7,label8,textfield8,label9,textfield9);



        paneelivaraus.setLeft(vbox2);







      // avataan tietokanta

		try {

			yhdista ();

		 } catch (SQLException se) {

            // SQL virheet

			JOptionPane.showMessageDialog(null, "Tapahtui virhe tietokantaa avattaessa." +se.getMessage() , "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {

            // JDBC virheet

            JOptionPane.showMessageDialog(null, "Tapahtui JDBCvirhe tietokantaa avattaessa.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		

    }

	



	/*

	Avataan tietokantayhteys

	*/

	public  void yhdista() throws SQLException, Exception {

		m_conn = null;

		String url = "jdbc:mariadb://localhost:3306/vp"; // palvelin = localhost, :portti annettu asennettaessa, tietokannan nimi

		try {

			// ota yhteys kantaan, kayttaja = root, salasana = root

			m_conn=DriverManager.getConnection(url,"root","Jiklaaee870");

		}

		catch (SQLException e) { // tietokantaan ei saada yhteyttä

			m_conn = null;

			throw e;

		}

		catch (Exception e ) { // JDBC ajuria ei löydy

			throw e;

		}

		

	}

	
	
	/*

	Suljetaan tietokantayhteys

	*/

	public  void sulje_kanta() throws SQLException, Exception {

		// suljetaan		

		try {

			// sulje yhteys kantaan

			m_conn.close ();

		}

		catch (SQLException e) { // tietokantavirhe

			throw e;

		}

		catch (Exception e ) { // muu virhe tapahtui

			throw e;

		}

		

	}

	

	/*

	Haetaan tietokannasta asiakkaan tiedot näytöllä olevan opiskelijaid:n perusteella ja näytetään tiedot lomakkeella

	*/

	public  void hae_tiedot() {

		// haetaan tietokannasta opiskelijaa, jonka opiskelija_id = txtOpiskelijaID 

		m_lasku = null;

		

		try {

			m_lasku = Lasku.haeLasku (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			JOptionPane.showMessageDialog(null, "Laskua ei loydy."+se.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			JOptionPane.showMessageDialog(null, "Laskua ei loydy."+e.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		if (m_lasku.getVarausId() == 0) {

		// muut virheet

			textfield.setText("");

			textfield2.setText("");

			textfield3.setText("");

			textfield4.setText("");

			textfield5.setText("");

            textfield6.setText("");

            textfield7.setText("");

            textfield8.setText("");

			textfield9.setText("");

			JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);

		}

		else

		{

			// naytetaan tiedot

			//textfield.setText(m_lasku.getLaskuId());

			textfield2.setText(""+m_lasku.getVarausId());

			textfield3.setText(""+m_lasku.getAsiakasId());

			textfield4.setText(m_lasku.getNimi());

			textfield5.setText(m_lasku.getLahiosoite());

			textfield6.setText(m_lasku.getPostitoimipaikka());

            textfield7.setText(m_lasku.getPostinro());

            textfield8.setText(""+m_lasku.getSumma());

            textfield9.setText(""+m_lasku.getAlv());

            

		}

		

	}

	/*

	Viedään näytöllä olevat tiedot opiskelijaoliolle ja kirjoitetaan ne tietokantaan

	*/

	public  void lisaa_tiedot() {

		// lisätään tietokantaan opiskelija

		//System.out.println("Lisataan...");

		boolean lasku_lisatty = true;

		//m_lasku = null;

		try {

			m_lasku = Lasku.haeLasku (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			lasku_lisatty = false;

			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe"+se.getMessage(), JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			lasku_lisatty = false;

			JOptionPane.showMessageDialog(null, "Tietokantavirhe.", "Tietokantavirhe"+e.getMessage(), JOptionPane.ERROR_MESSAGE);

		}
		System.out.print("täällä");
		//Lasku m_lasku = new Lasku();
		System.out.print(m_lasku.getVarausId()+"");
		System.out.print("täällä");
		if (m_lasku.getVarausId() != 0) {

		// opiskelija jo olemassa, näytetään tiedot

			lasku_lisatty = false;

			//textfield.setText(m_lasku.getLaskuId());
			
			textfield2.setText(""+m_lasku.getVarausId());

			textfield3.setText(""+m_lasku.getAsiakasId());

			textfield4.setText(m_lasku.getNimi());

			textfield5.setText(m_lasku.getLahiosoite());

			textfield6.setText(m_lasku.getPostitoimipaikka());

            textfield7.setText(m_lasku.getPostinro());

            textfield8.setText(""+m_lasku.getSumma());

            textfield9.setText(""+m_lasku.getAlv());

            

			JOptionPane.showMessageDialog(null, "Lasku on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);

		}

		else

		{
			
			// asetetaan tiedot oliolle
			
			m_lasku.setLaskuId(Integer.parseInt(textfield.getText()));

            m_lasku.setVarausId(Integer.parseInt(textfield2.getText()));

            m_lasku.setAsiakasId(Integer.parseInt(textfield3.getText()));

			m_lasku.setNimi(textfield4.getText());

            m_lasku.setLahiosoite(textfield5.getText());

            m_lasku.setPostitoimipaikka(textfield6.getText());

			m_lasku.setPostinro(textfield7.getText());

			m_lasku.setSumma(Double.parseDouble(textfield8.getText()));

			

			m_lasku.setAlv(Double.parseDouble(textfield9.getText()));

			try {

				// yritetään kirjoittaa kantaan

				m_lasku.lisaaLasku (m_conn);

			} catch (SQLException se) {

			// SQL virheet

				lasku_lisatty = false;

				JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

			//	 se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				lasku_lisatty = false;

				JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

			//	 e.printStackTrace();

			}finally {

				if (lasku_lisatty == true)

					JOptionPane.showMessageDialog(null, "laskun tiedot lisatty tietokantaan.");

			}

		

		}

		

	}

	
	
	
	
	
	
	/*

	Viedään näytöllä olevat tiedot opiskelijaoliolle ja muutetaan ne tietokantaan

	*/

	public  void muuta_tiedot() {

		//System.out.println("Muutetaan...");

			boolean lasku_muutettu = true;

		// asetetaan tiedot oliolle

        //m_lasku.setLaskuId(Integer.parseInt(textfield.getText()));

        m_lasku.setVarausId(Integer.parseInt(textfield2.getText()));

        m_lasku.setAsiakasId(Integer.parseInt(textfield3.getText()));

        m_lasku.setNimi(textfield4.getText());

        m_lasku.setLahiosoite(textfield5.getText());

        m_lasku.setPostitoimipaikka(textfield6.getText());

        m_lasku.setPostinro(textfield7.getText());

        m_lasku.setSumma(Double.parseDouble(textfield8.getText()));

        

        m_lasku.setAlv(Double.parseDouble(textfield9.getText()));

			

			try {

				// yritetään muuttaa (UPDATE) tiedot kantaan

				m_lasku.muutaLasku (m_conn);

			} catch (SQLException se) {

			// SQL virheet

				lasku_muutettu = false;

				JOptionPane.showMessageDialog(null, "Laskun tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				 //se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				lasku_muutettu = false;

				JOptionPane.showMessageDialog(null, "Laskun tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

				// e.printStackTrace();

			} finally {

				if (lasku_muutettu == true)

					JOptionPane.showMessageDialog(null, "Laskun tiedot muutettu.");

			}

		

	}

	public  void poista_tiedot() {

		// haetaan tietokannasta opiskelijaa, jonka opiskelija_id = txtOpiskelijaID 

		m_lasku = null;

		boolean lasku_poistettu = false;

		

		try {

			m_lasku = Lasku.haeLasku (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		if (m_lasku.getLaskuId() == 0) {

		// poistettavaa opiskelijaa ei löydy tietokannasta, tyhjennetään tiedot näytöltä

		textfield.setText("");

			textfield2.setText("");

			textfield3.setText("");

			textfield4.setText("");

			textfield5.setText("");

            textfield6.setText("");

            textfield7.setText("");

            textfield8.setText("");

			textfield9.setText("");

			JOptionPane.showMessageDialog(null, "Laskua ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);

			return; // poistutaan

		}

		else

		{

			// naytetaan poistettavan asiakkaan tiedot

           // textfield.setText(m_lasku.getLaskuId());

            textfield2.setText(""+m_lasku.getVarausId());

            textfield3.setText(""+m_lasku.getAsiakasId());

            textfield4.setText(m_lasku.getNimi());

            textfield5.setText(m_lasku.getLahiosoite());

            textfield6.setText(m_lasku.getPostitoimipaikka());

            textfield7.setText(m_lasku.getPostinro());

            textfield8.setText(""+m_lasku.getSumma());

            textfield9.setText(""+m_lasku.getAlv());

			

		}

		try {

			if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa laskun?")==0) {// vahvistus ikkunassa

				m_lasku.poistaLasku (m_conn);

				lasku_poistettu = true;

			}

			} catch (SQLException se) {

			// SQL virheet

				JOptionPane.showMessageDialog(null, "Laskun tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				// se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				JOptionPane.showMessageDialog(null, "Laskun tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

				// e.printStackTrace();

			} finally {

				if (lasku_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui

                    textfield.setText("");

                    textfield2.setText("");

                    textfield3.setText("");

                    textfield4.setText("");

                    textfield5.setText("");

                    textfield6.setText("");

                    textfield7.setText("");

                    textfield8.setText("");

                    textfield9.setText("");

					JOptionPane.showMessageDialog(null, "Laskun tiedot poistettu tietokannasta.");

					m_lasku = null;

                }

            }
	
	}
	
	
	
	}

    
