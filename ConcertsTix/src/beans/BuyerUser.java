package beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beans.BuyerType.TypeBuyer;

public class BuyerUser extends User {

	private List<Ticket> allTickets = new ArrayList<>();
	private Double points = 0.0;
	private TypeBuyer buyerType = TypeBuyer.BRONZE;

	public BuyerUser() {
		super();
	}

	public BuyerUser(String username, String password, String name, String surname, String gender,
			LocalDate dateOfBirth) {
		super(username, password, name, surname, gender, dateOfBirth, Role.BUYER);
	}

	public List<Ticket> getAllTickets() {
		return allTickets;
	}

	public void setAllTickets(List<Ticket> allTickets) {
		this.allTickets = allTickets;
	}

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
		this.points = points;
	}

	public TypeBuyer getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(TypeBuyer buyerType) {
		this.buyerType = buyerType;
	}

}
