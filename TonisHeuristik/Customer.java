package Dashboard;

public class Customer {
	double x, y;
	int knotID;

	Customer(double x, double y, int knotID) {
		this.x = x;
		this.y = y;
		this.knotID = knotID;

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
