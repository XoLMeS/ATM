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
	private ArrayList<ATM> atms;
	private transient Database db;
	private String title;
	private int last_card_id;
	private int last_account_id;
	private int last_atm_id;
	private int last_office_id;

	private Bank(String title) {
		db = Database.getInstance();
		File test = new File("Bank");
		if (test.exists()) {
			instance = (Bank) db.readObject("Bank");
			last_card_id = instance.last_card_id;
			last_account_id = instance.last_account_id;
			last_atm_id = instance.last_atm_id;
			last_office_id = instance.last_office_id;
		}
		cards = new ArrayList<Card>();
		accounts = new ArrayList<Account>();
		offices = new ArrayList<Office>();
		logger = new ConsoleLogger();
		atms = new ArrayList<ATM>();
		this.title = title;

		logger.print("#Bank '" + this.title + "' created");

		// LOAD ALL CARDS STORED IN PROJECT FOLDER
		for (int i = 0; i < last_card_id; i++) {
			File test_if_card_exists_in_folder = new File("Card" + i);
			if (test_if_card_exists_in_folder.exists()) {
				cards.add((Card) db.readObject("Card" + i));

			}
		}

		// LOAD ALL ACCOUNTS STORED IN PROJECT FOLDER
		for (int i = 0; i < last_account_id; i++) {
			File test_if_account_exists_in_folder = new File("Account" + i);
			if (test_if_account_exists_in_folder.exists()) {
				accounts.add((Account) db.readObject("Account" + i));

			}
		}

		// LOAD ALL ATMs STORED IN PROJECT FOLDER
		for (int i = 0; i < last_atm_id; i++) {
			File test_if_atm_exists_in_folder = new File("ATM" + i);
			if (test_if_atm_exists_in_folder.exists()) {
				atms.add((ATM) db.readObject("ATM" + i));
				
			}
		}

		// LOAD ALL OFFICES STORED IN PROJECT FOLDER
		for (int i = 0; i < last_office_id; i++) {
			File test_if_office_exists_in_folder = new File("Office" + i);
			if (test_if_office_exists_in_folder.exists()) {
				offices.add((Office) db.readObject("Office" + i));
			}
		}
	}

	public Office getOffice(int id) {
		if (id < 0 || id >= offices.size()) {
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
			if (cards.get(cardId).getPIN().equals(PIN)) {
				return true;
			}
		}
		else {
			logger.print("#Bank.checkPIN(). Bad index. No card with id "
					+ cardId);
		}
		return false;
	}

	public boolean withdrawCash(int cardId, int amount) {
		if (cardExists(cardId) && amount > 0) {
			if (cards.get(cardId).getFunds() >= amount) {
				logger.print("#Bank.withdrawCash(). Card ¹" + cardId
						+ ". Withdrawn " + amount);
				if (cards.get(cardId).withdraw(amount)
						&& db.writeObject("Card" + cardId, cards.get(cardId))) {
					return true;
				}
			}
			else {
				logger.print("#Bank.withdrawCash(). Card ¹" + cardId
						+ ". Not enough money.");
				return false;
			}
		}
		return false;
	}

	public boolean enrollMoney(int cardId, int amount) {
		if (cardExists(cardId) && amount > 0) {
			logger.print("#Bank.enrollMoney(). Card ¹" + cardId + ". Enrolled "
					+ amount);
			if (cards.get(cardId).enroll(amount)
					&& db.writeObject("Card" + cardId, cards.get(cardId))) {
				return true;
			}
		}
		return false;
	}

	public boolean openOffice(Address a) {
		Office o = new Office(last_office_id++, a);
		offices.add(o);
		db.writeObject("Office" + o.getId(), o);
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

	public boolean registerNewCard(String pib, String secret, String PIN) {
		int acc_id = -1;
		for (Account a : accounts) {
			if (a.getPib().equals(pib)) {
				acc_id = a.getId();
			}
		}
		if (acc_id < 0 || acc_id > accounts.size()) {
			logger.print("#Bank.registerNewCard(). Bad index. No acc ¹"
					+ acc_id);
			return false;
		}
		if (secret.equals(accounts.get(acc_id).getSecret())) {
			Card to_add = new Card(last_card_id++);
			to_add.setAccountId(acc_id);
			to_add.setPIN(PIN);
			accounts.get(acc_id).addCard(to_add.getId());
			cards.add(to_add);
			if (db.writeObject("Card" + to_add.getId(), to_add)) {
				logger.print("#Bank.registerNewCard(). Card ¹" + to_add.getId()
						+ " saved to database.");
			}
			;
			saveBank();
			return true;
		}
		return false;
	}

	private void saveBank() {
		db.writeObject("Bank", Bank.getInstance());
	}

	public boolean createATM() {
		ATM atm = new ATM(last_atm_id++, null);
		atms.add(atm);
		db.writeObject("ATM" + atm.getId(), atm);
		return true;
	}

	public ATM getATM(int id) {
		if(id < 0 || id >= last_atm_id){
			return null;
		}
		return atms.get(id);
	}

}
