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
	public int lisaaToimi(int toimipiste_id , String nimi , String lahiosoite , String postitoimipaikka , String postinro , String email,  String puhelinnro ){
		int lkm = 0;
		int onnistuiko = 2;
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
	
	public int muokkaaToimi(int toimipiste_id , String nimi , String lahiosoite , String postitoimipaikka , String postinro , String email,  String puhelinnro ){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "UPDATE Toimipiste "
		+ "SET nimi = ?, lahiosoite = ?, postitoimipaikka = ?, postinro = ?, email= ?"
		+ ", puhelinnro = ? WHERE toimipiste_id = ? ";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		System.out.println("toimi:"+toimipiste_id);
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
	
	public int poistaToimi(int toimipiste_id){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "DELETE FROM Toimipiste WHERE toimipiste_id = ?";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin

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
	
	public ArrayList<String> haeToimi(int id, String nimi){
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "2");
		try {
			// luo PreparedStatement-olio sql-lauseelle
			if(id != 0 && nimi.isEmpty()){
			String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
					+ " FROM Toimipiste WHERE toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			}
			else if(id == 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
						+ " FROM Toimipiste WHERE nimi = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
			}
			else if(id != 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, nimi, lahiosoite , postitoimipaikka , postinro , email, puhelinnro " 
						+ " FROM Toimipiste WHERE nimi = ? AND toimipiste_id = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
				lause.setInt( 2, id);
			}

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	

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
					if(tulosjoukko.next () == true){
			
						tulokset.set(0, "4");
					}else{
						tulokset.set(0, "1");
					}
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
	
	public int lisaaMokki(int toimipiste_id , int mokki_id,  String nimi , int hinta){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "INSERT INTO Mokki "
		+ "(toimipiste_id, mokki_id, nimi, hinta) "
		+ " VALUES (?, ?, ?, ?)";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		
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
            //se.printStackTrace();
        } catch (Exception e) {
            // JDBC virheet
            e.printStackTrace();
		}
	}
		return onnistuiko;
	}
	
	
	public int muokkaaMokki(int toimipiste_id , int mokki_id,  String nimi , int hinta ){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "UPDATE Mokki "
		+ "SET toimipiste_id = ?, mokki_id = ?, nimi = ?, hinta = ? WHERE mokki_id = ? ";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo

		if(mokki_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
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
	
	public int poistaMokki(int mokki_id){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "DELETE FROM Mokki WHERE mokki_id = ?";
		
		PreparedStatement lause = null;
		//tarkistetaan että kaikilla tiedoilla on arvo
		if(mokki_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin

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
	
	public ArrayList<String> haeMokki(int id, String nimi){
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "2");
		try {
			// luo PreparedStatement-olio sql-lauseelle
			if(id != 0 && nimi.isEmpty()){
			String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
					+ " FROM Mokki WHERE mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			}
			else if(id == 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
						+ " FROM Mokki WHERE nimi = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
			}
			else if(id != 0 && nimi.isEmpty()==false){
				String sql = "SELECT toimipiste_id, mokki_id, nimi, hinta " 
						+ " FROM Mokki WHERE nimi = ? AND mokki_id = ?"; // ehdon arvo asetetaan jäljempänä
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
				lause.setInt( 2, id);
			}

			// suorita sql-lause
			tulosjoukko = lause.executeQuery();	

				if (tulosjoukko.next () == true){
/*					if(tulosjoukko.next () == true){
						tulokset.add("4");
					}else{*/
					
					tulokset.add(1,""+tulosjoukko.getInt("toimipiste_id"));					
					tulokset.add(2,""+tulosjoukko.getInt("mokki_id"));					
						
					tulokset.add(3,tulosjoukko.getString("nimi"));
					tulokset.add(4,""+tulosjoukko.getInt("hinta"));
					
					if(tulosjoukko.next () == true){
			
						tulokset.set(0, "4");
					}else{
						tulokset.set(0, "1");
					}
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
