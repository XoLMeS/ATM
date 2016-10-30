package khrystosov.atm;

public class Office {
	
	private Bank bank;
	
	public Office(){
		bank = Bank.getInstance();
	}
}
