package Dashboard;

public class ChargeInIntervals {

	public static String chargingHeuristic(Order currentOrder, Order nextOrder, Bot currentBot,
			double batteryConsumption, int timeTrackerBeforeExecution) {
		String chargingDecision = "No charging.";

		if ((currentBot.getTimeTracker() - currentBot.getLastChargingMoment()) >= DataDashboard.getChargingFrequency()
				|| currentBot.getSoc() < DataDashboard.batteryCapacity * 0.3) {
			try {

				Customer chargingStation = Methods.chooseChargingStation(currentBot, nextOrder);

				double minutesToChargingStation = Methods.calculateDistance(currentBot.getLocation(), chargingStation)
						/ DataDashboard.velocity;
				double batteryConsumptionToNextChargingStation = minutesToChargingStation * DataDashboard.dechargingSpeed;

				double chargingTime = (DataDashboard.getChargingGoal() - currentBot.getSoc()) / DataDashboard.chargingSpeed;

				Methods.charge(currentBot, chargingTime, chargingStation);

				if ((currentBot.getTimeTracker() - currentBot.getLastChargingMoment()) >= DataDashboard.getChargingFrequency()) {
					chargingDecision = "Last charging " + DataDashboard.getChargingFrequency() + " min ago.";
				} else if (currentBot.getSoc() < DataDashboard.batteryCapacity * 0.3) {
					chargingDecision = "SOC under 30%.";
				}

				currentBot.setLastChargingMoment(currentBot.getTimeTracker());
			} catch (Exception e) {
				chargingDecision = "No charging station in reach. Bot is removed.";
			}
		}
		return chargingDecision;
	}

}
