package exceptions;

public class TicketAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6065932507054626960L;

	public TicketAlreadyExistsException(String message) {
		super(message);
	}

}
