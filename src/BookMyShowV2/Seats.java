package BookMyShowV2;

public class Seats {
	private final String name;
	private final PricingPlan pricingPlan;

	public  Seats(String name, PricingPlan pricingPlan) {
		this.name = name;
		this.pricingPlan = pricingPlan;
	}
	public String getName() {
		return name;
	}

	public PricingPlan getPricingPlan() {
		return pricingPlan;
	}
}
