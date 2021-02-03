package testYourCode;
import java.util.ArrayList;

public class ServiceBot {

	int iD;
	double soc;
	Knot currentLocation;
	
	double charge (double soc, double time) {
		double newSoc = 1.0;
		try {
			Thread.sleep((long) (time));
			
		} catch (Exception e) {
			System.out.println("Fehler in Charge-Methode.");
		}
		
		return newSoc;
	}

	public ServiceBot(int iD, double soc, Knot currentLocation) {
		this.iD = iD;
		this.soc = soc;
		this.currentLocation = currentLocation;

	}

	int getID() {
		int iD = this.iD;
		return iD;
	}

	double getSoc() {
		double soc = this.soc;
		return soc;
	}

	void setSoc(double soc) {
		this.soc = soc;
	}

	Knot getLocation() {	
		return this.currentLocation;
	}

	void setLocation(Knot currentLocation) {
		this.currentLocation = currentLocation;
	}
}
