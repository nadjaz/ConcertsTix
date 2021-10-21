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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.BuyerUser;
import beans.Manifestation;
import beans.Ticket;
import beans.Ticket.StatusTicket;
import beans.Ticket.TypeTicket;
import beans.User;
import dao.ManifestationDAO;
import dao.TicketDAO;

@Path("/tickets")
public class TicketService {

	@Context
	ServletContext ctx;

	public TicketService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("ticketDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("ticketDAO", new TicketDAO(contextPath));
		}
	}

	/**
	 * Returns a list of all tickets with all statuses.
	 * 
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		return dao.findAll();
	}

	/**
	 * Returns a list of all reserved tickets
	 * 
	 * @return
	 */
	@GET
	@Path("/listReserved")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getReservedTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		return dao.findAllReserved();
	}

	/**
	 * Returns a list of all reserved tickets for a specific user
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/list/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getTickets(@PathParam("username") String username, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		return dao.findForUser(loggedInUser);
	}

	/**
	 * Returns a list of all finished manifestations that the user has bought
	 * tickets for
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/myFinishedManifestations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> getFinishedManifestations(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		return dao.findManifestationsForUser(loggedInUser, manifestationDao.returnManifestationMap());
	}

	/**
	 * Reserving specific number of tickets for a manifestation
	 * 
	 * @param type                      - ticket type user wants to reserve
	 * @param num                       - number of tickets user wants to reserve
	 * @param manifestationToBeReserved
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Path("/reserve/{type}/{num}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reserve(@PathParam("type") TypeTicket type, @PathParam("num") Integer num,
			UUID uuidManifestationToBeReserved, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");

		BuyerUser loggedInUser = (BuyerUser) request.getSession().getAttribute("loggedInUser");

		Optional<Manifestation> manifestationToReserve = manifestationDao.find(uuidManifestationToBeReserved);
		if (manifestationToReserve.isEmpty()) {
			return Response.status(400).entity("Manifestation not found!").build();
		}

		loggedInUser.setPoints(
				dao.calculateLostPoints(manifestationToReserve.get().getPriceRegular(), loggedInUser.getPoints()));
		Ticket newTicket = new Ticket(manifestationToReserve.get(), loggedInUser, StatusTicket.RESERVED, type, num);
		Optional<Ticket> ticketToSave = dao.saveTicket(newTicket);

		if (ticketToSave.isPresent()) {
			return Response.status(200).entity(newTicket.getId()).build();
		}
		return Response.status(400).entity("Ticket with the same id already created").build();
	}

	/**
	 * Cancels the ticket with a sent id.
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PUT
	@Path("/cancel/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelTicket(@PathParam("id") UUID id, @Context HttpServletRequest request) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");

		Optional<Ticket> ticket = dao.cancelTicket(id);
		if (ticket.isPresent()) {
			// setovanje novog broja slobodnih mesta nakon odustanka od karte
			// trenutni broj povecan za broj kupljenih karata

			ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
			Optional<Manifestation> foundManifestation = manifestationDao.find(ticket.get().getManifestation().getId());

			if (foundManifestation.isEmpty()) {
				return Response.status(404).entity("Manifestation not found!").build();
			}

			Integer currentSeatingNumber = foundManifestation.get().getSeatingNumber();

			ticket.get().getNumberOfTickets();
			foundManifestation.get().setSeatingNumber(currentSeatingNumber + ticket.get().getNumberOfTickets());

			// racunanje broja izgubljenih bodova zbog odustanka od rezervacije
			BuyerUser loggedInUser = (BuyerUser) request.getSession().getAttribute("loggedInUser");
			Double newPoints = dao.calculateLostPoints(foundManifestation.get().getPriceRegular(),
					loggedInUser.getPoints());
			loggedInUser.setPoints(newPoints);
			return Response.status(200).entity("Found the ticket").build();
		}
		return Response.status(404).entity("Ticket not found").build();
	}

	/**
	 * Returns the last created ticket.
	 * 
	 * @param ticketId
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/findOne/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lastTicketCreated(@PathParam("id") UUID ticketId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		Optional<Ticket> ticket = dao.find(ticketId);
		if (ticket.isPresent()) {
			return Response.status(200).entity(ticket.get()).build();
		}
		return Response.status(404).entity("Ticket not found").build();
	}

}
