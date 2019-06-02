/*
 * Tekijä Joona Piispanen
 */


package harjoitustyo_test;

import java.time.LocalDate;

//Käytetään tietojen siirtämiseen kanta luokasta takas käyttöliittymä luokkaan
public class Pari {
	public LocalDate alkupaiv;
	public LocalDate loppupaiv;
	
	//Asettaa alku ja loppupäivän muuttujiin
	Pari(LocalDate alkupaiv, LocalDate loppupaiv){
		this.alkupaiv = alkupaiv;
		this.loppupaiv = loppupaiv;
	}
}
