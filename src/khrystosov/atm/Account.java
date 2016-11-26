package khrystosov.atm;

import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Serializable{

	private int id;
	private ArrayList<Integer> cards;
	private String secret;
	private String pib;

	public Account(int id,String pib,String secret) {
		this.id = id;
		this.pib = pib;
		this.secret = secret;
		cards = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public boolean addCard(int id) {
		if (!cards.contains(id)) {
			cards.add(id);
			Bank.getInstance().logger.print("Card added");
			return true;
		}
		return false;
	}
	
	public ArrayList<Integer> getCards(){
		return cards;
	}
	
	public String getPib(){
		return pib;
	}
	
	public String getSecret(){
		return secret;
	}
}
