package services;

import java.util.Collection;
import java.util.Optional;
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
import javax.ws.rs.QueryParam;
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

	String contextPath;

	public ManifestationService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("manifestationDAO") == null) {
			ctx.setAttribute("manifestationDAO", new ManifestationDAO(contextPath));
		}
	}

	/**
	 * Returns a list of all manifestations
	 * 
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAll();
	}

	/**
	 * Returns a list of all manifestations with active status (so that the buyer
	 * can see only manifestations that he can buy tickets to)
	 * 
	 * @return
	 */
	@GET
	@Path("/listActive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getActiveManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAllActive();
	}

	/**
	 * Returns a list of all manifestations with inactive status (so that the
	 * administrator can change their status to active)
	 * 
	 * @return
	 */
	@GET
	@Path("/listInactive")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getInactiveManifestations() {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		return dao.findAllInactive();
	}

	/**
	 * Creating a new manifestation.
	 * 
	 * @param manifestation
	 * @param request
	 * @param response
	 * @return
	 */
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

		if (dao.saveManifestation(newManifestation, contextPath).isPresent()) {
			return Response.status(200).entity(newManifestation.getId()).build();
		}
		return Response.status(400).entity("Manifestation with the same id already created").build();
	}

	/**
	 * Updating manifestation with the sent id
	 * 
	 * @param id
	 * @param manifestation - manifestation with changed info
	 * @param request
	 * @param response
	 * @return
	 */
	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateManifestation(@PathParam("id") UUID id, Manifestation manifestation,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> updatedManifestation = dao.update(id, manifestation, contextPath);
		if (updatedManifestation.isEmpty()) {
			return Response.status(404).entity("Manifestation not found!").build();
		}
		return Response.status(200).entity("Successfully updated the manifestation").build();
	}

	/**
	 * Return manifestation for the sent id
	 * 
	 * @param id
	 * @return
	 */
	@GET
	@Path("/findOne/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response reserveManifestation(@PathParam("id") UUID id) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> manifestation = dao.find(id);
		if (manifestation.isPresent()) {
			return Response.status(200).entity(manifestation.get()).build();

		}
		return Response.status(404).entity("Manifestation not found").build();
	}

	/**
	 * Decreasing the seating number for a manifestation that user reserved seats
	 * (tickets) for. This happens after the manifestations ticket are reserved by
	 * the user
	 * 
	 * @param id
	 * @param num - number of tickets (seats) user reserved
	 * @return
	 */
	@PUT
	@Path("/reserveOne/{id}/{num}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reserveManifestationSeatingNumber(@PathParam("id") UUID id, @PathParam("num") Integer num) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> manifestation = dao.find(id);
		if (manifestation.isPresent()) {
			// setovanje novog broja slobodnih mesta nakon rezervacije karte
			// trenutni broj umanjen za broj kupljenih karata
			manifestation.get().setSeatingNumber(manifestation.get().getSeatingNumber() - num);
			return Response.status(200).entity("Found the manifestation").build();
		}
		return Response.status(404).entity("Manifestation not found").build();
	}

	/**
	 * Change a manifestation status to active
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * Search manifestations for the sent parameters.
	 * 
	 * @param nameMani
	 * @param typeMani
	 * @param startDate
	 * @param endDate
	 * @param priceMin
	 * @param priceMax
	 * @return
	 */
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchManifestations(@QueryParam("name") String nameMani,
			@QueryParam("type") TypeManifestation typeMani, @QueryParam("startDate") String startDate,
			@QueryParam("endDate") String endDate, @QueryParam("priceMin") Double priceMin,
			@QueryParam("priceMax") Double priceMax) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.search(nameMani, typeMani, startDate, endDate, priceMin,
				priceMax);
		return Response.status(200).entity(foundManifestations).build();

	}

	/**
	 * Returns a list of sorted manifestations by the sort value
	 * 
	 * @param sortValue - value by which we want to sort manifestations
	 * @return
	 */
	@GET
	@Path("/sort/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sortManifestations(@PathParam("value") String sortValue) {

		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		Collection<Manifestation> foundManifestations = dao.sort(sortValue);

		if (foundManifestations != null) {
			return Response.status(200).entity(foundManifestations).build();
		}
		return Response.status(404).entity("Manifestations not found").build();
	}

}
