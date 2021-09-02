package beans;

import java.time.LocalDate;

public class Manifestation {

	public enum TypeManifestation {
		CONCERT, FESTIVAL, THEATRE;
	};

	public enum StatusManifestation {
		ACTIVE, INACTIVE;
	};

	private String name;
	private TypeManifestation typeManifestation;
	private int seatingNumber;
	private LocalDate date;
	private Double priceRegular;
	private StatusManifestation status;
	private Location location;
	private String image;

	public Manifestation() {
		super();
	}

	public Manifestation(String name, TypeManifestation typeManifestation, int seatingNumber, LocalDate date,
			Double priceRegular, StatusManifestation status, Location location, String image) {
		super();
		this.name = name;
		this.typeManifestation = typeManifestation;
		this.seatingNumber = seatingNumber;
		this.date = date;
		this.priceRegular = priceRegular;
		this.status = status;
		this.location = location;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeManifestation getTypeManifestation() {
		return typeManifestation;
	}

	public void setTypeManifestation(TypeManifestation typeManifestation) {
		this.typeManifestation = typeManifestation;
	}

	public int getSeatingNumber() {
		return seatingNumber;
	}

	public void setSeatingNumber(int seatingNumber) {
		this.seatingNumber = seatingNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getPrice() {
		return priceRegular;
	}

	public void setPrice(Double price) {
		this.priceRegular = price;
	}

	public StatusManifestation getStatus() {
		return status;
	}

	public void setStatus(StatusManifestation status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
