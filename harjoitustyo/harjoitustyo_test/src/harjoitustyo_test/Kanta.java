package harjoitustyo_test;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

public class Kanta {
	// JDBC driver name and database URL
	Connection conn;
	int mokkiID = 0;
	int toimiID = 0;
	int asiakasID = 0;
	int hinta = 0;
	int varaus = 0;
	
	public Kanta(Connection conn) {
		this.conn = conn;
		conn = null;
	
	}//end main
	
	//Haetaan kannasta asiakkaan nimi ja puhelinnumero tiedot
	public String haetaanAsiakas(int id){
		String sql = "SELECT etunimi, sukunimi, puhelinnro" 
				+ " FROM Asiakas WHERE asiakas_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
			//Jos löydetään tuloksia
			if (tulosjoukko.next () == true){
				//Luodaan palautettava merkkijono jossa asiakkaan etunimi ja sukunimi
				haku = "Asiakas: "+tulosjoukko.getString("etunimi")+" "+tulosjoukko.getString("sukunimi");
				asiakasID = id;
			//Jos asiakasta ei löydy palautetaan siitä tieto
			}else{
				haku = "Asiakasta ei löydy";
				asiakasID = 0;
			}


		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return haku;
	}//haetaanAsiakas

	//Haetaan toimipaikka
	public String haetaanPaikka(int id){
		String sql = "SELECT nimi" 
				+ " FROM Toimipiste WHERE toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
				//Palautetaan toimipaikan nimi
				if (tulosjoukko.next () == true){
				
					haku = "Toimipaikka: \n"+tulosjoukko.getString("nimi");
				}else{
					haku = "Toimipaikkaa ei löydy";
				}

			
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return haku;
	}//haetaanToimipiste
	
	//Haetaan kannasta mökki
	public String haetaanMokki(int id, int toimipiste){
		String sql = "SELECT nimi" 
				+ " FROM Mokki WHERE Mokki_id = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			lause.setInt( 2, toimipiste);// asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				
			//Palautetaan löydetty mökki ja päivitetään muuttujat mokkiID ja toimiID
				if (tulosjoukko.next () == true){

					haku = "Mökki: \n"+tulosjoukko.getString("nimi");
					mokkiID = id;
					toimiID = toimipiste;
				//Palautetaan ei löytynyt ja nollataan muuttujat mokkiID ja toimiID
				}else{
					haku = "Mökkiä ei löydy";
					mokkiID = 0;
					toimiID = 0;
				}

			
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return haku;
	}//haetaanMökki
	
	//Tehdään varaus
	public int varaaMokki(int varausID, Date varattu, Date vahvistus, Date alotus, Date lopetus){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan kysely
		String sql = "INSERT INTO Varaus "
		+ "(varaus_id, asiakas_id, toimipiste_id , mokki_id , varattu_pvm , vahvistus_pvm, varattu_alkupvm, varattu_loppupvm) "
		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		if(asiakasID != 0 && toimiID != 0 &&mokkiID != 0){
		onnistuiko = 1;	
		varaus = varausID;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, varausID);
			lause.setInt( 2, asiakasID);
			lause.setInt(3, toimiID); 
			lause.setInt(4, mokkiID); 
			lause.setDate(5, varattu);
			lause.setDate(6, vahvistus);
			lause.setDate(7, alotus);
			lause.setDate(8, lopetus);
			
			// suorita sql-lause
			lkm = lause.executeUpdate();	
			if (lkm == 0) {
				onnistuiko = 2;
				System.out.println("Ei onnistu...");
			}
		} catch (SQLException se) {
            // SQL virheet
			onnistuiko = 3;
            //se.printStackTrace();
        } catch (Exception e) {
            // JDBC virheet
            e.printStackTrace();
		}
	}	
		//Palautetaan varauksen tulos 
		return onnistuiko;
	}
	
	//Haetaan kaikkien varauksien päivät, jotta voidaan tarkistaa ettei päälekkäisiä varauksia voi tehdä
	public ArrayList<Pari> haetaanPaivat(int id){
		String sql = "SELECT varattu_alkupvm, varattu_loppupvm" 
				+ " FROM Varaus WHERE mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		Date haku = null;
		//Luodaan paivat arraylist johon asetetaan Pari tyyppiset oliot ja siten haun tulokset
		ArrayList<Pari> paivat = new ArrayList<Pari>();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				
				//Otetaan alku ja loppupäivä ja lisätään ne pari olioon ja tämä taas paivat arraylistiin
				if (tulosjoukko.next () == true){
					LocalDate paiv = tulosjoukko.getDate("varattu_alkupvm").toLocalDate();
					LocalDate loppupaiv = tulosjoukko.getDate("varattu_loppupvm").toLocalDate();
					Pari pari = new Pari(paiv,loppupaiv);
					paivat.add(pari);
					while(tulosjoukko.next()){
						paiv = tulosjoukko.getDate("varattu_alkupvm").toLocalDate();
						loppupaiv = tulosjoukko.getDate("varattu_loppupvm").toLocalDate();
						pari = new Pari(paiv,loppupaiv);
						paivat.add(pari);
						
					}
				}else{
					paivat = null;
				}
				//Suljetaan yhteydet
				tulosjoukko.close();
				lause.close();
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return paivat;
	}//haetaanPaivat
	
	//Haetaan tietyn mökin hinta
	public int haetaanMokkiHinta(){
		String sql = "SELECT hinta" 
				+ " FROM Mokki WHERE mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		int id = mokkiID;
		int haku = 0;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				
			//Palautetaan hinta ja kasvatetaan tämän olion hinta muuttujaa palautetun arvon verran
				if (tulosjoukko.next () == true){
					haku = tulosjoukko.getInt("hinta");
					hinta += haku;
				}else{
					haku = 0;
				}
				//Suljetaan yhteys
				tulosjoukko.close();
				lause.close();
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}
		
		return haku;
	}//haetaanPaivat
	
	//Lisätään palvelu
	public String lisaaPalv(int id){
		//Haetaan palvelun hinta ja nimi
		String sql = "SELECT nimi, hinta" 
				+ " FROM Palvelu WHERE toimipiste_id = ? AND palvelu_id = ?"; // ehdon arvo asetetaan jäljempänä

		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		int lkm =0;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, toimiID); // asetetaan where ehtoon (?) arvo
			lause.setInt( 2, id);
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	

				if (tulosjoukko.next () == true){
					//Lisätään haluttu palvelu
					String sql2 = "INSERT INTO Varauksen_palvelut "
							+ "(varaus_id, palvelu_id, lkm ) "
							+ " VALUES (?, ?, ?)";
	
					// luo PreparedStatement-olio sql-lauseelle
					lause = conn.prepareStatement(sql2);
					// laitetaan arvot INSERTtiin
					lause.setInt( 1, varaus);
					lause.setInt(2, id); 
					lause.setInt(3, 1); 
					
					// suorita sql-lause
					lkm = lause.executeUpdate();	
					if (lkm == 0) {
						haku = null;
						System.out.println("Ei onnistu...");
					}
					//Palautetaan lisätyn palvelun nimi ja hinta
					else{
						hinta += tulosjoukko.getInt("hinta");
						haku = tulosjoukko.getString("nimi");
					}

				}else{
					haku = null;
				}
				lause.close();
				tulosjoukko.close();
			
		} catch (SQLException se) {
			// SQL virheet
			//se.printStackTrace();
			haku =null;
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}
		
		return haku;
	}//haetaanToimipiste
	
	//Voidaan hakea hinta muuttuja
	public int gethinta(){
		return hinta;
	}
	
	//Poistetaan palvelu
	public String poistaPalvelu(int palveluid){
		//Luodaan poisto lause
		String sql = "DELETE FROM  Varauksen_palvelut  WHERE varaus_id  = ? AND palvelu_id = ?";
		PreparedStatement lause = null;
		String tulos = null;
		ResultSet tulosjoukko = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
			lause.setInt( 1, varaus);
			lause.setInt( 2, palveluid);
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				tulos = null;
				
			//Haetaan poistetun hinta jotta voidaan vähentää summaa, palauttaa hinta muuttujan vähennettynä poistetun hinta
			}else{
				tulos = "onnistui";
				sql = "SELECT nimi, hinta" 
						+ " FROM Palvelu WHERE toimipiste_id = ? AND palvelu_id = ?"; // ehdon arvo asetetaan jäljempänä

					// luo PreparedStatement-olio sql-lauseelle
					lause = conn.prepareStatement(sql);
					lause.setInt( 1, toimiID); // asetetaan where ehtoon (?) arvo
					lause.setInt( 2, palveluid);
					tulosjoukko = lause.executeQuery();	
					if (tulosjoukko.next () == true) {
					hinta -= tulosjoukko.getInt("hinta");
					}
			}
			//Suljetaan yhteys
			lause.close();
			tulosjoukko.close();
			} catch (SQLException se) {
            // SQL virheet
				tulos = null;
				se.printStackTrace();
             } catch (Exception e) {
            // JDBC virheet
               
		}
		return tulos;
	}
	
	//Haetaan varaus
	public VarausTiedot haetaanVaraus(int id){
		String sql = "SELECT asiakas_id, toimipiste_id, mokki_id, varattu_alkupvm,varattu_loppupvm " 
				+ " FROM Varaus WHERE varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		//Luodaan olio, jonka avulla voidaan palauttaa haetut tiedot
		VarausTiedot varausolio = new VarausTiedot();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
				//Haetaan tuloksesta tiedot ja asetetaan ne varausolio olioon
				if (tulosjoukko.next () == true){
					varaus = id; 
					asiakasID = tulosjoukko.getInt("asiakas_id");					
					toimiID = tulosjoukko.getInt("toimipiste_id");
					mokkiID = tulosjoukko.getInt("mokki_id");
					Date alkupaiv = tulosjoukko.getDate("varattu_alkupvm");
					Date loppupaiv = tulosjoukko.getDate("varattu_loppupvm");
					varausolio.lisaatiedot(true,asiakasID, toimiID, mokkiID,alkupaiv,loppupaiv );
				}
				lause.close();
				tulosjoukko.close();
			
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}
		//palautetaan varausolio
		return varausolio;
	}//haetaanVaraus

	//Päivitetään varausta
	public int paivitaVaraus(int varausID, Date alotus, Date lopetus){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan päivitys lause
		String sql = "UPDATE Varaus "
		+ "SET asiakas_id = ?, toimipiste_id = ?, mokki_id = ?, varattu_alkupvm = ?, varattu_loppupvm = ?"
		+ " WHERE varaus_id = ?";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		if(asiakasID != 0 && toimiID != 0 &&mokkiID != 0){
		onnistuiko = 1;	
		varaus = varausID;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot Updateen

			lause.setInt( 1, asiakasID);
			lause.setInt(2, toimiID); 
			lause.setInt(3, mokkiID); 
			lause.setDate(4, alotus);
			lause.setDate(5, lopetus);
			lause.setInt( 6, varausID);
			
			// suorita sql-lause
			lkm = lause.executeUpdate();	
			if (lkm == 0) {
				onnistuiko = 2;
				System.out.println("Ei onnistu...");
			}
			
			lause.close();
		} catch (SQLException se) {
            // SQL virheet
			onnistuiko = 3;
            //se.printStackTrace();
        } catch (Exception e) {
            // JDBC virheet
            e.printStackTrace();
		}
	}
		return onnistuiko;
	}
	
	//Poistetaan varaus
	public boolean poistaVaraus(int varattu){
		String sql = "DELETE FROM Varaus WHERE varaus_id = ?"; //Luodaan sql lause
		PreparedStatement lause = null;
		boolean poisto = false;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
			lause.setInt( 1, varattu);
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				poisto = false;
			}else{
				poisto = true;
			}
			lause.close();
			} catch (SQLException se) {
            // SQL virheet
				se.printStackTrace();
               poisto = false;
             } catch (Exception e) {
            // JDBC virheet
               
		}
		return poisto; // toiminto ok
	}
	
	
	//Asetetaan liput nollaksi
	public void asiakaslippu(){
		asiakasID = 0;
	}
	public void toimilippu(){
		toimiID = 0;
	}
	public void mokkilippu(){
		mokkiID = 0;
	}

	//Haetaan palvelut
	public ArrayList<String> haePalvelut(int id){
		//Luodaan arraylist, jolla palautetaan varauksen palvelut
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "5");
		try {
			// luo PreparedStatement-olio sql-lauseelle
			String sql = "SELECT palvelu_id " 
					+ " FROM Varauksen_palvelut WHERE varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 


			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				int i = 0;
				//Käydään sql kyselyn tulokset lävitse ja lisätään ne arraylistaan
				while(tulosjoukko.next () == true){
					i++;
					tulokset.add(i,""+tulosjoukko.getInt("palvelu_id"));
					tulokset.set(0,"1");
				}
			
		} catch (SQLException se) {
			// SQL virheet
			tulokset.set(0,"3");
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			tulokset.set(0,"2");
			e.printStackTrace();
		}

		return tulokset;
	}
	
}//end JDBCExample

