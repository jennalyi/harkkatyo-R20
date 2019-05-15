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
	public ArrayList<VarausTiedot> haetaanVaraus(int id){
		String sql = "SELECT asiakas_id, toimipiste_id, mokki_id, varattu_alkupvm,varattu_loppupvm " 
				+ " FROM Varaus WHERE toimipiste_id = ? AND ((varattu_alkupvm between ? AND ? ) " 
				+"OR (varattu_loppupvm between ? AND ?))"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		//Luodaan olio, jonka avulla voidaan palauttaa haetut tiedot
		VarausTiedot varausolio = new VarausTiedot();
		ArrayList<VarausTiedot> varauksettt = new ArrayList<VarausTiedot>();
		
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
					i++;
					varausolio = new VarausTiedot();
					
					int varaus = id; 
					int asiakasID = tulosjoukko.getInt("asiakas_id");					
					int toimiID = tulosjoukko.getInt("toimipiste_id");
					int mokkiID = tulosjoukko.getInt("mokki_id");
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
		return varauksettt;
	}//haetaanVaraus
	
	
}
