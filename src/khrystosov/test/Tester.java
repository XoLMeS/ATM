package khrystosov.test;

import khrystosov.atm.Bank;
import khrystosov.atm.Office;

public class Tester {

	public static void main(String[] args) {
		Bank bank = Bank.getInstance();
		bank.openOffice(null);
		
		Office o = bank.getOffice(0);
		o.registerNewAcc("Alex khr","Secret");
		o.registerNewCard(0,"Secret");
		
		System.out.println(bank.getBalance(0));
		bank.enrollMoney(0, 1000);
		System.out.println(bank.getBalance(0));
		bank.withdrawCash(0, 500);
		System.out.println(bank.getBalance(0));
		System.out.println(bank.getInfo(0));
	}
}
