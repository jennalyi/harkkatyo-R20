/**
 * Tekijä Joona Piispanen
 */

package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Haetaan tietoja laskun hallintaa ja laskun lähetystä varten kannasta
 * @author Joona
 *
 */
public class haeTiedot {
	Connection conn;
	int varausid;
	double alvi;
	long diff;
	long dif1;
	public haeTiedot(Connection conn){
		this.conn = conn;

	}
	//Haetaan kannasta asiakkaan yhteystiedot
	public ArrayList<String> haetaanAsiakas(int id){
		String sql = "SELECT asiakas_id,etunimi, sukunimi, lahiosoite, postitoimipaikka, postinro " 
				+ " FROM Asiakas WHERE asiakas_id = "
				+ " (select asiakas_id from Varaus where varaus_id = ?)"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		
		ArrayList<String> palautus = new ArrayList<String>();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
			//Jos löydetään tuloksia
			if (tulosjoukko.next () == true){
				palautus.add(""+tulosjoukko.getInt("asiakas_id"));
				palautus.add(tulosjoukko.getString("etunimi"));
				palautus.add(tulosjoukko.getString("sukunimi"));
				palautus.add(tulosjoukko.getString("lahiosoite"));
				palautus.add(tulosjoukko.getString("postitoimipaikka"));
				palautus.add(tulosjoukko.getString("postinro"));

				//Jos asiakasta ei löydy 
			}else{

			}

			//Suljetaan lopuksi
			lause.close();
			tulosjoukko.close();
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return palautus;
	}//haetaanAsiakas

	//Haetaan kannasta vaurauksen yhteenlaskettu hinta
	public double haetaanHinta(int id){
		//Haetaan kaikki varauksen palvelut
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
		diff = 0;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	

			//Lisätään jokaisen palvelun hinta haku muuttujaan
			while (tulosjoukko.next () == true){
				alvi = tulosjoukko.getDouble("alv");
				haku += tulosjoukko.getDouble("hinta");


				//Jos asiakasta ei löydy palautetaan siitä tieto
			}
			//Haetaan varauksen mökin hinta
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

			//Haetaan varauksen alotus ja lopetuspäivä 
			//ja lasketaan niiden perusteella mökin hinta
			//päivät*hinta
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
				//lasketaan päivien määrä
				days = Math.abs(aloitus.getTime() - lopetus.getTime());
				diff = TimeUnit.DAYS.convert(days, TimeUnit.MILLISECONDS);
				dif1 = diff;
			}
			//Lasketaan mökin hinta kertomalla se päivien määrällä ja
			//lisätään se kokonaissummaan
			haku += mokkihinta*diff;

			
			//Suljetaan lopuksi
			lause.close();
			tulosjoukko.close();

		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}

		return haku;
	}//haetaanHinta
	
	
	//Voidaan hakea alvi arvo
	public double getAlv(){
		return alvi;
	}

	//Hakee mökin hinnan ja nimen
	public ArrayList<String> mokkiTiedot(int id){
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		ArrayList<String> palautus = new ArrayList<String>();
		try{
			String sql = "SELECT hinta,nimi from Varaus, Mokki" 
					+" where Varaus.toimipiste_id = Mokki.toimipiste_id" 
					+" AND Varaus.varaus_id = ?"; // ehdon arvo asetetaan jäljempänä

			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();
			if(tulosjoukko.next()==true){
				palautus.add(tulosjoukko.getString("nimi"));
				//kerrotaan mökin hinta päivillä. Päivien saamiseksi tulee ajaa
				//ensin haetaan hinta metodi
				palautus.add(""+(tulosjoukko.getInt("hinta")*dif1));
			}

			lause.close();
		} catch (SQLException se) {
			// SQL virheet
			se.printStackTrace();
		} catch (Exception e) {
			// JDBC virheet
			e.printStackTrace();
		}
		return palautus;
	}
	
	//haetaan tietyn varauksen kaikki palvelut ja hinnat
	public ArrayList<ArrayList<String>> haetaanPalvelut(int id){
		String sql = "SELECT hinta, Palvelu.nimi from Palvelu, Varauksen_palvelut" 
				+" where Palvelu.palvelu_id = Varauksen_palvelut.palvelu_id" 
				+" AND Varauksen_palvelut.varaus_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		//Luodaan Arraylisti, jonka avulla voidaan palauttaa haetut tiedot
		ArrayList<ArrayList<String>> kaikkiVaraukset = new ArrayList<ArrayList<String>>();
		ArrayList<String> varaus = new ArrayList<String>();
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 


			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			
				//Haetaan tulosjoukosta tiedot ja asetataan ne arraylistiin
			int i = 0;	
			while (tulosjoukko.next () == true){
					i++;
					varaus = new ArrayList<String>();
					varaus.add(""+tulosjoukko.getString("Palvelu.nimi"));
					varaus.add(""+tulosjoukko.getDouble("hinta"));
					
					kaikkiVaraukset.add(varaus);

				}
			//Suljetaan lopuksi
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
	}//haetaanPalvelut


}
