/*
 * Tekij� Joona Piispanen
 */

package harjoitustyo_test;

import java.sql.Date;


/**
 * K�ytet��n palauttamaan tietoja kanta hauista
 * @author Joona
 *
 */
public class VarausTiedot {
	int asiakasID; 				
	int toimiID;
	int mokkiID;
	Date alkupaiv;
	Date loppupaiv;
	boolean lippu = false;
	
	//Konstruktori asettaa tiedot muuttujiin
	public void lisaatiedot(boolean lippu,int asiakasID, int toimiID,int mokkiID,Date alkupaiv,Date loppupaiv){
		this.asiakasID = asiakasID; 				
		this.toimiID = toimiID; 
		this.mokkiID = mokkiID; 
		this.alkupaiv = alkupaiv;
		this.loppupaiv = loppupaiv;
		this.lippu = lippu;
	}
}
