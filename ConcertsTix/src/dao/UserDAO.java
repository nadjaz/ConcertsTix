package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import beans.BuyerUser;
import beans.Manifestation;
import beans.SellerUser;
import beans.Ticket;
import beans.User;
import beans.User.Role;

public class UserDAO {

	// collection of all registered users
	private Map<String, User> users = new HashMap<>();

	private static Logger logger = Logger.getLogger(UserDAO.class.getName());

	public UserDAO() {

	}

	/***
	 * @param contextPath Path to application in Tomcat. Can be accessed only
	 *                    through servlet.
	 */
	public UserDAO(String contextPath) {
		loadAdministrators(contextPath);
	}

	/**
	 * Returns user for inputed username and password. Returns empty Optional if
	 * user doesn't exist.
	 * 
	 * @param username
	 * @param password
	 * @return Optional<User>
	 */
	public Optional<User> find(String username, String password) {
		if (!users.containsKey(username)) {
			return Optional.empty();
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return Optional.empty();
		}
		return Optional.of(user);
	}

	/**
	 * return all registered users
	 * 
	 * @return Collection<User>
	 */
	public Collection<User> findAll() {
		return users.values();
	}

	public void addTicket(BuyerUser user, Ticket ticket) {
		List<Ticket> tickets = user.getAllTickets();
		tickets.add(ticket);
	}

	public Collection<Ticket> findTickets(BuyerUser user) {
		return user.getAllTickets();
	}

	public void addManifestation(SellerUser user, Manifestation manifestation) {
		List<Manifestation> manifestations = user.getAllManifestations();
		manifestations.add(manifestation);
	}

	public Collection<Manifestation> findManifestations(SellerUser user) {
		return user.getAllManifestations();
	}

	/**
	 * Registering a new user
	 * 
	 * @param user        - user we are registering
	 * @param contextPath
	 */
	public void register(User user, String contextPath) {
		writeUserToFile(user, contextPath);
		users.put(user.getUsername(), user);
	}

	/**
	 * Updating user with new information. Return empty if user doesn't exist.
	 * 
	 * @param changedUser - new user information
	 * @return Optional<User> - return updated user
	 */
	public Optional<User> update(User changedUser, String contextPath) {
		if (!users.containsKey(changedUser.getUsername())) {
			return Optional.empty();
		}
		User user = users.get(changedUser.getUsername());
		String lineToChange = user.getUsername() + ";" + user.getPassword() + ";" + user.getName() + ";"
				+ user.getSurname() + ";" + user.getGender() + ";" + user.getDateOfBirth() + ";" + user.getRole();

		user.setPassword(changedUser.getPassword());
		user.setName(changedUser.getName());
		user.setSurname(changedUser.getSurname());
		user.setGender(changedUser.getGender());
		user.setDateOfBirth(changedUser.getDateOfBirth());

		if (user.getRole() == Role.ADMINISTRATOR) {
			String administratorsFilePath = "administrators.txt";
			updateUserInFile(lineToChange, user, administratorsFilePath, contextPath);
		} else {
			String othersFilePath = "buyersAndSellers.txt";
			// updating user info in file
			updateUserInFile(lineToChange, user, othersFilePath, contextPath);
		}
		return Optional.of(user);
	}

	/**
	 * Updating users in file
	 * 
	 * @param lineToChange - file line we are changing
	 * @param updatedUser  - updated user information
	 * @param filePath     - path of the file we are writing to (depends if we are
	 *                     updating regular users or administrators)
	 */
	private void updateUserInFile(String lineToChange, User updatedUser, String filePath, String contextPath) {
		StringBuilder buffer = new StringBuilder();
		try (Scanner sc = new Scanner(new File(contextPath + "WEB-INF/resources/" + filePath))) {

			// Reading lines of the file and appending them to StringBuilder
			while (sc.hasNextLine()) {
				buffer.append(sc.nextLine() + System.lineSeparator());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter writer = new FileWriter(new File(contextPath + "WEB-INF/resources/" + filePath))) {
			String newLine = updatedUser.getUsername() + ";" + updatedUser.getPassword() + ";" + updatedUser.getName()
					+ ";" + updatedUser.getSurname() + ";" + updatedUser.getGender() + ";"
					+ updatedUser.getDateOfBirth() + ";" + updatedUser.getRole();

			String fileContents = buffer.toString();
			// Replacing the old line with new line
			fileContents = fileContents.replaceAll(lineToChange, newLine);

			writer.append(fileContents);
			writer.flush();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Updating users in file failed", ex);
		}
	}

	/**
	 * Adding a newly registered user into file
	 * 
	 * @param user - user that we are adding
	 */
	private void writeUserToFile(User user, String contextPath) {
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getName();
		String surname = user.getSurname();
		String gender = user.getGender();
		LocalDate dateOfBirth = user.getDateOfBirth();
		Role role = user.getRole();

		// append new users to file
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(new File(contextPath + "WEB-INF/resources/buyersAndSellers.txt"), true))) {
			writer.write(username + ";" + password + ";" + name + ";" + surname + ";" + gender + ";" + dateOfBirth + ";"
					+ role);
			writer.newLine();
			writer.flush();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Writing buyers and sellers to file failed", ex);
		}
	}

	/**
	 * Loading administrators from administrators.txt file and adding them to all
	 * users map {@link #users}. Map key is username.
	 */
	private void loadAdministrators(String contextPath) {
		File file = new File(contextPath + "WEB-INF/resources/administrators.txt");
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String name = st.nextToken().trim();
					String surname = st.nextToken().trim();
					String gender = st.nextToken().trim();
					DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dateOfBirth = LocalDate.parse(st.nextToken().trim(), pattern);
					Role role = Role.valueOf(st.nextToken().trim());
					// adding administrators into all users map
					users.put(username, new User(username, password, name, surname, gender, dateOfBirth, role));
				}
			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Loading administrators failed", ex);
		}
	}

}
