package services;

import java.net.URISyntaxException;
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

import beans.Comment;
import beans.Comment.StatusComment;
import beans.Manifestation;
import beans.User;
import dao.CommentDAO;
import dao.ManifestationDAO;

@Path("/comments")
public class CommentService {

	@Context
	ServletContext ctx;

	public CommentService() {

	}

	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora
	// (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vise puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("commentDAO") == null) {
			ctx.setAttribute("commentDAO", new CommentDAO());
		}
	}

	// vraca listu svih komentara
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAll();
	}

	// vraca listu svih STANDBY komentara
	// kako bi seller mogao da ih approve ili deny
	@GET
	@Path("/listStandby")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getStandbyComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAllStandby();
	}

	// vraca listu approved komentara za poslatu manifestaciju kako bi buyer mogao da ih vidi
	@GET
	@Path("/find/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Comment> findApprovedCommentForManifestation(@PathParam("id") UUID manifestationId,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");

		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = manifestationDao.find(manifestationId);

		Collection<Comment> allComments = dao.findByManifestation(manifestation);
		Response.status(200).entity("Successfully created a new comment").build();
		return allComments;
	}

	// pravi novi komentar za poslatu manfestaciju
	@POST
	@Path("/create/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createComment(@PathParam("id") UUID manifestationId, Comment comment,
			@Context HttpServletRequest request, @Context HttpServletResponse response) throws URISyntaxException {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");

		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Manifestation manifestation = manifestationDao.find(manifestationId);

		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		Comment newComment = new Comment(loggedInUser, manifestation, comment.getComment(), comment.getRating(),
				StatusComment.STANDBY);

		dao.make(newComment);
		return Response.status(200).entity("Successfully created a new comment").build();
	}

	// updejtuje status komentara u APPROVED
	// seller ovo moze da uradi
	@PUT
	@Path("/approve/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response makeApproved(@PathParam("id") UUID commentId) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		if (dao.approve(commentId)) {
			return Response.status(200).entity("Successfully approved the comment").build();
		}
		return Response.status(404).entity("Comment not found!").build();
	}

}
