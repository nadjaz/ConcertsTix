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
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import beans.User;
import beans.User.Role;

public class UserDAO {

	// kolekcija svih registrovanih inicijalnih korisnika ??????
	private Map<String, User> users = new HashMap<>();

	public UserDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo
	 *                    iz servleta.
	 */
	public UserDAO(String contextPath) {
		loadUsers(contextPath);
	}

	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik
	 * ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User find(String username, String password) {
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	public Collection<User> findAll() {
		return users.values();
	}

	public void register(User user) {
		writeUserToFile(user);
		users.put(user.getUsername(), user);
	}

	public User update(User changedUser) {
		if (!users.containsKey(changedUser.getUsername())) {
			return null;
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
			String administratorsFilePath = "C:/Users/nadja/git/WEB_Projekat_2020-2021/ConcertsTix/WebContent/administrators.txt"; 
			updateUserInFile(lineToChange, user, administratorsFilePath);
		} else {
			String othersFilePath = "C:/Users/nadja/git/WEB_Projekat_2020-2021/ConcertsTix/WebContent/buyersAndSellers.txt";
			// updating user info in file
			updateUserInFile(lineToChange, user, othersFilePath);
		}
		return user;
	}

	private void updateUserInFile(String lineToChange, User updatedUser, String filePath) {

		// Instantiating the Scanner class to read the file
		Scanner sc = null;
		FileWriter writer = null;
		try {
			sc = new Scanner(new File(filePath));
			// instantiating the StringBuffer class
			StringBuffer buffer = new StringBuffer();
			// Reading lines of the file and appending them to StringBuffer
			while (sc.hasNextLine()) {
				buffer.append(sc.nextLine() + System.lineSeparator());
			}
			String fileContents = buffer.toString();
			// System.out.println("Contents of the file: "+fileContents);

			String newLine = updatedUser.getUsername() + ";" + updatedUser.getPassword() + ";" + updatedUser.getName()
					+ ";" + updatedUser.getSurname() + ";" + updatedUser.getGender() + ";"
					+ updatedUser.getDateOfBirth() + ";" + updatedUser.getRole();
			// Replacing the old line with new line
			fileContents = fileContents.replaceAll(lineToChange, newLine);
			// instantiating the FileWriter class
			writer = new FileWriter(filePath);
			//System.out.println("");
			//System.out.println("new data: " + fileContents);
			writer.append(fileContents);
			//writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
			// closing the Scanner object
			if (sc != null) {
				try {
					sc.close();
				} catch (Exception e) {
				}
			}
		}

	}

	private void writeUserToFile(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getUsername();
		String surname = user.getSurname();
		String gender = user.getGender();
		LocalDate dateOfBirth = user.getDateOfBirth();
		Role role = user.getRole();

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(
					"C:/Users/nadja/git/WEB_Projekat_2020-2021/ConcertsTix/WebContent/buyersAndSellers.txt", true));
			writer.write(username + ";" + password + ";" + name + ";" + surname + ";" + gender + ";" + dateOfBirth + ";"
					+ role);
			writer.newLine();
			// out.flush();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Uèitava korisnike iz WebContent/administrators.txt fajla i dodaje ih u mapu
	 * {@link #users}. Kljuè je korisnièko ime korisnika.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadUsers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/administrators.txt");
			in = new BufferedReader(new FileReader(file));
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
					// pravimo administatore
					users.put(username, new User(username, password, name, surname, gender, dateOfBirth));
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}
}
