package khrystosov.atm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Database {

	private static Database instance;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	private Database() {

	}

	public Object readObject(String file) {
		File test = new File(file);
		if (test.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Object to_return =  ois.readObject();
				ois.close();
				return to_return;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean writeObject(String file, Serializable o) {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(file));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		try {
			oos.writeObject(o);
			oos.flush();
			oos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}