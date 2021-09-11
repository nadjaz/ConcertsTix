package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;

import beans.Location;
import beans.Manifestation;
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;

public class ManifestationDAO {

	// kolekcija svih manifestacija
	private NavigableMap<UUID, Manifestation> manifestations = new TreeMap<>();

	public ManifestationDAO() {

	}

	public ManifestationDAO(String contextPath) {
		loadManifestations(contextPath);
	}

	public NavigableMap<UUID, Manifestation> returnManifestationMap() {
		return manifestations;
	}

	/**
	 * Vraæa manifestaciju za prosleðene parametre. Vraæa null ako manifestacija ne
	 * postoji
	 * 
	 * @param id
	 * @return
	 */
	public Manifestation find(UUID id) {
		if (!manifestations.containsKey(id)) {
			return null;
		}
		Manifestation manifestation = manifestations.get(id);
		return manifestation;
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

	// updejtuje postojecu manifestaciju
	public Manifestation update(UUID id, Manifestation changedManifestation) {
		if (!manifestations.containsKey(id)) {
			return null;
		}
		Manifestation manifestation = manifestations.get(id);
		// String lineToChange = user.getUsername() + ";" + user.getPassword() + ";" +
		// user.getName() + ";"
		// + user.getSurname() + ";" + user.getGender() + ";" + user.getDateOfBirth() +
		// ";" + user.getRole();

		manifestation.setName(changedManifestation.getName());
		manifestation.setTypeManifestation(changedManifestation.getTypeManifestation());
		manifestation.setSeatingNumber(changedManifestation.getSeatingNumber());
		manifestation.setPriceRegular(changedManifestation.getPriceRegular());

		// String manifestationsFilePath =
		// "C:/Users/nadja/git/WEB_Projekat_2020-2021/ConcertsTix/WebContent/manifestations.txt";
		// updateManifestationInFile(lineToChange, manifestation,
		// manifestationsFilePath);

		return manifestation;
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

	public boolean activate(UUID id) {
		Manifestation manifestation = find(id);
		if (manifestation != null) {
			manifestation.setStatus(StatusManifestation.ACTIVE);
			return true;
		}
		return false;
	}

	public Collection<Manifestation> searchDateManifestation(Collection<Manifestation> foundManifestations,
			LocalDate startDate, LocalDate endDate) {
		Collection<Manifestation> foundManifestationsDate = new ArrayList<>();

		for (Manifestation manifestation : foundManifestations) {
			if (manifestation.getDate().isAfter(startDate) && manifestation.getDate().isBefore(endDate)) {
				foundManifestationsDate.add(manifestation);
			}
		}
		return foundManifestationsDate;

	}

	public Collection<Manifestation> searchPriceManifestation(Collection<Manifestation> foundManifestations,
			Double priceMin, Double priceMax) {
		Collection<Manifestation> foundManifestationsPrice = new ArrayList<>();

		// ukoliko su unesene i min i max price
		// da li je cena veca od minimalne unesene i manja od maksimalne
		if (!priceMin.equals(null) && !priceMax.equals(null)) {
			for (Manifestation manifestation : foundManifestations) {
				if (manifestation.getPriceRegular() > priceMin && manifestation.getPriceRegular() < priceMax) {
					foundManifestationsPrice.add(manifestation);
				}
			}
			return foundManifestationsPrice;
		}

		// da li je cena veca od minimalne unesene
		else if (!priceMin.equals(null)) {
			for (Manifestation manifestation : foundManifestations) {
				if (manifestation.getPriceRegular() > priceMin) {
					foundManifestationsPrice.add(manifestation);
				}
			}
		}

		// da li je cena manja od maksimalne unesene
		else if (!priceMax.equals(null)) {
			for (Manifestation manifestation : foundManifestations) {
				if (manifestation.getPriceRegular() < priceMax) {
					foundManifestationsPrice.add(manifestation);
				}
			}
		}

		return foundManifestationsPrice;
	}

	public Collection<Manifestation> searchNameManifestation(Collection<Manifestation> foundManifestations,
			String nameMani) {
		Collection<Manifestation> foundManifestationsName = new ArrayList<>();

		// ukoliko je uneseno ime manifestacije
		// izbacujemo sve manifestacije cije se ime ne poklapa
		if (!nameMani.equals(null)) {
			for (Manifestation manifestation : foundManifestations) {
				if (manifestation.getName().equalsIgnoreCase(nameMani)) {
					foundManifestationsName.add(manifestation);
				}
			}
		}

		return foundManifestationsName;
	}

	// search manifestations by criteria
	public Collection<Manifestation> search(String nameMani, TypeManifestation typeMani, LocalDate startDate,
			LocalDate endDate, Double priceMin, Double priceMax) {

		Collection<Manifestation> foundManifestations = new ArrayList<>();

		// tip manifestacija upada u datumski opseg (koji je required)
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getTypeManifestation().equals(typeMani)) {
				if (manifestation.getDate().isAfter(startDate) && manifestation.getDate().isBefore(endDate)) {
					foundManifestations.add(manifestation);
				}
			}
		}

		Collection<Manifestation> nameManifestations = searchNameManifestation(foundManifestations, nameMani);
		Collection<Manifestation> finalManifestations = searchPriceManifestation(nameManifestations, priceMin,
				priceMax);
		if (finalManifestations.size() == 0) {
			return null;
		}
		return finalManifestations;
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

					Manifestation manifestation = new Manifestation(name, typeManifestation, seatingNumber, date,
							priceRegular, status, location, image);
					manifestations.put(manifestation.getId(), manifestation);
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

	public Collection<Manifestation> searchWithoutPrice(String nameMani, TypeManifestation typeMani,
			LocalDate dateStart, LocalDate dateEnd) {
		Collection<Manifestation> nameManifestations = searchName(nameMani, typeMani);

		return searchDateManifestation(nameManifestations, dateStart, dateEnd);
	}

	public Collection<Manifestation> searchWithoutDate(String nameMani, TypeManifestation typeMani, Double priceMin,
			Double priceMax) {
		Collection<Manifestation> nameManifestations = searchName(nameMani, typeMani);

		return searchPriceManifestation(nameManifestations, priceMin, priceMax);
	}

	public Collection<Manifestation> searchName(String nameMani, TypeManifestation typeMani) {
		Collection<Manifestation> foundManifestationsName = new ArrayList<>();

		// ukoliko je uneseno ime manifestacije
		// izbacujemo sve manifestacije cije se ime ne poklapa
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getName().equalsIgnoreCase(nameMani)
					&& manifestation.getTypeManifestation().equals(typeMani)) {
				foundManifestationsName.add(manifestation);
			}
		}
		return foundManifestationsName;
	}

	public Collection<Manifestation> searchWithoutName(TypeManifestation typeMani, LocalDate dateStart,
			LocalDate dateEnd, Double priceMin, Double priceMax) {
		Collection<Manifestation> foundManifestationsType = new ArrayList<>();

		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getTypeManifestation().equals(typeMani)) {
				foundManifestationsType.add(manifestation);
			}
		}
		Collection<Manifestation> foundManifestationsPrice = searchPriceManifestation(foundManifestationsType, priceMin,
				priceMax);
		return searchDateManifestation(foundManifestationsPrice, dateStart, dateEnd);
	}

	public Collection<Manifestation> searchDate(TypeManifestation typeMani, LocalDate dateStart, LocalDate dateEnd) {
		Collection<Manifestation> foundManifestationsType = new ArrayList<>();

		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getTypeManifestation().equals(typeMani)) {
				foundManifestationsType.add(manifestation);
			}
		}

		return searchDateManifestation(foundManifestationsType, dateStart, dateEnd);
	}

	public Collection<Manifestation> searchPrice(TypeManifestation typeMani, Double priceMin, Double priceMax) {
		Collection<Manifestation> foundManifestationsType = new ArrayList<>();

		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getTypeManifestation().equals(typeMani)) {
				foundManifestationsType.add(manifestation);
			}
		}

		return searchPriceManifestation(foundManifestationsType, priceMin, priceMax);
	}

	public Collection<Manifestation> searchType(TypeManifestation typeMani) {
		Collection<Manifestation> foundManifestationsType = new ArrayList<>();

		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getTypeManifestation().equals(typeMani)) {
				foundManifestationsType.add(manifestation);
			}
		}
		return foundManifestationsType;
	}

	public Collection<Manifestation> sort(String sortValue) {
		if (sortValue.equals("name")) {
			SortedMap<String, Manifestation> sm = new TreeMap<String, Manifestation>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getName(), manifestation);
			}
			return sm.values();
		} else if (sortValue.equals("typeManifestation")) {
			EnumMap<TypeManifestation, Manifestation> enumMap = new EnumMap<TypeManifestation, Manifestation>(TypeManifestation.class);
			for (Manifestation manifestation : manifestations.values()) {
				enumMap.put(manifestation.getTypeManifestation(), manifestation);
			}
			return enumMap.values();

		} else if (sortValue.equals("date")) {
			SortedMap<LocalDate, Manifestation> sm = new TreeMap<LocalDate, Manifestation>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getDate(), manifestation);
			}
			return sm.values();

		} else if (sortValue.equals("priceRegular")) {
			SortedMap<Double, Manifestation> sm = new TreeMap<Double, Manifestation>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getPriceRegular(), manifestation);
			}
			return sm.values();
		}
		return null;
	}
}
