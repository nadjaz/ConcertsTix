package services;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Location;
import beans.Manifestation;
import beans.Manifestation.StatusManifestation;
import beans.Manifestation.TypeManifestation;
import dao.ManifestationDAO;

@Path("/manifestations")
public class ManifestationService {

	@Context
	ServletContext ctx;

	public ManifestationService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("manifestationDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("manifestationDAO", new ManifestationDAO(contextPath));
		}
	}

	// vraca listu svih manifestacija
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAll();
	}

	// vraca listu svih AKTIVNIH manifestacija
	// kako bi sam kupac mogao da vidi samo manifestacije za kojih ima dovoljno
	// karata
	@GET
	@Path("/listActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getActiveManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAllActive();
	}

	// vraca listu svih NEAKTIVNIH manifestacija
	// kako bi administrator mogao da od manifestacije napravi AKTIVNU
	@GET
	@Path("/listInactive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getInactiveManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAllInactive();
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createManifestation(Manifestation manifestation, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		if (!dao.checkDateAvailability(manifestation.getDate())) {
			return Response.status(400)
					.entity("Date already occupied, you need to entry a different manifestation date").build();
		}

		Location location = new Location(45, 35, "Pennsylvania Plaza", 4, "New York", 10001);

		Manifestation newManifestation = new Manifestation(manifestation.getName(),
				manifestation.getTypeManifestation(), manifestation.getSeatingNumber(), manifestation.getDate(),
				manifestation.getPriceRegular(), StatusManifestation.INACTIVE, location, manifestation.getImage());

		if (dao.saveManifestation(newManifestation) != null) {
			return Response.status(200).entity(newManifestation.getId()).build();
		}
		return Response.status(400).entity("Manifestation with the same id already created").build();
	}

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateManifestation(@PathParam("id") UUID id, Manifestation manifestation,
			@Context HttpServletRequest request, @Context HttpServletResponse response) throws URISyntaxException {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation updatedManifestation = dao.update(id, manifestation);
		if (updatedManifestation == null) {
			return Response.status(404).entity("Manifestation not found!").build();
		}
		return Response.status(200).entity("Successfully updated the manifestation").build();
	}

	// vraca manifestaciju za zadati id
	@GET
	@Path("/findOne/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation reserveManifestation(@PathParam("id") UUID id) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = dao.find(id);
		if (manifestation != null) {
			Response.status(200).entity("Found the manifestation").build();
			return manifestation;
		}
		Response.status(404).entity("Manifestation not found").build();
		return null;
	}

	// updejtuje manifestaciju kako bi smanjio broj slobodnih mesta
	// jer je manifestacija upravo rezervisana
	@PUT
	@Path("/reserveOne/{id}/{num}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reserveManifestationSeatingNumber(@PathParam("id") UUID id, @PathParam("num") Integer num) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = dao.find(id);
		if (manifestation != null) {
			// setovanje novog broja slobodnih mesta nakon rezervacije karte
			// trenutni broj umanjen za broj kupljenih karata
			manifestation.setSeatingNumber(manifestation.getSeatingNumber() - num);
			return Response.status(200).entity("Found the manifestation").build();
		}
		return Response.status(404).entity("Manifestation not found").build();
	}

	// aktivira manifestaciju
	@GET
	@Path("/activateOne/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response activateManifestation(@PathParam("id") UUID id) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		if (dao.activate(id)) {
			return Response.status(200).entity("Successfully activated the manifestation").build();
		}
		return Response.status(400).entity("Failed to active the manifestation").build();
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findOne/{name}/{type}/{startDate}/{endDate}/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("name") String nameMani,
			@PathParam("type") TypeManifestation typeMani, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate, @PathParam("priceMin") Double priceMin,
			@PathParam("priceMax") Double priceMax) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateStart = LocalDate.parse(startDate, pattern);
		LocalDate dateEnd = LocalDate.parse(endDate, pattern);

		Collection<Manifestation> foundManifestations = dao.search(nameMani, typeMani, dateStart, dateEnd, priceMin,
				priceMax);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findDate/{name}/{type}/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("name") String nameMani,
			@PathParam("type") TypeManifestation typeMani, @PathParam("startDate") String startDate,
			@PathParam("endDate") String endDate) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateStart = LocalDate.parse(startDate, pattern);
		LocalDate dateEnd = LocalDate.parse(endDate, pattern);

		Collection<Manifestation> foundManifestations = dao.searchWithoutPrice(nameMani, typeMani, dateStart, dateEnd);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findPrice/{name}/{type}/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("name") String nameMani,
			@PathParam("type") TypeManifestation typeMani, @PathParam("priceMin") Double priceMin,
			@PathParam("priceMax") Double priceMax) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.searchWithoutDate(nameMani, typeMani, priceMin, priceMax);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findOne/{name}/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("name") String nameMani,
			@PathParam("type") TypeManifestation typeMani) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.searchName(nameMani, typeMani);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findOne/{type}/{startDate}/{endDate}/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("type") TypeManifestation typeMani,
			@PathParam("startDate") String startDate, @PathParam("endDate") String endDate,
			@PathParam("priceMin") Double priceMin, @PathParam("priceMax") Double priceMax) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateStart = LocalDate.parse(startDate, pattern);
		LocalDate dateEnd = LocalDate.parse(endDate, pattern);

		Collection<Manifestation> foundManifestations = dao.searchWithoutName(typeMani, dateStart, dateEnd, priceMin,
				priceMax);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findJustDate/{type}/{startDate}/{endDate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("type") TypeManifestation typeMani,
			@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateStart = LocalDate.parse(startDate, pattern);
		LocalDate dateEnd = LocalDate.parse(endDate, pattern);

		Collection<Manifestation> foundManifestations = dao.searchDate(typeMani, dateStart, dateEnd);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findJustPrice/{type}/{priceMin}/{priceMax}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("type") TypeManifestation typeMani,
			@PathParam("priceMin") Double priceMin, @PathParam("priceMax") Double priceMax) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.searchPrice(typeMani, priceMin, priceMax);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}

	// search manifestacija za prosledjene parametre
	@GET
	@Path("/findType/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> searchManifestations(@PathParam("type") TypeManifestation typeMani) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.searchType(typeMani);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}
	
	// sort manifestacija za prosledjene parametre
	@GET
	@Path("/sort/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> sortManifestations(@PathParam("value") String sortValue) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.sort(sortValue);

		if (foundManifestations != null) {
			Response.status(200).entity("Found the manifestations that match the criteria").build();
			return foundManifestations;
		}
		Response.status(404).entity("Manifestations not found").build();
		return null;
	}
	
	

}
