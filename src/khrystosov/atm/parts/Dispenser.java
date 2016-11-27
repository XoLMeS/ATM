package khrystosov.atm.parts;

import khrystosov.atm.Bank;

public class Dispenser{

	
	public Dispenser(){
		
	}
	
	public boolean withdrawCash(int amount){
		Bank.getInstance().logger.print("#Dispenser.withdrawCash(). Withdrawn "
				+ amount + ".");
		return true;
	}
}
