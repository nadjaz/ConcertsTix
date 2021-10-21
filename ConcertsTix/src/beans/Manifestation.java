package beans;

import java.time.LocalDate;
import java.util.UUID;

public class Manifestation {

	public enum TypeManifestation {
		CONCERT, FESTIVAL, THEATRE;
	}

	public enum StatusManifestation {
		ACTIVE, INACTIVE;
	}

	private final UUID id = UUID.randomUUID();
	private String name;
	private TypeManifestation typeManifestation;
	private Integer seatingNumber;
	private LocalDate date;
	private Double priceRegular;
	private StatusManifestation status;
	private Location location;
	private String image;

	public Manifestation() {

	}

	public Manifestation(String name, TypeManifestation typeManifestation, Integer seatingNumber, LocalDate date,
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

	public Integer getSeatingNumber() {
		return seatingNumber;
	}

	public void setSeatingNumber(Integer seatingNumber) {
		this.seatingNumber = seatingNumber;
		if (this.seatingNumber == 0) {
			this.status = StatusManifestation.INACTIVE;
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getPriceRegular() {
		return priceRegular;
	}

	public void setPriceRegular(Double price) {
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

	public UUID getId() {
		return id;
	}

}
