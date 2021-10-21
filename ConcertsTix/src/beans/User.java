package beans;

import java.time.LocalDate;

public class User {

	public enum Role {
		ADMINISTRATOR, SELLER, BUYER;
	}

	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private LocalDate dateOfBirth;
	private Role role;

	public User() {

	}

	/**
	 * Constructor for CREATING A USER
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param surname
	 * @param gender
	 * @param dateOfBirth
	 * @param role
	 */
	public User(String username, String password, String name, String surname, String gender, LocalDate dateOfBirth,
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
