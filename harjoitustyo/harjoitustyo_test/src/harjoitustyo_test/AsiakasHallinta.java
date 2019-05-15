package harjoitustyo_test;

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

import javax.swing.JOptionPane;








    public class AsiakasHallinta{

    BorderPane paneelivaraus;

    Button button,Btakaisin,btnRight,btnRight2,btn1,btn2,btnRight3,btnRight4,btnRight5;

    Label paaotsikko,joku;

    Stage window;

    VBox paneelit,vbox,vbox2;

    Scene paasivu;

    TextField textfield,textfield2,textfield3,textfield4,textfield5,textfield6,textfield7,textfield8;

    StackPane stackPane;

    HBox hbox;



    Asiakas m_asiakas = new Asiakas();

    Connection m_conn;



    

    public AsiakasHallinta(BorderPane paneelivaraus, Stage window, Scene paasivu,VBox vbox, Connection m_conn){

		this.paneelivaraus = paneelivaraus;

		this.window = window;

    this.paasivu = paasivu;

    this.vbox = vbox;  
    this.m_conn = m_conn;

   



        //window.setTitle("Asiakas hallinta");

      



       //StackPane layout = new StackPane();



       

       



        //paasivu = new Scene(paneelivaraus,400,400);





				btnRight = new Button("Hae");
				
				btnRight.setOnAction(e -> hae_tiedot());

        btnRight.setPadding(new Insets(30, 30, 30, 30));

        btnRight.setMaxWidth(120);

        BorderPane.setMargin(btnRight, new Insets(100, 100, 0, 100));



				btnRight2 = new Button("Muuta");
				
				btnRight2.setOnAction(e -> muuta_tiedot());

        btnRight2.setPadding(new Insets(30, 30, 30, 30));

        btnRight2.setMaxWidth(120);

        BorderPane.setMargin(btnRight2, new Insets(30, 100, 100, 100));



        btnRight3 = new Button("Lisaa");

				btnRight3.setPadding(new Insets(30, 30, 30, 30));
				
				btnRight3.setOnAction(e -> lisaa_tiedot());

        btnRight3.setMaxWidth(120);

        BorderPane.setMargin(btnRight3, new Insets(30, 100, 100, 100));



				btnRight4 = new Button("Poista");
				
				btnRight4.setOnAction(e -> poista_tiedot());

        btnRight4.setPadding(new Insets(30,30, 30,30));

        btnRight4.setMaxWidth(120);

        BorderPane.setMargin(btnRight4, new Insets(30, 100, 100, 100));



				btnRight5 = new Button("Tyhjenna");
				
				btnRight5.setOnAction((e) ->{ 
					
				textfield.setText("");

				textfield2.setText("");
	
				textfield3.setText("");
	
				textfield4.setText("");
	
				textfield5.setText("");
	
				textfield6.setText("");
	
				textfield7.setText("");
	
				textfield8.setText(""); });
	

				btnRight5.setPadding(new Insets(30, 30, 30, 30));
				
				btnRight5.setMaxWidth(120);

        BorderPane.setMargin(btnRight5, new Insets(30, 100, 100, 100)); 



        





        //vbox = new VBox();

        
        vbox.setPadding(new Insets(0,25,0,0));


        HBox.setMargin(vbox, new Insets(0, 0, 0, 600));

        vbox.getChildren().addAll(btnRight,btnRight2,btnRight3,btnRight4,btnRight5);



       



        paneelivaraus.setRight(vbox);



        



        

        Btakaisin = new Button("takaisin");

		

        paneelivaraus.setBottom(Btakaisin);
        Btakaisin.setOnAction(e -> window.setScene(paasivu));






        

         textfield = new TextField();

       /* textfield.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



        textfield2 = new TextField();

       /* textfield2.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield2, new Insets(10, 10, 10, 0));*/



         textfield3 = new TextField();

       /* textfield3.setPadding(new Insets(25, 25, 25,0));

        BorderPane.setMargin(textfield, new Insets(10, 10, 10, 0));*/



         textfield4 = new TextField();

        /*textfield4.setPadding(new Insets(25, 25, 25,0));

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



      

        Label label1 = new Label("Asiakas_ID");



        Label label2 = new Label("Etunimi");

        Label label3 = new Label("Sukunimi");

        Label label4 = new Label("lahiosoite");

        Label label5 = new Label("Postinumero:");

        Label label6 = new Label("Postitoimipaikka:");

        Label label7 = new Label("Email:");



        Label label8 = new Label("Puhelinnumero:");



     









        

        VBox vbox2 = new VBox();

        vbox2.setMaxHeight(5);
        vbox2.setPadding(new Insets(0,0,0,25));




        vbox2.getChildren().addAll(label1,textfield,label2,textfield2,label3,textfield3,label4,textfield4,label5,textfield5

        ,label6,textfield6,label7,textfield7,label8,textfield8);



        paneelivaraus.setLeft(vbox2);















     



      





      //window.setScene(paasivu);

      //window.show();

    





		

		

    }

   


	/*

	Haetaan tietokannasta asiakkaan tiedot n�yt�ll� olevan opiskelijaid:n perusteella ja n�ytet��n tiedot lomakkeella

	*/

	public  void hae_tiedot() {

		// haetaan tietokannasta opiskelijaa, jonka opiskelija_id = txtOpiskelijaID 

		//m_asiakas = null;

		

		try {

			m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy."+se.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy."+e.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		if (m_asiakas.getEtunimi() == null) {

		// muut virheet

			textfield.setText("");

			textfield2.setText("");

			textfield3.setText("");

			textfield4.setText("");

			textfield5.setText("");

			textfield6.setText("");

      textfield7.setText("");

      textfield8.setText("");

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);

		}

		else

		{

			// naytetaan tiedot

			textfield2.setText(m_asiakas.getEtunimi());

			textfield3.setText(m_asiakas.getSukunimi());

			textfield4.setText(m_asiakas.getLahiosoite());

			textfield5.setText(m_asiakas.getPostinro());

			textfield6.setText(m_asiakas.getPostitoimipaikka());

			textfield7.setText(m_asiakas.getEmail());

			textfield8.setText(m_asiakas.getPuhelinnro());

		}

		

	}

	/*

	Vied��n n�yt�ll� olevat tiedot opiskelijaoliolle ja kirjoitetaan ne tietokantaan

	*/

	public  void lisaa_tiedot() {

		// lis�t��n tietokantaan opiskelija

		//System.out.println("Lisataan...");

		boolean asiakas_lisatty = true;

		//m_asiakas = null;

		try {

			m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			asiakas_lisatty = false;

			JOptionPane.showMessageDialog(null, "Tietokantavirhe."+se.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			asiakas_lisatty = false;

			JOptionPane.showMessageDialog(null, "Tietokantavirhe."+e.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		if (m_asiakas.getEtunimi() != null) {

		// opiskelija jo olemassa, n�ytet��n tiedot

			asiakas_lisatty = false;

			textfield2.setText(m_asiakas.getEtunimi());

			textfield3.setText(m_asiakas.getSukunimi());

			textfield4.setText(m_asiakas.getLahiosoite());

			textfield5.setText(m_asiakas.getPostinro());

			textfield6.setText(m_asiakas.getPostitoimipaikka());

			textfield7.setText(m_asiakas.getEmail());

			textfield8.setText(m_asiakas.getPuhelinnro());

			JOptionPane.showMessageDialog(null, "Asiakas on jo olemassa.", "Virhe", JOptionPane.ERROR_MESSAGE);

		}

		else

		{

			// asetetaan tiedot oliolle

			m_asiakas.setAsiakasId(Integer.parseInt(textfield.getText()));

			m_asiakas.setEtunimi(textfield2.getText());

			m_asiakas.setSukunimi(textfield3.getText());

			m_asiakas.setLahiosoite(textfield4.getText());

			m_asiakas.setPostinro(textfield5.getText());

			m_asiakas.setPostitoimipaikka(textfield6.getText());

			m_asiakas.setEmail(textfield.getText());

			m_asiakas.setPuhelinnro(textfield.getText());

			try {

				// yritet��n kirjoittaa kantaan

				m_asiakas.lisaaAsiakas (m_conn);

			} catch (SQLException se) {

			// SQL virheet

				asiakas_lisatty = false;

				JOptionPane.showMessageDialog(null, "Asiakkaan lisaaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				 se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				asiakas_lisatty = false;

				JOptionPane.showMessageDialog(null, "Asiakkaan lisaaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

				 e.printStackTrace();

			}finally {

				if (asiakas_lisatty == true)

					JOptionPane.showMessageDialog(null, "Asiakkaan tiedot lisatty tietokantaan.");

			}

		

		}

		

	}

	/*

	Vied��n n�yt�ll� olevat tiedot opiskelijaoliolle ja muutetaan ne tietokantaan

	*/

	public  void muuta_tiedot() {

		//System.out.println("Muutetaan...");

			boolean asiakas_muutettu = true;

		// asetetaan tiedot oliolle

		m_asiakas.setEtunimi(textfield2.getText());

			m_asiakas.setSukunimi(textfield3.getText());

			m_asiakas.setLahiosoite(textfield4.getText());

			m_asiakas.setPostinro(textfield5.getText());

			m_asiakas.setPostitoimipaikka(textfield6.getText());

			m_asiakas.setEmail(textfield7.getText());

			m_asiakas.setPuhelinnro(textfield8.getText());

			try {

				// yritet��n muuttaa (UPDATE) tiedot kantaan

				m_asiakas.muutaAsiakas (m_conn);

			} catch (SQLException se) {

			// SQL virheet

				asiakas_muutettu = false;

				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen muuttaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				 //se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				asiakas_muutettu = false;

				JOptionPane.showMessageDialog(null, "asiakkaan tietojen muuttaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

				// e.printStackTrace();

			} finally {

				if (asiakas_muutettu == true)

					JOptionPane.showMessageDialog(null, "Asiakkaan tiedot muutettu.");

			}

		

	}

	public  void poista_tiedot() {

		// haetaan tietokannasta opiskelijaa, jonka opiskelija_id = txtOpiskelijaID 

		m_asiakas = null;

		boolean asiakas_poistettu = false;

		

		try {

			m_asiakas = Asiakas.haeAsiakas (m_conn, Integer.parseInt(textfield.getText()));

		} catch (SQLException se) {

		// SQL virheet

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e) {

		// muut virheet

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

		}

		if (m_asiakas.getEtunimi() == null) {

		// poistettavaa opiskelijaa ei l�ydy tietokannasta, tyhjennet��n tiedot n�yt�lt�

			textfield.setText("");

			textfield2.setText("");

			textfield3.setText("");

			textfield4.setText("");

			textfield5.setText("");

			textfield6.setText("");

      textfield7.setText("");

      textfield8.setText("");

			JOptionPane.showMessageDialog(null, "Asiakasta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE);

			return; // poistutaan

		}

		else

		{

			// naytetaan poistettavan asiakkaan tiedot

			textfield2.setText(m_asiakas.getEtunimi());

			textfield3.setText(m_asiakas.getSukunimi());

			textfield4.setText(m_asiakas.getLahiosoite());

			textfield5.setText(m_asiakas.getPostinro());

			textfield6.setText(m_asiakas.getPostitoimipaikka());

			textfield7.setText(m_asiakas.getEmail());

			textfield8.setText(m_asiakas.getPuhelinnro());

		}

		try {

			if (JOptionPane.showConfirmDialog(null, "Haluatko todella poistaa asiakkaan?")==0) {// vahvistus ikkunassa

				m_asiakas.poistaAsiakas (m_conn);

				asiakas_poistettu = true;

			}

			} catch (SQLException se) {

			// SQL virheet

				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen poistaminen ei onnistu.", "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				// se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				JOptionPane.showMessageDialog(null, "Asiakkaan tietojen poistaminen ei onnistu.", "Virhe", JOptionPane.ERROR_MESSAGE);

				// e.printStackTrace();

			} finally {

				if (asiakas_poistettu == true) { // ainoastaan, jos vahvistettiin ja poisto onnistui

					textfield.setText("");

					textfield2.setText("");

					textfield3.setText("");

					textfield4.setText("");

					textfield5.setText("");

					textfield6.setText("");

					textfield7.setText("");

					textfield8.setText("");

					JOptionPane.showMessageDialog(null, "Asiakkaan tiedot poistettu tietokannasta.");

					m_asiakas = null;

				}

			}

			

		

	}
	}
    

