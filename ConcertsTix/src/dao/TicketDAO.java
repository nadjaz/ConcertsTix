package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import beans.Location;
import beans.Manifestation;
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;
import beans.Ticket;
import beans.Ticket.StatusTicket;
import beans.Ticket.TypeTicket;
import beans.User;
import beans.User.Role;

public class TicketDAO {

	// collection of all tickets in the system
	private NavigableMap<UUID, Ticket> tickets = new TreeMap<>();

	private static Logger logger = Logger.getLogger(TicketDAO.class.getName());

	public TicketDAO() {

	}

	public TicketDAO(String contextPath) {
		loadTickets(contextPath);
	}

	public Collection<Ticket> findAll() {
		return tickets.values();
	}

	/**
	 * Return Ticket for the sent id. Returns Optional empty if a ticket with the
	 * sent id doesn't exists.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Ticket> find(UUID id) {
		if (!tickets.containsKey(id)) {
			return Optional.empty();
		}
		return Optional.of(tickets.get(id));
	}

	/**
	 * find all reserved tickets
	 * 
	 * @return
	 */
	public Collection<Ticket> findAllReserved() {
		Collection<Ticket> reservedTickets = new ArrayList<>();
		for (Ticket ticket : tickets.values()) {
			if (ticket.getStatusTicket().equals(StatusTicket.RESERVED)) {
				reservedTickets.add(ticket);
			}
		}
		return reservedTickets;
	}

	/**
	 * returns all reserved tickets for the sent user
	 * 
	 * @param user
	 * @return
	 */
	public Collection<Ticket> findForUser(User user) {
		Collection<Ticket> myTickets = new ArrayList<>();
		for (Ticket ticket : tickets.values()) {
			if (ticket.getBuyerNameSurname().equals(user) && ticket.getStatusTicket().equals(StatusTicket.RESERVED)) {
				myTickets.add(ticket);
			}
		}
		return myTickets;
	}

	/**
	 * Return all manifestation that user reserved in the past, so that the user can
	 * comment on them.
	 * 
	 * @param user
	 * @param manifestations - all manifestations
	 * @return
	 */
	public Collection<Manifestation> findManifestationsForUser(User user,
			NavigableMap<UUID, Manifestation> manifestations) {

		Collection<Ticket> usersTickets = findForUser(user);
		Collection<Manifestation> usersManifestations = new ArrayList<>();
		Collection<Manifestation> filteredManifestations = new ArrayList<>();

		LocalDate todaysDate = LocalDate.now();

		for (Ticket ticket : usersTickets) {
			usersManifestations.add(ticket.getManifestation());
		}
		for (Manifestation manifestation : usersManifestations) {
			if (todaysDate.isAfter(manifestation.getDate())) {
				filteredManifestations.add(manifestations.get(manifestation.getId()));
			}
		}
		return filteredManifestations;
	}

	public UUID findLastId() {
		return tickets.lastKey();
	}

	/**
	 * Creating a new ticket. Returns Optional empty if a ticket with the same id
	 * already exists.
	 * 
	 * @param ticket
	 * @return
	 */
	public Optional<Ticket> saveTicket(Ticket ticket) {
		if (!tickets.containsKey(ticket.getId())) {
			tickets.put(ticket.getId(), ticket);
			return Optional.of(ticket);
		}
		return Optional.empty();
	}

	/**
	 * Cancel ticket, so that it's not valid anymore (set ticket status to
	 * cancelled). Returns Optional empty if ticket with the sent id isn't found.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Ticket> cancelTicket(UUID id) {
		Optional<Ticket> ticket = find(id);
		if (ticket.isPresent()) {
			ticket.get().setStatusTicket(StatusTicket.CANCELED);
			return ticket;
		}
		return Optional.empty();

	}

	/**
	 * Calculates the points lost because of the ticket cancelation
	 * 
	 * @param regularPrice - regular price for a ticket for that manifestation
	 * @param userPoints   - current users points
	 * @return
	 */
	public Double calculateLostPoints(Double regularPrice, Double userPoints) {
		return userPoints - (regularPrice / 1000 * 133 * 4);
	}

	/**
	 * create Location object from file
	 * 
	 * @param locationString - string to be divided into Location information
	 * @return
	 */
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

	/**
	 * create Manifestation object from file
	 * 
	 * @param manifestationString - string to be divided into Manifestation
	 *                            information
	 * @return
	 */
	public Manifestation createManifestation(String manifestationString) {
		StringTokenizer st = new StringTokenizer(manifestationString, ",");
		// Game Of Thrones,THEATRE,90,2021-10-17,250,ACTIVE,45:35:Pennsylvania
		// Plaza:4:New York:10001,got.jpg
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

			manifestation = new Manifestation(name, typeManifestation, seatingNumber, date, priceRegular, status,
					location, image);
		}
		return manifestation;
	}

	/**
	 * creating User object from file
	 * 
	 * @param userString - string to be divided into User information
	 * @return
	 */
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

	/**
	 * Loading Tickets from tickets.txt file and adding them to all tickets map
	 * {@link #tickets}. Ticket key is ticketId.
	 * 
	 * @param contextPath
	 */
	private void loadTickets(String contextPath) {
		File file = new File(contextPath + "/WEB-INF/resources/tickets.txt");
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {

			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String manifestationString = st.nextToken().trim();
					Manifestation manifestation = createManifestation(manifestationString);

					String userString = st.nextToken().trim();
					User buyerNameSurname = createUser(userString);

					StatusTicket statusTicket = StatusTicket.valueOf(st.nextToken().trim());
					TypeTicket typeTicket = TypeTicket.valueOf(st.nextToken().trim());

					Ticket newTicket = new Ticket(manifestation, buyerNameSurname, statusTicket, typeTicket, 1);
					tickets.put(newTicket.getId(), newTicket);
				}

			}
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Loading tickets failed", ex);
		}
	}

}
