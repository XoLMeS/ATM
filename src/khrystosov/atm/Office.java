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
		if(pib==null || secret== null){
			return false;
		}
		Bank.getInstance().registerNewAcc(pib,secret);
		return true;
	}

	public boolean registerNewCard(String pib, String secret, String PIN) {
		if(pib==null || secret== null){
			return false;
		}
		if (PIN == null || PIN.length() != 4){
			return false;
		}
		for (int i = 0; i < 4; i++) {
			if (PIN.charAt(i) < 48 || PIN.charAt(i) > 57) {
				Bank.getInstance().logger.print("#Office" + id
						+ ".registerNewCard(). Invalid PIN " + PIN);
				return false;
			}
		}
		return 	Bank.getInstance().registerNewCard(pib, secret, PIN);
	}

	public int getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}
}