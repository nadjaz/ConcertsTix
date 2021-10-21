package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import beans.Comment;
import beans.Comment.StatusComment;
import beans.Manifestation;

public class CommentDAO {

	// kolekcija svih komentara
	private NavigableMap<UUID, Comment> comments = new TreeMap<>();

	public CommentDAO() {

	}

	/**
	 * Returns a Comment for the sent id. Returns Optional empty if the comment with
	 * the sent id isn't found.
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Comment> find(UUID id) {
		if (!comments.containsKey(id)) {
			return Optional.empty();
		}
		return Optional.of(comments.get(id));
	}

	public Collection<Comment> findAll() {
		return comments.values();
	}

	public UUID findLastId() {
		return comments.lastKey();
	}

	/**
	 * Returns a list of all comments with a standby status.
	 * 
	 * @return
	 */
	public Collection<Comment> findAllStandby() {
		Collection<Comment> standbyComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getStatus() == StatusComment.STANDBY) {
				standbyComments.add(comment);
			}
		}
		return standbyComments;
	}

	/**
	 * Returns a list of all comments with approved status. (so that buyer user can
	 * see them on manifestations)
	 * 
	 * @return
	 */
	public Collection<Comment> findAllApproved() {
		Collection<Comment> approvedComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getStatus() == StatusComment.APPROVED) {
				approvedComments.add(comment);
			}
		}
		return approvedComments;
	}

	/**
	 * Returns a list of all comments with approved status, for the sent
	 * manifestation.
	 * 
	 * @param manifestation
	 * @return
	 */
	public Collection<Comment> findApprovedByManifestation(Manifestation manifestation) {
		Collection<Comment> foundComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getManifestation().equals(manifestation)
					&& comment.getStatus().equals(StatusComment.APPROVED)) {
				foundComments.add(comment);
			}
		}
		return foundComments;
	}

	/**
	 * Create a comment and put it into the comments map.
	 * 
	 * @param comment
	 */
	public void make(Comment comment) {
		UUID id = comment.getId();
		comments.put(id, comment);
	}

	/**
	 * Changes the comment status to approved.
	 * 
	 * @param commentId
	 * @return boolean - true if the comment is now approved, false if comment isn't
	 *         found
	 */
	public boolean approve(UUID commentId) {
		Optional<Comment> comment = find(commentId);
		if (comment.isPresent()) {
			comment.get().setStatus(StatusComment.APPROVED);
			return true;
		}
		return false;
	}

	/**
	 * Changes the comment status to denied.
	 * 
	 * @param commentId
	 * @return boolean - true if the comment is now denied, false if comment isn't
	 *         found
	 */
	public boolean deny(UUID commentId) {
		Optional<Comment> comment = find(commentId);
		if (comment.isPresent()) {
			comment.get().setStatus(StatusComment.DENIED);
			return true;
		}
		return false;
	}

}
