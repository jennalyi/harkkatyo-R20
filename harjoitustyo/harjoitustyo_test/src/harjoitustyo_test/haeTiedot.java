package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class haeTiedot {
	Connection conn;
	int varausid;
	double alvi;
	
	public haeTiedot(Connection conn){
		this.conn = conn;
		
	}
	//Haetaan kannasta asiakkaan nimi ja puhelinnumero tiedot
	public ArrayList<String> haetaanAsiakas(int id){
		String sql = "SELECT asiakas_id,etunimi, sukunimi, lahiosoite, postitoimipaikka, postinro " 
				+ " FROM Asiakas WHERE asiakas_id = "
				+ "(select asiakas_id from Varaus where varaus_id = ?)"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		ArrayList<String> palautus = new ArrayList<String>();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			palautus.add("onnistui");
			//Jos löydetään tuloksia
			if (tulosjoukko.next () == true){
				palautus.add(""+tulosjoukko.getInt("asiakas_id"));
				palautus.add(tulosjoukko.getString("etunimi"));
				palautus.add(tulosjoukko.getString("sukunimi"));
				palautus.add(tulosjoukko.getString("lahiosoite"));
				palautus.add(tulosjoukko.getString("postitoimipaikka"));
				palautus.add(tulosjoukko.getString("postinro"));
				
			//Jos asiakasta ei löydy palautetaan siitä tieto
			}else{
				palautus.set(0,"ei onnistunut");
				
			}


		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return palautus;
	}//haetaanAsiakas
	
	//Haetaan kannasta asiakkaan nimi ja puhelinnumero tiedot
	public double haetaanHinta(int id){
		String sql = "SELECT hinta, alv from Palvelu, Varauksen_palvelut" 
				+" where Palvelu.palvelu_id = Varauksen_palvelut.palvelu_id" 
				+" AND Varauksen_palvelut.varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		
		Date aloitus;
		Date lopetus;
		double haku = 0;
		int mokkihinta = 0;
		long days = 0;
		long diff = 0;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
			//Jos löydetään tuloksia
			while (tulosjoukko.next () == true){
				alvi = tulosjoukko.getDouble("alv");
				haku += tulosjoukko.getDouble("hinta");
				
				
			//Jos asiakasta ei löydy palautetaan siitä tieto
			}
			
			sql = "SELECT hinta from Varaus, Mokki" 
					+" where Varaus.toimipiste_id = Mokki.toimipiste_id" 
					+" AND Varaus.varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
			tulosjoukko = null;
			lause = null;
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();
			if(tulosjoukko.next()==true){
				mokkihinta = tulosjoukko.getInt("hinta");
			}
			
			
			sql = "SELECT varattu_alkupvm, varattu_loppupvm from Varaus" 
					+" where varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
			tulosjoukko = null;
			lause = null;
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();
			if(tulosjoukko.next()==true){
				aloitus = tulosjoukko.getDate("varattu_alkupvm");
				lopetus = tulosjoukko.getDate("varattu_loppupvm");
				days = Math.abs(aloitus.getTime() - lopetus.getTime());
			    diff = TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
			    System.out.println("Päivät: "+ diff);
			}
			haku += mokkihinta*diff;
			
			

		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return haku;
	}//haetaanAsiakas
	public double getAlv(){
		return alvi;
	}
	}
