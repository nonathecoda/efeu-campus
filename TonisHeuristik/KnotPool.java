
public class KnotPool {
	double x, y, distanceToKnotInMinutes;
	int knotID;
	boolean availableForCharging;

	KnotPool(double x, double y, int knotID, double distanceToKnotInMinutes, boolean availableForCharging) {
		this.x = x;
		this.y = y;
		this.knotID = knotID;

		this.distanceToKnotInMinutes = distanceToKnotInMinutes;
		this.availableForCharging = availableForCharging;
	}

	double getDistanceToKnot() {
		return this.distanceToKnotInMinutes;
	}

	void setDistanceToKnot(double distanceInMinutes) {
		this.distanceToKnotInMinutes = distanceInMinutes;
	}

	double getX() {
		double x = this.x;
		return x;
	}

	double getY() {
		double y = this.y;
		return y;
	}

	void setID(int ID) {
		this.knotID = ID;
	}

	int getID() {
		int iD = this.knotID;
		return iD;
	}

	void setIsAvailable(boolean availabilty) {
		this.availableForCharging = availabilty;
	}

	boolean getIsAvailable() {
		return this.availableForCharging;
	}
}
