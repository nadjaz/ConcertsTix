package beans;

public class BuyerType {

	public enum TypeBuyer {
		GOLDEN, SILVER, BRONZE
	};

	private TypeBuyer type;
	private int discount;
	private int pointsLimit;

	public BuyerType() {

	}

	public BuyerType(TypeBuyer type, int discount, int pointsLimit) {
		super();
		this.type = type;
		this.discount = discount;
		this.pointsLimit = pointsLimit;
	}

	public TypeBuyer getType() {
		return type;
	}

	public void setType(TypeBuyer type) {
		this.type = type;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPointsLimit() {
		return pointsLimit;
	}

	public void setPointsLimit(int pointsLimit) {
		this.pointsLimit = pointsLimit;
	}

}
