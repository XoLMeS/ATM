package khrystosov.atm;

import java.io.Serializable;

public class Card implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private int id;
	private int accountId;
	private int funds;
	private String PIN;

	public Card(int id) {
		this.id = id;
		this.accountId = -1;
		this.funds = 0;
		Bank.getInstance().logger.print("#Card ¹" + id + " created");
	}

	public void setAccountId(int id) {
		this.accountId = id;
		Bank.getInstance().logger.print("#Card ¹" + this.id + ". New account id set.");
	}

	public void setPIN(String newPin) {
		PIN = newPin;
	}

	public String getPIN() {
		return PIN;
	}

	public int getId() {
		return id;
	}

	public int getAccountId() {
		return accountId;
	}

	public int getFunds() {
		return funds;
	}

	public boolean enroll(int cap) {
		funds += cap;
		Bank.getInstance().logger.print("#Card ¹" + id + " enrolled " + cap);
		return true;
	}

	public boolean withdraw(int cap) {
		funds -= cap;
		Bank.getInstance().logger.print("#Card ¹" + id + " withdrawn " + cap);
		return true;
	}
}
