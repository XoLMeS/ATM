package khrystosov.atm;

import java.io.Serializable;

import khrystosov.tools.Address;

public class Office implements Serializable {

	private int id;
	private Address address;

	public Office(int id, Address adr) {
		this.id = id;
		this.address = adr;
	}

	public boolean registerNewAcc(String pib,String secret) {
		Bank.getInstance().registerNewAcc(pib,secret);
		return true;
	}

	public boolean registerNewCard(int acc_id, String secret) {
		Bank.getInstance().registerNewCard(acc_id, secret);
		return true;
	}

	public int getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}
}