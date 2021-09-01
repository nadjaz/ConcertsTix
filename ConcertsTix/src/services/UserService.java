package services;

import java.net.URI;
import java.net.URISyntaxException;
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

import beans.LoginInformation;
import beans.User;
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
	@Produces("text/html")
	public Response login(LoginInformation loginInformation, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User loggedUser = userDao.find(loginInformation.getUsername(), loginInformation.getPassword());
		if (loggedUser == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("user", loggedUser);

		URI uri = new URI("http://localhost:8080/ConcertsTix/homepage.html");
		//response.sendRedirect("http://localhost:8080/ConcertsTix/homepage.html");
		return Response.temporaryRedirect(uri).build();
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/html")
	public Response register(User user, @Context HttpServletRequest request, @Context HttpServletResponse response)
			throws URISyntaxException {
		UserDAO userDao = (UserDAO) ctx.getAttribute("userDAO");
		User newUser = userDao.find(user.getUsername(), user.getPassword());
		if (newUser != null) {
			return Response.status(400).entity("Username is not avaliable").build();
		}
		userDao.register(user);
		request.getSession().setAttribute("user", newUser);
		URI uri = new URI("http://localhost:8080/ConcertsTix/homepage.html");
		// response.sendRedirect("http://localhost:8080/ConcertsTix/homepage.html");
		return Response.temporaryRedirect(uri).build();
	}

	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}

}
