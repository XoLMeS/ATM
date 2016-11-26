package khrystosov.tools;

public class Address {

	
	private String country;
	private String town;
	private String street;
	private String number;
	
	public Address(String country, String town, String street, String number){
		this.country = country;
		this.town = town;
		this.street = street;
		this.number = number;
	}
	
	public String getCountry(){
		return country;
	}
	
	public String getTown(){
		return town;
	}
	
	public String getStreet(){
		return street;
	}
	
	public String getNumber(){
		return number;
	}
	
	public String toString(){
		return number + " " + street + " , " + town + " , " + country;
	}
	
}
