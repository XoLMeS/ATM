package khrystosov.test;

import java.io.IOException;

import javax.xml.crypto.Data;

import khrystosov.atm.ATM;
import khrystosov.atm.Bank;
import khrystosov.atm.Office;
import khrystosov.tools.DataInput;

public class Tester {

	public static void main(String[] args) {
		Bank.getInstance();
		Office o = Bank.getInstance().getOffice(0);
		if (o == null) {
			Bank.getInstance().openOffice(null);
		}
		o = Bank.getInstance().getOffice(0);

		int req = 0;
		while (true) {

			System.out.println("******************MAIN MENU******************");
			System.out.println("" + "1.Create Account " + "\n"
					+ "2.Create Card " + "\n" + "3.Open ATM" + "\n" + "4.Exit");
			System.out.print("->");
			req = DataInput.getInt();
			System.out.println("");
			switch (req) {
			case 1:
				System.out.println("Wellcome to Office ¹" + o.getId() + ".");
				System.out.println("To create a new account enter your PIB.");
				String pib = null;
				try {
					pib = DataInput.getString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out
						.println("Now, enter the secret word. You wiil need it to access your Account.");
				String secret = null;
				try {
					secret = DataInput.getString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (!o.registerNewAcc(pib, secret)) {
					System.out
							.println("Sorry we can`t currently create this Account. Check your pib and secret.");
				} else {
					System.out
							.println("Congratulations. You have just become our client. \n Now you can create your own cards.");
				}
				break;
			case 2:
				System.out.println("Wellcome to Office ¹" + o.getId() + ".");
				System.out.println("To create a new card enter your PIB.");
				String pib2 = null;
				try {
					pib2 = DataInput.getString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out
						.println("Now, to access your account enter the secret word.");
				String secret2 = null;
				try {
					secret2 = DataInput.getString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String pin0 = null;
				System.out.println("Set PIN for this Card.");
				System.out.print("PIN: ");
				try {
					pin0 = DataInput.getString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!o.registerNewCard(pib2, secret2, pin0)) {
					System.out
							.println("Sorry we can`t currently create a new Card. Check your pib and secret.");
				} else {
					System.out
							.println("Congratulations. You have just created a new Card. You can check card`s id in the project folder");
				}
				break;
			case 3:

				ATM atm = Bank.getInstance().getATM(0);
				if (atm == null) {
					Bank.getInstance().createATM();
				}
				atm = Bank.getInstance().getATM(0);
				atm(atm);
				break;
			case 4:
				System.out.println("#Exit. Bye.");
				return;
			default:
				System.out.println("#Bad number. 1-4 allowed.");
				break;
			}
		}
	}

	private static void atm(ATM atm) {
		int req = 0;
		String card = null;
		System.out.println("Enter name of card file");
		try {
			card = DataInput.getString();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!atm.readCard(card)) {
			System.out.println("Wrong Card. Must exist file 'Card'+id.");
			return;
		}
		while (true) {
			System.out.println("Welcome to ATM ¹" + atm.getId());
			System.out.println("******************ATM MENU******************");
			System.out.println("" + "1.Withdraw cash " + "\n"
					+ "2.Enroll money " + "\n" + "3.Get balance" + "\n"
					+ "4.Cancel");
			System.out.print("->");

			req = DataInput.getInt();
			switch (req) {
			case 1:
				int amount = 0;
				System.out
						.println("Enter amount of money you want to withdraw");
				System.out.print("->");
				amount = DataInput.getInt();
				System.out.println("");
				System.out.print("PIN: ");
				String PIN = null;
				try {
					PIN = DataInput.getString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (atm.withdrawCash(amount, PIN)) {
					System.out.println("You have successfully withdrawn "
							+ amount);
				}
				break;
			case 2:
				int amount2 = 0;
				System.out.println("Enter amount of money you want to enroll");
				System.out.print("->");
				amount2 = DataInput.getInt();
				System.out.println("");
				System.out.print("PIN: ");
				String PIN2 = null;
				try {
					PIN2 = DataInput.getString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (atm.enrollMoney(amount2, PIN2)) {
					System.out.println("You have successfully enrolled "
							+ amount2);
				}
				break;
			case 3:
				String PIN3 = null;
				System.out.print("PIN: ");
				try {
					PIN3 = DataInput.getString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Your balance: " + atm.getBalance(PIN3));
				break;
			case 4:
				atm.cancel();
				return;
			default:
				break;
			}
		}
	}
}
