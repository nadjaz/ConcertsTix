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

	/**
	 * Return a list of all comments
	 * 
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAll();
	}

	/**
	 * Returns a list of all comments with a standby status. (so that seller can
	 * approve or deny them)
	 * 
	 * @return
	 */
	@GET
	@Path("/listStandby")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> getStandbyComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAllStandby();
	}

	/**
	 * Returns a list of comments with an approved status for the manifestation id
	 * sent. (so that buyer can see them)
	 * 
	 * @param manifestationId
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Path("/find/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response findApprovedCommentForManifestation(@PathParam("id") UUID manifestationId,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");

		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> manifestation = manifestationDao.find(manifestationId);

		if (manifestation.isPresent()) {
			Collection<Comment> allComments = dao.findApprovedByManifestation(manifestation.get());
			return Response.status(200).entity(allComments).build();
		}
		return Response.status(404).entity("Manifestation not found!").build();
	}

	/**
	 * Creating a new comment for the sent manifestation
	 * 
	 * @param manifestationId
	 * @param comment
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Path("/create/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createComment(@PathParam("id") UUID manifestationId, Comment comment,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");

		ManifestationDAO manifestationDao = (ManifestationDAO) ctx.getAttribute("manifestationDAO");
		Optional<Manifestation> manifestation = manifestationDao.find(manifestationId);

		if (manifestation.isEmpty()) {
			return Response.status(404).entity("Manifestation not found!").build();
		}
		User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

		Comment newComment = new Comment(loggedInUser, manifestation.get(), comment.getComment(), comment.getRating(),
				StatusComment.STANDBY);

		dao.make(newComment);
		return Response.status(200).entity("Successfully created a new comment").build();

	}

	/**
	 * Approving a comment with the sent id. Changing the comment status to
	 * approved. (only seller is authorized to do this)
	 * 
	 * @param commentId
	 * @return
	 */
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

	/**
	 * Denying a comment with the sent id. Changing the comment status to denied.
	 * (only seller is authorized to do this)
	 * 
	 * @param commentId
	 * @return
	 */
	@PUT
	@Path("/deny/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response makeDenied(@PathParam("id") UUID commentId) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		if (dao.deny(commentId)) {
			return Response.status(200).entity("Successfully denied the comment").build();
		}
		return Response.status(404).entity("Comment not found!").build();
	}

}
