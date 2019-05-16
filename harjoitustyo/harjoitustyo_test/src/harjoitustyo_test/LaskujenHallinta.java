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
import java.util.ArrayList;
import java.awt.*;

import java.awt.event.*;

//import javax.swing.*;

import javax.swing.JOptionPane;







public class LaskujenHallinta{






    BorderPane paneelivaraus;

    Button Btakaisin,btnRight,btnRight2,btnRight3,btnRight4,btnRight5,btnRight6, btnRight7;

    Label paaotsikko,joku;

    Stage window;

    VBox paneelit,vbox,vbox2;

    Scene paasivu;

    TextField textfield,textfield2,textfield3,textfield4,textfield5,textfield6,textfield7,textfield8,textfield9;

    StackPane stackPane;

	HBox hbox;
	
		Lasku m_lasku = new Lasku();

		
	

		Connection m_conn;

		
		haeTiedot m_tiedot;
		


		

  



    

    public LaskujenHallinta(BorderPane paneelivaraus, Stage window, Scene paasivu,VBox vbox, Connection m_conn){

		this.paneelivaraus = paneelivaraus;

		this.window = window;

    this.paasivu = paasivu;

		this.vbox = vbox;  
		this.m_conn = m_conn;

		m_tiedot = new haeTiedot(m_conn);

      

      



     



// tehdään napit ja toiminnot

		btnRight = new Button("Hae");

        btnRight.setOnAction(e -> hae_tiedot());

        btnRight.setPadding(new Insets(25, 25, 25, 25));

        btnRight.setMaxWidth(120);

        BorderPane.setMargin(btnRight, new Insets(100, 100, 100, 100));



        

        btnRight2 = new Button("Muuta");

        btnRight2.setOnAction(e -> muuta_tiedot());

        btnRight2.setPadding(new Insets(25, 25, 25, 25));

        btnRight2.setMaxWidth(120);

        BorderPane.setMargin(btnRight2, new Insets (100,100, 100, 100));

        



       btnRight3 = new Button("Lisaa");

        btnRight3.setOnAction(e -> lisaa_tiedot());

        btnRight3.setPadding(new Insets(25, 25, 25, 25));

        btnRight3.setMaxWidth(120);

        BorderPane.setMargin(btnRight3, new Insets(100, 100, 100, 100));



         btnRight4 = new Button("Poista");

        btnRight4.setOnAction(e -> poista_tiedot());

        btnRight4.setPadding(new Insets(25,25, 25,25));

        btnRight4.setMaxWidth(120);

        BorderPane.setMargin(btnRight4, new Insets(100, 100, 100, 100));



		 btnRight5 = new Button("Tyhjenna");
		 
		 btnRight5.setOnAction((e) ->{ 
					
			textfield.setText("");

			textfield2.setText("");

			textfield3.setText("");

			textfield4.setText("");

			textfield5.setText("");

			textfield6.setText("");

			textfield7.setText("");

			textfield8.setText("");

		    textfield9.setText(""); });

        btnRight5.setPadding(new Insets(25, 25, 25, 25));

        btnRight5.setMaxWidth(120);

				BorderPane.setMargin(btnRight5, new Insets(100, 100, 100, 100));
				

				
				btnRight6 = new Button("Hae tiedot");

        btnRight6.setOnAction(e -> hae_tiedot2());

        btnRight6.setPadding(new Insets(25,25, 25,25));

        btnRight6.setMaxWidth(120);

        BorderPane.setMargin(btnRight4, new Insets(100, 100, 100, 100));

        
        
		btnRight7 = new Button("Lähetä lasku");

        //btnRight7.setOnAction(e -> hae_tiedot2());

        btnRight7.setPadding(new Insets(25,25, 25,25));

        btnRight7.setMaxWidth(120);

        BorderPane.setMargin(btnRight7, new Insets(100, 100, 100, 100));
        

//lisätään napit vboxiin

        vbox.getChildren().addAll(btnRight,btnRight2,btnRight3,btnRight4,btnRight5,btnRight6,btnRight7);





        paneelivaraus.setRight(vbox);



        



        

        Btakaisin = new Button("takaisin");
        Btakaisin.setMaxWidth(100);
        Btakaisin.setMaxHeight(60);
        Btakaisin.setPadding(new Insets(5,5, 5,5));
				paneelivaraus.setBottom(Btakaisin);
				paneelivaraus.setPadding(new Insets(0,5, 15,5));
				// nappi joka vie takaisin pääsivulle
        Btakaisin.setOnAction(e -> window.setScene(paasivu));





//tehdään tekstikentät
        

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

//tehdään labelit tekstikenttiin

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

        vbox2.setPadding(new Insets(0,0,0,25));
        vbox.setPadding(new Insets(0,25,0,0));






		

    }



// haetaan asiakkaan tiedot varaus_idn avulla ilman että laskua on tehty
	public void hae_tiedot2(){
		int varausid = 0;
		ArrayList<String> palautus = new ArrayList<String>();

		try {
			varausid = Integer.parseInt(textfield2.getText());
		

//haku
palautus = m_tiedot.haetaanAsiakas (varausid);

} catch (Exception se) {  JOptionPane.showMessageDialog(null, "Varausta ei loydy."+se.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

	
}
	



//katotaan onnistuiko haku

if(palautus.isEmpty()==false){



String asiakas_id= palautus.get(0);

String etunimi= palautus.get(1);

String sukunimi= palautus.get(2);

String lahiosoite= palautus.get(3);

String postitoimipaikka= palautus.get(4);

String postinro= palautus.get(5);



textfield3.setText(asiakas_id);

textfield4.setText(etunimi +" "+sukunimi);

textfield5.setText(lahiosoite);

textfield6.setText(postitoimipaikka);

			textfield7.setText(postinro);

			//haku
			 double summa = m_tiedot.haetaanHinta (varausid);
			textfield8.setText(""+summa);
			textfield9.setText(""+m_tiedot.getAlv());
			
			

}else{ 
	textfield.setText("");

	textfield2.setText("");

	textfield3.setText("");

	textfield4.setText("");

	textfield5.setText("");

				textfield6.setText("");

				textfield7.setText("");

				textfield8.setText("");

	textfield9.setText(""); 

	JOptionPane.showMessageDialog(null, "varausta ei loydy.", "Virhe", JOptionPane.ERROR_MESSAGE); 


	
}


//ilmoita käyttäjälle ei onnistunnut 

}
	
// haetaan summa
public void hae_tiedot3(){

	//ArrayList<String> palautus = new ArrayList<String>();

	






//katotaan onnistuiko haku


			




//ilmoita käyttäjälle ei onnistunnut 

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

	Viedään näytöllä olevat tiedot oliolle ja kirjoitetaan ne tietokantaan

	*/

	public  void lisaa_tiedot() {

		// lisätään tietokantaan lasku

		

		boolean lasku_lisatty = true;

		

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

		if (m_lasku.getVarausId() != 0) {

		// lasku jo olemassa, näytetään tiedot

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

			try {
	

			m_lasku.setLaskuId(Integer.parseInt(textfield.getText()));

            m_lasku.setVarausId(Integer.parseInt(textfield2.getText()));

            m_lasku.setAsiakasId(Integer.parseInt(textfield3.getText()));

			m_lasku.setNimi(textfield4.getText());

            m_lasku.setLahiosoite(textfield5.getText());

            m_lasku.setPostitoimipaikka(textfield6.getText());

			m_lasku.setPostinro(textfield7.getText());

			m_lasku.setSumma(Double.parseDouble(textfield8.getText()));

			

			m_lasku.setAlv(Double.parseDouble(textfield9.getText()));

		} catch (NumberFormatException exception) {

			JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu."+exception.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				 exception.printStackTrace();

			
		}

			try {

				// yritetään kirjoittaa kantaan

				m_lasku.lisaaLasku (m_conn);

			} catch (SQLException se) {

			// SQL virheet

				lasku_lisatty = false;

				JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu."+se.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);

				 se.printStackTrace();

			} catch (Exception e) {

			// muut virheet

				lasku_lisatty = false;

				JOptionPane.showMessageDialog(null, "Laskun lisaaminen ei onnistu."+e.getMessage(), "Virhe", JOptionPane.ERROR_MESSAGE);

				 e.printStackTrace();

			}finally {

				if (lasku_lisatty == true)

					JOptionPane.showMessageDialog(null, "laskun tiedot lisatty tietokantaan.");

			}

		

		}

		

	}

	/*

	Viedään näytöllä olevat tiedot oliolle ja muutetaan ne tietokantaan

	*/

	public  void muuta_tiedot() {

		

			boolean lasku_muutettu = true;

		// asetetaan tiedot oliolle

        //m_lasku.setLaskuId(Integer.parseInt(textfield.getText()));
try {
	
       m_lasku.setVarausId(Integer.parseInt(textfield2.getText()));

        m_lasku.setAsiakasId(Integer.parseInt(textfield3.getText()));

        m_lasku.setNimi(textfield4.getText());

        m_lasku.setLahiosoite(textfield5.getText());

        m_lasku.setPostitoimipaikka(textfield6.getText());

        m_lasku.setPostinro(textfield7.getText());

        m_lasku.setSumma(Double.parseDouble(textfield8.getText()));

        

		m_lasku.setAlv(Double.parseDouble(textfield9.getText()));
	
	} catch (NumberFormatException exception) {

		JOptionPane.showMessageDialog(null, "Laskun tietojen muuttaminen ei onnistu."+exception.getMessage(), "Tietokantavirhe", JOptionPane.ERROR_MESSAGE);
		
	}
	 

			

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

		// haetaan tietokannasta laskua, jonka lasku_id = textfield 

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

		// poistettavaa laskua ei löydy tietokannasta, tyhjennetään tiedot näytöltä

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

    

    
