package Dashboard;

public class Customer {
	double x, y;
	int knotID;
	int[] occupied;

	Customer(double x, double y, int knotID, int[] occupied) {
		this.x = x;
		this.y = y;
		this.knotID = knotID;
		this.occupied = occupied;

	}

	boolean checkIfFree(int timeTracker, double minutesToChargingStation) {
		boolean isFree = false;
		int arrivalTime = timeTracker + (int) Math.round(minutesToChargingStation);

		if (arrivalTime < this.getOccupied()[0] || arrivalTime > this.getOccupied()[1]) {
			isFree = true;
		}

		return isFree;
	}

	int[] getOccupied() {
		return this.occupied;
	}

	void setOccupied(int[] occupied) {
		this.occupied = occupied;
	}

	double getX() {
		double x = this.x;
		return x;
	}

	double getY() {
		double y = this.y;
		return y;
	}

	int getID() {
		int iD = this.knotID;
		return iD;
	}

}
