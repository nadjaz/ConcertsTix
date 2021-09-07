package beans;

import java.time.LocalDate;

public class Ticket {

	public enum TypeTicket {
		REGULAR, FAN_PIT, VIP;
	};

	public enum StatusTicket {
		RESERVED, CANCELED;
	};

	private Integer id;
	private Manifestation manifestation;
	private LocalDate date;
	private Double price;
	private User buyerNameSurname;
	private StatusTicket statusTicket;
	private TypeTicket typeTicket;

	public Ticket() {
		super();
	}

	public Ticket(Integer id, Manifestation manifestation, User buyerNameSurname,
			StatusTicket statusTicket, TypeTicket typeTicket) {
		super();
		this.id = id;
		this.manifestation = manifestation;
		this.date = manifestation.getDate();

		if (typeTicket == TypeTicket.REGULAR) {
			this.price = manifestation.getPriceRegular();
		} else if (typeTicket == TypeTicket.FAN_PIT) {
			this.price = manifestation.getPriceRegular() * 2;
		} else {
			this.price = manifestation.getPriceRegular() * 4;
		}

		this.buyerNameSurname = buyerNameSurname;
		this.statusTicket = statusTicket;
		this.typeTicket = typeTicket;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public User getBuyerNameSurname() {
		return buyerNameSurname;
	}

	public void setBuyerNameSurname(User buyerNameSurname) {
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
