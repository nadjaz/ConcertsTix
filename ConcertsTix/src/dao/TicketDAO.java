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

import beans.Location;
import beans.Manifestation;
import beans.Ticket;
import beans.User;
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;
import beans.Ticket.StatusTicket;
import beans.Ticket.TypeTicket;
import beans.User.Role;

public class TicketDAO {

	// kolekcija svih registrovanih inicijalnih karata
	private Map<String, Ticket> tickets = new HashMap<>();

	public TicketDAO() {

	}

	public TicketDAO(String contextPath) {
		loadTickets(contextPath);
	}

	public Collection<Ticket> findAll() {
		return tickets.values();
	}
	
	public Location createLocation(String locationString) {
		StringTokenizer st = new StringTokenizer(locationString, ":");
		// 45:35:Pennsylvania Plaza:4:New York:10001
		Location location = null;
		while (st.hasMoreTokens()) {
			int lenght = Integer.parseInt(st.nextToken().trim());
			int width = Integer.parseInt(st.nextToken().trim());
			String street = st.nextToken().trim();
			int streetNumber = Integer.parseInt(st.nextToken().trim());
			String city = st.nextToken().trim();
			int postalCode = Integer.parseInt(st.nextToken().trim());

			location = new Location(lenght, width, street, streetNumber, city, postalCode);
		}
		return location;
	}

	public Manifestation createManifestation(String manifestationString) {
		StringTokenizer st = new StringTokenizer(manifestationString, ",");
		// Game Of Thrones,THEATRE,90,2021-10-17,250,ACTIVE,45:35:Pennsylvania Plaza:4:New York:10001,got.jpg
		Manifestation manifestation = null;
		while (st.hasMoreTokens()) {
			String name = st.nextToken().trim();
			TypeManifestation typeManifestation = TypeManifestation.valueOf(st.nextToken().trim());
			int seatingNumber = Integer.parseInt(st.nextToken().trim());
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(st.nextToken().trim(), pattern);
			Double priceRegular = Double.parseDouble(st.nextToken().trim());
			StatusManifestation status = StatusManifestation.valueOf(st.nextToken().trim());

			String locationString = st.nextToken().trim();
			Location location = createLocation(locationString);

			String image = "img/" + st.nextToken().trim();

			manifestation = new Manifestation(name, typeManifestation, seatingNumber, date,
					priceRegular, status, location, image);
		}
		return manifestation;
	}

	public User createUser(String userString) {
		StringTokenizer st = new StringTokenizer(userString, ",");
		// nadjaz,nadja,Nadja,Zorboski,female,1995-08-17,ADMINISTRATOR
		User user = null;
		while (st.hasMoreTokens()) {
			String username = st.nextToken().trim();
			String password = st.nextToken().trim();
			String name = st.nextToken().trim();
			String surname = st.nextToken().trim();
			String gender = st.nextToken().trim();
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateOfBirth = LocalDate.parse(st.nextToken().trim(), pattern);
			Role role = Role.valueOf(st.nextToken().trim());

			user = new User(username, password, name, surname, gender, dateOfBirth, role);
		}
		return user;
	}

	private void loadTickets(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/tickets.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String id = st.nextToken().trim();

					String manifestationString = st.nextToken().trim();
					Manifestation manifestation = createManifestation(manifestationString);

					Double price = Double.parseDouble(st.nextToken().trim());

					String userString = st.nextToken().trim();
					User buyerNameSurname = createUser(userString);

					StatusTicket statusTicket = StatusTicket.valueOf(st.nextToken().trim());
					TypeTicket typeTicket = TypeTicket.valueOf(st.nextToken().trim());

					tickets.put(id, new Ticket(id, manifestation, price, buyerNameSurname, statusTicket,
							typeTicket));
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
