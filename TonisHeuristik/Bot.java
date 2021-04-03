package Dashboard;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Bot {

	int id;
	double soc;
	Customer currentLocation;
	int timeTracker;
	ArrayList<Double> socList;
	int lastChargingMoment;
	int totalExecutionTime;

	// jeder Roboter hat eine ID, eine "Batterie" (=SOC), die momentane Position und
	// wie weit im Tag er schon vorangeschritten ist(=timeTracker)
	public Bot(int id, double soc, Customer currentLocation, int timeTracker, ArrayList<Double> socList,
			int lastChargingMoment, int totalExecutionTime) {
		this.id = id;
		this.soc = soc;
		this.currentLocation = currentLocation;
		this.timeTracker = timeTracker;
		this.socList = socList;
		this.lastChargingMoment = lastChargingMoment;
		this.totalExecutionTime = totalExecutionTime;

	}

	int getLastChargingMoment() {
		return this.lastChargingMoment;
	}

	void setLastChargingMoment(int lastChargingMoment) {
		this.lastChargingMoment = lastChargingMoment;
	}

	ArrayList<Double> getSocList() {
		return this.socList;
	}

	int getTimeTracker() {
		return this.timeTracker;
	}

	void setTimeTracker(int timeTracker) {
		this.timeTracker = timeTracker;
	}

	double getSoc() {
		return this.soc;
	}

	void setSoc(double soc) {
		this.soc = soc;
	}

	int getId() {
		return this.id;
	}

	Customer getLocation() {
		return this.currentLocation;
	}

	void setLocation(Customer currentLocation) {
		this.currentLocation = currentLocation;
	}

	int getTotalExecutionTime() {

		return this.totalExecutionTime;
	}

	void setTotalExecutionTime(int executionTime, int earliest) {
		double idleTime = earliest - executionTime - this.getTimeTracker();
		if (idleTime <= 0) {
			idleTime = 0;
		}

		this.totalExecutionTime = (int) Math.round(executionTime + idleTime);

	}
}
