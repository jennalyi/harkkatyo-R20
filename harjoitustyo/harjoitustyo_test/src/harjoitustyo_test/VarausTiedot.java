package harjoitustyo_test;

import java.sql.Date;

public class VarausTiedot {
	int asiakasID; 				
	int toimiID;
	int mokkiID;
	Date alkupaiv;
	Date loppupaiv;
	boolean lippu = false;
	
	public VarausTiedot(){

	}
	public void lisaatiedot(boolean lippu,int asiakasID, int toimiID,int mokkiID,Date alkupaiv,Date loppupaiv){
		this.asiakasID = asiakasID; 				
		this.toimiID = toimiID; 
		this.mokkiID = mokkiID; 
		this.alkupaiv = alkupaiv;
		this.loppupaiv = loppupaiv;
		this.lippu = lippu;
	}
}
