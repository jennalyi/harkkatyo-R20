package harjoitustyo_test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ToimiMokkiKanta {
	Connection conn;
	
	public ToimiMokkiKanta(Connection conn){
		this.conn = conn;
		conn = null;
		
	}
	
	//Lisätään uusi toimipiste
	public int lisaaToimi(int toimipiste_id , String nimi , String lahiosoite , String postitoimipaikka , String postinro , String email,  String puhelinnro ){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan sql insert lause
		String sql = "INSERT INTO Toimipiste "
		+ "(toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro) "
		+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, toimipiste_id);
			lause.setString( 2, nimi);
			lause.setString(3, lahiosoite); 
			lause.setString(4, postitoimipaikka); 
			lause.setString(5, postinro);
			lause.setString(6, email);
			lause.setString(7, puhelinnro);
			
			
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
	
	//Muokataan toimipistettä
	public int muokkaaToimi(int toimipiste_id , String nimi , String lahiosoite , String postitoimipaikka , String postinro , String email,  String puhelinnro ){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan update lause
		String sql = "UPDATE Toimipiste "
		+ "SET nimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, email= ?"
		+ ", puhelinnro = ? WHERE toimipiste_id = ? ";
		
		PreparedStatement lause = null;
		//Toimipiste_id ei saa olla nolla
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setString( 1, nimi);
			lause.setString(2, lahiosoite); 
			lause.setString(3, postitoimipaikka); 
			lause.setString(4, postinro);
			lause.setString(5, email);
			lause.setString(6, puhelinnro);
			lause.setInt( 7, toimipiste_id);
			
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
	
	//Poistetaan toimipiste
	public int poistaToimi(int toimipiste_id){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan delete lause
		String sql = "DELETE FROM Toimipiste WHERE toimipiste_id = ?";
		
		PreparedStatement lause = null;
		
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot Deleteen

			lause.setInt( 1, toimipiste_id);
			
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
	
	//Haetaan toimipiste
	public ArrayList<String> haeToimi(int id, String nimi){
		//Luodaan arraylist palautusta varten
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "2");
		try {
			// luo PreparedStatement-olio sql-lauseelle
			
			//Haetaan toimipiste_id perusteella 
			if(id != 0 && nimi.isEmpty()){
			String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
					+ " FROM Toimipiste WHERE toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			}
			//Haetaan nimen perusteella
			else if(id == 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
						+ " FROM Toimipiste WHERE nimi = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
			}
			//Haetaan nimen ja toimipiste_id perusteella
			else if(id != 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
						+ " FROM Toimipiste WHERE nimi = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
				lause.setInt( 2, id);
			}

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				
				//Asetetaan tulokset arraylistiin, ensimmäinen paikka listalla varattu virheille
				if (tulosjoukko.next () == true){
/*					if(tulosjoukko.next () == true){
						tulokset.add("4");
					}else{*/
					
					tulokset.add(1,""+tulosjoukko.getInt("toimipiste_id"));					
					tulokset.add(2,tulosjoukko.getString("nimi"));
					tulokset.add(3,tulosjoukko.getString("lahiosoite"));
					tulokset.add(4,tulosjoukko.getString("postitoimipaikka"));
					tulokset.add(5,tulosjoukko.getString("postinro"));
					tulokset.add(6,tulosjoukko.getString("email"));
					tulokset.add(7,tulosjoukko.getString("puhelinnro"));	
					//Tarkistetaan ettei kysely palauta useita rivejä, koska tuloksen täytyy olla yksi selitteinen
					if(tulosjoukko.next () == true){
			
						tulokset.set(0, "4");
					//Palautetaan että onnistui
					}else{
						tulokset.set(0, "1");
					}
					//Palautetaan että haulla ei löytynyt mitään
					}else{
						tulokset.set(0, "5");
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
	
	//Lisätään mökki
	public int lisaaMokki(int toimipiste_id , int mokki_id,  String nimi , int hinta){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan insert lause
		String sql = "INSERT INTO Mokki "
		+ "(toimipiste_id, mokki_id, nimi, hinta) "
		+ " VALUES (?, ?, ?, ?)";
		
		PreparedStatement lause = null;
		
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, toimipiste_id);
			lause.setInt( 2, mokki_id);
			lause.setString( 3, nimi);
			lause.setInt(4, hinta); 
	
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
            se.printStackTrace();
        } catch (Exception e) {
            // JDBC virheet
            e.printStackTrace();
		}
	}
		return onnistuiko;
	}
	
	//Muokataan mökkiä
	public int muokkaaMokki(int toimipiste_id , int mokki_id,  String nimi , int hinta ){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan päivitys lause
		String sql = "UPDATE Mokki "
		+ "SET toimipiste_id = ?, mokki_id = ?, nimi = ?, hinta = ? WHERE mokki_id = ? ";
		
		PreparedStatement lause = null;

		if(mokki_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot Updateen
			lause.setInt( 1, toimipiste_id);
			lause.setInt( 2, mokki_id);
			lause.setString( 3, nimi);
			lause.setInt(4, hinta); 
			lause.setInt( 5, mokki_id);
			
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
	
	//Poistetaan mökki
	public int poistaMokki(int mokki_id){
		int lkm = 0;
		int onnistuiko = 2;
		//Luodaan delete lause
		String sql = "DELETE FROM Mokki WHERE mokki_id = ?";
		
		PreparedStatement lause = null;
		//tarkistetaan että mokki_id nolla
		if(mokki_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot deleteen

			lause.setInt( 1, mokki_id);
			
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
	
	//Haetaan mökki
	public ArrayList<String> haeMokki(int id, String nimi){
		//Luodaan arraylist jolla palautetaan tulokset
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "2");
		try {
			// Haetaan mokki_id perusteella
			if(id != 0 && nimi.isEmpty()){
			String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
					+ " FROM Mokki WHERE mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			}
			// Haetaan nimen perusteella
			else if(id == 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
						+ " FROM Mokki WHERE nimi = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
			}
			// Haetaan mokki_id ja nimen perusteella
			else if(id != 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
						+ " FROM Mokki WHERE nimi = ? AND mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
				lause.setInt( 2, id);
			}

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	
				
			//Asetetaan tulokset arraylistiin
				if (tulosjoukko.next () == true){					
					tulokset.add(1,""+tulosjoukko.getInt("toimipiste_id"));					
					tulokset.add(2,""+tulosjoukko.getInt("mokki_id"));					
						
					tulokset.add(3,tulosjoukko.getString("nimi"));
					tulokset.add(4,""+tulosjoukko.getInt("hinta"));
					//Tarkistetaan ettei kysely palauta useita rivejä, koska tuloksen täytyy olla yksi selitteinen
					if(tulosjoukko.next () == true){
			
						tulokset.set(0, "4");
					//Haku onnistui
					}else{
						tulokset.set(0, "1");
					}
					//Haulla ei löytynyt mitään
					}else{
						tulokset.set(0, "5");
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
	

	
	
}
