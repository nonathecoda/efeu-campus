package Dashboard;

public class Order {

	Customer pickUpID, deliveryID;
	int earliest;
	int latest;
	int isTakenByBot;
	boolean delayed;
	int id;

	// jede order hat einen Pickuppoint, einen Deliverypoint und eine Zeit, zu
	// welcher sie ausgeführt werden soll.
	Order(int id, Customer pickUpID, Customer deliveryID, int earliest, int latest, int isTakenByBot, boolean delayed) {
		this.id = id;
		this.pickUpID = pickUpID;
		this.deliveryID = deliveryID;
		this.earliest = earliest;
		this.latest = latest;
		this.isTakenByBot = isTakenByBot;
		this.delayed = delayed;
	}

	int getId() {
		return this.id;
	}
	
	void setId(int id) {
		this.id = id;
	}

	boolean getDelayed() {
		return this.delayed;
	}

	void setDelayed(boolean delayed) {
		this.delayed = delayed;
	}

	int getEarliest() {
		return this.earliest;
	}

	int getLatest() {
		return this.latest;
	}

	void setEarliest(int earliest) {
		this.earliest = earliest;
	}

	int getIsTaken() {
		return this.isTakenByBot;
	}

	void setIsTaken(int isTakenByBot) {
		this.isTakenByBot = isTakenByBot;
	}

	void setLatest(int latest) {
		this.latest = latest;
	}

	Customer getPickUpID() {
		return this.pickUpID;
	}

	Customer getDeliveryID() {
		return this.deliveryID;
	}

}
