package khrystosov.atm;

import java.io.Serializable;

import khrystosov.atm.parts.CardReader;
import khrystosov.atm.parts.Dispenser;
import khrystosov.tools.Address;

public class ATM implements Serializable {

	private int id;
	private transient Address adress;
	private transient Dispenser dispenser;
	private transient CardReader cardReader;
	private boolean cardIn = false;
	private boolean available = true;

	public ATM(int id, Address adr) {
		this.id = id;
		this.adress = adr;
		cardReader = new CardReader();
		dispenser = new Dispenser();
	}

	public boolean withdrawCash(int amount, String PIN) {
		if (available && cardIn) {
			if (!checkPIN(PIN)) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".withdrawCash(). PIN check fail. Card id "
						+ cardReader.getCardId() + ".");
				return false;
			}
			if (amount <= 0) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".withdrawCash(). Invalid amount " + amount
						+ ". Card id " + cardReader.getCardId() + ".");
				return false;
			}
			if (Bank.getInstance().withdrawCash(cardReader.getCardId(), amount)) {
				if (dispenser.withdrawCash(amount)) {
					Bank.getInstance().logger.print("#ATM " + id
							+ ".withdrawCash(). Card id "
							+ cardReader.getCardId() + ". Withdrawn " + amount
							+ ".");
					return true;
				}
			}
		}
		return false;
	}

	public boolean enrollMoney(int amount, String PIN) {
		if (available && cardIn) {
			if (!checkPIN(PIN)) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".withdrawCash(). PIN check fail. Card id "
						+ cardReader.getCardId() + ".");
				return false;
			}
			if (amount <= 0) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".enrollMoney(). Invalid amount " + amount
						+ ". Card id " + cardReader.getCardId() + ".");
				return false;
			}
			if (Bank.getInstance().enrollMoney(cardReader.getCardId(), amount)) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".enrollMoney(). Enrolled " + amount + ". Card id "
						+ cardReader.getCardId() + ".");
				return true;
			} else {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".enrollMoney(). Error. Card id "
						+ cardReader.getCardId() + ".");
				return false;
			}
		}
		return false;
	}

	public int getBalance(String PIN) {
		if (available && cardIn) {
			if (!checkPIN(PIN)) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".getBalance(). PIN check fail. Card id "
						+ cardReader.getCardId() + ".");
			}
			Bank.getInstance().logger.print("#ATM " + id
					+ ".getBalance(). Card id " + cardReader.getCardId() + ".");
			return Bank.getInstance().getBalance(cardReader.getCardId());
		}
		return -1;
	}

	public boolean readCard(String card) {
		if (available && !cardIn) {
			if (card.contains("Card")) {
				if (cardReader.readCard(card)) {
					if (!Bank.getInstance().cardExists(cardReader.getCardId())) {
						return false;
					}
					cardIn = true;
					return true;
				}
			}
			else {
				// TO DO
			}
		}
		return false;
	}

	private boolean checkPIN(String PIN) {
		if (available && cardIn) {
			if (PIN == null || PIN.length() != 4) {
				Bank.getInstance().logger.print("#ATM " + id
						+ ".checkPin(). Invalid PIN " + PIN + ". Card id "
						+ cardReader.getCardId() + ".");
				return false;
			}
			for (int i = 0; i < 4; i++) {
				if (PIN.charAt(i) < 48 || PIN.charAt(i) > 57) {
					Bank.getInstance().logger.print("#ATM " + id
							+ ".checkPin(). Invalid PIN " + PIN + ". Card id "
							+ cardReader.getCardId() + ".");
					return false;
				}
			}
			if (Bank.getInstance().checkPIN(cardReader.getCardId(), PIN)) {
				return true;
			}
		}
		return false;
	}

	public boolean cancel() {
		if (available && cardIn) {
			if (cardReader.pullOutCard())
				cardIn = false;
		}
		return true;
	}

	public Address getAddress() {
		return adress;
	}

	public String getInfo() {
		if (available && cardIn) {
			return Bank.getInstance().getInfo(cardReader.getCardId());
		}
		return null;
	}

	public int getId() {
		return id;
	}
}
