package services;

import java.net.URISyntaxException;
import java.util.ArrayList;
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

import beans.LoginInformation;
import beans.Manifestation;
import beans.Ticket;
import beans.User;
import dao.ManifestationDAO;
import dao.UserDAO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;

	public UserService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("userDAO") == null) {
			String contextPath = ctx.getRealPath("");
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}

	// vraca listu svih proizvoda
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.findAll();
	}

	// logovanje korisnika sa kredincijalima
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginInformation loginInformation, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User loggedUser = userDao.find(loginInformation.getUsername(), loginInformation.getPassword());
		if (loggedUser == null) {
			return Response.status(401).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("loggedInUser", loggedUser);
		return Response.status(200).entity("Successfully logged in").build();
	}

	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		// String username = ((User)
		// request.getSession().getAttribute("loggedInUser")).getUsername();
		request.getSession().invalidate();
		// return username;
	}

	@POST
	@Path("/registerBuyer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerBuyer(User user, @Context HttpServletRequest request, @Context HttpServletResponse response)
			throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User newUser = userDao.find(user.getUsername(), user.getPassword());
		if (newUser != null) {
			return Response.status(400).entity("Username is not avaliable, try entering a different username").build();
		}
		user.setRole(User.Role.BUYER);
		user.setPoints(0.0);
		user.setAllTickets(new ArrayList<Ticket>());
		userDao.register(user);
		request.getSession().setAttribute("loggedInUser", user);
		return Response.status(200).entity("Successfully created a new user").build();
	}

	@POST
	@Path("/registerSeller")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerSeller(User user, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User newUser = userDao.find(user.getUsername(), user.getPassword());
		if (newUser != null) {
			return Response.status(400).entity("Username is not avaliable, try entering a different username").build();
		}
		user.setRole(User.Role.SELLER);
		user.setAllManifestations(new ArrayList<Manifestation>());
		userDao.register(user);

		return Response.status(200).entity("Successfully created a new user").build();
	}

	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user, @Context HttpServletRequest request, @Context HttpServletResponse response)
			throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User updatedUser = userDao.update(user);
		if (updatedUser == null) {
			return Response.status(404).entity("User not found!").build();
		}
		request.getSession().setAttribute("loggedInUser", updatedUser);
		return Response.status(200).entity("Successfully updated a user").build();
	}

	@POST
	@Path("/addTicket")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTicket(Ticket ticket, @Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		
		// error occurs
		// Recursive reference has been found in class
		// TicketDAO ticketDao = (TicketDAO) ctx.getAttribute("ticketDAO");
		// Ticket foundTicket = ticketDao.find(ticket.getId());
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		userDao.addTicket(user, ticket);
		return Response.status(200).entity("Successfully added ticket to users ticket list").build();
	}

	@GET
	@Path("/listTickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> listTickets(@Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		return userDao.findTickets(user);
	}

	@POST
	@Path("/addManifestation/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addManifestation(@PathParam("id") UUID manifestationId, Manifestation manifestation, @Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		
		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation foundManifestation = manifestationDao.find(manifestationId);
		
		User user = (User) request.getSession().getAttribute("loggedInUser");
		userDao.addManifestation(user, foundManifestation);
		return Response.status(200).entity("Successfully added manifestation to users manifestation list").build();
	}

	@GET
	@Path("/listManifestations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> listManifestations(@Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User user = (User) request.getSession().getAttribute("loggedInUser");
		return userDao.findManifestations(user);
	}

	@GET
	@Path("/userPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Double userPoints(@Context HttpServletRequest request) {
		return ((User) request.getSession().getAttribute("loggedInUser")).getPoints();
	}

	@GET
	@Path("/loggedInUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User loggedInUser(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("loggedInUser");
	}

	@GET
	@Path("/loggedInUserRole")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User.Role loggedInUserRole(@Context HttpServletRequest request) {
		return ((User) request.getSession().getAttribute("loggedInUser")).getRole();
	}

}
