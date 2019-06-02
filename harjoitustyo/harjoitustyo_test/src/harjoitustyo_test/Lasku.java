

package harjoitustyo_test;

import java.sql.*;
import java.lang.*;
public class Lasku {
	
	// attribuutit, vastaavat tietokantataulun sarakkeita
    private int m_lasku_id;
    private int m_varaus_id;
    private int m_asiakas_id;
	private String m_nimi;
	
	private String m_lahiosoite;
	private String m_postitoimipaikka;
	private String m_postinro;
	private Double m_summa;
	private Double m_alv;
	private boolean m_maksettu;

    public Lasku(){
 
    }
	// getterit ja setterit
	public int getLaskuId()
	{
		return m_lasku_id;
	}	public int getVarausId()
	{
		return m_varaus_id;
    }
    public int getAsiakasId()
	{
		return m_asiakas_id;
	}
	public String getNimi() {
		return m_nimi;
	}
	
	public String getLahiosoite() {
		return m_lahiosoite;
	}
	public String getPostitoimipaikka() {
		return m_postitoimipaikka;
	}
	public String getPostinro() {
		return m_postinro;
	}
	public Double getSumma() {
		return m_summa;
	}
	public Double getAlv() {
		return m_alv;
    }
	public boolean getMaksettu() {
		return m_maksettu;
    }
    
	public void setLaskuId (int Lid)
	{
		m_lasku_id = Lid;
    }
    public void setVarausId (int Vid)
	{
		m_varaus_id = Vid;
    }
    public void setAsiakasId (int Aid)
	{
		m_asiakas_id = Aid;
	}
	public void setNimi (String ni) {
		m_nimi = ni;
	}
	
	public void setLahiosoite (String os) {
		m_lahiosoite = os;
	}
	public void setPostitoimipaikka (String ptp) {
		m_postitoimipaikka = ptp;
	}
	public void setPostinro (String pno) {
		m_postinro = pno;
	}
	public void setSumma (Double sum) {
		m_summa = sum;
	}
	public void setAlv (Double alv) {
		m_alv = alv;
	}
	public void setMaksettu (boolean maksettu) {
		m_maksettu = maksettu;
	}
	
	
    @Override
    public String toString(){
        return (m_lasku_id + " " + m_nimi + " " + m_asiakas_id);
    }
	/*
	Haetaan laskun tiedot ja palautetaan olio kutsujalle.
	Staattinen metodi, ei vaadi fyysisen olion olemassaoloa.
	*/
	public static Lasku haeLasku (Connection connection, int Lid) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
		// haetaan tietokannasta laskua, jonka lasku_id = Lid 
		String sql = "SELECT lasku_id,varaus_id,asiakas_id, nimi,  lahiosoite, postitoimipaikka, postinro, summa, alv, maksettu " 
					+ " FROM lasku WHERE lasku_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt( 1, Lid); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko == null) {
				throw new Exception("Laskua ei loydy");
			}
		} catch (SQLException se) {
            // SQL virheet
                        throw se;
                } catch (Exception e) {
            // JDBC virheet
                        throw e;
		}
		// käsitellään resultset - laitetaan tiedot oliolle
		Lasku LaskuOlio = new Lasku ();
		
		try {
			if (tulosjoukko.next () == true){
				//lasku_id,varaus_id,asiakas_id, nimi,  lahiosoite, postitoimipaikka, postinro, summa, alv
                LaskuOlio.setLaskuId (tulosjoukko.getInt("lasku_id"));
                LaskuOlio.setVarausId (tulosjoukko.getInt("varaus_id"));
                LaskuOlio.setAsiakasId (tulosjoukko.getInt("asiakas_id"));
				LaskuOlio.setNimi (tulosjoukko.getString("nimi"));
				
				LaskuOlio.setLahiosoite (tulosjoukko.getString("lahiosoite"));
				LaskuOlio.setPostitoimipaikka (tulosjoukko.getString("postitoimipaikka"));
                LaskuOlio.setPostinro (tulosjoukko.getString("postinro"));
                LaskuOlio.setSumma (tulosjoukko.getDouble("summa"));
                LaskuOlio.setAlv (tulosjoukko.getDouble("alv"));
                LaskuOlio.setMaksettu (tulosjoukko.getBoolean("maksettu"));
			}
			
		}catch (SQLException e) {
			throw e;
		}
		// palautetaan olio
		
		return LaskuOlio;
	}
	/*
	Lisätään laskun tiedot tietokantaan.
	Metodissa "Laskuolio kirjoittaa tietonsa tietokantaan".
	*/
     public int lisaaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
		// haetaan tietokannasta laskua, jonka lasku_id = olion id -> ei voi lisätä, jos on jo kannassa
		String sql = "SELECT lasku_id" 
					+ " FROM lasku WHERE lasku_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null; 
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt( 1, getLaskuId()); // asetetaan where ehtoon (?) arvo, olion Opiskelijaid
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == true) { // lasku loytyi
				throw new Exception("Lasku on jo olemassa");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan INSERT
		sql = "INSERT INTO lasku "
		+ "(lasku_id,varaus_id,asiakas_id, nimi,  lahiosoite, postitoimipaikka, postinro, summa, alv, maksettu) "
		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
		// System.out.println("Lisataan " + sql);
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, getLaskuId());
			lause.setInt(2, getVarausId()); 
            lause.setInt(3, getAsiakasId()); 
            lause.setString(4, getNimi());
			lause.setString(5, getLahiosoite());
			lause.setString(6, getPostitoimipaikka ());
			lause.setString(7, getPostinro ());
			lause.setDouble(8, getSumma ());
			lause.setDouble(9, getAlv ());
			lause.setBoolean(10, getMaksettu ());
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
		//	System.out.println("lkm " + lkm);
			if (lkm == 0) {
				throw new Exception("Laskun lisaaminen ei onnistu");
			}
		} catch (SQLException se) {
            // SQL virheet
            throw se;
        } catch (Exception e) {
            // JDBC ym. virheet
            throw e;
		}
		return 0;
	}
	/*
	Muutetaan asiakkaan tiedot tietokantaan id-tietoa (avain) lukuunottamatta. 
	Metodissa "Laskuolio muuttaa tietonsa tietokantaan".
	*/
    public int muutaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
		// haetaan tietokannasta laskua, jonka lasku_id = olion id, virhe, jos ei löydy
		String sql = "SELECT lasku_id" 
					+ " FROM lasku WHERE lasku_id = ?"; // ehdon arvo asetetaan jäljempänä
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			lause.setInt( 1, getLaskuId()); // asetetaan where ehtoon (?) arvo
			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
			if (tulosjoukko.next () == false) { // lasku ei löytynyt
				throw new Exception("Laskua ei loydy tietokannasta");
			}
		} catch (SQLException se) {
            // SQL virheet
                    throw se;
                } catch (Exception e) {
            // JDBC virheet
                    throw e;
		}
		// parsitaan Update, päiviteään tiedot lukuunottamatta avainta
		sql = "UPDATE  lasku "
		+ "SET varaus_id = ?,asiakas_id= ?, nimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, summa = ?, alv = ?, maksettu = ? "
		+ " WHERE lasku_id = ?";
		
		lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			
			// laitetaan olion attribuuttien arvot UPDATEen
			
			
			lause.setInt(1, getVarausId()); 
            lause.setInt(2, getAsiakasId()); 
            lause.setString(3, getNimi());
			lause.setString(4, getLahiosoite());
			lause.setString(5, getPostitoimipaikka ());
			lause.setString(6, getPostinro ());
			lause.setDouble(7, getSumma ());
			lause.setDouble(8, getAlv ());
			lause.setBoolean( 9, getMaksettu());
			// where-ehdon arvo
            lause.setInt( 10, getLaskuId());
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Laskun muuttaminen ei onnistu");
			}
		} catch (SQLException se) {
            // SQL virheet
                throw se;
        } catch (Exception e) {
            // JDBC ym. virheet
                throw e;
		}
		return 0; // toiminto ok
	}
	/*
	Poistetaan laskun tiedot tietokannasta. 
	Metodissa "Laskuolio poistaa tietonsa tietokannasta".
	*/
	public int poistaLasku (Connection connection) throws SQLException, Exception { // tietokantayhteys välitetään parametrina
		
		// parsitaan DELETE
		String sql = "DELETE FROM  lasku WHERE lasku_id = ?";
		PreparedStatement lause = null;
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = connection.prepareStatement(sql);
			// laitetaan arvot DELETEn WHERE-ehtoon
			lause.setInt( 1, getLaskuId());
			// suorita sql-lause
			int lkm = lause.executeUpdate();	
			if (lkm == 0) {
				throw new Exception("Laskun poistaminen ei onnistu");
			}
			} catch (SQLException se) {
            // SQL virheet
                throw se;
             } catch (Exception e) {
            // JDBC virheet
                throw e;
		}
		return 0; // toiminto ok
	}
}