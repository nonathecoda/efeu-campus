package Dashboard;

public class Order {

	Customer pickUpID, deliveryID;
	int startingTime;

	// jede order hat einen Pickuppoint, einen Deliverypoint und eine Zeit, zu
	// welcher sie ausgeführt werden soll.
	Order(Customer pickUpID, Customer deliveryID, int startingTime) {
		this.pickUpID = pickUpID;
		this.deliveryID = deliveryID;
		this.startingTime = startingTime;

	}

	 int getStartingTime() {
		return this.startingTime;
	}

	 Customer getPickUpID() {
		return this.pickUpID;
	}

	 Customer getDeliveryID() {
		return this.deliveryID;
	}

}
