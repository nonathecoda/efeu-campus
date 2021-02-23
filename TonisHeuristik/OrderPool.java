
public class OrderPool {

	KnotPool pickUpID, deliveryID;
	int startingTime;

	// jede order hat einen Pickuppoint, einen Deliverypoint und eine Zeit, zu
	// welcher sie ausgeführt werden soll.
	OrderPool(KnotPool pickUpID, KnotPool deliveryID, int startingTime) {
		this.pickUpID = pickUpID;
		this.deliveryID = deliveryID;
		this.startingTime = startingTime;

	}

	synchronized int getStartingTime() {
		return this.startingTime;
	}

	synchronized KnotPool getPickUpID() {
		return this.pickUpID;
	}

	synchronized KnotPool getDeliveryID() {
		return this.deliveryID;
	}

	/*
	 * synchronized double calculateBatteryConsumption(BotThreadECB bot) { double
	 * overallDistance = MethodsPool.calculateDistance(bot.getLocation(),
	 * this.getPickUpID(), this.getDeliveryID()); double minutesNeeded =
	 * overallDistance / DataECB.velocity; double batteryConsumption = minutesNeeded
	 * * DataECB.dechargingSpeed; return batteryConsumption; }
	 */
}
