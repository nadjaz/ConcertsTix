package beans;

public class Location {

	private int length;
	private int width;
	private String street;
	private int streetNumber;
	private String city;
	private int postalCode;

	public Location() {

	}

	public Location(int length, int width, String street, int streetNumber, String city, int postalCode) {
		super();
		this.length = length;
		this.width = width;
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.postalCode = postalCode;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

}
