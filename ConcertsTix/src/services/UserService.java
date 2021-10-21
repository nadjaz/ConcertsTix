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
import beans.LoginInformation;
import beans.Manifestation;
import beans.SellerUser;
import beans.Ticket;
import beans.User;
import dao.ManifestationDAO;
import dao.UserDAO;

@Path("/users")
public class UserService {

	@Context
	ServletContext ctx;

	String contextPath;

	public UserService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		contextPath = ctx.getRealPath("");
		if (ctx.getAttribute("userDAO") == null) {
			ctx.setAttribute("userDAO", new UserDAO(contextPath));
		}
	}

	/**
	 * Returns a list of all users.
	 * 
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.findAll();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginInformation loginInformation, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		Optional<User> loggedUser = userDao.find(loginInformation.getUsername(), loginInformation.getPassword());
		if (loggedUser.isEmpty()) {
			return Response.status(401).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("loggedInUser", loggedUser.get());
		return Response.status(200).entity("Successfully logged in").build();
	}

	@POST
	@Path("/logout")
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}

	@POST
	@Path("/registerBuyer")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerBuyer(BuyerUser user, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		Optional<User> newUser = userDao.find(user.getUsername(), user.getPassword());
		if (newUser.isPresent()) {
			return Response.status(400).entity("Username is not avaliable, try entering a different username").build();
		}
		user.setRole(User.Role.BUYER);
		userDao.register(user, contextPath);
		request.getSession().setAttribute("loggedInUser", user);
		return Response.status(200).entity("Successfully created a new user").build();
	}

	@POST
	@Path("/registerSeller")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerSeller(SellerUser user, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		Optional<User> newUser = userDao.find(user.getUsername(), user.getPassword());
		if (newUser.isPresent()) {
			return Response.status(400).entity("Username is not avaliable, try entering a different username").build();
		}
		user.setRole(User.Role.SELLER);
		userDao.register(user, contextPath);

		return Response.status(200).entity("Successfully created a new user").build();
	}

	@PUT
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(User user, @Context HttpServletRequest request, @Context HttpServletResponse response) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		Optional<User> updatedUser = userDao.update(user, contextPath);
		if (updatedUser.isEmpty()) {
			return Response.status(404).entity("User not found!").build();
		}
		request.getSession().setAttribute("loggedInUser", updatedUser.get());
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

		BuyerUser user = (BuyerUser) request.getSession().getAttribute("loggedInUser");
		userDao.addTicket(user, ticket);
		return Response.status(200).entity("Successfully added ticket to users ticket list").build();
	}

	@GET
	@Path("/listTickets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> listTickets(@Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		BuyerUser user = (BuyerUser) request.getSession().getAttribute("loggedInUser");
		return userDao.findTickets(user);
	}

	@POST
	@Path("/addManifestation/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addManifestation(@PathParam("id") UUID manifestationId, Manifestation manifestation,
			@Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");

		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> foundManifestation = manifestationDao.find(manifestationId);

		SellerUser user = (SellerUser) request.getSession().getAttribute("loggedInUser");
		if (foundManifestation.isEmpty()) {
			return Response.status(404).entity("Manifestation not found!").build();
		}
		userDao.addManifestation(user, foundManifestation.get());
		return Response.status(200).entity("Successfully added manifestation to users manifestation list").build();

	}

	@GET
	@Path("/listManifestations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Manifestation> listManifestations(@Context HttpServletRequest request) {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		SellerUser user = (SellerUser) request.getSession().getAttribute("loggedInUser");
		return userDao.findManifestations(user);
	}

	@GET
	@Path("/userPoints")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Double userPoints(@Context HttpServletRequest request) {
		return ((BuyerUser) request.getSession().getAttribute("loggedInUser")).getPoints();
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
