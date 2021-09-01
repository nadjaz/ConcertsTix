package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
		users.put(user.getUsername(), user);
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
					//Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(st.nextToken().trim());
					
					DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dateOfBirth = LocalDate.parse(st.nextToken().trim(), pattern);
					
					Role role = Role.valueOf(st.nextToken().trim());
					users.put(username, new User(username, password, name, surname, gender, dateOfBirth, role));
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
