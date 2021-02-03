package testYourCode;

public class Order {

	Knot pickUpID, deliveryID;
	int startingTime, handlingTime;
	boolean unhandled;

	double calculateDistance(Knot currentLocation, Knot pickUp, Knot delivery) {
		double dist1X = currentLocation.getX() + pickUp.getX();
		double dist1Y = currentLocation.getY() + pickUp.getY();
		double dist1 = (Math.sqrt(Math.pow(dist1X, 2) + Math.pow(dist1Y, 2)));

		double dist2X = pickUp.getX() + delivery.getX();
		double dist2Y = pickUp.getY() + delivery.getY();
		double dist2 = (Math.sqrt(Math.pow(dist2X, 2) + Math.pow(dist2Y, 2)));

		double overallDistance = dist1 + dist2;

		return overallDistance;
	}

	double calculateBatteryConsumption(double overallDistance) {
		double batteryConsumption = 0.5;
		return batteryConsumption;

	}



	Order(Knot pickUpID, Knot deliveryID, int startingTime, int handlingTime, boolean unhandled) {
		this.pickUpID = pickUpID;
		this.deliveryID = deliveryID;
		this.startingTime = startingTime;
		this.handlingTime = handlingTime;
		this.unhandled = unhandled;
	}

	int getStartingTime() {
		return this.startingTime;
	}

	Knot getPickUpID() {
		return this.pickUpID;
	}

	Knot getDeliveryID() {
		return this.deliveryID;
	}

	boolean getUnhandled() {
		return this.unhandled;
	}

	void setUnhandled(boolean unhandled) {
		this.unhandled = unhandled;
	}

}
