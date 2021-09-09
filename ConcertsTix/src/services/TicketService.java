package services;

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

	// vraca listu svih karata
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		return dao.findAll();
	}
	
	// vraca listu svih karata
	@GET
	@Path("/listReserved")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> getReservedTickets() {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		return dao.findAllReserved();
	}
	
	// vraca listu svih karata za poslati username
	// samo onih koji su statusa reserved, ne canceled
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
	
	// rezervacija karte za odredjenu manifestaciju
	// num - broj karata koje se rezervisu
	// type - tip karata koje se rezervisu
	@POST
	@Path("/reserve/{type}/{num}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response reserve(@PathParam("type") TypeTicket type, @PathParam("num") Integer num, Manifestation manifestationToBeReserved, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		
		Integer getLastId = dao.findLastId();
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		loggedInUser.setPoints(loggedInUser.getPoints() + (manifestationToBeReserved.getPriceRegular()/1000 * 133));
		
		Ticket newTicket = new Ticket(++getLastId, manifestationToBeReserved, loggedInUser, StatusTicket.RESERVED, type, num);
		
		if (dao.saveTicket(newTicket) != null) {
			return Response.status(200).entity("Successfully reserveded your ticket").build();
		}
		return Response.status(400).entity("Ticket with the same id already created").build();
	}
	
	// menja status ticketa koji je napravio user
	// status prelazi u CANCELED
	@PUT
	@Path("/cancel/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response cancelTicket(@PathParam("id") Integer id, @Context HttpServletRequest request) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		
		Ticket ticket = dao.cancelTicket(id);
		if (ticket != null) {
			// setovanje novog broja slobodnih mesta nakon odustanka od karte
			// trenutni broj povecan za broj kupljenih karata
			
			ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
			Manifestation foundManifestation = manifestationDao.find(ticket.getManifestation().getId());
			
			Integer currentSeatingNumber = foundManifestation.getSeatingNumber();
			
			ticket.getNumberOfTickets();
			foundManifestation.setSeatingNumber(currentSeatingNumber + ticket.getNumberOfTickets());
			
			// racunanje broja izgubljenih bodova zbog odustanka od rezervacije
			User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
			loggedInUser.setPoints(loggedInUser.getPoints() - (foundManifestation.getPriceRegular()/1000 * 133 * 4));
			
			return Response.status(200).entity("Found the ticket").build();
		}
		return Response.status(404).entity("Ticket not found").build();
	}
	
	// vraca poslednju napravljeni ticket
	@GET
	@Path("/lastOne")
	@Produces(MediaType.APPLICATION_JSON)
	public Ticket lastTicketCreated(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		Ticket ticket = dao.findLastTicket();
		if (ticket != null) {
			Response.status(200).entity("Found the ticket").build();
			return ticket;
		}
		Response.status(404).entity("Ticket not found").build();
		return null;
	}

}
