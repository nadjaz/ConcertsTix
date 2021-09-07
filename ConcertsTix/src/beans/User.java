package beans;

import java.time.LocalDate;
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
	private LocalDate dateOfBirth;
	private Role role;
	private List<Ticket> allTickets;
	private List<Manifestation> allManifestations;
	private Double points;
	private BuyerType buyerType;

	public User() {
		super();
	}

	// Constructor for CREATE ADMINISTRATOR USER
	public User(String username, String password, String name, String surname, String gender, LocalDate dateOfBirth) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = Role.ADMINISTRATOR;
	}

	// Constructor for CREATE SELLER USER
	public User(String username, String password, String name, String surname, String gender, LocalDate dateOfBirth,
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
	public User(String username, String password, String name, String surname, String gender, LocalDate dateOfBirth,
			Role role) {
		// List<Ticket> allTickets, int points, BuyerType buyerType) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		// this.allTickets =;
		this.points = 0.0;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
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

	public Double getPoints() {
		return points;
	}

	public void setPoints(Double points) {
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
