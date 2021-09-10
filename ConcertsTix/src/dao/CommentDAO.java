package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableMap;
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
	 * Vraæa komentar za prosleðene parametre. Vraæa null ako komentar ne postoji
	 * 
	 * @param id
	 * @return
	 */
	public Comment find(UUID id) {
		if (!comments.containsKey(id)) {
			return null;
		}
		Comment comment = comments.get(id);
		return comment;
	}

	public Collection<Comment> findAll() {
		return comments.values();
	}

	public UUID findLastId() {
		return comments.lastKey();
	}

	// vraca listu svih standby komentara
	public Collection<Comment> findAllStandby() {
		Collection<Comment> standbyComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getStatus() == StatusComment.STANDBY) {
				standbyComments.add(comment);
			}
		}
		return standbyComments;
	}

	// vraca listu svih approved komentara
	// kako bi kupac mogao da ih vidi na manifestacijama
	public Collection<Comment> findAllApproved() {
		Collection<Comment> approvedComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getStatus() == StatusComment.APPROVED) {
				approvedComments.add(comment);
			}
		}
		return approvedComments;
	}

	// vraca listu svih komentara (koji su approved) za poslatu manifestaciju
	public Collection<Comment> findByManifestation(Manifestation manifestation) {
		Collection<Comment> foundComments = new ArrayList<>();
		for (Comment comment : comments.values()) {
			if (comment.getManifestation().equals(manifestation)
					&& comment.getStatus().equals(StatusComment.APPROVED)) {
				foundComments.add(comment);
			}
		}
		return foundComments;
	}

	// put the just made comment into comments map
	public void make(Comment comment) {
		UUID id = comment.getId();
		comments.put(id, comment);
	}

	// menja status komentara u approve
	public boolean approve(UUID commentId) {
		Comment comment = find(commentId);
		if (comment != null) {
			comment.setStatus(StatusComment.APPROVED);
			return true;
		}
		return false;
	}

}
