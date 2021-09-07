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

public class ManifestationDAO {

	// kolekcija svih manifestacija
	private NavigableMap<Integer, Manifestation> manifestations = new TreeMap<>();

	public ManifestationDAO() {

	}

	public ManifestationDAO(String contextPath) {
		loadManifestations(contextPath);
	}

	/**
	 * Vra�a manifestaciju za prosle�ene parametre. Vra�a null ako manifestacija ne
	 * postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Manifestation find(Integer id) {
		if (!manifestations.containsKey(id)) {
			return null;
		}
		Manifestation manifestation = manifestations.get(id);
		return manifestation;
	}

	public Integer findLastId() {
		return manifestations.lastKey();
	}

	public Collection<Manifestation> findAll() {
		return manifestations.values();
	}

	// vraca sve aktivne manifestacije
	public Collection<Manifestation> findAllActive() {
		Collection<Manifestation> activeManifestations = new ArrayList<>();
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getStatus() == StatusManifestation.ACTIVE) {
				activeManifestations.add(manifestation);
			}
		}
		return activeManifestations;
	}

	// vraca se INACTIVE manifestacije
	public Collection<Manifestation> findAllInactive() {
		Collection<Manifestation> inactiveManifestations = new ArrayList<>();
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getStatus() == StatusManifestation.INACTIVE) {
				inactiveManifestations.add(manifestation);
			}
		}
		return inactiveManifestations;
	}

	// proverava da li vec postoji manifestacija na odredjeni datum
	public boolean checkDateAvailability(LocalDate manifestationDate) {
		for (Manifestation value : manifestations.values()) {
			if (manifestationDate.equals(value.getDate())) {
				return false;
			}
		}
		return true;
	}

	// dodavanje novo kreirane manifestacije u listu manifestacija
	public Manifestation saveManifestation(Manifestation manifestation) {
		if (!manifestations.containsKey(manifestation.getId())) {
			manifestations.put(manifestation.getId(), manifestation);
			return manifestation;
		}
		return null;
	}

	public boolean activate(Integer id) {
		Manifestation manifestation = find(id);
		if (manifestation != null) {
			manifestation.setStatus(StatusManifestation.ACTIVE);
			return true;
		}
		return false;
	}

	// helper metoda za loadManifestations()
	public Location createLocation(String locationString) {
		StringTokenizer st = new StringTokenizer(locationString, ",");
		// 45,35,Pennsylvania Plaza,4,New York,10001
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
	 * U�itava manifestacije iz WebContent/manifestation.txt fajla i dodaje ih u
	 * mapu {@link #users}. Klju� je korisni�ko ime korisnika.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadManifestations(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/manifestations.txt");
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

					manifestations.put(id, new Manifestation(id, name, typeManifestation, seatingNumber, date,
							priceRegular, status, location, image));
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
