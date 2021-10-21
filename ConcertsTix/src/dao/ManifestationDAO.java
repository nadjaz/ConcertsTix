package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import beans.Location;
import beans.Manifestation;
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;

public class ManifestationDAO {

	// collection of all manifestations
	private NavigableMap<UUID, Manifestation> manifestations = new TreeMap<>();

	private static Logger logger = Logger.getLogger(ManifestationDAO.class.getName());

	public ManifestationDAO() {

	}

	public ManifestationDAO(String contextPath) {
		loadManifestations(contextPath);
	}

	/**
	 * Return the whole manifestations map with keys and values.
	 * 
	 * @return
	 */
	public NavigableMap<UUID, Manifestation> returnManifestationMap() {
		return manifestations;
	}

	/**
	 * Returns manifestation for the sent id. Returns Optional empty if the
	 * manifestation doesn't exist.
	 * 
	 * @param id
	 * @returns
	 */
	public Optional<Manifestation> find(UUID id) {
		if (!manifestations.containsKey(id)) {
			return Optional.empty();
		}
		return Optional.of(manifestations.get(id));
	}

	public Collection<Manifestation> findAll() {
		return manifestations.values();
	}

	/**
	 * Returns all manifestations with an active status
	 * 
	 * @return
	 */
	public Collection<Manifestation> findAllActive() {
		Collection<Manifestation> activeManifestations = new ArrayList<>();
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getStatus() == StatusManifestation.ACTIVE) {
				activeManifestations.add(manifestation);
			}
		}
		return activeManifestations;
	}

	/**
	 * Returns all manifestations with an inactive status.
	 * 
	 * @return
	 */
	public Collection<Manifestation> findAllInactive() {
		Collection<Manifestation> inactiveManifestations = new ArrayList<>();
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestation.getStatus() == StatusManifestation.INACTIVE) {
				inactiveManifestations.add(manifestation);
			}
		}
		return inactiveManifestations;
	}

	/**
	 * Update current manifestation information. Return Optional empty if the
	 * manifestation with the sent id is not found.
	 * 
	 * @param id
	 * @param changedManifestation - manifestation with changed information
	 * @return Optional<Manfistation> - updated Manifestation
	 */
	public Optional<Manifestation> update(UUID id, Manifestation changedManifestation, String contextPath) {
		if (!manifestations.containsKey(id)) {
			return Optional.empty();
		}
		// Game Of Thrones;THEATRE;90;2021-08-17;250;ACTIVE;45,35,Pennsylvania
		// Plaza,4,New York,10001;got.jpg
		Manifestation manifestation = manifestations.get(id);
		String lineToChange = manifestation.getName() + ";" + manifestation.getTypeManifestation() + ";"
				+ manifestation.getSeatingNumber() + ";" + manifestation.getDate() + ";"
				+ manifestation.getPriceRegular() + ";" + manifestation.getStatus() + ";"
				+ manifestation.getLocation().getLength() + "," + manifestation.getLocation().getWidth() + ","
				+ manifestation.getLocation().getStreet() + "," + manifestation.getLocation().getStreetNumber() + ","
				+ manifestation.getLocation().getCity() + "," + manifestation.getLocation().getPostalCode() + ";"
				+ manifestation.getImage();

		manifestation.setName(changedManifestation.getName());
		manifestation.setTypeManifestation(changedManifestation.getTypeManifestation());
		manifestation.setSeatingNumber(changedManifestation.getSeatingNumber());
		manifestation.setPriceRegular(changedManifestation.getPriceRegular());

		String manifestationsFilePath = "manifestations.txt";
		updateManifestationInFile(lineToChange, manifestation, manifestationsFilePath, contextPath);

		return Optional.of(manifestation);
	}

	private void updateManifestationInFile(String lineToChange, Manifestation manifestation,
			String manifestationsFilePath, String contextPath) {
		StringBuilder buffer = new StringBuilder();
		try (Scanner sc = new Scanner(new File(contextPath + "WEB-INF/resources/" + manifestationsFilePath))) {

			// Reading lines of the file and appending them to StringBuilder
			while (sc.hasNextLine()) {
				buffer.append(sc.nextLine() + System.lineSeparator());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileWriter writer = new FileWriter(
				new File(contextPath + "WEB-INF/resources/" + manifestationsFilePath))) {
			String newLine = manifestation.getName() + ";" + manifestation.getTypeManifestation() + ";"
					+ manifestation.getSeatingNumber() + ";" + manifestation.getDate() + ";"
					+ manifestation.getPriceRegular() + ";" + manifestation.getStatus() + ";"
					+ manifestation.getLocation().getLength() + "," + manifestation.getLocation().getWidth() + ","
					+ manifestation.getLocation().getStreet() + "," + manifestation.getLocation().getStreetNumber()
					+ "," + manifestation.getLocation().getCity() + "," + manifestation.getLocation().getPostalCode()
					+ ";" + manifestation.getImage();

			String fileContents = buffer.toString();
			// Replacing the old line with new line
			fileContents = fileContents.replaceAll(lineToChange, newLine);

			writer.append(fileContents);
			writer.flush();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Updating manifestations in file failed", ex);
		}

	}

	/**
	 * Checks if the sent date is not already occupied by some other manifestation
	 * 
	 * @param manifestationDate
	 * @return boolean - true if date is not occupied, date is avaliable
	 */
	public boolean checkDateAvailability(LocalDate manifestationDate) {
		for (Manifestation manifestation : manifestations.values()) {
			if (manifestationDate.equals(manifestation.getDate())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Adding a newly created manifestation into the manifestations map. Return
	 * Optional empty if the manifestation already exists.
	 * 
	 * @param manifestation
	 * @return
	 * 
	 */
	public Optional<Manifestation> saveManifestation(Manifestation manifestation, String contextPath) {
		if (!manifestations.containsKey(manifestation.getId())) {
			writeManifestationToFile(manifestation, contextPath);
			manifestations.put(manifestation.getId(), manifestation);
			return Optional.of(manifestation);
		}
		return Optional.empty();
	}

	private void writeManifestationToFile(Manifestation manifestation, String contextPath) {
		String lineToWrite = manifestation.getName() + ";" + manifestation.getTypeManifestation() + ";"
				+ manifestation.getSeatingNumber() + ";" + manifestation.getDate() + ";"
				+ manifestation.getPriceRegular() + ";" + manifestation.getStatus() + ";"
				+ manifestation.getLocation().getLength() + "," + manifestation.getLocation().getWidth() + ","
				+ manifestation.getLocation().getStreet() + "," + manifestation.getLocation().getStreetNumber() + ","
				+ manifestation.getLocation().getCity() + "," + manifestation.getLocation().getPostalCode() + ";"
				+ manifestation.getImage();

		// append new manifestation to file
		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(new File(contextPath + "WEB-INF/resources/manifestations.txt"), true))) {
			writer.write(lineToWrite);
			writer.newLine();
			writer.flush();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Adding manifestation to file failed", ex);
		}
	}

	/**
	 * Change the manifestation status to active
	 * 
	 * @param id
	 * @return boolean - true if manifestation is activated
	 */
	public boolean activate(UUID id) {
		Optional<Manifestation> manifestation = find(id);
		if (manifestation.isPresent()) {
			manifestation.get().setStatus(StatusManifestation.ACTIVE);
			return true;
		}
		return false;
	}

	/**
	 * Search manifestations by certain criteria (parameter).
	 * 
	 * @param nameMani
	 * @param typeMani
	 * @param startDate
	 * @param endDate
	 * @param priceMin
	 * @param priceMax
	 * @return - collection of all the found manifestations for the entered criteria
	 */
	public Collection<Manifestation> search(String nameMani, TypeManifestation typeMani, String startDate,
			String endDate, Double priceMin, Double priceMax) {

		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateStart = LocalDate.parse(startDate, pattern);
		LocalDate dateEnd = LocalDate.parse(endDate, pattern);

		Collection<Manifestation> foundManifestations = new ArrayList<>();

		for (Manifestation manifestation : manifestations.values()) {
			if (nameMani != null && !manifestation.getName().toLowerCase().contains(nameMani.toLowerCase())) {
				continue;
			} else if (startDate != null && endDate != null
					&& !(manifestation.getDate().isAfter(dateStart) && manifestation.getDate().isBefore(dateEnd))) {
				continue;
			} else if (priceMin != null && priceMax != null
					&& !(manifestation.getPriceRegular() > priceMin && manifestation.getPriceRegular() < priceMax)) {
				continue;
			} else if (typeMani != null && !manifestation.getTypeManifestation().equals(typeMani)) {
				continue;
			}
			foundManifestations.add(manifestation);
		}
		return foundManifestations;
	}

	/**
	 * Create Location object from file.
	 * 
	 * @param locationString - string to be made into Location object (divided into
	 *                       Location information)
	 * @return
	 */
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
	 * Loading Manifestations from manifestations.txt file and adding them to all
	 * manifestations map {@link #manifestations}. Manifestations key is
	 * manifestationId.
	 * 
	 * @param contextPath
	 */
	private void loadManifestations(String contextPath) {
		try (BufferedReader in = new BufferedReader(
				new FileReader(new File(contextPath + "WEB-INF/resources/manifestations.txt")))) {
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
			logger.log(Level.SEVERE, "Loading manifestations failed", ex);
		}
	}

	/**
	 * sort the manifestitons collection by the sent value
	 * 
	 * @param sortValue - value by which we sort manifestation (name, manifestation
	 *                  type, date...)
	 * @return
	 */
	public Collection<Manifestation> sort(String sortValue) {
		if (sortValue.equals("name")) {
			SortedMap<String, Manifestation> sm = new TreeMap<>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getName(), manifestation);
			}
			return sm.values();
		} else if (sortValue.equals("typeManifestation")) {
			EnumMap<TypeManifestation, Manifestation> enumMap = new EnumMap<>(TypeManifestation.class);
			for (Manifestation manifestation : manifestations.values()) {
				enumMap.put(manifestation.getTypeManifestation(), manifestation);
			}
			return enumMap.values();

		} else if (sortValue.equals("date")) {
			SortedMap<LocalDate, Manifestation> sm = new TreeMap<>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getDate(), manifestation);
			}
			return sm.values();

		} else if (sortValue.equals("priceRegular")) {
			SortedMap<Double, Manifestation> sm = new TreeMap<>();
			for (Manifestation manifestation : manifestations.values()) {
				sm.put(manifestation.getPriceRegular(), manifestation);
			}
			return sm.values();
		}
		return Collections.emptyList();
	}
}
