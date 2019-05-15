package harjoitustyo_test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PalveluidenHallintaKanta {
	Connection conn;
	
	public PalveluidenHallintaKanta(Connection conn){
		this.conn = conn;
		conn = null;
		
	}
	public int lisaaPalvelu(int palvelu_id , int toimipiste_id, String nimi , int tyyppi , String kuvaus, double hinta,  double alv){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "INSERT INTO Palvelu "
		+ "(palvelu_id, toimipiste_id, nimi, tyyppi, kuvaus, hinta, alv) "
		+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement lause = null;
		//tarkistetaan ett‰ kaikilla tiedoilla on arvo
		
		if(toimipiste_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, palvelu_id);
			lause.setInt( 2, toimipiste_id);
			lause.setString(3, nimi); 
			lause.setInt(4, tyyppi); 
			lause.setString(5, kuvaus);
			lause.setDouble(6, hinta);
			lause.setDouble(7, alv);
			
			
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
	}//lis‰‰ palvelu
	
	public int muokkaaPalvelua(int palvelu_id , int toimipiste_id, String nimi , int tyyppi , String kuvaus, double hinta,  double alv){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "UPDATE Palvelu "
		+ "SET palvelu_id = ?, toimipiste_id = ?, nimi = ?, tyyppi = ?, kuvaus = ?, hinta = ?, alv = ? WHERE palvelu_id = ? ";
		
		PreparedStatement lause = null;
		//tarkistetaan ett‰ kaikilla tiedoilla on arvo

		if(palvelu_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin
			lause.setInt( 1, palvelu_id);
			lause.setInt( 2, toimipiste_id);
			lause.setString( 3, nimi);
			lause.setInt(4, tyyppi); 
			lause.setString(5, kuvaus); 
			lause.setDouble(6, hinta); 
			lause.setDouble(7, alv); 
			lause.setInt( 8, palvelu_id);
			
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
	}//muokkaa palvelua
	
	public int poistaPalvelu(int palvelu_id){
		int lkm = 0;
		int onnistuiko = 2;
		String sql = "DELETE FROM Palvelu WHERE palvelu_id = ?";
		
		PreparedStatement lause = null;
		//tarkistetaan ett‰ kaikilla tiedoilla on arvo
		if(palvelu_id != 0 ){
		onnistuiko = 1;	
		
		try {
			// luo PreparedStatement-olio sql-lauseelle
			lause = conn.prepareStatement(sql);
			// laitetaan arvot INSERTtiin

			lause.setInt( 1, palvelu_id);
			
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
	
	
	public ArrayList<String> haePalvelu(int id, String nimi){
		ArrayList<String> tulokset = new ArrayList<String>();
		ResultSet tulosjoukko = null;
		PreparedStatement lause = null;
		tulokset.add(0, "2");
		try {
			// luo PreparedStatement-olio sql-lauseelle
			if(id != 0 && nimi.isEmpty()){
			String sql = "SELECT palvelu_id, toimipiste_id, nimi, tyyppi, kuvaus, hinta, alv " 
					+ " FROM Palvelu WHERE palvelu_id = ?"; // ehdon arvo asetetaan j‰ljemp‰n‰
			
			lause = conn.prepareStatement(sql);
			lause.setInt( 1, id); 
			}
			else if(id == 0 && nimi.isEmpty()==false){
				String sql = "SELECT palvelu_id, toimipiste_id, nimi, tyyppi, kuvaus, hinta, alv " 
						+ " FROM Palvelu WHERE nimi = ?"; // ehdon arvo asetetaan j‰ljemp‰n‰
				
				lause = conn.prepareStatement(sql);
				lause.setString( 1, nimi); 
			}
			else if(id != 0 && nimi.isEmpty()==false){
				String sql = "SELECT palvelu_id, toimipiste_id, nimi, tyyppi, kuvaus, hinta, alv " 
						+ " FROM Palvelu WHERE nimi = ? AND palvelu_id = ?"; // ehdon arvo asetetaan j‰ljemp‰n‰
				
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
					
					tulokset.add(1,""+tulosjoukko.getInt("palvelu_id"));					
					tulokset.add(2,""+tulosjoukko.getInt("toimipiste_id"));					
					tulokset.add(3,tulosjoukko.getString("nimi"));
					tulokset.add(4,""+tulosjoukko.getInt("tyyppi"));
					tulokset.add(5,""+tulosjoukko.getString("kuvaus"));
					tulokset.add(6,""+tulosjoukko.getDouble("hinta"));
					tulokset.add(7,""+tulosjoukko.getDouble("alv"));
					
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
