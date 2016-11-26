package khrystosov.atm;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import khrystosov.tools.*;

public class Bank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Bank instance;

	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank("Super_Bank");
		}
		return instance;
	}

	public transient Logger logger;

	private ArrayList<Card> cards;
	private ArrayList<Account> accounts;
	private ArrayList<Office> offices;
	private transient Database db;
	private String title;
	private int last_card_id;
	private int last_account_id;

	private Bank(String title) {
		db = Database.getInstance();
		File test = new File("Bank");
		if(test.exists()){
			instance = (Bank)db.readObject("Bank");
			last_card_id = instance.last_card_id;
			last_account_id = instance.last_account_id;
		}
		cards = new ArrayList<Card>();
		accounts = new ArrayList<Account>();
		offices = new ArrayList<Office>();
		logger = new ConsoleLogger();
		this.title = title;

		logger.print("#Bank '" + this.title + "' created");
		
		for(int i = 0; i < last_card_id; i++){
			File test_if_card_exists_in_folder = new File("Card" + i);
			if(test_if_card_exists_in_folder.exists()){
			cards.add((Card)db.readObject("Card" + i));

			}
		}
		
		for(int i = 0; i < last_account_id; i++){
			File test_if_account_exists_in_folder = new File("Account" + i);
			if(test_if_account_exists_in_folder.exists()){
			accounts.add((Account)db.readObject("Account" + i));

			}
		}
	}

	public Office getOffice(int id) {
		if (id < 0 || id > offices.size()) {
			logger.print("#Bank.getOffice(). Bad index. No office with id "
					+ id);
			return null;
		}
		return offices.get(id);
	}

	public boolean cardExists(int id) {
		if (id < 0 || id >= cards.size()) {
			return false;
		}
		return true;
	}

	public String getInfo(int cardId) {
		if (cardExists(cardId)) {
			return accounts.get(cards.get(cardId).getAccountId()).getPib();
		}
		return null;
	}

	public boolean checkPIN(int cardId, String PIN) {
		if (cardExists(cardId)) {
			logger.print("#Bank.checkPIN(). Bad index. No card with id "
					+ cardId);
			if (cards.get(cardId).getPIN().equals(PIN)) {
				return true;
			}
		}
		return false;
	}

	public boolean withdrawCash(int cardId, int amount) {
		if (cardExists(cardId) && amount > 0) {
			if (cards.get(cardId).getFunds() >= amount) {
				logger.print("#Bank.withdrawCash(). Card ¹" + cardId
						+ ". Withdrawn " + amount);
				if (cards.get(cardId).withdraw(amount) && db.writeObject("Card" + cardId, cards.get(cardId))) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean enrollMoney(int cardId, int amount) {
		if (cardExists(cardId) && amount > 0) {
			logger.print("#Bank.enrollMoney(). Card ¹" + cardId + ". Enrolled "
					+ amount);
			if (cards.get(cardId).enroll(amount) && db.writeObject("Card" + cardId, cards.get(cardId))) {
				return true;
			}
		}
		return false;
	}

	public boolean openOffice(Address a) {
		offices.add(new Office(offices.size(), a));
		return true;
	}

	public int getBalance(int cardId) {
		if (!cardExists(cardId)) {
			logger.print("#Bank.getBalance(). Bad index. No card with id "
					+ cardId);
			return -1;
		}
		logger.print("#Bank.getBalance(). Card ¹" + cardId);
		return cards.get(cardId).getFunds();
	}

	public boolean registerNewAcc(String pib, String secret) {
		for (Account a : accounts) {
			if (a.getPib().equals(pib)) {
				return false;
			}
		}
		Account to_add = new Account(last_account_id++, pib, secret);
		accounts.add(to_add);
		db.writeObject("Account" + to_add.getId(), to_add);
		saveBank();
		return true;
	}

	public boolean registerNewCard(int acc_id, String secret) {
		if (acc_id < 0 || acc_id > accounts.size()) {
			logger.print("#Bank.registerNewCard(). Bad index. No acc ¹" + acc_id);
			return false;
		}
		if (secret.equals(accounts.get(acc_id).getSecret())) {
			Card to_add = new Card(last_card_id++);
			to_add.setAccountId(acc_id);
			accounts.get(acc_id).addCard(to_add.getId());
			cards.add(to_add);
			if(db.writeObject("Card" + to_add.getId(), to_add)){
				logger.print("#Bank.registerNewCard(). Card ¹" + to_add.getId() + " saved to database.");
			};
			saveBank();
			return true;
		}
		return false;
	}
	
	private void saveBank(){
		db.writeObject("Bank", Bank.getInstance());
	}

	
	

}
