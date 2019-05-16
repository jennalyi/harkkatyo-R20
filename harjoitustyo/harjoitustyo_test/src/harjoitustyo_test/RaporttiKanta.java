package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaporttiKanta {
	Connection conn;
	Date aloituspaiva;
	Date lopetuspaiva;
	
	
	
	public RaporttiKanta(Connection conn){
		this.conn = conn;
	}
	//Haetaan varaus
	public ArrayList<ArrayList<String>> haetaanVaraus(int id, Date aloituspaiva, Date lopetuspaiva){
		String sql = "SELECT v.varaus_id, v.varattu_pvm, a.etunimi, a.sukunimi , t.nimi"   
				+ " FROM Varaus AS v, Asiakas AS A, Toimipiste AS t"
				+" WHERE v.asiakas_id = a.asiakas_id AND v.toimipiste_id = t.toimipiste_id AND"
				+ " v.toimipiste_id = ? "			 
				 +" AND ((v.varattu_alkupvm between ? AND ? )"  
					+" OR (v.varattu_loppupvm between ? AND ? ))" ; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		//Luodaan olio, jonka avulla voidaan palauttaa haetut tiedot
		//VarausTiedot varausolio = new VarausTiedot();
		ArrayList<ArrayList<String>> kaikkiVaraukset = new ArrayList<ArrayList<String>>();
		ArrayList<String> varaus = new ArrayList<String>();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			lause.setDate(2, aloituspaiva);
			lause.setDate(3, lopetuspaiva);
			lause.setDate(4, aloituspaiva);
			lause.setDate(5, lopetuspaiva);

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
				//Haetaan tuloksesta tiedot ja asetetaan ne varausolio olioon
			int i = 0;	
			while (tulosjoukko.next () == true){
					System.out.println("Tulosjoukko");
					i++;
					varaus = new ArrayList<String>();
					varaus.add(""+tulosjoukko.getInt("v.varaus_id"));
					varaus.add(""+tulosjoukko.getDate("v.varattu_pvm"));
					varaus.add(tulosjoukko.getString("a.etunimi"));
					varaus.add(tulosjoukko.getString("a.sukunimi"));
					varaus.add(tulosjoukko.getString("t.nimi"));
					
					kaikkiVaraukset.add(varaus);

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
		return kaikkiVaraukset;
	}//haetaanVaraus
	
	//Haetaan kannasta toimipisteet nimi 
	public ArrayList<Integer> haetaanToimipisteet(){
		String sql = "SELECT toimipiste_id " 
				+ " FROM Toimipiste"; // ehdon arvo asetetaan jäljempänä
		ArrayList<Integer> toimipisteet = new ArrayList<Integer>();
		
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		String haku = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			//lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
			//Jos löydetään tuloksia
			while(tulosjoukko.next () == true){
				toimipisteet.add(tulosjoukko.getInt("toimipiste_id"));
				
			//Jos asiakasta ei löydy palautetaan siitä tieto
			}


		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return toimipisteet;
	}//haetaanAsiakas
}
