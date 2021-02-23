import java.util.concurrent.atomic.AtomicInteger;

public class RoboterPool {

	int id;
	double soc;
	KnotPool currentLocation;
	int timeTracker;

	// jeder Roboter hat eine ID, eine "Batterie" (=SOC), die momentane Position und
	// wie weit im Tag er schon vorangeschritten ist(=timeTracker)
	public RoboterPool(int id, double soc, KnotPool currentLocation, int timeTracker) {
		this.id = id;
		this.soc = soc;
		this.currentLocation = currentLocation;
		this.timeTracker = timeTracker;
	}

	synchronized int getTimeTracker() {
		return this.timeTracker;
	}

	synchronized void setTimeTracker(int timeTracker) {
		this.timeTracker = timeTracker;
	}

	synchronized double getSoc() {
		return this.soc;
	}

	synchronized void setSoc(double soc) {
		this.soc = soc;
	}

	synchronized int getId() {
		return this.id;
	}

	synchronized void setId(int id) {
		this.id = id;
	}

	synchronized KnotPool getLocation() {
		return this.currentLocation;
	}

	synchronized void setLocation(KnotPool currentLocation) {
		this.currentLocation = currentLocation;
	}
}
