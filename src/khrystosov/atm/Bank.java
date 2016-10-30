package khrystosov.atm;

import java.util.ArrayList;

import khrystosov.tools.*;

public class Bank {

	private static Bank instance;
	
	public static Bank getInstance(){
		if(instance == null){
			instance = new Bank("Bank");
		}
		return instance;
	}
	
	private String title;
	private ArrayList<Card> cards;
	private int last_card_id;
	private Logger logger = new ConsoleLogger();
	
	private Bank() {
		this.title = "unknown";
		cards = new ArrayList<Card>();
	}

	private Bank(String title) {
		this.title = title;
		cards = new ArrayList<Card>();
		last_card_id = 0;
	}

	private boolean createCards(int capacity) {
		for (int i = last_card_id; i < (i + capacity); i++) {
			cards.add(new Card(i));
		}
		last_card_id += capacity;
		
		logger.print("#" + capacity + " cards created. IDs " + (last_card_id - capacity) + " - " + (last_card_id - 1));
		return true;
	}

	public int countAvailableCards() {
		int result = 0;
		for (Card c : cards) {
			if (c.getAccountId() == -1) {
				result++;
			}
		}
	
		logger.print("#Available cards counted: " + result);
		return result;
	}

	public class Card {

		private int id;
		private int accountId;
		private int funds;

		public Card() {
			// this.id = -1;
		}

		public Card(int id) {
			this.id = id;
			this.accountId = -1;
			this.funds = 0;
			logger.print("#Card №" + id + " created");
		}

		public void setAccountId(int id) {
			this.accountId = id;
			logger.print("#Card №" + this.id + ". New account id set." );
		}

		public int getId() {
			return this.id;
		}

		public int getAccountId() {
			return this.accountId;
		}
		
		public int getFunds(){
			
			return this.funds;
		}
		
		public boolean enroll(int cap){
			funds += cap;
			logger.print("#Card №" + id + " enrolled " + cap);
			return true;
		}
		
		public boolean withdraw(int cap){
			funds -= cap;
			logger.print("#Card №" + id + " withdrawn " + cap);
			return true;
		}
	}
}
