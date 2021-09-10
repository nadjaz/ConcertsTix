package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

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

	// kolekcija svih registrovanih inicijalnih karata
	private NavigableMap<Integer, Ticket> tickets = new TreeMap<>();

	public TicketDAO() {

	}

	public TicketDAO(String contextPath) {
		loadTickets(contextPath);
	}

	public Collection<Ticket> findAll() {
		return tickets.values();
	}
	
	/**
	 * Vraæa ticket za prosleðene parametre. Vraæa null ako ticket ne
	 * postoji
	 * 
	 * @param id
	 * @return
	 */
	public Ticket find(Integer id) {
		if (!tickets.containsKey(id)) {
			return null;
		}
		Ticket ticket = tickets.get(id);
		return ticket;
	}
	
	// find all reserved tickets
	public Collection<Ticket> findAllReserved() {
		Collection<Ticket> reservedTickets = new ArrayList<Ticket>();
		for (Ticket ticket : tickets.values()) {
			if (ticket.getStatusTicket().equals(StatusTicket.RESERVED))  {
				reservedTickets.add(ticket);
			}
		}
		return reservedTickets;
	}

	// returns all reserved tickets for the sent user
	public Collection<Ticket> findForUser(User user) {
		Collection<Ticket> myTickets = new ArrayList<>();
		for (Ticket ticket : tickets.values()) {
			if (ticket.getBuyerNameSurname().equals(user) && ticket.getStatusTicket().equals(StatusTicket.RESERVED)) {
				myTickets.add(ticket);
			}
		}
		return myTickets;
	}
	
	// vracamo manifestacije za koje je korisnik rezervisao karte
	// a koje su se vec odrzale
	// da bi kupac mogao da ostavi komentar
	public Collection<Manifestation> findManifestationsForUser(User user, NavigableMap<Integer, Manifestation> manifestations) {
		
		Collection<Ticket> usersTickets = findForUser(user);
		Collection<Manifestation> usersManifestations = new ArrayList<Manifestation>();
		Collection<Manifestation> filteredManifestations = new ArrayList<Manifestation>();
		
		LocalDate todaysDate = LocalDate.now();
		
		for (Ticket ticket : usersTickets) {
			usersManifestations.add(ticket.getManifestation());
		}
		for (Manifestation manifestation : usersManifestations) {
			if(todaysDate.isAfter(manifestation.getDate())) {
				filteredManifestations.add(manifestations.get(manifestation.getId()));
			}
		}
		return filteredManifestations;
	}

	public Integer findLastId() {
		return tickets.lastKey();
	}
	
	public Ticket findLastTicket() {
		Integer lastId = findLastId();
		Ticket foundTicket = tickets.get(lastId);
		if (foundTicket != null) {
			return foundTicket;
		}
		return null;
	}

	public Ticket saveTicket(Ticket ticket) {
		if (!tickets.containsKey(ticket.getId())) {
			tickets.put(ticket.getId(), ticket);
			return ticket;
		}
		return null;
	}
	
	// cancel ticket, change ticket status
	public Ticket cancelTicket(Integer id) {
		Ticket ticket = find(id);
		if (!ticket.equals(null)) {
			ticket.setStatusTicket(StatusTicket.CANCELED);
			return ticket;
		}
		return null;
		
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
		// 0001,Game Of Thrones,THEATRE,90,2021-10-17,250,ACTIVE,45:35:Pennsylvania
		// Plaza:4:New York:10001,got.jpg
		Manifestation manifestation = null;
		while (st.hasMoreTokens()) {
			Integer id = Integer.parseInt(st.nextToken().trim());
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

			manifestation = new Manifestation(id, name, typeManifestation, seatingNumber, date, priceRegular, status,
					location, image);
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
					Integer id = Integer.parseInt(st.nextToken().trim());

					String manifestationString = st.nextToken().trim();
					Manifestation manifestation = createManifestation(manifestationString);

					String userString = st.nextToken().trim();
					User buyerNameSurname = createUser(userString);

					StatusTicket statusTicket = StatusTicket.valueOf(st.nextToken().trim());
					TypeTicket typeTicket = TypeTicket.valueOf(st.nextToken().trim());

					tickets.put(id, new Ticket(id, manifestation, buyerNameSurname, statusTicket, typeTicket, 1));
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
