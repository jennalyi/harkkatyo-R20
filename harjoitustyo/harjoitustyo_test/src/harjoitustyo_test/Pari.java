/*
 * Tekij� Joona Piispanen
 */


package harjoitustyo_test;

import java.time.LocalDate;

//K�ytet��n tietojen siirt�miseen kanta luokasta takas k�ytt�liittym� luokkaan
public class Pari {
	public LocalDate alkupaiv;
	public LocalDate loppupaiv;
	
	//Asettaa alku ja loppup�iv�n muuttujiin
	Pari(LocalDate alkupaiv, LocalDate loppupaiv){
		this.alkupaiv = alkupaiv;
		this.loppupaiv = loppupaiv;
	}
}
