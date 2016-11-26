package khrystosov.atm.parts;

import java.io.File;

public class CardReader {

	private int cardId;

	public CardReader() {

	}

	public boolean readCard(String card) {
		File f = new File(card);
		if (f.exists()) {
			String id = f.getName().replaceAll("Card", "");
			for (int i = 0; i < id.length(); i++) {
				if (id.charAt(i) > 57 || id.charAt(i) < 48) {
					System.out.println("#CardReader.readCard(). Bad Card id.");
					return false;
				}
			}
			cardId = Integer.valueOf(id);
			System.out.println("#CardReader.readCard(). Card read.");
			return true;
		}
		return false;
	}

	public int getCardId() {
		return cardId;
	}

	public boolean pullOutCard() {
		cardId = -1;
		return true;
	}
}
