package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

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

}
