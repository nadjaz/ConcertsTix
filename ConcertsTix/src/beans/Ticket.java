package beans;

import java.util.Date;

public class Ticket {

	public enum TypeTicket {
		VIP, REGULAR, FAN_PIT;
	};

	public enum StatusTicket {
		RESERVED, CANCELED;
	};

	private String id;
	private Manifestation manifestation;
	private Date date;
	private Double price;
	private String buyerNameSurname;
	private StatusTicket statusTicket;
	private TypeTicket typeTicket;

	public Ticket() {
		super();
	}

	public Ticket(String id, Manifestation manifestation, Date date, Double price, String buyerNameSurname,
			StatusTicket statusTicket, TypeTicket typeTicket) {
		super();
		this.id = id;
		this.manifestation = manifestation;
		this.date = date;
		this.price = price;
		this.buyerNameSurname = buyerNameSurname;
		this.statusTicket = statusTicket;
		this.typeTicket = typeTicket;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBuyerNameSurname() {
		return buyerNameSurname;
	}

	public void setBuyerNameSurname(String buyerNameSurname) {
		this.buyerNameSurname = buyerNameSurname;
	}

	public StatusTicket getStatusTicket() {
		return statusTicket;
	}

	public void setStatusTicket(StatusTicket statusTicket) {
		this.statusTicket = statusTicket;
	}

	public TypeTicket getTypeTicket() {
		return typeTicket;
	}

	public void setTypeTicket(TypeTicket typeTicket) {
		this.typeTicket = typeTicket;
	}

}
