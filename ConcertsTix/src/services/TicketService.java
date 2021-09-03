package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Manifestation;
import beans.Ticket;
import beans.Ticket.StatusTicket;
import beans.Ticket.TypeTicket;
import beans.User;
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
	
	// rezervacija karte za odredjenu manifestaciju
	@POST
	@Path("/reserve")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(Manifestation manifestationToBeReserved, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		TicketDAO dao = (TicketDAO) ctx.getAttribute("ticketDAO");
		
		Integer getLastId = dao.findLastId();
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
		
		Ticket newTicket = new Ticket(++getLastId, manifestationToBeReserved, loggedInUser, StatusTicket.RESERVED, TypeTicket.REGULAR);
		
		Ticket ticketAdded = dao.saveTicket(newTicket);
		if (ticketAdded != null) {
			return Response.status(200).entity("Successfully reserveded your ticket").build();
		}
		return Response.status(400).entity("Ticket with the same id already created").build();
	}

}
