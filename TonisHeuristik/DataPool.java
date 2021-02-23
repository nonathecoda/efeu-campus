
public class DataPool {


	static int campusSize = 500;	//Maße des quadratischen Campus in Metern
	static int numberOfCustomers = 10;	//wie viele Kunden befinden sich auf dem Campus?
	static int numberOfBots = 5;	
	static int numberOfOrders = 250;
	static int durationWorkdayInMinutes = 480;	//wie lange dauert der Arbeitstag? (480min = 8h)
	
	static double velocity = 90; // Geschwindigkeit Roboter in m/min, 90 m/min entsprechen 1.5 m/sec
	static double chargingSpeed = 16.6666667; // Wh/minute
	static double dechargingSpeed = 6.6666667; // Wh/Minute
	static double batteryCapacity = 556.0;
	static double chargingGoal = 556.0;	//auf wie viel soll beim Ladevorgang geladen werden?
}
