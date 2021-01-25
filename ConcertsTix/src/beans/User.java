package beans;

import java.util.Date;
import java.util.List;

public class User {

	public enum Role {
		ADMINISTRATOR, SELLER, BUYER;

	};

	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private Date dateOfBirth;
	private Role role;
	private List<Ticket> allTickets;
	private List<Manifestation> allManifestations;
	private int points;
	private BuyerType buyerType;

	public User() {
		super();
	}

	// Constructor for CREATE ADMINISTRATOR USER
	public User(String username, String password, String name, String surname, String gender, Date dateOfBirth,
			Role role) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
	}

	// Constructor for CREATE SELLER USER
	public User(String username, String password, String name, String surname, String gender, Date dateOfBirth,
			Role role, List<Manifestation> allManifestations) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.allManifestations = allManifestations;
	}

	// Constructor for CREATE BUYER USER
	public User(String username, String password, String name, String surname, String gender, Date dateOfBirth,
			Role role, List<Ticket> allTickets, int points, BuyerType buyerType) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.allTickets = allTickets;
		this.points = points;
		this.buyerType = buyerType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Ticket> getAllTickets() {
		return allTickets;
	}

	public void setAllTickets(List<Ticket> allTickets) {
		this.allTickets = allTickets;
	}

	public List<Manifestation> getAllManifestations() {
		return allManifestations;
	}

	public void setAllManifestations(List<Manifestation> allManifestations) {
		this.allManifestations = allManifestations;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public BuyerType getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(BuyerType buyerType) {
		this.buyerType = buyerType;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
