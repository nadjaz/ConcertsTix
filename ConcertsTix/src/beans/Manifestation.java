package beans;

import java.util.Date;

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
	private Date date;
	private Double priceRegular;
	private StatusManifestation status;
	private String location;
	private String image;

	public Manifestation() {
		super();
	}

	public Manifestation(String name, TypeManifestation typeManifestation, int seatingNumber, Date date, Double priceRegular,
			StatusManifestation status, String location, String image) {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
