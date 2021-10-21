package beans;

import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

	public enum TypeTicket {
		REGULAR, FAN_PIT, VIP;
	}

	public enum StatusTicket {
		RESERVED, CANCELED;
	}

	private UUID id;
	private Manifestation manifestation;
	private LocalDate date;
	private Double price;
	private User buyerNameSurname;
	private StatusTicket statusTicket;
	private TypeTicket typeTicket;
	private Integer numberOfTickets;

	public Ticket() {
		super();
	}

	public Ticket(Manifestation manifestation, User buyerNameSurname, StatusTicket statusTicket, TypeTicket typeTicket,
			Integer numberOfTickets) {
		super();
		this.id = UUID.randomUUID();
		this.manifestation = manifestation;
		this.date = manifestation.getDate();
		this.price = calculateFullPrice(numberOfTickets, typeTicket, manifestation.getPriceRegular());
		this.buyerNameSurname = buyerNameSurname;
		this.statusTicket = statusTicket;
		this.typeTicket = typeTicket;
		this.numberOfTickets = numberOfTickets;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	/**
	 * Calculates ticket full price for a manifestation, for a given number of
	 * tickets user wants to buy, based on the ticket type and the regular price for
	 * the manifestation
	 *
	 * @param numberOfTickets - number of tickets user wants to buy
	 * @param typeTicket      - ticket type which can be regular, fan pit or VIP
	 * @param regularPrice    - regular price for a ticket, for a specific
	 *                        manifestation
	 */
	public Double calculateFullPrice(Integer numberOfTickets, TypeTicket typeTicket, Double regularPrice) {
		if (typeTicket == TypeTicket.REGULAR) {
			return regularPrice * numberOfTickets;
		} else if (typeTicket == TypeTicket.FAN_PIT) {
			return regularPrice * 2 * numberOfTickets;
		} else {
			return regularPrice * 4 * numberOfTickets;
		}
	}

}
