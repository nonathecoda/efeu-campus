package BachelorarbeitAntonia;

public class ChargeInIntervals {

	static int lastChargingMoment = 0;

	public static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot,
			double batteryConsumption, int timeTrackerBeforeExecution) {
		String chargingDecision = "No charging.";

		if ((currentBot.getTimeTracker() - lastChargingMoment) >= 20
				|| currentBot.getSoc() < Data.batteryCapacity * 0.3) {
			try {

				Customer chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);

				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(), chargingStation)
						/ Data.velocity;
				double batteryConsumptionToNextChargingStation = minutesToChargingStation * Data.dechargingSpeed;

				double chargingTime = (Data.chargingGoal - currentBot.getSoc()) / Data.chargingSpeed;

				Methods.charge(currentBot, chargingTime, chargingStation);

				if ((currentBot.getTimeTracker() - lastChargingMoment) >= 20) {
					chargingDecision = "Last charging 20 min ago.";
				} else if (currentBot.getSoc() < Data.batteryCapacity * 0.3) {
					chargingDecision = "SOC under 30%.";
				}

				lastChargingMoment = currentBot.getTimeTracker();
			} catch (Exception e) {
				chargingDecision = "No charging station in reach. Bot is removed.";
			}
		}
		return chargingDecision;
	}

}
