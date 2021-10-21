package beans;

import java.util.UUID;

public class Comment {

	public enum StatusComment {
		APPROVED, DENIED, STANDBY;
	}

	private final UUID id = UUID.randomUUID();
	private User user;
	private Manifestation manifestation;
	private String comment;
	private int rating;
	private StatusComment status;

	public Comment() {

	}

	public Comment(User user, Manifestation manifestation, String comment, int rating, StatusComment status) {
		super();
		this.user = user;
		this.manifestation = manifestation;
		this.comment = comment;
		this.rating = rating;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public StatusComment getStatus() {
		return status;
	}

	public void setStatus(StatusComment status) {
		this.status = status;
	}

}
