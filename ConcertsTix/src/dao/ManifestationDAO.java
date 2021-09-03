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
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;

public class ManifestationDAO {

	// kolekcija svih manifestacija
	private Map<String, Manifestation> manifestations = new HashMap<>();

	public ManifestationDAO() {

	}

	public ManifestationDAO(String contextPath) {
		loadManifestations(contextPath);
	}
	
	/**
	 * Vraæa manifestaciju za prosleðene parametre. Vraæa null ako manifestacija
	 * ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public Manifestation find(String id) {
		if (!manifestations.containsKey(id)) {
			return null;
		}
		Manifestation manifestation = manifestations.get(id);
		return manifestation;
	}
	
	public Collection<Manifestation> findAll() {
		return manifestations.values();
	}

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
	 * Uèitava manifestacije iz WebContent/manifestation.txt fajla i dodaje ih u
	 * mapu {@link #users}. Kljuè je korisnièko ime korisnika.
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
					String id = st.nextToken().trim();
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
