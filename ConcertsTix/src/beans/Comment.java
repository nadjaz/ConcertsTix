package beans;

public class Comment {

	private User user;
	private Manifestation manifestation;
	private String comment;
	private int rating;

	public Comment() {
		super();
	}

	public Comment(User user, Manifestation manifestation, String comment, int rating) {
		super();
		this.user = user;
		this.manifestation = manifestation;
		this.comment = comment;
		this.rating = rating;
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

}
