package khrystosov.atm.parts;

import java.io.File;

public class CardReader implements ATMInput{

	private int cardId;

	public CardReader(){
		
	}
	
	public boolean readCard(File card){
	// TODO read from file card id
		
		System.out.println("#Card read");
		return true;
	}
	
	public int getCardId(){
		return cardId;
	}
}
