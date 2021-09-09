package services;

import java.net.URISyntaxException;
import java.util.Collection;

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
	// kako bi sam kupac mogao da vidi samo manifestacije za kojih ima dovoljno karata
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
	public Response createManifestation(Manifestation manifestation, @Context HttpServletRequest request, @Context HttpServletResponse response) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
	
		if (!dao.checkDateAvailability(manifestation.getDate())) {
			return Response.status(400).entity("Date already occupied, you need to entry a different manifestation date").build();
		}
		
		Integer getLastId = dao.findLastId();
		manifestation.setId(++getLastId);
		manifestation.setStatus(StatusManifestation.INACTIVE);
		Location location = new Location(45, 35, "Pennsylvania Plaza", 4, "New York", 10001);
		manifestation.setLocation(location);
		
		//User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		Manifestation newManifestation = dao.saveManifestation(manifestation);
		if (newManifestation != null) {
			return Response.status(200).entity("Successfully created a manifestation").build();
		}
		return Response.status(400).entity("Manifestation with the same id already created").build();
	}
	
	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateManifestation(@PathParam("id") Integer id, Manifestation manifestation, @Context HttpServletRequest request, @Context HttpServletResponse response)
			throws URISyntaxException {
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
	public Manifestation reserveManifestation(@PathParam("id") Integer id) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = dao.find(id);
		if (manifestation != null) {
			Response.status(200).entity("Found the manifestation").build();
			return manifestation;
		}
		Response.status(404).entity("Manifestation not found").build();
		return null;
	}
	
	// vraca poslednju napravljenu manifestaciju
	@GET
	@Path("/lastOne")
	@Produces(MediaType.APPLICATION_JSON)
	public Manifestation lastManifestationCreated(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = dao.findLastManifestation();
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
	public Response reserveManifestationSeatingNumber(@PathParam("id") Integer id, @PathParam("num") Integer num) {
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
	public Response activateManifestation(@PathParam("id") Integer id) {
		ManifestationDAO dao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		if (dao.activate(id)) {
			return Response.status(200).entity("Successfully activated the manifestation").build();
		}
		return Response.status(400).entity("Failed to active the manifestation").build();
	}

}
