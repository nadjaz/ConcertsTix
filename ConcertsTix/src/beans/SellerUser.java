package beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SellerUser extends User {

	private List<Manifestation> allManifestations = new ArrayList<>();

	public SellerUser() {
		super();
	}

	public SellerUser(String username, String password, String name, String surname, String gender,
			LocalDate dateOfBirth) {
		super(username, password, name, surname, gender, dateOfBirth, Role.SELLER);
	}

	public List<Manifestation> getAllManifestations() {
		return allManifestations;
	}

	public void setAllManifestations(List<Manifestation> allManifestations) {
		this.allManifestations = allManifestations;
	}

}
